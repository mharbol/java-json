
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONBoolean;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;

/**
 * BooleanProperty
 */
class BooleanProperty extends AbstractJSONProperty {

    public BooleanProperty(JSONObject booleanSchema) {
        super(booleanSchema, PropertyTypeEnum.BOOLEAN);
    }

    @Override
    public boolean validate(JSONValue value) {
        // TODO Auto-generated method stub
        return (value instanceof JSONBoolean);
    }
}
