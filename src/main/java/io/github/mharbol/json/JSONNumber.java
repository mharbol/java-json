
package io.github.mharbol.json;

/**
 * JSONNumber
 */
public class JSONNumber extends JSONValue {

    private Object value;
    private NumericSubtype subtype;

    public JSONNumber(int value) {
        this(NumericSubtype.INT);
        this.value = value;
    }

    public JSONNumber(long value) {
        this(NumericSubtype.LONG);
        this.value = value;
    }

    public JSONNumber(float value) {
        this(NumericSubtype.FLOAT);
        this.value = value;
    }

    public JSONNumber(double value) {
        this(NumericSubtype.DOUBLE);
        this.value = value;
    }

    private JSONNumber(NumericSubtype subtype) {
        super(JSONTypeEnum.NUMBER);
    }

    protected JSONNumber(String stringRepr) {
        super(JSONTypeEnum.NUMBER);
        // TODO
    }

    private static enum NumericSubtype {
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        BIG_INT,
        BIG_FLOAT,
        SCIENTIFIC,
    }
}
