
package io.github.mharbol.json.schema;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONObject;

/**
 * ObjectSchemaTest
 */
public class ObjectSchemaTest extends TestBase {

    private ObjectSchema cut;
    private JSONObject schemaObject;

    @Before
    public void setup() {
        schemaObject = new JSONObject();
    }

    /**
     * Test 'maxProperties' keyword validation
     * 
     * @implSpec IAW JSON Schema Validation 6.5.1
     */
    @Test
    public void testMaxPropertiesValidation() {
        final JSONObject instanceObject = new JSONObject();
        schemaObject.put("maxProperties", 3);
        cut = new ObjectSchema(schemaObject);

        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("a", 1);
        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("b", 2);
        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("c", 3);
        Assert.assertTrue(cut.validate(instanceObject));

        instanceObject.put("d", 4);
        Assert.assertFalse(cut.validate(instanceObject));
        instanceObject.put("e", 5);
        Assert.assertFalse(cut.validate(instanceObject));
    }

    /**
     * Test 'minProperties' keyword validation
     * 
     * @implSpec IAW JSON Schema Validation 6.5.2
     */
    @Test
    public void testMinPropertiesValidation() {
        final JSONObject instanceObject = new JSONObject();
        schemaObject.put("minProperties", 2);
        cut = new ObjectSchema(schemaObject);

        Assert.assertFalse(cut.validate(instanceObject));
        instanceObject.put("a", 1);
        Assert.assertFalse(cut.validate(instanceObject));
        instanceObject.put("b", 2);
        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("c", 3);
        Assert.assertTrue(cut.validate(instanceObject));
    }

    /**
     * Test 'minProperties' and 'maxProperties' combined keyword validation
     * 
     * @implSpec IAW JSON Schema Validation 6.5.1 and 6.5.2
     */
    @Test
    public void testMinMaxPropsValidation() {
        final JSONObject instanceObject = new JSONObject();
        schemaObject.put("minProperties", 1);
        schemaObject.put("maxProperties", 3);
        cut = new ObjectSchema(schemaObject);

        Assert.assertFalse(cut.validate(instanceObject));
        instanceObject.put("a", 1);
        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("b", 2);
        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("c", 3);
        Assert.assertTrue(cut.validate(instanceObject));
        instanceObject.put("d", 4);
        Assert.assertFalse(cut.validate(instanceObject));

        // this is silly but allowed...
        schemaObject.put("minProperties", 4);
        cut = new ObjectSchema(schemaObject);
        Assert.assertFalse(cut.validate(instanceObject));
    }

    /**
     * Test 'minProperties' and 'maxProperties' throw for bad values
     * 
     * @implSpec IAW JSON Schema Validation 6.5.1 and 6.5.2
     */
    @Test
    public void testMinMaxPropsValidValues() {
        final JSONObject badMin = new JSONObject();
        final JSONObject badMax = new JSONObject();
        badMin.put("minProperties", -1);
        badMax.put("maxProperties", -2);
        Assert.assertThrows(JSONSchemaException.class, () -> {
            new ObjectSchema(badMin);
        });
        badMin.put("minProperties", "1");
        Assert.assertThrows(JSONSchemaException.class, () -> {
            new ObjectSchema(badMin);
        });
        Assert.assertThrows(JSONSchemaException.class, () -> {
            new ObjectSchema(badMax);
        });
        badMax.put("maxProperties", "2");
        Assert.assertThrows(JSONSchemaException.class, () -> {
            new ObjectSchema(badMax);
        });
    }

    /**
     * Test 'required' keyword validation
     * 
     * @implSpec IAW JSON Schema Validation 6.5.3
     */
    @Test
    public void testRequiredPropertiesValidation() {
        final JSONObject instanceObject = new JSONObject();
        JSONArray requiredArray = new JSONArray();
        schemaObject.put("required", requiredArray);
        cut = new ObjectSchema(schemaObject);

        Assert.assertTrue(cut.validate(instanceObject));

        requiredArray.add("a");
        cut = new ObjectSchema(schemaObject);
        Assert.assertFalse(cut.validate(schemaObject));

        instanceObject.put("a", "value at a");
        Assert.assertTrue(cut.validate(instanceObject));

        instanceObject.put("b", "value at b");
        Assert.assertTrue(cut.validate(instanceObject));

        requiredArray.add("b");
        requiredArray.add("c");
        cut = new ObjectSchema(schemaObject);
        Assert.assertFalse(cut.validate(instanceObject));
    }

    /**
     * Test 'required' keyword for bad values
     * 
     * @implSpec IAW JSON Schema Validation 6.5.3
     */
    @Test(expected = JSONSchemaException.class)
    public void testRequireBadValues() {
        JSONArray required = new JSONArray();
        schemaObject.put("required", required);
        required.add("a");
        required.add("b");
        required.add("a");
        new ObjectSchema(schemaObject);
    }

    /**
     * Test 'dependentRequired' keyword validation
     *
     * @implSpec IAW JSON Schema Validation 6.5.4
     */
    @Test
    public void testDependentRequired() {
        JSONObject dependentRequired = new JSONObject();
        schemaObject.put("dependentRequired", dependentRequired);

        JSONArray drA = new JSONArray();
        dependentRequired.put("a", drA);
    }
}
