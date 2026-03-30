
package io.github.mharbol.json.schema;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.exception.JSONException;

/**
 * ObjectProperty
 */
class ObjectProperty extends AbstractJSONProperty {

    private Optional<Map<String, JSONProperty>> properties = Optional.empty();

    public ObjectProperty(JSONObject objectSchema) throws JSONSchemaException {
        super(objectSchema);
        try {
            if (objectSchema.containsKey("properties")) {
                parseSetProperties();
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse Object property", e);
        }
    }

    private void parseSetProperties() throws JSONException {
        Map<String, JSONProperty> propMap = new HashMap<>();
        // TODO
        properties = Optional.of(propMap);
    }

	public Optional<Map<String, JSONProperty>> getProperties() {
		return properties;
	}
}
