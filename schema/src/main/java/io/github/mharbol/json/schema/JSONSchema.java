
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

    static JSONSchema parseSchema(JSONValue schemaEntry) throws JSONSchemaException {
        if (schemaEntry instanceof JSONBoolean) {
            return new UntypedSchema((JSONBoolean) schemaEntry);
        } else if (schemaEntry instanceof JSONObject) {
            JSONObject schemaObject = (JSONObject) schemaEntry;
            if (!schemaObject.containsKey("type")) {
                return new UntypedSchema(schemaObject);
            }
            JSONValue typeValue = schemaObject.get("type");
            if (typeValue instanceof JSONArray) {
                throw new JSONSchemaException("ComposedSchema not yet supported"); // TODO, return ComposedSchema
            } else if (typeValue instanceof JSONString) {
                return parseSingleType(schemaObject, typeValue.toString());
            } else {
                throw new JSONSchemaException("Got a bad JSON type describing the JSON schema type. "
                        + "Expected string, array, or no \"type\".");
            }
        } else {
            throw new JSONSchemaException("Got a bad JSON type describing the JSON schema. "
                    + "Expected object or boolean.");
        }
    }

    static JSONSchema parseSingleType(JSONObject schemaObject, String schemaType) throws JSONSchemaException {
        switch (schemaType) {
            case "object":
                return new ObjectSchema(schemaObject);
            case "integer":
                return new IntegerSchema(schemaObject);
            case "string":
                return new StringSchema(schemaObject);
            case "number":
                return new NumberSchema(schemaObject);
            case "array":
                return new ArraySchema(schemaObject);
            case "null":
                return new NullSchema(schemaObject);
            case "boolean":
                return new BooleanSchema(schemaObject);
            default:
                throw new JSONSchemaException("Got a bad string describing the JSON property type. "
                        + "Expected \"object\", \"integer\", \"string\", \"number\", \"array\", "
                        + "\"null\", or \"boolean\".");
        }
    }
}
