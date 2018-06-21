package eugene.codewars.pathFinder;

/*
    Path Finder #2: shortest path
    https://www.codewars.com/kata/path-finder-number-2-shortest-path

    TASK:

    You are at position [0, 0] in maze NxN and you can only move in one of the four cardinal directions (i.e. North, East, South, West).
    Return the minimal number of steps to exit position [N-1, N-1] if it is possible to reach the exit from the starting position.
    Otherwise, return -1.

    Empty positions are marked "."
    Walls are marked "W".
    Start and exit positions are empty in all test cases.
 */

public class Finder2 {

    private static final int CELL_UNKNOWN = -2;
    private static final int CELL_WALL = -1;
    private static final int CELL_EMPTY = 0;

    private static final char MAZE_EMPTY = '.';

    private final int[][] field;
    private boolean madeChange;

    static int pathFinder(String maze) {
        System.out.println(maze);
        return new Finder2(maze).findPathLength();
    }

    private Finder2(String maze) {
        field = parseMaze(maze);
    }

    private int findPathLength() {
        final int size = field.length;
        field[0][0] = 1;

        do {
            madeChange = false;
            for (int ii = 0; ii < size; ii++) {
                for (int jj = 0; jj < size; jj++) {
                    int value = getValue(ii, jj);
                    if (value > 0) {
                        setValueIfLower(ii + 1, jj, value + 1);
                        setValueIfLower(ii - 1, jj, value + 1);
                        setValueIfLower(ii, jj + 1, value + 1);
                        setValueIfLower(ii, jj - 1, value + 1);
                    }
                }
            }
        } while (madeChange);

        if (field[size - 1][size - 1] > 0) {
            return field[size - 1][size - 1] - 1;   // this is our shortest path
        }

        return -1;
    }

    private int[][] parseMaze(String maze) {
        String[] rows = maze.split("\n");

        int size = rows.length;
        int[][] result = new int[size][size];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].chars()
                    .map(ch -> ch == MAZE_EMPTY ? CELL_EMPTY : CELL_WALL)
                    .toArray();
        }

        return result;
    }

    private void setValueIfLower(int i, int j, int value) {
        int oldValue = getValue(i, j);
        if (oldValue == CELL_EMPTY || value < oldValue) {
            field[i][j] = value;
            madeChange = true;
        }
    }

    private int getValue(int i, int j) {
        if (i >= 0 && j >= 0 && i < field.length && j < field.length) {
            return field[i][j];
        }
        return CELL_UNKNOWN;
    }
}