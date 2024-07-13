
package io.github.mharbol.json;

import java.util.ArrayList;
import java.util.List;

/**
 * JSONArray
 */
public class JSONArray extends JSONValue {

    private List<JSONValue> items;

    public JSONArray() {
        super(JSONTypeEnum.ARRAY);
        this.items = new ArrayList<>();
    }
}
