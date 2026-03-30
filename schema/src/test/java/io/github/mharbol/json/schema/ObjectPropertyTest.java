
package io.github.mharbol.json.schema;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import io.github.mharbol.json.JSONObject;

/**
 * ObjectSchemaTest
 */
public class ObjectPropertyTest extends BaseTest {

    private ObjectProperty cut;

    @Test
    public void testSimpleObejctProperty() throws Exception {
        JSONObject jsonObject = new JSONObject();
        cut = new ObjectProperty(jsonObject);
        Assert.assertEquals(Optional.empty(), cut.getTitle());
        Assert.assertEquals(Optional.empty(), cut.getDescription());

        jsonObject.put("title", "The title");
        cut = new ObjectProperty(jsonObject);
        Assert.assertEquals(Optional.of("The title"), cut.getTitle());
        Assert.assertEquals(Optional.empty(), cut.getDescription());

        jsonObject.put("description", "The description");
        cut = new ObjectProperty(jsonObject);
        Assert.assertEquals(Optional.of("The title"), cut.getTitle());
        Assert.assertEquals(Optional.of("The description"), cut.getDescription());
    }
}
