
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONBoolean;
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

    static JSONSchema parseProperty(JSONValue propertyEntry) throws JSONSchemaException {
        if (propertyEntry instanceof JSONBoolean) {
            return new AnyProperty((JSONBoolean) propertyEntry);
        } else if (propertyEntry instanceof JSONObject) {
            JSONObject propertyObject = (JSONObject) propertyEntry;
            if (!propertyObject.containsKey("type")) {
                return new AnyProperty(propertyObject);
            }
            JSONValue typeValue = propertyObject.get("type");
            if (typeValue instanceof JSONArray) {
                throw new JSONSchemaException("CompositeProperty not yet supported"); // TODO, return CompositeProperty
            }
            if (typeValue instanceof JSONString) {
                switch (typeValue.toString()) {
                    case "object":
                        return new ObjectProperty(propertyObject);
                    case "integer":
                        return new IntegerProperty(propertyObject);
                    case "string":
                        return new StringProperty(propertyObject);
                    case "number":
                        return new NumberProperty(propertyObject);
                    case "array":
                        return new ArrayProperty(propertyObject);
                    case "null":
                        return new NullProperty(propertyObject);
                    case "boolean":
                        return new BooleanProperty(propertyObject);
                }
            }
            throw new JSONSchemaException("Got a bad type describing the JSON property type. "
                    + "Expected string, array, or default.");
        } else {
            throw new JSONSchemaException("Got a bad JSON type describing the JSON schema. "
                    + "Expected object or boolean.");
        }
    }
}
