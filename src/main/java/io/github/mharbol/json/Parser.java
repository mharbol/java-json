
package io.github.mharbol.json;

import java.util.Iterator;
import java.util.List;

/**
 * Converts the VERIFIED {@link Token}s into a {@link JSONObject}.
 */
public class Parser {

    private final List<Token> tokens;
    private Iterator<Token> iter;
    private Token currToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.iter = this.tokens.iterator();
    }

    public JSONObject parse() {
        iter.next();
        return parseObject();
    }

    private JSONObject parseObject() {
        JSONObject object = new JSONObject();
        currToken = iter.next(); // string key or close brace
        while (TokenTypeEnum.CLOSE_BRACE != currToken.tokenType) {
            String key = currToken.value;
            iter.next(); // colon
            iter.next(); // value
            object.put(key, parseValue());
            iter.next(); // comma or close brace
        }
        return object;
    }

    private JSONValue parseValue() {
        switch (currToken.tokenType) {
            case TRUE:
                return new JSONBoolean(true);
            case FALSE:
                return new JSONBoolean(false);
            case NULL:
                return JSONNull.NULL;
            case STRING:
                return new JSONString(currToken.value);
            case OPEN_BRACE:
                return parseObject();
            case OPEN_BRACKET:
                return parseArray();
            case NUMBER:
                return parseNumber();
            default:
                return null; // never should get here since the Tokesn are verified
        }
    }

    private JSONArray parseArray() {
        JSONArray array = new JSONArray();
        currToken = iter.next(); // value or close bracket
        while (TokenTypeEnum.CLOSE_BRACKET != currToken.tokenType) {
            array.add(parseValue());
            iter.next(); // comma or close bracket
        }
        return array;
    }

    private JSONNumber parseNumber() {
        // see if the number decimal, float, or scientific
        final int sciIdx = currToken.value.toLowerCase().indexOf('e');
        final int decIdx = currToken.value.indexOf('.');
        if (-1 != sciIdx) {
            return parseSci();
        } else if (-1 != decIdx) {
            return parseDouble();
        } else {
            return parseInt();
        }
    }

    private JSONNumber parseInt() {
        // TODO
        return null;
    }

    private JSONNumber parseDouble() {
        // TODO
        return null;
    }

    private JSONNumber parseSci() {
        // TODO
        return null;
    }
}
