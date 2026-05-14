
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
            return new UntypedSchema((JSONBoolean) propertyEntry);
        } else if (propertyEntry instanceof JSONObject) {
            JSONObject propertyObject = (JSONObject) propertyEntry;
            if (!propertyObject.containsKey("type")) {
                return new UntypedSchema(propertyObject);
            }
            JSONValue typeValue = propertyObject.get("type");
            if (typeValue instanceof JSONArray) {
                throw new JSONSchemaException("ComposedSchema not yet supported"); // TODO, return ComposedSchema
            } else if (typeValue instanceof JSONString) {
                return parseSingleProperty(propertyObject, typeValue.toString());
            } else {
                throw new JSONSchemaException("Got a bad JSON type describing the JSON schema type. "
                        + "Expected string, array, or no \"type\".");
            }
        } else {
            throw new JSONSchemaException("Got a bad JSON type describing the JSON schema. "
                    + "Expected object or boolean.");
        }
    }

    static JSONSchema parseSingleProperty(JSONObject propertyObject, String propertyType) throws JSONSchemaException {
        switch (propertyType) {
            case "object":
                return new ObjectSchema(propertyObject);
            case "integer":
                return new IntegerSchema(propertyObject);
            case "string":
                return new StringSchema(propertyObject);
            case "number":
                return new NumberSchema(propertyObject);
            case "array":
                return new ArraySchema(propertyObject);
            case "null":
                return new NullSchema(propertyObject);
            case "boolean":
                return new BooleanSchema(propertyObject);
            default:
                throw new JSONSchemaException("Got a bad string describing the JSON property type. "
                        + "Expected \"object\", \"integer\", \"string\", \"number\", \"array\", "
                        + "\"null\", or \"boolean\".");
        }
    }
}
