
package io.github.mharbol.json.schema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * AbstractJSONSchema
 */
abstract class AbstractJSONSchema implements JSONSchema {

    private Optional<String> title = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<String> id = Optional.empty();
    private Optional<String> schema = Optional.empty();

    // Validation keywords
    private final PropertyTypeEnum type; // TODO, getType()
    private Optional<List<JSONValue>> enumKeyword = Optional.empty();
    private Optional<JSONValue> constKeyword = Optional.empty();

    // Logical subschema keywords
    private Optional<List<JSONSchema>> allOf = Optional.empty(); // TODO
    private Optional<List<JSONSchema>> anyOf = Optional.empty(); // TODO
    private Optional<List<JSONSchema>> oneOf = Optional.empty(); // TODO
    private Optional<JSONSchema> not = Optional.empty(); // TODO

    // Conditional subschema keywords
    private Optional<JSONSchema> ifKeyword = Optional.empty();// TODO
    private Optional<JSONSchema> thenKeyword = Optional.empty();// TODO
    private Optional<JSONSchema> elseKeyword = Optional.empty();// TODO
    private Optional<Map<String, JSONSchema>> dependentSchemas = Optional.empty();// TODO (only useful with
                                                                                  // ObjectSchema)

    public AbstractJSONSchema(PropertyTypeEnum type) {
        this.type = type;
    }

    public AbstractJSONSchema(JSONObject jsonObject, PropertyTypeEnum type) throws JSONSchemaException {
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
        return (!constKeyword.isPresent() || constKeyword.get().equals(value)) &&
                (!enumKeyword.isPresent() || enumKeyword.get().stream().anyMatch(e -> e.equals(value)));
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

    public Optional<List<JSONValue>> getEnum() {
        return enumKeyword;
    }

    public Optional<JSONValue> getConst() {
        return constKeyword;
    }

    public Optional<JSONSchema> getIf() {
        return ifKeyword;
    }

    public Optional<JSONSchema> getThen() {
        return thenKeyword;
    }

    public Optional<JSONSchema> getElse() {
        return elseKeyword;
    }

    public Optional<Map<String, JSONSchema>> getDependentSchemas() {
        return dependentSchemas;
    }

    public Optional<List<JSONSchema>> getAllOf() {
        return allOf;
    }

    public Optional<List<JSONSchema>> getAnyOf() {
        return anyOf;
    }

    public Optional<List<JSONSchema>> getOneOf() {
        return oneOf;
    }

    public Optional<JSONSchema> getNot() {
        return not;
    }
}
