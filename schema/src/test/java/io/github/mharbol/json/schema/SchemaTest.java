
package io.github.mharbol.json.schema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONBoolean;
import io.github.mharbol.json.JSONNull;
import io.github.mharbol.json.JSONNumber;
import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.JSONString;

/**
 * SchemaTest
 */
public class SchemaTest extends TestBase {

    private JSONSchema cut;

    private JSONObject schemaObject;

    @Before
    public void setup() {
        schemaObject = new JSONObject();
    }

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

    /**
     * Test type validation
     * 
     * @implSpec IAW JSON Schema Validation 6.1.1
     */
    @Test
    public void testTypeValidation() {
        final JSONNull jsonNull = new JSONNull();
        final JSONBoolean jsonBoolean = new JSONBoolean(true);
        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonArray = new JSONArray();
        final JSONNumber jsonNumber = new JSONNumber(1.2);
        final JSONString jsonString = new JSONString("");
        final JSONNumber jsonInteger = new JSONNumber(34);

        schemaObject.put("type", "null");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonNull));
        Assert.assertFalse(cut.validate(jsonString));

        schemaObject.put("type", "boolean");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonBoolean));
        Assert.assertFalse(cut.validate(jsonNull));

        schemaObject.put("type", "object");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonObject));
        Assert.assertFalse(cut.validate(jsonBoolean));

        schemaObject.put("type", "array");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonArray));
        Assert.assertFalse(cut.validate(jsonObject));

        schemaObject.put("type", "number");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonNumber));
        Assert.assertFalse(cut.validate(jsonArray));

        schemaObject.put("type", "string");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonString));
        Assert.assertFalse(cut.validate(jsonNumber));

        schemaObject.put("type", "integer");
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonInteger));
        Assert.assertFalse(cut.validate(jsonString));
    }

    /**
     * Test type validation for invalid type parameter
     * 
     * @implSpec IAW JSON Schema Validation 6.1.1
     */
    @Test(expected = JSONSchemaException.class)
    public void testBadTypeValidation() {
        schemaObject.put("type", "float");
        JSONSchema.parseProperty(schemaObject);
    }

    /**
     * Test 'enum' keyword validation
     * 
     * @implSpec IAW JSON Schema Validation 6.1.2
     */
    @Test
    public void testEnumValidation() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", "value");
        final JSONBoolean jsonBoolean = new JSONBoolean(true);
        final JSONNumber jsonNumber = new JSONNumber(1234);
        final JSONString jsonString = new JSONString("am in the enum");
        final JSONString notInEnum = new JSONString("not in the enum");

        final JSONArray enumArray = new JSONArray();
        enumArray.add(jsonObject);
        enumArray.add(jsonBoolean);
        enumArray.add(jsonNumber);
        enumArray.add(jsonString);

        schemaObject.put("enum", enumArray);
        cut = JSONSchema.parseProperty(schemaObject);

        Assert.assertTrue(cut.validate(jsonObject));
        Assert.assertTrue(cut.validate(jsonBoolean));
        Assert.assertTrue(cut.validate(jsonNumber));
        Assert.assertTrue(cut.validate(jsonString));

        Assert.assertFalse(cut.validate(notInEnum));
    }

    /**
     * Test 'enum' keyword validation for invalid enum value
     * 
     * @implSpec IAW JSON Schema Validation 6.1.2
     */
    @Test(expected = JSONSchemaException.class)
    public void testBadTypeEnumValidation() {
        schemaObject.put("enum", new JSONString("my_enum"));
        JSONSchema.parseProperty(schemaObject);
    }

    /**
     * Test 'const' keyword validation
     * 
     * @implSpec IAW JSON Schema Validation 6.1.3
     */
    @Test
    public void testConstValidation() {
        final JSONNull jsonNull = new JSONNull();
        final JSONObject jsonObject = new JSONObject();

        schemaObject.put("const", new JSONNull());
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonNull));
        Assert.assertFalse(cut.validate(jsonObject));

        schemaObject.put("const", new JSONObject());
        cut = JSONSchema.parseProperty(schemaObject);
        Assert.assertTrue(cut.validate(jsonObject));
        Assert.assertFalse(cut.validate(jsonNull));
    }

    @Test
    public void testSimpleObejctProperty() {
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
