
package io.github.mharbol.json;

import org.junit.Test;

/**
 * Test class for the {@link Verifier} class.
 */
public class VerifierTest {

    private Verifier cut;
    private Tokenizer tokenizer;

    @Test
    public void testBasicVerifiers() throws Exception {
        testVerify("{\"key\":\"value\"}");
        testVerify("{}");
        testVerify("{\"key\":{\"other_key\":null}}");
        testVerify("{\"key\":\n\t[1, 2, false, null, true, {}, {\"str\": 0}]}");
        testVerify("{\"item0\": 0, \"item1\": false, \"item_list\":[\"0\", 1, true, 3]}");
        testVerify("{\"list_of_list\" : [[[[],[],{}]]]}");
    }

    @Test(expected = VerifierException.class)
    public void testIntKey() throws Exception {
        testVerify("{3 : 5}");
    }

    @Test(expected = VerifierException.class)
    public void testEmptyStr() throws Exception {
        testVerify("");
    }

    @Test(expected = VerifierException.class)
    public void testUnclosedObj() throws Exception {
        testVerify("{\"key\":\n\t[1, 2, false, null, true, {}, {\"str\": 0}]");
    }

    @Test(expected = VerifierException.class)
    public void testMultipleRootObj() throws Exception {
        testVerify("{}{\"totally_good\": 0}");
    }

    @Test(expected = VerifierException.class)
    public void testTrailingCommaObj() throws Exception {
        testVerify("{\"key\":{\"other_key\":null},}");
    }

    @Test(expected = VerifierException.class)
    public void testTrailingCommaArray() throws Exception {
        testVerify("{\"key\":\n\t[1, 2, false, null, true, {}, {\"str\": 0},]}");
    }

    @Test(expected = VerifierException.class)
    public void testDoubleCommaArray() throws Exception {
        testVerify("{\"item0\": 0, \"item1\": false,, \"item_list\":[\"0\", 1, true, 3]}");
    }

    @Test(expected = VerifierException.class)
    public void testDoubleCommaObj() throws Exception {
        testVerify("{\"item0\": 0 , , \"item1\": false, \"item_list\":[\"0\", 1, true, 3]}");
    }

    private void testVerify(String jsonString) throws Exception {
        tokenizer = new Tokenizer(jsonString);
        cut = new Verifier(tokenizer.tokenize());
        cut.verify();
    }
}
