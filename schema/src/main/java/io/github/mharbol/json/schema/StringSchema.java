
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;
import io.github.mharbol.json.JSONValue;

/**
 * StringSchema
 */
class StringSchema extends AbstractJSONSchema {

    // Validation keywords
    private int maxLength = -1; // TODO
    private int minLength = 0; // TODO
    private String pattern = null; // TODO

    public StringSchema(JSONObject stringSchema) throws JSONSchemaException {
        super(stringSchema, PropertyTypeEnum.STRING);
    }

    @Override
    public boolean validate(JSONValue value) {
        // TODO fill out
        return (value instanceof JSONString) && super.validate(value);
    }

	public int getMinLength() {
		return minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getPattern() {
		return pattern;
	}
}
