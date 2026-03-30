
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.exception.JSONException;

/**
 * AbstractJSONProperty
 */
public class AbstractJSONProperty implements JSONProperty {

    private Optional<String> title = Optional.empty();
    private Optional<String> description = Optional.empty();

    public AbstractJSONProperty(JSONObject jsonObject) throws JSONSchemaException {
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

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getTitle() {
        return title;
    }

}
