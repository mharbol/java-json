
package io.github.mharbol.json.schema;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * SchemaTest
 */
public class SchemaTest extends BaseTest {

    private Schema cut;

    @Test
    public void testBasicReadSchema() throws Exception {
        cut = readSchemaFile("basic.schema.json");
        Assert.assertEquals(Optional.of("http://example.com/schemas/basic.schema.json"), cut.getId());
        Assert.assertEquals(Optional.of("https://json-schema.org/draft/2020-12/schema"), cut.getSchema());
        Assert.assertEquals(Optional.of("Product"), cut.getTitle());
        Assert.assertEquals(Optional.of("A product in the catalog"), cut.getDescription());
    }

    private Schema readSchemaFile(String file) throws Exception {
        return new Schema(readTestObject(file));
    }
}
