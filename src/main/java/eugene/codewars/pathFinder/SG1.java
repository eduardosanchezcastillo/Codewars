package eugene.codewars.pathFinder;

/*
    Stargate SG-1: Cute and Fuzzy (Improved version)
    https://www.codewars.com/kata/stargate-sg-1-cute-and-fuzzy-improved-version

    Previously on Stargate SG-1
    Arriving on P4F-976, SG-1 finally come into contact with the Furlings, one of the four great races within the Milky Way.
    After several days of deliberation with the Furling Directorate, the Tauri finally have access to the knowledge they have been searching for.

    The Furlings, having provided assistance with the Ancient's expansion into the Milky Way, have extensive knowledge of the Stargate Network and it's components.
    One such component, the Dial Home Device, has caused many disasters at Stargate Command through it's absence.
    Thankfully, the Furlings have all the necessary blueprints for it's construction, and have handed copies over to the Tauri.
    After beginning mass production of the control crystals necessary for it's function, Stargate Command has hit a snag.
    The Ancients had designed the control crystals to function if their inner pathways are as efficient as possible - essentially,
    the pathways must choose the shortest path between two nodes. Stargate Command has turned to you - a software engineer - to fix their problems.

    Your Mission
    Given a string containing the current state of the control crystals inner pathways (labeled as "X") and its gaps (labeled as "."),
    generate the shortest path from the start node (labeled as "S") to the goal node (labeled as "G") and return the new pathway (labeled with "P" characters).
    If no solution is possible, return the string "Oh for crying out loud..." (in frustration).

    The Rules
    - Nodes labeled as "X" are not traversable.
    - Nodes labeled as "." are traversable.
    - A pathway can be grown in eight directions (up, down, left, right, up-left, up-right, down-left, down-right), so diagonals are possible.
    - Nodes labeled "S" and "G" are not to be replaced with "P" in the case of a solution.
    - The shortest path is defined as the path with the shortest euclidiean distance going from one node to the next.
    - If several paths are possible with the same shortest distance, return any one of them.

    Note that the mazes won't always be squares.

    Example #1: Valid solution
        .S...             .SP..
        XXX..             XXXP.
        .X.XX      =>     .XPXX
        ..X..             .PX..
        G...X             G...X

    Example #2: No solution
        S....
        XX...
        ...XX      =>     "Oh for crying out loud..."
        .XXX.
        XX..G

    Note: Your solution will have to be efficient because it will have to deal with a lot of maps and big ones.

    Caracteristics of the random tests:
    - map sizes from 3x3 to 73x73 (step is 5 from one size to the other, mazes won't always be squares)
    - 20 random maps for each size.
    - Overall, 311 tests to pass with the fixed ones.
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SG1 {
    public static String wireDHD(String existingWires) {
        return new SG1(existingWires).solve();
    }

    private static final char INPUT_EMPTY = '.';
    private static final char INPUT_WIRE = 'X';
    private static final char INPUT_START = 'S';
    private static final char INPUT_FINISH = 'G';
    private static final char INPUT_PATH = 'P';

    private static final double CELL_UNKNOWN = -2;
    private static final double CELL_WALL = -1;
    private static final double CELL_EMPTY = 0;
    private static final double CELL_PATH = 1;

    private static final double LENGTH_STRAIGHT = 1;
    private static final double LENGTH_DIAGONAL = Math.sqrt(2 * LENGTH_STRAIGHT * LENGTH_STRAIGHT);

    private final int sizeX;
    private final int sizeY;

    private final double[][] field;     // field of calculated distances from starting point to current point
    private final PathStep[][] steps;   // tail of the shortest path from starting point to current point

    private final Point start;
    private final Point finish;

    private List<Point> changedPoints;  // list of changes made to the field during the latest iteration

    private SG1(String existingWires) {
        Point start = null;
        Point finish = null;

        String[] rows = existingWires.split("\n");
        sizeX = rows.length;
        sizeY = rows[0].length();

        field = new double[sizeX][sizeY];
        steps = new PathStep[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            field[x] = new double[sizeY];
            steps[x] = new PathStep[sizeY];

            for (int y = 0; y < sizeY; y++) {
                char wire = rows[x].charAt(y);
                field[x][y] = wireToCell(wire);

                if (wire == INPUT_START) {
                    start = new Point(x, y);
                } else if (wire == INPUT_FINISH) {
                    finish = new Point(x, y);
                }
            }
        }

        this.start = start;
        this.finish = finish;
    }

    private String solve() {
        if (start.equals(finish)) {
            return generateResult();
        }

        changedPoints = Collections.singletonList(start);
        field[start.x][start.y] = CELL_PATH;

        // Optimization: don't need to look at all cells on the field.
        // We should only concentrate on the cells that were changed during last iteration
        // because those are the only cells that will produce new paths.
        while (!changedPoints.isEmpty()) {
            List<Point> oldChanges = changedPoints;
            changedPoints = new ArrayList<>();

            for (Point point : oldChanges) {
                double distance = getValue(point.x, point.y);
                PathStep pathStep = steps[point.x][point.y];
                updateValue(point.x, point.y - 1, distance + LENGTH_STRAIGHT, pathStep);
                updateValue(point.x + 1, point.y, distance + LENGTH_STRAIGHT, pathStep);
                updateValue(point.x, point.y + 1, distance + LENGTH_STRAIGHT, pathStep);
                updateValue(point.x - 1, point.y, distance + LENGTH_STRAIGHT, pathStep);

                // diagonal movements cost more
                updateValue(point.x + 1, point.y - 1, distance + LENGTH_DIAGONAL, pathStep);
                updateValue(point.x - 1, point.y - 1, distance + LENGTH_DIAGONAL, pathStep);
                updateValue(point.x + 1, point.y + 1, distance + LENGTH_DIAGONAL, pathStep);
                updateValue(point.x - 1, point.y + 1, distance + LENGTH_DIAGONAL, pathStep);
            }
        }

        if (field[finish.x][finish.y] <= 0) {
            // can't reach the finish point..
            return "Oh for crying out loud...";
        }

        // erase all the numbers we put earlier
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (field[x][y] > 0) {
                    field[x][y] = 0;
                }
            }
        }

        // now update the path cells.
        // each cell (that was reached at least once) references the tail of the shortest path
        PathStep pathStep = steps[finish.x][finish.y];
        while (pathStep != null) {
            field[pathStep.x][pathStep.y] = CELL_PATH;
            pathStep = pathStep.previousStep;
        }

        return generateResult();
    }

    private void updateValue(int x, int y, double newValue, PathStep previousStep) {
        double oldValue = getValue(x, y);
        if (oldValue < 0) {
            return;
        }

        if (oldValue == CELL_EMPTY || newValue < oldValue) {
            field[x][y] = newValue;
            steps[x][y] = new PathStep(previousStep, x, y);
            changedPoints.add(new Point(x, y));
        }
    }

    private double getValue(int x, int y) {
        if (x >= 0 && y >= 0 && x < sizeX && y < sizeY) {
            return field[x][y];
        }
        return CELL_UNKNOWN;
    }

    private double wireToCell(char wire) {
        switch (wire) {
            case INPUT_EMPTY:
            case INPUT_START:
            case INPUT_FINISH:
                return CELL_EMPTY;
            case INPUT_WIRE:
                return CELL_WALL;
        }
        throw new RuntimeException("Unhandled wire: " + wire);
    }

    private char cellToWire(int x, int y) {
        if (x == start.x && y == start.y) {
            return INPUT_START;
        }
        if (x == finish.x && y == finish.y) {
            return INPUT_FINISH;
        }

        double cell = field[x][y];
        if (cell > 0) {
            return INPUT_PATH;
        } else if (cell == CELL_WALL) {
            return INPUT_WIRE;
        }

        return INPUT_EMPTY;
    }

    private String generateResult() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                sb.append(cellToWire(x, y));
            }
            if (x < sizeX - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private static class PathStep {
        final PathStep previousStep;
        final int x;
        final int y;

        PathStep(PathStep previousStep, int x, int y) {
            this.previousStep = previousStep;
            this.x = x;
            this.y = y;
        }
    }
}
