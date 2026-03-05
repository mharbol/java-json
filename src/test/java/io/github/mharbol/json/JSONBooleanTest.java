
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
}
