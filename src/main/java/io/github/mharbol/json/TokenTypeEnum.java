
package io.github.mharbol.json;

public enum TokenTypeEnum {

    OPEN_BRACE("{"),
    CLOSE_BRACE("}"),
    OPEN_BRACKET("["),
    CLOSE_BRACKET("]"),
    COLON(":"),
    COMMA(","),
    STRING("STRING"),
    NUMBER("NUMBER"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    NULL("NULL");

    String name;

    private TokenTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
