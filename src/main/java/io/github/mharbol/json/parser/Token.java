
package io.github.mharbol.json.parser;

/**
 * Token
 */
public class Token {

    // Content-less Tokens
    protected static final Token OPEN_BRACE = new Token(TokenTypeEnum.OPEN_BRACE);
    protected static final Token CLOSE_BRACE = new Token(TokenTypeEnum.CLOSE_BRACE);
    protected static final Token OPEN_BRACKET = new Token(TokenTypeEnum.OPEN_BRACKET);
    protected static final Token CLOSE_BRACKET = new Token(TokenTypeEnum.CLOSE_BRACKET);
    protected static final Token TRUE = new Token(TokenTypeEnum.TRUE);
    protected static final Token FALSE = new Token(TokenTypeEnum.FALSE);
    protected static final Token NULL = new Token(TokenTypeEnum.NULL);
    protected static final Token COLON = new Token(TokenTypeEnum.COLON);
    protected static final Token COMMA = new Token(TokenTypeEnum.COMMA);

    protected final TokenTypeEnum tokenType;
    protected final String value;

    protected Token(TokenTypeEnum tokenTypeEnum, String value) {
        this.tokenType = tokenTypeEnum;
        this.value = value;
    }

    protected Token(TokenTypeEnum tokenTypeEnum) {
        this(tokenTypeEnum, null);
    }

    @Override
    public boolean equals(Object other) {
        if (null == other || !(other instanceof Token)) {
            return false;
        }
        Token o = (Token) other;
        if (this.tokenType != o.tokenType) {
            return false;
        }
        if (o.value == null) {
            return this.value == null;
        } else {
            return o.value.equals(this.value);
        }
    }

    @Override
    public String toString() {
        String ret = this.tokenType.toString();
        if (null != value) {
            ret += " - " + value;
        }
        return ret;
    }
}
