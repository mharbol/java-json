
package io.github.mharbol.json;

import java.util.Iterator;
import java.util.List;

/**
 * Converts the VERIFIED {@link Token}s into a {@link JSONObject}.
 */
public class Parser {

    private final List<Token> tokens;
    private Iterator<Token> iter;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.iter = this.tokens.iterator();
    }

    public JSONObject parseObject() {
        // TODO
        return null;
    }
}
