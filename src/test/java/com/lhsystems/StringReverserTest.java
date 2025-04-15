package com.lhsystems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringReverserTest {

    StringReverser stringReverser = new StringReverser();

    @Test
    public void testEmptyString() {
        assertEquals("", stringReverser.reverse(""));
    }

    @Test
    public void testSingleCharacter() {
        assertEquals("a", stringReverser.reverse("a"));
    }

    @Test
    public void testSimpleWord() {
        assertEquals("olleh", stringReverser.reverse("hello"));
    }

    @Test
    public void testSentenceWithSpaces() {
        assertEquals("dlrow olleH", stringReverser.reverse("Hello world"));
    }

    @Test
    public void testStringWithNumbers() {
        assertEquals("321cba", stringReverser.reverse("abc123"));
    }

    @Test
    public void testPalindrome() {
        assertEquals("madam", stringReverser.reverse("madam"));
    }

    @Test
    public void testSpecialCharacters() {
        assertEquals("!@#$%^", stringReverser.reverse("^%$#@!"));
    }

    @Test
    public void testUnicodeCharacters() {
        assertEquals("界世好你", stringReverser.reverse("你好世界"));
    }

    @Test
    public void testLongString() {
        String input = "abcdefghijklmnopqrstuvwxyz";
        String expected = "zyxwvutsrqponmlkjihgfedcba";
        assertEquals(expected, stringReverser.reverse(input));
    }

    @Test
    public void testEmojiSingle() {
        assertEquals("😊", stringReverser.reverse("😊"));
    }

    @Test
    public void testEmojiSequence() {
        assertEquals("🚀🔥😊", stringReverser.reverse("😊🔥🚀"));
    }

    @Test
    public void testMixedTextAndEmoji() {
        assertEquals("olleh🔥😊", stringReverser.reverse("😊🔥hello"));
    }

    @Test
    public void testComplexEmoji() {
        assertEquals("🇵🇱😎🎉", stringReverser.reverse("🎉😎🇵🇱"));
    }
}