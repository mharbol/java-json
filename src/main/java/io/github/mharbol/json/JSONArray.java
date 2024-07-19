
package io.github.mharbol.json;

import java.util.ArrayList;
import java.util.List;

/**
 * JSONArray
 */
public class JSONArray implements JSONValue {

    private List<JSONValue> items;

    public JSONArray() {
        this.items = new ArrayList<>();
    }

    public int size() {
        return items.size();
    }

    public boolean add(JSONValue item) {
        if (null == item) {
            return items.add(JSONNull.NULL);
        } else {
            return items.add(item);
        }
    }

    public boolean add(int item) {
        return items.add(new JSONNumber(item));
    }

    public boolean add(long item) {
        return items.add(new JSONNumber(item));
    }

    public boolean add(float item) {
        return items.add(new JSONNumber(item));
    }

    public boolean add(double item) {
        return items.add(new JSONNumber(item));
    }

    public boolean add(boolean item) {
        return items.add(new JSONBoolean(item));
    }

    public boolean add(String item) {
        return items.add(new JSONString(item));
    }
}
