package io.github.mharbol.json;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.junit.Assert;
import org.junit.Test;

/**
 * TokenizerTest
 */
public class TokenizerTest {

    private Tokenizer cut;
    private List<Token> tokens;
    private Iterator<Token> iter;

    private void expectTokens(String jsonString, Token... tokens) throws Exception {
        expectTokens(jsonString, Arrays.asList(tokens));
    }

    private void expectTokens(String jsonString, List<Token> expectedTokens) throws Exception {
        cut = new Tokenizer(jsonString);
        tokens = cut.tokenize();
        Iterator<Token> expectedIter = expectedTokens.iterator();
        Iterator<Token> actualIter = tokens.iterator();
        Token actual, expected;

        while (expectedIter.hasNext()) {
            actual = actualIter.next();
            expected = expectedIter.next();
            Assert.assertEquals(expected, actual);
        }
        Assert.assertFalse(expectedIter.hasNext());
        Assert.assertFalse(actualIter.hasNext());
    }

    @Test
    public void testBasicTokenDetect() throws Exception {
        cut = new Tokenizer("{} [   ]:,true false null   ");
        tokens = cut.tokenize();
        iter = tokens.iterator();
        Assert.assertEquals(TokenTypeEnum.OPEN_BRACE, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.CLOSE_BRACE, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.OPEN_BRACKET, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.CLOSE_BRACKET, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.COLON, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.COMMA, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.TRUE, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.FALSE, iter.next().tokenType);
        Assert.assertEquals(TokenTypeEnum.NULL, iter.next().tokenType);
        Assert.assertFalse(iter.hasNext());
    }

    @Test
    public void testBasic() throws Exception {
        expectTokens("{}", new Token(TokenTypeEnum.OPEN_BRACE), new Token(TokenTypeEnum.CLOSE_BRACE));
    }

    @Test
    public void testStringBasic() throws Exception {
        cut = new Tokenizer("   \"Hey Json\"   ");
        tokens = cut.tokenize();
        Assert.assertEquals(1, tokens.size());
        Assert.assertEquals(TokenTypeEnum.STRING, tokens.get(0).tokenType);
        Assert.assertEquals("Hey Json", tokens.get(0).value);
    }

    @Test
    public void testStringWithQuote() throws Exception {
        cut = new Tokenizer("\"Here is \\\" a quote.\"  ");
        tokens = cut.tokenize();
        Assert.assertEquals(1, tokens.size());
        Assert.assertEquals(TokenTypeEnum.STRING, tokens.get(0).tokenType);
        Assert.assertEquals("Here is \\\" a quote.", tokens.get(0).value);
    }

    @Test
    public void numbersInObj() throws Exception {
        expectTokens("{\"number\":-0.31e-5}", Token.OPEN_BRACE, new Token(TokenTypeEnum.STRING, "number"), Token.COLON,
                new Token(TokenTypeEnum.NUMBER, "-0.31e-5"), Token.CLOSE_BRACE);
    }

