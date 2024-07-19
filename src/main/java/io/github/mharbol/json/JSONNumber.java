
package io.github.mharbol.json;

/**
 * JSONNumber
 */
public class JSONNumber implements JSONValue {

    private Number value;

    public JSONNumber(Number value) {
        this.value = value;
    }

    protected JSONNumber(String stringRepr) {
        final int decIdx = stringRepr.indexOf('.');
        final int sciIdx = stringRepr.toLowerCase().indexOf('e');
        // TODO Handle NumberFormatException (long, BigInt, BigDec)
        if (-1 == decIdx && -1 == sciIdx) {
            this.value = Integer.parseInt(stringRepr);
        } else {
            this.value = Double.valueOf(stringRepr);
        }
    }
}
