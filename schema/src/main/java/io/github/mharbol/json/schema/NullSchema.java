
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONNull;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;

/**
 * NullSchema
 */
class NullSchema extends AbstractJSONSchema {

    public NullSchema(JSONObject nullSchema) {
        super(nullSchema, PropertyTypeEnum.NULL);
    }

    @Override
    public boolean validate(JSONValue value) {
        return (value instanceof JSONNull) /* && super.validate(value) */;
    }
}
