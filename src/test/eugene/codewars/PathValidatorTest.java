package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathValidatorTest {

    @Test
    public void blockedPath() {
        boolean[][] inputGrid = new boolean[][]{new boolean[]{true, true, true},
                new boolean[]{false, false, true},
                new boolean[]{false, true, false}};
        assertEquals(0, PathValidator.getNumberOfReachableFields(inputGrid, 3, 3, 0, 0));
    }

    @Test
    public void singlePath() {
        boolean[][] inputGrid = new boolean[][]{new boolean[]{true}, new boolean[]{true}};
        assertEquals(1, PathValidator.getNumberOfReachableFields(inputGrid, 2, 1, 0, 0));
    }

    @Test
    public void multiplePaths() {
        boolean[][] inputGrid = new boolean[][]{new boolean[]{false, false, true},
                new boolean[]{true, false, true},
                new boolean[]{true, true, false}};
        assertEquals(2, PathValidator.getNumberOfReachableFields(inputGrid, 3, 3, 1, 0));
    }
}