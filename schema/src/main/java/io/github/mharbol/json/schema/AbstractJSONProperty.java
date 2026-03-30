
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.exception.JSONException;

/**
 * AbstractJSONProperty
 */
abstract class AbstractJSONProperty implements JSONSchema {

    private Optional<String> title = Optional.empty(); // this might move to ObjectProperty
    private Optional<String> description = Optional.empty();

    private final PropertyTypeEnum type;

    public AbstractJSONProperty(JSONObject jsonObject, PropertyTypeEnum type) throws JSONSchemaException {
        this.type = type;
        try {
            if (jsonObject.containsKey("title")) {
                title = Optional.of(jsonObject.getString("title"));
            }
            if (jsonObject.containsKey("description")) {
                description = Optional.of(jsonObject.getString("description"));
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse JSON property", e);
        }
    }

    public Optional<String> getTitle() {
        return title;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public PropertyTypeEnum getType() {
        return type;
    }
}
