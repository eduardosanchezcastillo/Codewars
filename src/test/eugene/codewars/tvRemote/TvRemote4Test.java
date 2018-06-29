package eugene.codewars.tvRemote;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TvRemote4Test {
    @Test
    public void example() {
        assertEquals(71, TvRemote4.tvRemote("Too Easy?"));
    }

    @Test
    public void lower() {
        assertEquals(16, TvRemote4.tvRemote("does"));
        assertEquals(21, TvRemote4.tvRemote("your"));
        assertEquals(33, TvRemote4.tvRemote("solution"));
        assertEquals(18, TvRemote4.tvRemote("work"));
        assertEquals(12, TvRemote4.tvRemote("for"));
        assertEquals(27, TvRemote4.tvRemote("these"));
        assertEquals(23, TvRemote4.tvRemote("words"));
    }

    @Test
    public void upper() {
        assertEquals(19, TvRemote4.tvRemote("DOES"));
        assertEquals(22, TvRemote4.tvRemote("YOUR"));
        assertEquals(34, TvRemote4.tvRemote("SOLUTION"));
        assertEquals(19, TvRemote4.tvRemote("WORK"));
        assertEquals(15, TvRemote4.tvRemote("FOR"));
        assertEquals(28, TvRemote4.tvRemote("THESE"));
        assertEquals(24, TvRemote4.tvRemote("WORDS"));
    }

    @Test
    public void symbols() {
        assertEquals(33, TvRemote4.tvRemote("^does^"));
        assertEquals(53, TvRemote4.tvRemote("$your$"));
        assertEquals(49, TvRemote4.tvRemote("#solution#"));
        assertEquals(34, TvRemote4.tvRemote("\u00bfwork\u00bf"));
        assertEquals(38, TvRemote4.tvRemote("{for}"));
        assertEquals(57, TvRemote4.tvRemote("\u00a3these\u00a3"));
        assertEquals(54, TvRemote4.tvRemote("?symbols?"));
    }
}