
package io.github.mharbol.json.schema;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * ObjectProperty
 */
class ObjectProperty extends AbstractJSONProperty {

    private Optional<Map<String, JSONSchema>> properties = Optional.empty();
    private Optional<List<String>> required = Optional.empty();

    public ObjectProperty(JSONObject objectSchema) throws JSONSchemaException {
        super(objectSchema, PropertyTypeEnum.OBJECT);
        try {
            if (objectSchema.containsKey("properties")) {
                parseSetProperties(objectSchema.getObject("properties"));
            }
            if (objectSchema.containsKey("required")) {
                parseSetRequired(objectSchema.getArray("required"));
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse Object property", e);
        }
    }

    /**
     * Parse the "properties" section of this object schema.
     * 
     * @param propertiesObject {@link JSONObject} listing the properties for this
     *                         ObjectProperty
     * @throws JSONException
     * @throws JSONSchemaException
     */
    private void parseSetProperties(JSONObject propertiesObject) throws JSONException, JSONSchemaException {

        Map<String, JSONSchema> propMap = propertiesObject.stream()
                .map(kvPair -> new AbstractMap.SimpleEntry<>(kvPair.getKey(),
                        parseProperty((JSONObject) kvPair.getValue())))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        if (!propMap.isEmpty()) {
            this.properties = Optional.of(propMap);
        }
    }

    private void parseSetRequired(List<JSONValue> requiredArray) {
        List<String> requiredList = requiredArray.stream()
                .map(v -> ((JSONString) v).toString())
                .collect(Collectors.toList());
        if (!requiredList.isEmpty()) {
            required = Optional.of(requiredList);
        }
    }

    private JSONSchema parseProperty(JSONObject propertyEntry) throws JSONSchemaException {
        if (!propertyEntry.containsKey("type")) {
            // TODO, return the AnyProperty
            throw new JSONSchemaException("AnyProperty not yet supported");
        }
        JSONValue typeValue = propertyEntry.get("type");
        if (typeValue instanceof JSONArray) {
            // TODO, return CompositeProperty
            throw new JSONSchemaException("CompositeProperty not yet supported");
        }
        if (typeValue instanceof JSONString) {
            switch (typeValue.toString()) {
                case "object":
                    return new ObjectProperty(propertyEntry);
                case "integer":
                    return new IntegerProperty(propertyEntry);
                case "string":
                    return new StringProperty(propertyEntry);
                case "number":
                    return new NumberProperty(propertyEntry);
                case "array":
                    return new ArrayProperty(propertyEntry);
                case "null":
                    // TODO, return the NullProperty
                    throw new JSONSchemaException("NullProperty not yet supported");
                case "boolean":
                    // TODO, return the BooleanProperty
                    throw new JSONSchemaException("BooleanProperty not yet supported");
            }
        }
        throw new JSONSchemaException("Got a bad type describing the JSON property type. "
                + "Expected string, array, or default.");
    }

    public Optional<List<String>> getRequired() {
        return required;
    }

    public Optional<Map<String, JSONSchema>> getProperties() {
        return properties;
    }

    @Override
    public boolean validate(JSONValue value) {
        if (!(value instanceof JSONObject)) {
            return false;
        } else {
            JSONObject jsonObject = (JSONObject) value;
            return validateRequired(jsonObject) && validateProperties(jsonObject);
        }
    }

    private boolean validateRequired(JSONObject jsonObject) {
        if (required.isPresent()) {
            return required.get()
                    .stream()
                    .allMatch(key -> jsonObject.containsKey(key));
        }
        return true;
    }

    private boolean validateProperties(JSONObject jsonObject) {
        if (properties.isPresent()) {
            return properties.get().entrySet()
                    .stream()
                    .filter(kv -> jsonObject.containsKey(kv.getKey()))
                    .allMatch(kv -> kv.getValue().validate(jsonObject.get(kv.getKey())));
        } else {
            return true;
        }
    }
}
