
package io.github.mharbol.json;

import java.util.List;

/**
 * Class to check lexical correctness of the JSON contents in accordance with
 * JSON.org. This will make building the final JSON object tree much easier.
 */
public class Verifier {

    private final List<Token> tokens;
    private final int len;
    private int idx;

    public Verifier(List<Token> tokens) {
        this.tokens = tokens;
        this.idx = 0;
        this.len = this.tokens.size();
    }

    public void verify() throws VerifierException {
        verifyObject();
        if (idx < len) {
            throw new VerifierException("Malformed JSON data. Information after root object declaration.");
        }
    }

    private void verifyObject() throws VerifierException {
        try {
            throwIfNot(TokenTypeEnum.OPEN_BRACE);
            idx++;
            if (TokenTypeEnum.CLOSE_BRACE == tokens.get(idx).tokenType) {
                idx++;
                return;
            }
            while (true) {
                throwIfNot(TokenTypeEnum.STRING);
                idx++;
                throwIfNot(TokenTypeEnum.COLON);
                idx++;
                verifyValue();
                if (TokenTypeEnum.CLOSE_BRACE == tokens.get(idx).tokenType) {
                    idx++;
                    return;
                }
                throwIfNot(TokenTypeEnum.COMMA);
                idx++;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new VerifierException("Malformed JSON data. Ran out of tokens while verifying Object.", e);
        }
    }

    private void verifyValue() throws VerifierException {
        switch (tokens.get(idx).tokenType) {
            case NUMBER:
            case NULL:
            case TRUE:
            case FALSE:
            case STRING:
                idx++;
                break;
            case OPEN_BRACE:
                verifyObject();
                break;
            case OPEN_BRACKET:
                verifyArray();
                break;
            default:
                throw new VerifierException("Malformed JSON data. Expected to find a value after key.");
        }
    }

    private void verifyArray() throws VerifierException {
        throwIfNot(TokenTypeEnum.OPEN_BRACKET);
        idx++;
        if (TokenTypeEnum.CLOSE_BRACKET == tokens.get(idx).tokenType) {
            idx++;
            return;
        }
        while (true) {
            verifyValue();
            if (TokenTypeEnum.CLOSE_BRACKET == tokens.get(idx).tokenType) {
                idx++;
                return;
            }
            throwIfNot(TokenTypeEnum.COMMA);
            idx++;
        }
    }

    private void throwIfNot(TokenTypeEnum expectedType) throws VerifierException {
        if (expectedType != tokens.get(idx).tokenType) {
            throw new VerifierException("Malformed JSON data. Expected " + expectedType.toString() + " for token index "
                    + idx + ". Got " + tokens.get(idx).toString());
        }
    }
}
