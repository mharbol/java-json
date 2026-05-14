
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;

/**
 * IntegerSchema
 */
class IntegerSchema extends NumberSchema {
    // TODO: all with int
    public IntegerSchema(JSONObject integerSchema) throws JSONSchemaException {
        super(integerSchema, PropertyTypeEnum.INTEGER);
    }
}
