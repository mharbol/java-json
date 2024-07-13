package io.github.mharbol.json;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
        Assert.assertEquals(TokenTypeEnum.OPEN_BRACE, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.CLOSE_BRACE, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.OPEN_BRACKET, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.CLOSE_BRACKET, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.COLON, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.COMMA, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.TRUE, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.FALSE, iter.next().tokenTypeEnum);
        Assert.assertEquals(TokenTypeEnum.NULL, iter.next().tokenTypeEnum);
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
        Assert.assertEquals(TokenTypeEnum.STRING, tokens.get(0).tokenTypeEnum);
        Assert.assertEquals("Hey Json", tokens.get(0).value);
    }

    @Test
    public void testStringWithQuote() throws Exception {
        cut = new Tokenizer("\"Here is \\\" a quote.\"  ");
        tokens = cut.tokenize();
        Assert.assertEquals(1, tokens.size());
        Assert.assertEquals(TokenTypeEnum.STRING, tokens.get(0).tokenTypeEnum);
        Assert.assertEquals("Here is \\\" a quote.", tokens.get(0).value);
    }
}
