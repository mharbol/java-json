
package io.github.mharbol.json;

/**
 * JSONString
 */
public class JSONString implements JSONValue {

    /**
     * The underlying {@link String} value of this JSONString.
     */
    private String value;

    public JSONString(String value) {
        this.value = value;
    }

    @Override
    public String serialize() {
        return new StringBuilder()
                .append('"')
                .append(value)
                .append('"')
                .toString();
    }

    @Override
    public String toString() {
        return value; // TODO make this whole class better
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof JSONString)) {
            return false;
        }
        return this.value.equals(((JSONString) o).value);
    }
}
