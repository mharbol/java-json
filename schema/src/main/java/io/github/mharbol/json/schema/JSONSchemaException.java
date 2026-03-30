
package io.github.mharbol.json.schema;

/**
 * JSONSchemaException
 */
public class JSONSchemaException extends RuntimeException {
    public JSONSchemaException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONSchemaException(String message) {
        super(message);
    }
}
