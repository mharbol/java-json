
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONValue;

/**
 * JSONSchema
 */
interface JSONSchema {

    Optional<String> getDescription();
    PropertyTypeEnum getType();
    boolean validate(JSONValue value);
}
