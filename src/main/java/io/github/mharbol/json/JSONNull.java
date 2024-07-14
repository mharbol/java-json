
package io.github.mharbol.json;

/**
 * JSONNull
 */
public class JSONNull extends JSONValue {

    public static final JSONNull NULL = new JSONNull();

    public JSONNull() {
        super(JSONTypeEnum.NULL);
    }
}
