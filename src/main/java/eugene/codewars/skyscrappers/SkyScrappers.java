package eugene.codewars.skyscrappers;

/*
    4 By 4 Skyscrapers
    https://www.codewars.com/kata/4-by-4-skyscrapers

    6 By 6 Skyscrapers
    https://www.codewars.com/kata/6-by-6-skyscrapers

    NOTE: this solution works for any field size. Just change the static constant.
    The description below is for 4x4, but it is pretty much identical for 6x6.


    In a grid of 4 by 4 squares you want to place a skyscraper in each square with only some clues:
        - The height of the skyscrapers is between 1 and 4
        - No two skyscrapers in a row or column may have the same number of floors
        - A clue is the number of skyscrapers that you can see in a row or column from the outside
        - Higher skyscrapers block the view of lower skyscrapers located behind them

    Can you write a program that can solve this puzzle?


    EXAMPLE:

    To understand how the puzzle works, this is an example of a row with 2 clues.
    Seen from the left side there are 4 buildings visible while seen from the right side only 1:
          ┏━━━┳━━━┳━━━┳━━━┓
        4 ┃   ┃   ┃   ┃   ┃ 1
          ┗━━━┻━━━┻━━━┻━━━┛

    There is only one way in which the skyscrapers can be placed.
    From left-to-right all four buildings must be visible and no building may hide behind another building:
          ┏━━━┳━━━┳━━━┳━━━┓
        4 ┃ 1 ┃ 2 ┃ 3 ┃ 4 ┃ 1
          ┗━━━┻━━━┻━━━┻━━━┛

    Example of a 4 by 4 puzzle with the solution:

                    1   2
          ┏━━━┳━━━┳━━━┳━━━┓
          ┃   ┃   ┃   ┃   ┃
          ┣━━━╋━━━╋━━━╋━━━┫
          ┃   ┃   ┃   ┃   ┃ 2
          ┣━━━╋━━━╋━━━╋━━━┫
        1 ┃   ┃   ┃   ┃   ┃
          ┣━━━╋━━━╋━━━╋━━━┫
          ┃   ┃   ┃   ┃   ┃
          ┗━━━┻━━━┻━━━┻━━━┛
                    3


                    1   2
          ┏━━━┳━━━┳━━━┳━━━┓
          ┃ 2 ┃ 1 ┃ 4 ┃ 3 ┃
          ┣━━━╋━━━╋━━━╋━━━┫
          ┃ 3 ┃ 4 ┃ 1 ┃ 2 ┃ 2
          ┣━━━╋━━━╋━━━╋━━━┫
        1 ┃ 4 ┃ 2 ┃ 3 ┃ 1 ┃
          ┣━━━╋━━━╋━━━╋━━━┫
          ┃ 1 ┃ 3 ┃ 2 ┃ 4 ┃
          ┗━━━┻━━━┻━━━┻━━━┛
                    3

    TASK:
        - Finish:
            public static int[][] solvePuzzle (int[] clues)
        - Pass the clues in an array of 16 items. This array contains the clues around the clock, index:
                0   1   2   3
              ┏━━━┳━━━┳━━━┳━━━┓
           15 ┃   ┃   ┃   ┃   ┃ 4
              ┣━━━╋━━━╋━━━╋━━━┫
           14 ┃   ┃   ┃   ┃   ┃ 5
              ┣━━━╋━━━╋━━━╋━━━┫
           13 ┃   ┃   ┃   ┃   ┃ 6
              ┣━━━╋━━━╋━━━╋━━━┫
           12 ┃   ┃   ┃   ┃   ┃ 7
              ┗━━━┻━━━┻━━━┻━━━┛
                11  10  9   8

        - If no clue is available, add value 0
        - Each puzzle has only one possible solution
        - SolvePuzzle() returns matrix int[][]. The first indexer is for the row, the second indexer for the column.

 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkyScrappers {

    private static final int MAX_COUNT = 4;     // change this for different field size

    private static final Map<Integer, Map<Integer, Set<Integer>>> POSSIBLE_POSITIONS;

    static {
        PossiblePositionsProvider possiblePositionsProvider = new PossiblePositionsProvider(MAX_COUNT);
        POSSIBLE_POSITIONS = possiblePositionsProvider.calculate();
    }

    public static void main(String[] args) {
        print(solvePuzzle(new int[] {1, 2, 2, 3, 3, 2, 1, 3, 2, 2, 1, 3, 2, 2, 3, 1}));
    }

    private static void print(int data[][]) {
        for (int[] row : data) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static int[][] solvePuzzle(int[] clue) {
        return new SkyScrappers().solve(clue);
    }

    public int[][] solve(int[] clues) {
        int[][] data = new int[MAX_COUNT][MAX_COUNT];

        // build views for all cells
        LineView LINES[][][] = new LineView[MAX_COUNT][MAX_COUNT][4];      // 4: horizontal forward/backward, vertical forward/backward
        for (int i = 0; i < LINES.length; i++) {
            for (int j = 0; j < LINES[i].length; j++) {
                LINES[i][j] = LineView.createFourLines(i, j, data, MAX_COUNT, clues);
            }
        }

        // put obvious values before running the algorithm;
        // to use all clues just once, go through cells that are on the main diagonal
        for (int i = 0; i < MAX_COUNT; i++) {
            putObvious(LINES[i][i][0]);
            putObvious(LINES[i][i][1]);
            putObvious(LINES[i][i][2]);
            putObvious(LINES[i][i][3]);
        }

        // make a flat list of all un-filled cells
        List<Cell> cellList = new ArrayList<>();
        for (int i = 0; i < MAX_COUNT; i++) {
            for (int j = 0; j < MAX_COUNT; j++) {
                if (data[i][j] == 0) {
                    cellList.add(new Cell(i, j, LINES[i][j]));
                }
            }
        }

        int index = 0;
        while (index >= 0 && index < cellList.size()) {
            Cell cell = cellList.get(index);

            int nextValue = findNextUnusedValue(cell, data);
            data[cell.row][cell.column] = nextValue;

            if (nextValue == 0) {
                // can't set anything to this cell; clear it and go back to previous one
                System.out.println("No value. Rollback.");
                index--;
            } else if (isCorrect(cell)) {
                // seems to be OK; moving on
                System.out.println("Valid.");
                index++;
            } else {
                // we just set an invalid value; stay here and look for a better value.
                System.out.println("INVALID. Stay here and look for better value.");
            }

            print(data);
            System.out.println();
        }

        return data;
    }

    private static int findNextUnusedValue(Cell cell, int[][] data) {
        int currentValue = data[cell.row][cell.column];
        if (currentValue == MAX_COUNT) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (LineView line : cell.lines) {
            if (!line.isForward()) {
                continue;
            }
            for (int i = 0; i < MAX_COUNT; i++) {
                set.add(line.getValue(i));
            }
        }

        for (int i = currentValue + 1; i <= MAX_COUNT; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return 0;
    }

    private void putObvious(LineView lineView) {
        if (lineView.getClue() == 0) {
            return;
        }
        if (lineView.getClue() == 1) {
            lineView.setValue(0, MAX_COUNT);
        }
        if (lineView.getClue() == MAX_COUNT) {
            for (int i = 0; i < MAX_COUNT; i++) {
                lineView.setValue(i, i + 1);
            }
        }
    }

    private boolean isCorrect(Cell cell) {
        for (LineView line : cell.lines) {
            if (!isCorrect(line)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCorrect(LineView lineView) {
        if (lineView.getClue() <= 0) {
            return true;
        }

        int clue = lineView.getClue();
        int visibleCount = 0;
        int maxHeight = 0;
        int emptySpaces = 0;

        for (int index = 0; index < MAX_COUNT; index++) {
            int height = lineView.getValue(index);
            if (height > maxHeight) {
                visibleCount++;
                maxHeight = height;
            }
            if (height == 0) {
                emptySpaces++;
            }

            if (height > 0 && !POSSIBLE_POSITIONS.get(clue).get(height).contains(index)) {
                return false;   // current height is not allowed here
            }
        }

        if (emptySpaces == 0) {
            return visibleCount == clue;
        }

        return visibleCount + emptySpaces >= clue;
    }

    private static class Cell {
        private final int row;
        private final int column;
        private final LineView lines[];

        private Cell(int row, int column, LineView[] lines) {
            this.row = row;
            this.column = column;
            this.lines = lines;
        }
    }
}
