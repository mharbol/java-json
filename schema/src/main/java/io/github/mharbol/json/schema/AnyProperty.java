
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;

/**
 * AnyProperty
 */
class AnyProperty extends AbstractJSONProperty {

    public AnyProperty(JSONObject anySchema) {
        // TODO update to accept the boolean schemas
        super(anySchema, PropertyTypeEnum.ANY);
    }

    @Override
    public boolean validate(JSONValue value) {
        return true;
    }
}
