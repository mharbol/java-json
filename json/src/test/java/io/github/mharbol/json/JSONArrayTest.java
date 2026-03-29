
package io.github.mharbol.json;

import org.junit.Assert;
import org.junit.Test;

/**
 * JSONArrayTest
 */
public class JSONArrayTest {

    private JSONArray cut;

    @Test
    public void testSerialize() {
        cut = new JSONArray();
        cut.add(1);
        cut.add(2);
        cut.add(3);
        Assert.assertEquals("[1,2,3]", cut.serialize());

        cut = new JSONArray();
        cut.add("str");
        cut.add(true);
        cut.add(30);
        cut.add(1.2);
        cut.add(new JSONObject());
        cut.add(JSONNull.NULL);
        Assert.assertEquals("[\"str\",true,30,1.2,{},null]", cut.serialize());
    }

    @Test
    public void testGet() {
        cut = new JSONArray();
        cut.add(30);
        Assert.assertEquals(1, cut.size());
        Assert.assertTrue(cut.get(0) instanceof JSONNumber);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfRange() {
        cut = new JSONArray();
        cut.add(1);
        Assert.assertEquals(1, cut.size());
        cut.get(2); // out of bounds
    }
}
