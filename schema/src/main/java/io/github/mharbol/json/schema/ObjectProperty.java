
package io.github.mharbol.json.schema;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;
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

    private Optional<Map<String, JSONProperty>> properties = Optional.empty();

    public ObjectProperty(JSONObject objectSchema) throws JSONSchemaException {
        super(objectSchema, PropertyTypeEnum.OBJECT);
        try {
            if (objectSchema.containsKey("properties")) {
                parseSetProperties(objectSchema.getObject("properties"));
            }
        } catch (JSONException | ClassCastException e) {
            // TODO, rework to get better error from properties parser
            throw new JSONSchemaException("Could not parse Object property", e);
        }
    }

    /**
     * Parse the "properties" section of this object schema.
     * 
     * @param propertiesObject {@link JSONObject} listing the properties for this
     *                         ObjectProperty
     * @throws JSONException
     */
    private void parseSetProperties(JSONObject propertiesObject) throws JSONException, JSONSchemaException {
        // TODO, better error from bad JSONValue type (throw from stream if possible)
        Map<String, JSONProperty> propMap = propertiesObject.stream()
                .map(kvPair -> new AbstractMap.SimpleEntry<>(kvPair.getKey(),
                        parseProperty((JSONObject) kvPair.getValue())))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        if (!propMap.isEmpty()) {
            this.properties = Optional.of(propMap);
        }
    }

    private JSONProperty parseProperty(JSONObject propertyEntry) throws JSONSchemaException {
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
                // TODO, rest of the types
                case "composite":
                case "null":
                case "boolean":
                case "array":
                case "number":
                case "string":
                case "any":
            }
        }
        throw new JSONSchemaException(
                "Got a bad type describing the JSON property type. Expected string, array, or default.");
    }

    public Optional<Map<String, JSONProperty>> getProperties() {
        return properties;
    }
}
