
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONBoolean;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;

/**
 * AnyProperty
 */
class AnyProperty extends AbstractJSONProperty {

    private boolean value = true;

    public AnyProperty(JSONObject anySchema) {
        super(anySchema, PropertyTypeEnum.ANY);
    }

    public AnyProperty(JSONBoolean anySchema) {
        super(PropertyTypeEnum.ANY);
        this.value = anySchema.get();
    }

    @Override
    public boolean validate(JSONValue value) {
        return this.value;
    }
}
