
package io.github.mharbol.json;

/**
 * JSONString
 */
public class JSONString extends JSONValue {

    private String value;

    public JSONString(String value) {
        super(JSONTypeEnum.STRING);
        this.value = value;
    }
}
