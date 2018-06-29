package eugene.codewars.tvRemote;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TvRemote3Test {
    @Test
    public void example() {
        assertEquals(49, TvRemote3.tvRemote("Code Wars"));
    }

    @Test
    public void lower() {
        assertEquals(16, TvRemote3.tvRemote("does"));
        assertEquals(21, TvRemote3.tvRemote("your"));
        assertEquals(33, TvRemote3.tvRemote("solution"));
        assertEquals(18, TvRemote3.tvRemote("work"));
        assertEquals(12, TvRemote3.tvRemote("for"));
        assertEquals(27, TvRemote3.tvRemote("these"));
        assertEquals(23, TvRemote3.tvRemote("words"));
    }

    @Test
    public void upper() {
        assertEquals(19, TvRemote3.tvRemote("DOES"));
        assertEquals(22, TvRemote3.tvRemote("YOUR"));
        assertEquals(34, TvRemote3.tvRemote("SOLUTION"));
        assertEquals(19, TvRemote3.tvRemote("WORK"));
        assertEquals(15, TvRemote3.tvRemote("FOR"));
        assertEquals(28, TvRemote3.tvRemote("THESE"));
        assertEquals(24, TvRemote3.tvRemote("WORDS"));
    }

    @Test
    public void mixed() {
        assertEquals(28, TvRemote3.tvRemote("Does"));
        assertEquals(33, TvRemote3.tvRemote("Your"));
        assertEquals(45, TvRemote3.tvRemote("Solution"));
        assertEquals(26, TvRemote3.tvRemote("Work"));
        assertEquals(20, TvRemote3.tvRemote("For"));
        assertEquals(35, TvRemote3.tvRemote("These"));
        assertEquals(31, TvRemote3.tvRemote("Words"));
    }
}