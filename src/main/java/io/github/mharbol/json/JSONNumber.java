
package io.github.mharbol.json;

/**
 * JSONNumber
 */
public class JSONNumber extends JSONValue {

    private Object value;
    private JSONTypeEnum  subtype;

    public JSONNumber(int value) {
        this(JSONTypeEnum.INT);
        this.value = value;
    }

    public JSONNumber(long value) {
        this(JSONTypeEnum.LONG);
        this.value = value;
    }

    public JSONNumber(float value) {
        this(JSONTypeEnum.FLOAT);
        this.value = value;
    }

    public JSONNumber(double value) {
        this(JSONTypeEnum.DOUBLE);
        this.value = value;
    }

    private JSONNumber(JSONTypeEnum subtype) {
        super(JSONTypeEnum.NUMBER);
    }
}
