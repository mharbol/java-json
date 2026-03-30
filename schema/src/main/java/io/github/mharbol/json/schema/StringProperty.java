
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;
import io.github.mharbol.json.JSONValue;

/**
 * StringProperty
 */
class StringProperty extends AbstractJSONProperty {

    public StringProperty(JSONObject stringSchema) throws JSONSchemaException {
        super(stringSchema, PropertyTypeEnum.STRING);
    }

    @Override
    public boolean validate(JSONValue value) {
        return (value instanceof JSONString);
    }
}
