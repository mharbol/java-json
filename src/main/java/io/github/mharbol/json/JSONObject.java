
package io.github.mharbol.json;

import java.util.HashMap;
import java.util.Map;

/**
 * JSONObject
 */
public class JSONObject extends JSONValue {

    private Map<String, JSONValue> items;

    public JSONObject() {
        super(JSONTypeEnum.OBJECT);
        this.items = new HashMap<>();
    }
}
