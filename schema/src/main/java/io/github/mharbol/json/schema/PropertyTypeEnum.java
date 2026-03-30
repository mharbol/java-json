
package io.github.mharbol.json.schema;

/**
 * PropertyTypeEnum
 */
enum PropertyTypeEnum {

    COMPOSITE("composite"), // this one will be tricky...
    NULL("null"),
    BOOLEAN("boolean"),
    OBJECT("object"),
    ARRAY("array"),
    NUMBER("number"),
    STRING("string"),
    INTEGER("integer"),
    ANY("any");

    private PropertyTypeEnum(String name) {
        this.name = name;
    }

    String name;

    @Override
    public String toString() {
        return this.name;
    }
}