    @Test
    public void testNumberInList() throws Exception {
        expectTokens("{\"numbers\": [1, 2, 3, 4, 23.4e4, 5, 0] }",
                Token.OPEN_BRACE,
                new Token(TokenTypeEnum.STRING, "numbers"),
                Token.COLON,
                Token.OPEN_BRACKET,
                new Token(TokenTypeEnum.NUMBER, "1"),
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "2"),
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "3"),
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "4"),
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "23.4e4"),
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "5"),
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "0"),
                Token.CLOSE_BRACKET,
                Token.CLOSE_BRACE);
    }

    @Test
    public void testAllValueTypesInList() throws Exception {
        final String json = "{\"stuff\" : {}, \"things\" : [1, false, {\"items\": null}, 4, \"\\\"{}string!\\\"\", true, 0]}";
        expectTokens(json,
                Token.OPEN_BRACE,
                new Token(TokenTypeEnum.STRING, "stuff"),
                Token.COLON,
                Token.OPEN_BRACE,
                Token.CLOSE_BRACE,
                Token.COMMA,
                new Token(TokenTypeEnum.STRING, "things"),
                Token.COLON,
                Token.OPEN_BRACKET,
                new Token(TokenTypeEnum.NUMBER, "1"),
                Token.COMMA,
                Token.FALSE,
                Token.COMMA,
                Token.OPEN_BRACE,
                new Token(TokenTypeEnum.STRING, "items"),
                Token.COLON,
                Token.NULL,
                Token.CLOSE_BRACE,
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "4"),
                Token.COMMA,
                new Token(TokenTypeEnum.STRING, "\\\"{}string!\\\""),
                Token.COMMA,
                Token.TRUE,
                Token.COMMA,
                new Token(TokenTypeEnum.NUMBER, "0"),
                Token.CLOSE_BRACKET,
                Token.CLOSE_BRACE
            );
    }

    // Tests for JSON Number format regex
    private void verifyNumberRegexMatch(String numberStr) {
        Matcher matcher = Tokenizer.numberFmtPattern.matcher(numberStr);
        Assert.assertTrue(matcher.find());
    }

    private void verifyNumberRegexNotMatch(String numberStr) {
        Matcher matcher = Tokenizer.numberFmtPattern.matcher(numberStr);
        Assert.assertFalse(matcher.find());
    }

    @Test
    public void testNumberFmtRegexCorrect() {
        // ints
        verifyNumberRegexMatch("0");
        verifyNumberRegexMatch("1");
        verifyNumberRegexMatch("-12");
        verifyNumberRegexMatch("-0"); // gross
        verifyNumberRegexMatch("43215");
        verifyNumberRegexMatch("195731289124");

        // floats
        verifyNumberRegexMatch("1.2");
        verifyNumberRegexMatch("0.432");
        verifyNumberRegexMatch("-0.432");
        verifyNumberRegexMatch("1234.5423");

        // decimal scientific
        verifyNumberRegexMatch("1.4e2");
        verifyNumberRegexMatch("1.4E2");
        verifyNumberRegexMatch("1.4e+2");
        verifyNumberRegexMatch("1.4e-2");
        verifyNumberRegexMatch("1231.3214e+1232");
        verifyNumberRegexMatch("1.3e0");
        verifyNumberRegexMatch("1.0e0");
        verifyNumberRegexMatch("12.0E6");

        // int scientific
        verifyNumberRegexMatch("12E6");
        verifyNumberRegexMatch("12e6");
        verifyNumberRegexMatch("12431142341e-413123412");
        verifyNumberRegexMatch("12431142341e+413123412");
        verifyNumberRegexMatch("12431142341E+413123412");
        verifyNumberRegexMatch("0E+413123412"); // gross
    }

    @Test
    public void testNumberFmtRegexIncorrect() {
        // ints
        verifyNumberRegexNotMatch("012");
        verifyNumberRegexNotMatch("+12");
        verifyNumberRegexNotMatch("-012");
        verifyNumberRegexNotMatch("-+12");
        verifyNumberRegexNotMatch("43m1234");
        verifyNumberRegexNotMatch("0x23ff");
        verifyNumberRegexMatch("-0.31e-5");

        // floats
        verifyNumberRegexNotMatch(".2");
        verifyNumberRegexNotMatch(".432");
        verifyNumberRegexNotMatch("-.432");
        verifyNumberRegexNotMatch("03.432");
        verifyNumberRegexNotMatch("-03.432");
        verifyNumberRegexNotMatch("-03.4.32");

        // decimal scientific
        verifyNumberRegexNotMatch("2.3ee2");
        verifyNumberRegexNotMatch("2.3e2.4");
        verifyNumberRegexNotMatch("2.3e--2");
        verifyNumberRegexNotMatch("2.3e+-2");
        verifyNumberRegexNotMatch("-2.3e+-2");
        verifyNumberRegexNotMatch("-2.3e");
        verifyNumberRegexNotMatch("e-5");

        // int scientific
        verifyNumberRegexNotMatch("12eE6");
        verifyNumberRegexNotMatch("12eE+6");
        verifyNumberRegexNotMatch("12E-+6");
        verifyNumberRegexNotMatch("12E");
        verifyNumberRegexNotMatch("E4");
        verifyNumberRegexNotMatch("-E4");
    }
}
