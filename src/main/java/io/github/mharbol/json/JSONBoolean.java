
package io.github.mharbol.json;

/**
 * JSONBoolean
 */
public class JSONBoolean extends JSONValue {

    private boolean value;

    public JSONBoolean(boolean value) {
        super(JSONTypeEnum.BOOLEAN);
        this.value = value;
    }
}
