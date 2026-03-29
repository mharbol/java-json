
package io.github.mharbol.json;

import org.junit.Assert;
import org.junit.Test;

/**
 * JSONBooleanTest
 */
public class JSONBooleanTest {

    private JSONBoolean cut;

    @Test
    public void testSerialize() {
        cut = new JSONBoolean(true);
        Assert.assertEquals("true", cut.serialize());
        cut = new JSONBoolean(false);
        Assert.assertEquals("false", cut.serialize());
    }

    @Test
    public void testPutBoolean() {
        JSONObject jso = new JSONObject();
        jso.put("test", true);
        Assert.assertEquals("{\"test\":true}", jso.serialize()); // this might need to change...
    }
}
