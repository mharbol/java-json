
package io.github.mharbol.json;

/**
 * Base value for the JSON object tree.
 */
public interface JSONValue {

    /**
     * Serialize this {@link JSONValue} to its {@link String} value in JSON.
     * 
     * @return the {@link String} representation of this {@link JSONValue}
     */
    String serialize();
}
