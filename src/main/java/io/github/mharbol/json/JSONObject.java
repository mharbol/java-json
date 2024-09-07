
package io.github.mharbol.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * JSONObject
 */
public class JSONObject implements JSONValue {

    private Map<String, JSONValue> items;

    public JSONObject() {
        this.items = new HashMap<>();
    }

    public JSONValue put(String key, JSONValue value) {
        if (null == value) {
            return items.put(key, JSONNull.NULL);
        } else {
            return items.put(key, value);
        }
    }

    public JSONValue get(String key) {
        return items.get(key);
    }

    public JSONValue put(String key, int value) {
        return items.put(key, new JSONNumber(value));
    }

    public JSONValue put(String key, long value) {
        return items.put(key, new JSONNumber(value));
    }

    public JSONValue put(String key, float value) {
        return items.put(key, new JSONNumber(value));
    }

    public JSONValue put(String key, double value) {
        return items.put(key, new JSONNumber(value));
    }

    public JSONValue put(String key, boolean value) {
        return items.put(key, new JSONBoolean(value));
    }

    public JSONValue put(String key, String value) {
        return items.put(key, new JSONString(value));
    }

    public Set<String> keySet() {
        return items.keySet();
    }

    @Override
    public boolean equals(Object o) {
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
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        Iterator<String> iter = items.keySet().iterator();
        String key;
        while (iter.hasNext()) {
            key = iter.next();
            builder.append('"').append(key).append('"').append(':');
            builder.append(items.get(key).serialize());
            if (iter.hasNext()) {
                builder.append(',');
            }
        }
        builder.append('}');
        return builder.toString();
    }

    @Override
    public String toString() {
        return serialize();
    }
}
