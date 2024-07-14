
package io.github.mharbol.json;

import java.util.HashMap;
import java.util.Map;

/**
 * JSONObject
 */
public class JSONObject extends JSONValue {

    private Map<String, JSONValue> items;

    public JSONObject() {
        super(JSONTypeEnum.OBJECT);
        this.items = new HashMap<>();
    }

    public JSONValue put(String key, JSONValue value) {
        if (null == value) {
            return items.put(key, JSONNull.NULL);
        } else {
            return items.put(key, value);
        }
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
}
