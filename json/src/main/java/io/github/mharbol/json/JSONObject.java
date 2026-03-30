
package io.github.mharbol.json;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.mharbol.json.exception.JSONException;

/**
 * JSONObject
 */
public class JSONObject implements JSONValue {

    // TODO javadoc everywhere
    private Map<String, JSONValue> items;

    public JSONObject() {
        this.items = new HashMap<>();
    }

    public JSONValue put(String key, JSONValue value) {
        if (null == value) {
            return items.put(JSONString.stringToJsonString(key), JSONNull.NULL);
        } else {
            return items.put(JSONString.stringToJsonString(key), value);
        }
    }

    public boolean containsKey(String key) {
        return items.containsKey(JSONString.stringToJsonString(key));
    }

    public JSONValue put(String key, int value) {
        return items.put(JSONString.stringToJsonString(key), new JSONNumber(value));
    }

    public JSONValue put(String key, long value) {
        return items.put(JSONString.stringToJsonString(key), new JSONNumber(value));
    }

    public JSONValue put(String key, float value) {
        return items.put(JSONString.stringToJsonString(key), new JSONNumber(value));
    }

    public JSONValue put(String key, double value) {
        return items.put(JSONString.stringToJsonString(key), new JSONNumber(value));
    }

    public JSONValue put(String key, boolean value) {
        return items.put(JSONString.stringToJsonString(key), new JSONBoolean(value));
    }

    public JSONValue put(String key, String value) {
        return items.put(JSONString.stringToJsonString(key), new JSONString(value));
    }

    public JSONValue get(String key) {
        return items.get(JSONString.stringToJsonString(key));
    }

    public String getString(String key) throws JSONException {
        try {
            return ((JSONString) this.get(key)).toString();
        } catch (ClassCastException | NullPointerException e) {
            throw new JSONException("Cannot get String with key: " + key, e);
        }
    }

    public JSONObject getObject(String key) throws JSONException {
        try {
            return ((JSONObject) this.get(key));
        } catch (ClassCastException | NullPointerException e) {
            throw new JSONException("Cannot get Object with key: " + key, e);
        }
    }

    public Set<String> keySet() {
        return items.keySet()
                .stream()
                .map(JSONString::jsonStringToString)
                .collect(Collectors.toSet());
    }

    public Stream<Entry<String, JSONValue>> stream() {
        return items.entrySet()
                .stream()
                .map(p -> new AbstractMap.SimpleEntry<>(p.getKey().toString(), p.getValue()));
    }

    public Set<Entry<String, JSONValue>> entrySet() {
        return this.stream().collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        // TODO update and test this method
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof JSONObject)) {
            return false;
        }
        JSONObject other = (JSONObject) o;
        return other.items.equals(this.items);
    }

    @Override
    public String serialize() {
        return new StringBuilder()
                .append('{')
                .append(items.entrySet()
                        .stream()
                        .map(kvPair -> "\"" + kvPair.getKey() + "\":" + kvPair.getValue().serialize())
                        .collect(Collectors.joining(",")))
                .append('}')
                .toString();
    }

    @Override
    public String toString() {
        return serialize();
    }
}
