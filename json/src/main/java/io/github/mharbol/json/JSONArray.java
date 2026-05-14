
package io.github.mharbol.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JSONArray
 */
public class JSONArray implements JSONValue, Iterable<JSONValue> {

    // TODO lots of javadoc

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

    public List<JSONValue> getItems() {
        return this.items;
    }

    public Stream<JSONValue> stream() {
        return this.items.stream();
    }

    @Override
    public String serialize() {
        return new StringBuilder()
                .append('[')
                .append(items.stream()
                        .map(item -> item.serialize())
                        .collect(Collectors.joining(",")))
                .append(']')
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        // TODO update and test this method DEFINITELY!!!
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
