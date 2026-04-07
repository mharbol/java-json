
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;
import io.github.mharbol.json.JSONValue;

/**
 * JSONSchema
 */
public interface JSONSchema {

    Optional<String> getDescription();

    PropertyTypeEnum getType();

    boolean validate(JSONValue value);

    Optional<String> getId();

    Optional<String> getSchema();

    Optional<String> getTitle();

    // TODO update for boolean schemas with AnyProperty
    static JSONSchema parseProperty(JSONObject propertyEntry) throws JSONSchemaException {
        if (!propertyEntry.containsKey("type")) {
            return new AnyProperty(propertyEntry);
        }
        JSONValue typeValue = propertyEntry.get("type");
        if (typeValue instanceof JSONArray) {
            // TODO, return CompositeProperty
            throw new JSONSchemaException("CompositeProperty not yet supported");
        }
        if (typeValue instanceof JSONString) {
            switch (typeValue.toString()) {
                case "object":
                    return new ObjectProperty(propertyEntry);
                case "integer":
                    return new IntegerProperty(propertyEntry);
                case "string":
                    return new StringProperty(propertyEntry);
                case "number":
                    return new NumberProperty(propertyEntry);
                case "array":
                    return new ArrayProperty(propertyEntry);
                case "null":
                    return new NullProperty(propertyEntry);
                case "boolean":
                    return new BooleanProperty(propertyEntry);
            }
        }
        throw new JSONSchemaException("Got a bad type describing the JSON property type. "
                + "Expected string, array, or default.");
    }
}
