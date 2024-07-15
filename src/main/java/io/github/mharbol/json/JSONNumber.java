
package io.github.mharbol.json;

/**
 * JSONNumber
 */
public class JSONNumber extends JSONValue {

    private Number value;
    private NumericSubtype subtype;

    public JSONNumber(int value) {
        this(NumericSubtype.INT);
        this.value = value;
    }

    public JSONNumber(long value) {
        this(NumericSubtype.LONG);
        this.value = value;
    }

    public JSONNumber(double value) {
        this(NumericSubtype.DOUBLE);
        this.value = value;
    }

    private JSONNumber(NumericSubtype subtype) {
        super(JSONTypeEnum.NUMBER);
        this.subtype = subtype;
    }

    protected JSONNumber(String stringRepr) {
        super(JSONTypeEnum.NUMBER);
        final int decIdx = stringRepr.indexOf('.');
        final int sciIdx = stringRepr.toLowerCase().indexOf('e');
        // TODO Handle NumberFormatException (long, BigInt, BigDec)
        if (-1 == decIdx && -1 == sciIdx) {
            this.value = Integer.parseInt(stringRepr);
            this.subtype = NumericSubtype.INT;
        } else {
            this.value = Double.valueOf(stringRepr);
            this.subtype = NumericSubtype.DOUBLE;
        }
    }

    private static enum NumericSubtype {
        INT,
        LONG,
        DOUBLE,
        BIG_INT,
    }
}
