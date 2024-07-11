
package io.github.mharbol.json;

/**
 * Token
 */
public class Token {

    public final TokenTypeEnum tokenTypeEnum;
    public final String value;

    public Token(TokenTypeEnum tokenTypeEnum, String value) {
        this.tokenTypeEnum = tokenTypeEnum;
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
        if (this.tokenTypeEnum != o.tokenTypeEnum) {
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
        String ret = this.tokenTypeEnum.toString();
        if (null != value) {
            ret += " - " + value;
        }
        return ret;
    }
}
