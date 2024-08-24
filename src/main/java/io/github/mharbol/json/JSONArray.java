
package io.github.mharbol.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * JSONArray
 */
public class JSONArray implements JSONValue, Iterable<JSONValue> {

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

    public Iterator<JSONValue> iterator() {
        return items.iterator();
    }

    public JSONValue get(int index) {
        return items.get(index);
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        Iterator<JSONValue> iter = iterator();
        while (iter.hasNext()) {
            builder.append(iter.next().serialize());
            if (iter.hasNext()) {
                builder.append(',');
            }
        }
        builder.append(']');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof JSONArray)) {
            return false;
        }
        JSONArray other = (JSONArray) o;
        return other.items.equals(this.items);
    }
}
