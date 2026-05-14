
package io.github.mharbol.json.schema;

import java.util.List;
import java.util.Optional;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * ArraySchema
 */
class ArraySchema extends AbstractJSONSchema {

    // Validation keywords
    private int minItems = 0; // TODO
    private int maxItems = -1; // TODO
    private boolean uniqueItems = false; // TODO
    private int maxContains = -1; // TODO
    private int minContains = -1; // TODO

    // Subschema keywords
    private Optional<List<JSONSchema>> prefixItems = Optional.empty(); // TODO
    private Optional<JSONSchema> items = Optional.empty(); // TODO
    private Optional<JSONSchema> contains = Optional.empty(); // TODO

    public ArraySchema(JSONObject arraySchema) throws JSONSchemaException {
        super(arraySchema, PropertyTypeEnum.ARRAY);
        try {
            if (arraySchema.containsKey("minItems")) {
                minItems = arraySchema.getNumber("minItems").intValue(); // TODO, probably some check with max items
            }
            if (arraySchema.containsKey("uniqueItems")) {
                uniqueItems = arraySchema.getBoolean("uniqueItems");
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse JSON property", e);
        }
    }

    @Override
    public boolean validate(JSONValue value) {
        // TODO Auto-generated method stub
        return (value instanceof JSONArray) && super.validate(value);
    }

    public Optional<JSONSchema> getItems() {
        return items;
    }

    public int getMinItems() {
        return minItems;
    }

    public boolean isUniqueItems() {
        return uniqueItems;
    }

    public Optional<List<JSONSchema>> getPrefixItems() {
        return prefixItems;
    }

    public Optional<JSONSchema> getContains() {
        return contains;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public int getMaxContains() {
        return maxContains;
    }

    public int getMinContains() {
        return minContains;
    }
}
