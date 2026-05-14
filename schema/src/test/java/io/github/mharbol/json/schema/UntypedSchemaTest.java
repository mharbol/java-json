
package io.github.mharbol.json.schema;

import org.junit.Assert;
import org.junit.Test;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONBoolean;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;

/**
 * UntypedSchemaTest
 */
public class UntypedSchemaTest extends TestBase {

    private UntypedSchema cut;

    @Test
    public void testTrueFalseEmpty() {

        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonArray = new JSONArray();
        final JSONString jsonString = new JSONString("");

        // always true with true
        JSONBoolean jsonBoolean = new JSONBoolean(true);
        cut = new UntypedSchema(jsonBoolean);
        Assert.assertTrue(cut.validate(jsonBoolean));
        Assert.assertTrue(cut.validate(jsonObject));
        Assert.assertTrue(cut.validate(jsonString));
        Assert.assertTrue(cut.validate(jsonArray));

        // always true with empty schema
        cut = new UntypedSchema(jsonObject);
        Assert.assertTrue(cut.validate(jsonBoolean));
        Assert.assertTrue(cut.validate(jsonObject));
        Assert.assertTrue(cut.validate(jsonString));
        Assert.assertTrue(cut.validate(jsonArray));

        // always false with false
        jsonBoolean = new JSONBoolean(false);
        cut = new UntypedSchema(jsonBoolean);
        Assert.assertFalse(cut.validate(jsonBoolean));
        Assert.assertFalse(cut.validate(jsonObject));
        Assert.assertFalse(cut.validate(jsonString));
        Assert.assertFalse(cut.validate(jsonArray));
    }
}
