
package io.github.mharbol.json.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.mharbol.json.exception.TokenizerException;

/**
 * Tokenizer
 */
public class Tokenizer {

    /**
     * Regex matching the JSON definition of a number format.
     */
    protected static final Pattern numberFmtPattern = Pattern
            .compile("^(-)?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][+-]?[0-9]+)?$");

    private final String jsonString;
    private final int len;
    private int charPtr;

    public Tokenizer(String jsonString) {
        this.jsonString = jsonString;
        this.len = jsonString.length();
        this.charPtr = -1;
    }

    public List<Token> tokenize() throws TokenizerException {

        List<Token> tokens = new ArrayList<>();
        progressPointer();

        while (charPtr < len) {
            switch (jsonString.charAt(charPtr)) {
                case '{':
                    tokens.add(Token.OPEN_BRACE);
                    break;
                case '}':
                    tokens.add(Token.CLOSE_BRACE);
                    break;
                case '[':
                    tokens.add(Token.OPEN_BRACKET);
                    break;
                case ']':
                    tokens.add(Token.CLOSE_BRACKET);
                    break;
                case ':':
                    tokens.add(Token.COLON);
                    break;
                case ',':
                    tokens.add(Token.COMMA);
                    break;
                case 't':
                case 'f':
                case 'n':
                    tokens.add(tokenizeTFN());
                    break;
                case '\"':
                    tokens.add(tokenizeString());
                    break;
                default:
                    tokens.add(tokenizeNumber());
                    break;
            }
            progressPointer();
        }

        return tokens;
    }

    /**
     * Progresses charPtr to the closing quote and creates a token with its
     * substring.
     * Does NOT check for string content correctness (for now).
     *
     * @return the correct token
     * @throws TokenizerException if the string cannot be closed
     */
    private Token tokenizeString() throws TokenizerException {
        final int ptrStart = ++charPtr;
        char lastChar = jsonString.charAt(charPtr);
        char currChar = lastChar;
        try {
            while ('\"' != currChar || '\\' == lastChar) {
                lastChar = currChar;
                charPtr++;
                currChar = jsonString.charAt(charPtr);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new TokenizerException("No closing quote for token starting at " + ptrStart, e);
        }

        return new Token(TokenTypeEnum.STRING, jsonString.substring(ptrStart, charPtr));
    }

    /**
     * Progresses charPtr to the end of the number and creates a token with its
     * substring.
     *
     * @return the correct token
     * @throws TokenizerException if the number is malformed
     */

    private Token tokenizeNumber() throws TokenizerException {

        final int ptrStart = charPtr;
        String tokenStr;

        try {
            while (-1 != "0123456789eE-+.".indexOf(jsonString.charAt(charPtr))) {
                charPtr++;
            }

            tokenStr = jsonString.substring(ptrStart, charPtr);

        } catch (StringIndexOutOfBoundsException e) {
            throw new TokenizerException("No end for number token starting at " + ptrStart, e);
        }

        Matcher matcher = numberFmtPattern.matcher(tokenStr);
        if (!matcher.find()) {
            throw new TokenizerException("Number pattern starting at " + ptrStart + " and ending at " + charPtr
                    + " does not match number pattern");
        }
        charPtr--;
        return new Token(TokenTypeEnum.NUMBER, tokenStr);
    }

    /**
     * Progresses charPtr onto the end of the token substring and returns the
     * correct token.
     *
     * @return the correct token
     * @throws TokenizerException if this is a bad identifier
     */
    private Token tokenizeTFN() throws TokenizerException {
        if (jsonString.substring(charPtr, charPtr + 4).equals("true")) {
            charPtr += 3;
            return Token.TRUE;
        }
        if (jsonString.substring(charPtr, charPtr + 4).equals("null")) {
            charPtr += 3;
            return Token.NULL;
        }
        if (jsonString.substring(charPtr, charPtr + 5).equals("false")) {
            charPtr += 4;
            return Token.FALSE;
        }
        throw new TokenizerException("Invalid token at index: " + charPtr);
    }

    private void progressPointer() {
        do {
            charPtr++;
        } while (charPtr < len && Character.isWhitespace(jsonString.charAt(charPtr)));
    }
}
