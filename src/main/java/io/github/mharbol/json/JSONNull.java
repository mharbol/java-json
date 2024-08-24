
package io.github.mharbol.json;

/**
 * JSONNull
 */
public class JSONNull implements JSONValue {

    public static final JSONNull NULL = new JSONNull();

    public JSONNull() {}

    @Override
    public String serialize() {
        return "null";
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || !(o instanceof JSONNull)) {
            return false;
        }
        return true;
    }
}
