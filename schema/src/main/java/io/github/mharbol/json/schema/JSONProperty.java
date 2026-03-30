
package io.github.mharbol.json.schema;

import java.util.Optional;

/**
 * JSONProperty
 */
interface JSONProperty {

    Optional<String> getDescription();
    PropertyTypeEnum getType();
}
