
package io.github.mharbol.json.schema;

import io.github.mharbol.json.JSONObject;

/**
 * Schema
 */
public class Schema extends ObjectProperty {

    public Schema(JSONObject schemaObject) {
        this(schemaObject, false);
    }

    private Schema(JSONObject schemaObject, boolean trustedSchema) {
        super(schemaObject);
    }
}
