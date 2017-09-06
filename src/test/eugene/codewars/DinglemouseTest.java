package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class DinglemouseTest {

    private static char[][] show(final char[][] field) {
        for (int i = 0; i < field.length; i++) {
            System.out.println(new String(field[i]));
        }
        return field;
    }

    @Test
    public void ex1() {
        final char[][] field = new char[][]{
                "cow.cow.cow.cow.cow".toCharArray(),
                "cow.cow.cow.cow.cow".toCharArray(),
                "cow.woc.cow.cow.cow".toCharArray(),
                "cow.cow.cow.cow.cow".toCharArray(),
        };
        assertArrayEquals(new int[]{6, 2}, Dinglemouse.findWrongWayCow(show(field)));
    }

    @Test
    public void ex2() {
        final char[][] field = new char[][]{
                "c..........".toCharArray(),
                "o...c......".toCharArray(),
                "w...o.c....".toCharArray(),
                "....w.o....".toCharArray(),
                "......w.cow".toCharArray()
        };
        assertArrayEquals(new int[]{8, 4}, Dinglemouse.findWrongWayCow(show(field)));
    }

    @Test
    public void ex3() {
        final char[][] field = new char[][]{
                "woc.woc.woc.woc.woc".toCharArray(),
                "woc.woc.woc.woc.woc".toCharArray(),
                "woc.cow.woc.woc.woc".toCharArray(),
                "woc.woc.woc.woc.woc".toCharArray(),
        };
        assertArrayEquals(new int[]{4, 2}, Dinglemouse.findWrongWayCow(show(field)));
    }

    @Test
    public void ex4() {
        final char[][] field = new char[][]{
                "c..........".toCharArray(),
                "o...w......".toCharArray(),
                "w...o.c....".toCharArray(),
                "....c.o....".toCharArray(),
                "......w....".toCharArray()
        };
        assertArrayEquals(new int[]{4, 3}, Dinglemouse.findWrongWayCow(show(field)));
    }

    @Test
    public void ex5() {
        final char[][] field = new char[][]{
                "cowc".toCharArray(),
                "cowo".toCharArray(),
                "coww".toCharArray(),
                "cow.".toCharArray()
        };
        assertArrayEquals(new int[]{3, 0}, Dinglemouse.findWrongWayCow(show(field)));
    }
}