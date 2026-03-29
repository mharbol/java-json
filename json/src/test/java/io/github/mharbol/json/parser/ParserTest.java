
package io.github.mharbol.json.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import io.github.mharbol.json.JSONArray;
import io.github.mharbol.json.JSONNull;
import io.github.mharbol.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for the {@link Parser} class.
 */
public class ParserTest {

    private Parser cut;
    private JSONObject object;

    @Test
    public void testEmptyObject() throws Exception {
        cut = getParser("{}");
        object = cut.parse();
        Assert.assertEquals(new JSONObject(), object);
    }

    @Test
    public void testBasicObject() throws Exception {
        cut = getParser("{\"key\" : \"value\"}");
        JSONObject expected = new JSONObject();
        expected.put("key", "value");
        Assert.assertEquals(expected, cut.parse());
    }

    @Test
    public void testObjectList() throws Exception {
        cut = getParser("{\"list\" :  [\"a\", \"b\", 3, false, null, \n true]}");
        JSONObject expected = new JSONObject();
        JSONArray arr = new JSONArray();
        arr.add("a");
        arr.add("b");
        arr.add(3);
        arr.add(false);
        arr.add(JSONNull.NULL);
        arr.add(true);
        expected.put("list", arr);
        Assert.assertEquals(expected, cut.parse());
    }

    @Test
    public void testNestedObject() throws Exception {
        cut = getParser("{\"obj\" : {\"inner\" : {\"most_inner\" : \"value\"}}}");
        JSONObject expected = new JSONObject();
        JSONObject inner = new JSONObject();
        JSONObject mostInner = new JSONObject();
        mostInner.put("most_inner", "value");
        inner.put("inner", mostInner);
        expected.put("obj", inner);
        Assert.assertEquals(expected, cut.parse());
    }

    @Test
    public void testMultiKey() throws Exception {
        cut = getParser("{\"a\" : \"a\", \"b\" : \"b\"}");
        JSONObject expected = new JSONObject();
        expected.put("b", "b");
        expected.put("a", "a");
        Assert.assertEquals(expected, cut.parse());
    }

    @Test
    public void testReadBigMessage() throws Exception {
        final String initMsg = readTestFile("lspinit.json");
        cut = getParser(initMsg);
        cut.parse(); // for now just reading in the big file
    }

    private String readTestFile(String file) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    private Parser getParser(String jsonString) throws Exception {
        Tokenizer tokenizer = new Tokenizer(jsonString);
        List<Token> tokens = tokenizer.tokenize();
        Verifier verifier = new Verifier(tokens);
        verifier.verify();
        return new Parser(tokens);
    }
}
