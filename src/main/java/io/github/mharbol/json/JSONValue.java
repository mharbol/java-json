
package io.github.mharbol.json;

/**
 * Base value for the JSON object tree.
 */
public abstract class JSONValue {

    protected JSONTypeEnum type;

    protected JSONValue(JSONTypeEnum type) {
        this.type = type;
    }
}
