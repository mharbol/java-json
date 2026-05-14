
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONBoolean;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;

/**
 * UntypedSchema
 */
class UntypedSchema extends AbstractJSONSchema {

    private boolean value = true;

    public UntypedSchema(JSONObject anySchema) {
        super(anySchema, PropertyTypeEnum.ANY);
    }

    public UntypedSchema(JSONBoolean anySchema) {
        super(PropertyTypeEnum.ANY);
        this.value = anySchema.get();
    }

    @Override
    public boolean validate(JSONValue value) {
        return super.validate(value) && this.value;
    }
}
