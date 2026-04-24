
package io.github.mharbol.json.schema;

import java.util.List;
import java.util.Optional;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * AbstractJSONProperty
 */
abstract class AbstractJSONProperty implements JSONSchema {

    private Optional<String> title = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<String> id = Optional.empty();
    private Optional<String> schema = Optional.empty();

    // Validation keywords
    private final PropertyTypeEnum type;
    private Optional<List<JSONValue>> enumKeyword = Optional.empty();
    private Optional<JSONValue> constKeyword = Optional.empty();

    public AbstractJSONProperty(PropertyTypeEnum type) {
        this.type = type;
    }

    public AbstractJSONProperty(JSONObject jsonObject, PropertyTypeEnum type) throws JSONSchemaException {
        this(type);
        try {
            if (jsonObject.containsKey("$schema")) {
                schema = Optional.of(jsonObject.getString("$schema"));
            }
            if (jsonObject.containsKey("$id")) {
                id = Optional.of(jsonObject.getString("$id"));
            }
            if (jsonObject.containsKey("title")) {
                title = Optional.of(jsonObject.getString("title"));
            }
            if (jsonObject.containsKey("description")) {
                description = Optional.of(jsonObject.getString("description"));
            }
            if (jsonObject.containsKey("enum")) {
                enumKeyword = Optional.of(jsonObject.getArray("enum"));
            }
            if (jsonObject.containsKey("const")) {
                constKeyword = Optional.of(jsonObject.get("const"));
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse JSON property", e);
        }
    }

    @Override
    public boolean validate(JSONValue value) {
        return (constKeyword.isEmpty() || constKeyword.get().equals(value)) &&
                (enumKeyword.isEmpty() || enumKeyword.get().stream().anyMatch(e -> e.equals(value)));
    }

    @Override
    public Optional<String> getId() {
        return id;
    }

    @Override
    public Optional<String> getSchema() {
        return schema;
    }

    @Override
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

    public Optional<List<JSONValue>> getEnumKeyword() {
        return enumKeyword;
    }

    public Optional<JSONValue> getConstKeyword() {
        return constKeyword;
    }
}
