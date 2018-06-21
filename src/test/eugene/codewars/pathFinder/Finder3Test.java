package eugene.codewars.pathFinder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Finder3Test {
    @Test
    public void sample1() {
        String map = "000\n" +
                "000\n" +
                "000";
        assertEquals(0, Finder3.pathFinder(map));
    }

    @Test
    public void sample2() {
        String map = "010\n" +
                "010\n" +
                "010";
        assertEquals(2, Finder3.pathFinder(map));
    }

    @Test
    public void sample3() {
        String map = "010\n" +
                "101\n" +
                "010";
        assertEquals(4, Finder3.pathFinder(map));
    }

    @Test
    public void sample4() {
        String map = "0707\n" +
                "7070\n" +
                "0707\n" +
                "7070";
        assertEquals(42, Finder3.pathFinder(map));
    }

    @Test
    public void sample5() {
        String map = "700000\n" +
                "077770\n" +
                "077770\n" +
                "077770\n" +
                "077770\n" +
                "000007";
        assertEquals(14, Finder3.pathFinder(map));
    }

    @Test
    public void sample6() {
        String map = "777000\n" +
                "007000\n" +
                "007000\n" +
                "007000\n" +
                "007000\n" +
                "007777";
        assertEquals(0, Finder3.pathFinder(map));
    }

    @Test
    public void sample7() {
        String map = "000000\n" +
                "000000\n" +
                "000000\n" +
                "000010\n" +
                "000109\n" +
                "001010";

        assertEquals(4, Finder3.pathFinder(map));
    }
}