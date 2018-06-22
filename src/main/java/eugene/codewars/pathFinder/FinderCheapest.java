package eugene.codewars.pathFinder;

/*
    Find the cheapest path
    https://www.codewars.com/kata/find-the-cheapest-path

    INSTRUCTIONS
    Your task is to find the path through the field which has the lowest cost to go through.

    As input you will receive:
        1) a "toll_map" matrix (as variable "t") which holds data about how expensive it is to go through the given field coordinates
        2) a "start" coordinate (tuple) which holds information about your starting position
        3) a "finish" coordinate (tuple) which holds information about the position you have to get to

    As output you should return:
        1) the directions list

    EXAMPLE

    INPUT:
        toll_map  |  start  |  finish
        [         |         |
         [1,9,1], |  (0,0)  |  (0,2)
         [2,9,1], |         |
         [2,1,1], |         |
        ]         |         |

    OUTPUT:
        ["down", "down", "right", "right", "up", "up"]

    CLARIFICATIONS
        1) the "start" and "finish" tuples have the (x, y) format which means start = (x_1, y_1) and finish = (x_2, y_2),
           start_pos = field[x_1][y_1] and finish_pos = field[x_2][y_2]
        2) the total cost is increased after leaving the matrix coordinate, not entering it
        3) the field will be rectangular, not necessarily a square
        4) the field will always be of correct shape
        5) the actual tests will check "total_cost" based on your returned directions list, not the directions themselves,
           so you shouldn't worry about having multiple possible solutions
*/

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinderCheapest {
    static List<String> cheapestPath(int[][] t, Point start, Point finish) {
        return new FinderCheapest(t, start, finish).findPath();
    }

    private static final int CELL_UNKNOWN = -2;
    private static final int CELL_EMPTY = 0;

    private final int sizeX;
    private final int sizeY;

    private final int[][] map;          // original map of step costs
    private final int[][] field;        // field of calculated costs from starting point to current point
    private final PathStep[][] steps;   // tail of the shortest path from starting point to current point

    private final Point start;
    private final Point finish;

    private List<Point> changedPoints;  // list of changes made to the field during the latest iteration

    private FinderCheapest(int[][] map, Point start, Point finish) {
        this.map = map;
        sizeX = map.length;
        sizeY = map[0].length;

        this.start = start;
        this.finish = finish;

        field = new int[sizeX][sizeY];
        steps = new PathStep[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            field[x] = new int[sizeY];
            steps[x] = new PathStep[sizeY];
        }
    }

    private List<String> findPath() {
        if (start.equals(finish)) {
            return Collections.emptyList();
        }

        changedPoints = Collections.singletonList(start);

        // Optimization: don't need to look at all cells on the field.
        // We should only concentrate on the cells that were changed during last iteration
        // because those are the only cells that will produce new paths.
        while (!changedPoints.isEmpty()) {
            List<Point> oldChanges = changedPoints;
            changedPoints = new ArrayList<>();

            for (Point point : oldChanges) {
                int value = getValue(point.x, point.y);
                PathStep pathStep = steps[point.x][point.y];
                updateValue(point.x + 1, point.y, value, pathStep, "down");
                updateValue(point.x, point.y + 1, value, pathStep, "right");
                updateValue(point.x - 1, point.y, value, pathStep, "up");
                updateValue(point.x, point.y - 1, value, pathStep, "left");
            }
        }

        if (field[finish.x][finish.y] <= 0) {
            // can't reach the finish point..
            return Collections.emptyList();
        }

        // each cell (that was reached at least once) references the tail of the shortest path
        List<String> result = new ArrayList<>();
        PathStep pathStep = steps[finish.x][finish.y];
        while (pathStep != null) {
            result.add(0, pathStep.direction);
            pathStep = pathStep.previousStep;
        }

        return result;
    }

    private void updateValue(int x, int y, int distance, PathStep previousStep, String direction) {
        int oldValue = getValue(x, y);
        if (oldValue < 0) {
            return;
        }

        int newValue = distance + map[x][y];

        if (oldValue == CELL_EMPTY || newValue < oldValue) {
            field[x][y] = newValue;
            steps[x][y] = new PathStep(previousStep, direction);
            changedPoints.add(new Point(x, y));
        }
    }

    private int getValue(int x, int y) {
        if (x >= 0 && y >= 0 && x < sizeX && y < sizeY) {
            return field[x][y];
        }
        return CELL_UNKNOWN;
    }

    private static class PathStep {
        final PathStep previousStep;
        final String direction;

        PathStep(PathStep previousStep, String direction) {
            this.previousStep = previousStep;
            this.direction = direction;
        }
    }
}
