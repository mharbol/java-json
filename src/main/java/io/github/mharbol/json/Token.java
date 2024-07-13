
package io.github.mharbol.json;

/**
 * Token
 */
public class Token {

    // Content-less Tokens
    public static final Token OPEN_BRACE = new Token(TokenTypeEnum.OPEN_BRACE);
    public static final Token CLOSE_BRACE = new Token(TokenTypeEnum.CLOSE_BRACE);
    public static final Token OPEN_BRACKET = new Token(TokenTypeEnum.OPEN_BRACKET);
    public static final Token CLOSE_BRACKET = new Token(TokenTypeEnum.CLOSE_BRACKET);
    public static final Token TRUE = new Token(TokenTypeEnum.TRUE);
    public static final Token FALSE = new Token(TokenTypeEnum.FALSE);
    public static final Token NULL = new Token(TokenTypeEnum.NULL);
    public static final Token COLON = new Token(TokenTypeEnum.COLON);
    public static final Token COMMA = new Token(TokenTypeEnum.COMMA);

    public final TokenTypeEnum tokenType;
    public final String value;

    public Token(TokenTypeEnum tokenTypeEnum, String value) {
        this.tokenType = tokenTypeEnum;
        this.value = value;
    }

    public Token(TokenTypeEnum tokenTypeEnum) {
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
