
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * ArrayProperty
 */
class ArrayProperty extends AbstractJSONProperty {

    private Optional<JSONSchema> items = Optional.empty();
    private int minItems = -1;
    private boolean uniqueItems = false;

    public ArrayProperty(JSONObject arraySchema) throws JSONSchemaException {
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

    public Optional<JSONSchema> getItems() {
        return items;
    }

    public int getMinItems() {
        return minItems;
    }

    public boolean isUniqueItems() {
        return uniqueItems;
    }

    @Override
    public boolean validate(JSONValue value) {
        // TODO Auto-generated method stub
        return (value instanceof JSONArray) && super.validate(value);
    }
}
