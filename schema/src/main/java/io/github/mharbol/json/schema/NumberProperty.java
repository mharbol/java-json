
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONNumber;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * NumberProperty
 */
class NumberProperty extends AbstractJSONProperty {

    private Optional<Number> minimum = Optional.empty();
    private boolean isMinExclusive = false;

    public NumberProperty(JSONObject numberSchema) throws JSONSchemaException {
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

    protected NumberProperty(JSONObject numberSchema, PropertyTypeEnum type) throws JSONSchemaException {
        super(numberSchema, type);
    }

    public Optional<Number> getMinimum() {
        return minimum;
    }

    public boolean isMinExclusive() {
        return isMinExclusive;
    }

    @Override
    public boolean validate(JSONValue value) {
        if (!(value instanceof JSONNumber)) {
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
}
