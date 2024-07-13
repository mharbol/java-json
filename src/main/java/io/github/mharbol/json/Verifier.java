
package io.github.mharbol.json;

import java.util.List;

/**
 * Class to check lexical correctness of the JSON contents in accordance with JSON.org.
 * This will make building the final JSON object tree much easier.
 */
public class Verifier {

    private final List<Token> tokens;
    private final int idx;

    public Verifier(List<Token> tokens) {
        this.tokens = tokens;
        this.idx = 0;
    }
}
