package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PsychicTest {
    @Test
    public void testRandom() {
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
    }
}