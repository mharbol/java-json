
package io.github.mharbol.json.parser;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import io.github.mharbol.json.exception.VerifierException;

/**
 * Class to check lexical correctness of the JSON contents in accordance with
 * JSON.org. This will make building the final JSON object tree much easier.
 */
public class Verifier {

    private final List<Token> tokens;
    private Iterator<Token> iter;
    private Token currToken;

    public Verifier(List<Token> tokens) {
        this.tokens = tokens;
        this.iter = this.tokens.iterator();
        this.currToken = Token.NULL;
    }

    public void verify() throws VerifierException {
        try {
            currToken = iter.next();
            verifyObject();
            if (iter.hasNext()) {
                throw new VerifierException("Malformed JSON data. Information after root object declaration.");
            }
        } catch (NoSuchElementException e) {
            throw new VerifierException("Malformed JSON data. Ran out of tokens while verifying data.");
        }
    }

    private void verifyObject() throws VerifierException {
        throwIfNot(TokenTypeEnum.OPEN_BRACE);
        currToken = iter.next();
        if (TokenTypeEnum.CLOSE_BRACE == currToken.tokenType) {
            return;
        }
        while (true) {
            throwIfNot(TokenTypeEnum.STRING);
            currToken = iter.next();
            throwIfNot(TokenTypeEnum.COLON);
            currToken = iter.next();
            verifyValue();
            currToken = iter.next();
            if (TokenTypeEnum.CLOSE_BRACE == currToken.tokenType) {
                return;
            }
            throwIfNot(TokenTypeEnum.COMMA);
            currToken = iter.next();
        }
    }

    private void verifyValue() throws VerifierException {
        switch (currToken.tokenType) {
            case NUMBER:
            case NULL:
            case TRUE:
            case FALSE:
            case STRING:
                break;
            case OPEN_BRACE:
                verifyObject();
                break;
            case OPEN_BRACKET:
                verifyArray();
                break;
            default:
                throw new VerifierException("Malformed JSON data. Expected to find a value after key but got "
                        + currToken.toString() + ".");
        }
    }

    private void verifyArray() throws VerifierException {
        throwIfNot(TokenTypeEnum.OPEN_BRACKET);
        currToken = iter.next();
        if (TokenTypeEnum.CLOSE_BRACKET == currToken.tokenType) {
            return;
        }
        while (true) {
            verifyValue();
            currToken = iter.next();
            if (TokenTypeEnum.CLOSE_BRACKET == currToken.tokenType) {
                return;
            }
            throwIfNot(TokenTypeEnum.COMMA);
            currToken = iter.next();
        }
    }

    private void throwIfNot(TokenTypeEnum expectedType) throws VerifierException {
        if (expectedType != currToken.tokenType) {
            throw new VerifierException("Malformed JSON data. Expected " + expectedType.toString()
                    + " for the current token: " + currToken.toString() + ".");
        }
    }
}
