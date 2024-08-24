
package io.github.mharbol.json;

/**
 * JSONBoolean
 */
public class JSONBoolean implements JSONValue {

    private boolean value;

    public JSONBoolean(boolean value) {
        this.value = value;
    }

    public boolean get() {
        return value;
    }

    @Override
    public String serialize() {
        return value ? "true" : "false";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof JSONBoolean)) {
            return false;
        }
        return ((JSONBoolean) o).value == this.value;
    }
}
