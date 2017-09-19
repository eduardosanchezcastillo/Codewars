package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RunesTest {

    @Test
    public void testSample() {
        assertEquals("Answer for expression '1+1=?' ", 2, Runes.solveExpression("1+1=?"));
        assertEquals("Answer for expression '123*45?=5?088' ", 6, Runes.solveExpression("123*45?=5?088"));
        assertEquals("Answer for expression '-5?*-1=5?' ", 0, Runes.solveExpression("-5?*-1=5?"));
        assertEquals("Answer for expression '19--45=5?' ", -1, Runes.solveExpression("19--45=5?"));
        assertEquals("Answer for expression '??*??=302?' ", 5, Runes.solveExpression("??*??=302?"));
        assertEquals("Answer for expression '?*11=??' ", 2, Runes.solveExpression("?*11=??"));
    }

    @Test
    public void testMultiplication() {
        assertEquals(0, Runes.solveExpression("123?45*?=?"));
        assertEquals(0, Runes.solveExpression("?*123?45=?"));
        assertEquals(1, Runes.solveExpression("??605*-63=-73???5"));
    }

    @Test
    public void testAddition() {
        assertEquals(0, Runes.solveExpression("123?45+?=123?45"));
        assertEquals(9, Runes.solveExpression("?8?170-1?6256=7?2?14"));
        assertEquals(2, Runes.solveExpression("?38???+595???=833444"));
    }

    @Test
    public void testSubtraction() {
        assertEquals(0, Runes.solveExpression("123?45-?=123?45"));
        assertEquals(6, Runes.solveExpression("-7715?5--484?00=-28?9?5"));
        assertEquals(4, Runes.solveExpression("50685?--1?5630=652?8?"));
    }

}