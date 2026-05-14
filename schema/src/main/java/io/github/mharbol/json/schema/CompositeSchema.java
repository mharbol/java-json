
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;

/**
 * CompositeSchema
 */
class CompositeSchema extends AbstractJSONSchema {

    public CompositeSchema(JSONObject jsonObject, PropertyTypeEnum type) throws JSONSchemaException {
        super(jsonObject, PropertyTypeEnum.COMPOSITE); // TODO
    }
}
