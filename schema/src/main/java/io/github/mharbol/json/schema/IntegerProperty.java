
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;

/**
 * IntegerProperty
 */
class IntegerProperty extends AbstractJSONProperty {
    public IntegerProperty(JSONObject integerSchema) throws JSONSchemaException {
        super(integerSchema, PropertyTypeEnum.INTEGER);
    }
}
