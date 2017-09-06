package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DartboardTests {
    Dartboard dartboard = new Dartboard();

    @Test
    public void getOutsideOfTheBoardTest() {
        assertEquals("X", dartboard.getScore(-133.69, -147.38));
    }

    @Test
    public void getBullsEyeTest() {
        assertEquals("DB", dartboard.getScore(4.06, 0.71));
    }

    @Test
    public void getSingleBullTest() {
        assertEquals("SB", dartboard.getScore(2.38, -6.06));
    }

    @Test
    public void getSingleNumber20Test() {
        assertEquals("20", dartboard.getScore(-5.43, 117.95));
    }

    @Test
    public void getSingleNumber7Test() {
        assertEquals("7", dartboard.getScore(-73.905, -95.94));
    }

    @Test
    public void getTriple2Test() {
        assertEquals("T2", dartboard.getScore(55.53, -87.95));
    }

    @Test
    public void getDouble9Test() {
        assertEquals("D9", dartboard.getScore(-145.19, 86.53));
    }

    @Test
    public void getTest1() {
        assertEquals("17", dartboard.getScore(53.101034661628205, -106.79879866680722));
    }
}
