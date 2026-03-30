
package io.github.mharbol.json.schema;

import java.util.Map;
import java.util.Optional;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.exception.JSONException;

/**
 * Schema
 */
public class Schema extends ObjectProperty {

    private Optional<String> id = Optional.empty();
    private Optional<String> schema = Optional.empty();
    private Optional<Map<String, JSONProperty>> properties = Optional.empty();

    public Schema(JSONObject schemaObject) throws JSONSchemaException {
        this(schemaObject, false);
    }

    private Schema(JSONObject schemaObject, boolean trustedSchema) throws JSONSchemaException {
        super(schemaObject);
        try {
            if (schemaObject.containsKey("$schema")) {
                schema = Optional.of(schemaObject.getString("$schema"));
            }
            if (schemaObject.containsKey("$id")) {
                id = Optional.of(schemaObject.getString("$id"));
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse Object property", e);
        }
    }

    public Optional<String> getId() {
        return id;
    }

    public Optional<String> getSchema() {
        return schema;
    }
}
