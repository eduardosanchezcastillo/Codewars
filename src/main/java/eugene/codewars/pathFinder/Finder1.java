package eugene.codewars.pathFinder;

/*
    Path Finder #1: can you reach the exit?
    https://www.codewars.com/kata/path-finder-number-1-can-you-reach-the-exit

    TASK:

    You are at position [0, 0] in maze NxN and you can only move in one of the four cardinal directions (i.e. North, East, South, West).
    Return true if you can reach position [N-1, N-1] or false otherwise.

    Empty positions are marked "."
    Walls are marked "W".
    Start and exit positions are empty in all test cases.
 */

public class Finder1 {

    private static final int CELL_EMPTY = 0;
    private static final int CELL_WALL = -1;
    private static final char MAZE_EMPTY = '.';

    static boolean pathFinder(String maze) {
        int[][] field = parseMaze(maze);
        return canEscape(field, 0, 0);
    }

    private static boolean canEscape(int[][] field, int x, int y) {
        if (x < 0 || y < 0 || x >= field.length || y >= field.length) {
            return false;       // outside of the maze
        }
        if (field[x][y] == CELL_WALL) {
            return false;       // wall!
        }
        if (field[x][y] > 0) {
            return false;       // we've been here before
        }

        if (x == field.length - 1 && y == field.length - 1) {
            return true;        // yay! that's the exit
        }

        field[x][y] = 1;

        return canEscape(field, x, y + 1)
                || canEscape(field, x + 1, y)
                || canEscape(field, x, y - 1)
                || canEscape(field, x - 1, y);
    }

    private static int[][] parseMaze(String maze) {
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
}