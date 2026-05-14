
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONNumber;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * NumberSchema
 */
class NumberSchema extends AbstractJSONSchema {

    // Validation keywords
    private Optional<Number> multipleOf = Optional.empty(); // TODO
    private Optional<Number> maximum = Optional.empty(); // TODO
    private Optional<Number> minimum = Optional.empty(); // TODO (with inclusive)

    private boolean isMinExclusive = false;
    private boolean isMaxExclusive = false;

    public NumberSchema(JSONObject numberSchema) throws JSONSchemaException {
        this(numberSchema, PropertyTypeEnum.NUMBER);
        try {
            if (numberSchema.containsKey("exclusiveMinimum")) {
                minimum = Optional.of(numberSchema.getNumber("exclusiveMinimum"));
                isMinExclusive = true;
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse JSON property", e);
        }
    }

    @Override
    public boolean validate(JSONValue value) {
        if (!(value instanceof JSONNumber) || !super.validate(value)) {
            return false;
        } else {
            JSONNumber jsonNumber = (JSONNumber) value;
            return validateMin(jsonNumber);
        }
    }

    private boolean validateMin(JSONNumber jsonNumber) {
        if (minimum.isPresent()) {
            // TODO - make this better and work
            return true;
        } else {
            return true;
        }
    }

    protected NumberSchema(JSONObject numberSchema, PropertyTypeEnum type) throws JSONSchemaException {
        super(numberSchema, type);
    }

    public Optional<Number> getMinimum() {
        return minimum;
    }

    public boolean isMinExclusive() {
        return isMinExclusive;
    }

	public Optional<Number> getMultipleOf() {
		return multipleOf;
	}

	public Optional<Number> getMaximum() {
		return maximum;
	}

	public boolean isMaxExclusive() {
		return isMaxExclusive;
	}
}
