
package io.github.mharbol.json;

/**
 * Enum to define the types of the JSON values.
 */
public enum JSONTypeEnum {
    NULL,
    OBJECT,
    BOOLEAN,
    NUMBER,
    STRING,
    ARRAY,

    // Numeric subtypes
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BIG_INT,
    BIG_FLOAT,
    SCIENTIFIC,
}
