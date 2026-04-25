
package io.github.mharbol.json.schema;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;

/**
 * ObjectProperty
 */
class ObjectProperty extends AbstractJSONProperty {

    // Validation keywords
    private Optional<Map<String, JSONSchema>> properties = Optional.empty();
    private Optional<List<String>> required = Optional.empty();
    private int minProperties = 0;
    private int maxProperties = Integer.MAX_VALUE;
    private Optional<Map<String, List<String>>> dependentRequired = Optional.empty();

    public ObjectProperty(JSONObject objectSchema) throws JSONSchemaException {
        super(objectSchema, PropertyTypeEnum.OBJECT);
        try {
            if (objectSchema.containsKey("properties")) {
                parseSetProperties(objectSchema.getObject("properties"));
            }
            if (objectSchema.containsKey("required")) {
                parseSetRequired(objectSchema.getArray("required"));
            }
            if (objectSchema.containsKey("dependentRequired")) {
                parseSetDependentRequired(objectSchema.getObject("dependentRequired"));
            }
            if (objectSchema.containsKey("minProperties")) {
                this.minProperties = objectSchema.getNumber("minProperties").intValue();
                if (0 > this.minProperties) {
                    throw new JSONSchemaException("minProperties must be non-negative");
                }
            }
            if (objectSchema.containsKey("maxProperties")) {
                this.maxProperties = objectSchema.getNumber("maxProperties").intValue();
                if (0 > this.maxProperties) {
                    throw new JSONSchemaException("maxProperties must be non-negative");
                }
            }
        } catch (JSONException e) {
            throw new JSONSchemaException("Could not parse Object property", e);
        }
    }

    @Override
    public boolean validate(JSONValue value) {
        if (!(value instanceof JSONObject) || !super.validate(value)) {
            return false;
        } else {
            JSONObject jsonObject = (JSONObject) value;
            return validateMinMaxProps(jsonObject) &&
                    validateRequired(jsonObject) &&
                    validateDependentRequired(jsonObject) &&
                    validateProperties(jsonObject);
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
                        JSONSchema.parseProperty((JSONObject) kvPair.getValue())))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        if (!propMap.isEmpty()) {
            this.properties = Optional.of(propMap);
        }
    }

    private void parseSetRequired(List<JSONValue> requiredArray) {
        List<String> requiredList = requiredArray.stream()
                .map(v -> ((JSONString) v).toString())
                .distinct()
                .collect(Collectors.toList());
        if (requiredArray.size() != requiredList.size()) {
            throw new JSONSchemaException("required list items must be unique");
        }
        if (!requiredList.isEmpty()) {
            required = Optional.of(requiredList);
        }
    }

    private void parseSetDependentRequired(JSONObject dependentRequiredObject) throws JSONSchemaException {
        throw new JSONSchemaException("dependentRequired not yet implimented"); // TODO
    }

    private boolean validateRequired(JSONObject jsonObject) {
        if (required.isPresent()) {
            return required.get()
                    .stream()
                    .allMatch(key -> jsonObject.containsKey(key));
        }
        return true;
    }

    private boolean validateDependentRequired(JSONObject jsonObject) {
        // TODO
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

    private boolean validateMinMaxProps(JSONObject jsonObject) {
        final int numProps = jsonObject.keySet().size();
        return numProps >= minProperties && numProps <= maxProperties;
    }

    public Optional<List<String>> getRequired() {
        return required;
    }

    public Optional<Map<String, JSONSchema>> getProperties() {
        return properties;
    }

    public int getMinProperties() {
        return minProperties;
    }

    public int getMaxProperties() {
        return maxProperties;
    }

    public Optional<Map<String, List<String>>> getDependentRequired() {
        return dependentRequired;
    }
}
