
package io.github.mharbol.json.parser;

import java.util.Iterator;
import java.util.List;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONBoolean;
import io.github.mharbol.json.JSONNull;
import io.github.mharbol.json.JSONNumber;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;
import io.github.mharbol.json.JSONValue;
import io.github.mharbol.json.exception.JSONException;
import io.github.mharbol.json.exception.TokenizerException;
import io.github.mharbol.json.exception.VerifierException;

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

    public static JSONObject parseJSON(String jsonString) throws JSONException {
        try {
            Tokenizer tokenizer = new Tokenizer(jsonString);
            List<Token> tokens = tokenizer.tokenize();
            Verifier verifier = new Verifier(tokens);
            verifier.verify();
            Parser parser = new Parser(tokens);
            return parser.parse();
        } catch (TokenizerException | VerifierException e) {
            throw new JSONException("Exception while parsing JSON String.", e);
        }
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
