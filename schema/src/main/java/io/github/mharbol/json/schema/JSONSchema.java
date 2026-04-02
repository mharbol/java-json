
package io.github.mharbol.json.schema;

import java.util.Optional;

import io.github.mharbol.json.JSONValue;

/**
 * JSONSchema
 */
public interface JSONSchema {

    Optional<String> getDescription();

    PropertyTypeEnum getType();

    boolean validate(JSONValue value);

    Optional<String> getId();

    Optional<String> getSchema();

    Optional<String> getTitle();
}
