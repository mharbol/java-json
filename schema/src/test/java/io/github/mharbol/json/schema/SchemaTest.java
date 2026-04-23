
package io.github.mharbol.json.schema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * SchemaTest
 */
public class SchemaTest extends TestBase {

    private JSONSchema cut;

    @Test
    public void testBasicReadSchema() throws Exception {
        cut = readSchemaFile("basic.schema.json");
        ObjectProperty objProperty = (ObjectProperty) cut;
        Assert.assertEquals(Optional.of("http://example.com/schemas/basic.schema.json"), objProperty.getId());
        Assert.assertEquals(Optional.of("https://json-schema.org/draft/2020-12/schema"), objProperty.getSchema());
        Assert.assertEquals(Optional.of("Product"), objProperty.getTitle());
        Assert.assertEquals(Optional.of("A product in the catalog"), objProperty.getDescription());
        Assert.assertEquals(PropertyTypeEnum.OBJECT, objProperty.getType());

        Map<String, JSONSchema> properties = objProperty.getProperties().get();
        Assert.assertEquals(5, properties.size());
        Assert.assertTrue(properties.containsKey("productId"));
        Assert.assertTrue(properties.containsKey("productName"));
        Assert.assertTrue(properties.containsKey("price"));
        Assert.assertTrue(properties.containsKey("tags"));
        Assert.assertTrue(properties.containsKey("dimensions"));

        JSONSchema productId = properties.get("productId");
        Assert.assertTrue(productId instanceof IntegerProperty);
        Assert.assertEquals(PropertyTypeEnum.INTEGER, productId.getType());
        Assert.assertEquals("The unique identifier for a product", productId.getDescription().get());

        JSONSchema productName = properties.get("productName");
        Assert.assertTrue(productName instanceof StringProperty);
        Assert.assertEquals(PropertyTypeEnum.STRING, productName.getType());
        Assert.assertEquals("Name of the product", productName.getDescription().get());

        JSONSchema price = properties.get("price");
        Assert.assertTrue(price instanceof NumberProperty);
        Assert.assertEquals(PropertyTypeEnum.NUMBER, price.getType());
        Assert.assertEquals("The price of the product", price.getDescription().get());
        NumberProperty numberPropertyPrice = (NumberProperty) price;
        Assert.assertTrue(numberPropertyPrice.isMinExclusive());
        Assert.assertEquals(0, numberPropertyPrice.getMinimum().get());

        List<String> required = objProperty.getRequired().get();
        Assert.assertEquals(3, required.size());
        Assert.assertEquals("productId", required.get(0));
        Assert.assertEquals("productName", required.get(1));
        Assert.assertEquals("price", required.get(2));

        JSONSchema tags = properties.get("tags");
        Assert.assertTrue(tags instanceof ArrayProperty);
        Assert.assertEquals(PropertyTypeEnum.ARRAY, tags.getType());
        Assert.assertEquals("Tags for the product", tags.getDescription().get());
        ArrayProperty tagsProperty = (ArrayProperty) tags;
        Assert.assertEquals(1, tagsProperty.getMinItems());
        Assert.assertTrue(tagsProperty.isUniqueItems());

        JSONSchema dimensionsJson = properties.get("dimensions");
        Assert.assertTrue(dimensionsJson instanceof ObjectProperty);
        Assert.assertEquals(PropertyTypeEnum.OBJECT, dimensionsJson.getType());

        ObjectProperty dimensions = (ObjectProperty) dimensionsJson;
        Map<String, JSONSchema> dimensionsProperties = dimensions.getProperties().get();
        Assert.assertEquals(3, dimensionsProperties.size());
        Assert.assertTrue(dimensionsProperties.containsKey("length"));
        Assert.assertTrue(dimensionsProperties.containsKey("width"));
        Assert.assertTrue(dimensionsProperties.containsKey("height"));
        List<String> requiredDimensions = dimensions.getRequired().get();
        Assert.assertEquals("length", requiredDimensions.get(0));
        Assert.assertEquals("width", requiredDimensions.get(1));
        Assert.assertEquals("height", requiredDimensions.get(2));
    }

    @Test
    public void testBasicValidate() throws Exception {
        cut = readSchemaFile("basic.schema.json");
        ObjectProperty objProperty = (ObjectProperty) cut;
        Assert.assertTrue(objProperty.validate(readTestObject("basic.test.pass.json")));
    }

    private JSONSchema readSchemaFile(String file) throws Exception {
        return new ObjectProperty(readTestObject(file));
    }
}
