
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
        iter.next(); // open brace
        return parseObject();
    }

    private JSONObject parseObject() {
        JSONObject object = new JSONObject();
        currToken = iter.next(); // string key or close brace
        while (TokenTypeEnum.CLOSE_BRACE != currToken.tokenType) {
            String key = currToken.value;
            iter.next(); // colon
            currToken = iter.next(); // value
            object.put(key, parseValue());
            currToken = iter.next(); // comma or close brace
            if (TokenTypeEnum.COMMA == currToken.tokenType) {
                currToken = iter.next(); // string key or close brace
            }
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
            case NUMBER:
                return new JSONNumber(currToken.value);
            case STRING:
                return new JSONString(currToken.value);
            case OPEN_BRACE:
                return parseObject();
            case OPEN_BRACKET:
                return parseArray();
            default:
                return null; // never should get here since the Tokens are verified
        }
    }

    private JSONArray parseArray() {
        JSONArray array = new JSONArray();
        currToken = iter.next(); // value or close bracket
        while (TokenTypeEnum.CLOSE_BRACKET != currToken.tokenType) {
            array.add(parseValue());
            currToken = iter.next(); // comma or close bracket
            if (TokenTypeEnum.COMMA == currToken.tokenType) {
                currToken = iter.next(); // value or close bracket
            }
        }
        return array;
    }
}
