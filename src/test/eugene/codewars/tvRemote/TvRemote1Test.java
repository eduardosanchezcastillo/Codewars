package eugene.codewars.tvRemote;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TvRemote1Test {
    @Test
    public void example() {
        assertEquals(36, TvRemote1.tvRemote("codewars"));
    }

    @Test
    public void misc() {
        assertEquals(16, TvRemote1.tvRemote("does"));
        assertEquals(23, TvRemote1.tvRemote("your"));
        assertEquals(33, TvRemote1.tvRemote("solution"));
        assertEquals(20, TvRemote1.tvRemote("work"));
        assertEquals(12, TvRemote1.tvRemote("for"));
        assertEquals(27, TvRemote1.tvRemote("these"));
        assertEquals(25, TvRemote1.tvRemote("words"));
    }
}