package eugene.codewars;

/*
    Sudoku Solution Validator
    https://www.codewars.com/kata/sudoku-solution-validator/java

    Sudoku Background
    Sudoku is a game played on a 9x9 grid. The goal of the game is to fill all cells of the grid with digits from 1 to 9,
    so that each column, each row, and each of the nine 3x3 sub-grids (also known as blocks) contain all of the digits from 1 to 9.
    (More info at: http://en.wikipedia.org/wiki/Sudoku)

    Sudoku Solution Validator
    Write a function validSolution/ValidateSolution/valid_solution() that accepts a 2D array representing a Sudoku board,
    and returns true if it is a valid solution, or false otherwise. The cells of the sudoku board may also contain 0's,
    which will represent empty cells. Boards containing one or more zeroes are considered to be invalid solutions.

    The board is always 9 cells by 9 cells, and every cell only contains integers from 0 to 9.

    Examples

    validSolution([
      [5, 3, 4, 6, 7, 8, 9, 1, 2],
      [6, 7, 2, 1, 9, 5, 3, 4, 8],
      [1, 9, 8, 3, 4, 2, 5, 6, 7],
      [8, 5, 9, 7, 6, 1, 4, 2, 3],
      [4, 2, 6, 8, 5, 3, 7, 9, 1],
      [7, 1, 3, 9, 2, 4, 8, 5, 6],
      [9, 6, 1, 5, 3, 7, 2, 8, 4],
      [2, 8, 7, 4, 1, 9, 6, 3, 5],
      [3, 4, 5, 2, 8, 6, 1, 7, 9]
    ]); // => true

    validSolution([
      [5, 3, 4, 6, 7, 8, 9, 1, 2],
      [6, 7, 2, 1, 9, 0, 3, 4, 8],
      [1, 0, 0, 3, 4, 2, 5, 6, 0],
      [8, 5, 9, 7, 6, 1, 0, 2, 0],
      [4, 2, 6, 8, 5, 3, 7, 9, 1],
      [7, 1, 3, 9, 2, 4, 8, 5, 6],
      [9, 0, 1, 5, 3, 7, 2, 1, 4],
      [2, 8, 7, 4, 1, 9, 6, 3, 5],
      [3, 0, 0, 4, 8, 1, 1, 7, 9]
    ]); // => false
 */

import java.util.*;

public class SudokuValidator {

    private static final int FULL_SIZE = 9;
    private static final int SQUARE_SIZE = 3;

    public static boolean check(int[][] sudoku) {
        List<SudokuIterator> iteratorList = new ArrayList<>();

        for (int x = 0; x < FULL_SIZE; x++) {
            iteratorList.add(new SudokuIterator(x, 0, sudoku, SudokuIterator.Direction.ROW));
        }

        for (int y = 0; y < FULL_SIZE; y++) {
            iteratorList.add(new SudokuIterator(0, y, sudoku, SudokuIterator.Direction.COLUMN));
        }

        int count = FULL_SIZE / SQUARE_SIZE;
        for (int x = 0; x < count; x++) {
            for (int y = 0; y < count; y++) {
                iteratorList.add(new SudokuIterator(x * SQUARE_SIZE, y * SQUARE_SIZE, sudoku, SudokuIterator.Direction.SQUARE));
            }
        }

        for (SudokuIterator iterator : iteratorList) {
            if (!check(iterator)) {
                return false;
            }
        }

        return true;
    }

    private static boolean check(SudokuIterator it) {
        Set<Integer> numbers = new HashSet<>();

        while (it.hasNext()) {
            Integer n = it.next();
            if (n < 1 || n > FULL_SIZE) {
                return false;
            }
            numbers.add(n);
        }

        return numbers.size() == FULL_SIZE;
    }

    private static class SudokuIterator implements Iterator<Integer> {
        enum Direction {ROW, COLUMN, SQUARE}

        private final Direction direction;
        private final int[][] data;
        private int x;
        private int y;

        private int count = 0;

        SudokuIterator(int x, int y, int[][] data, Direction direction) {
            this.x = x;
            this.y = y;
            this.data = data;
            this.direction = direction;
        }

        @Override
        public boolean hasNext() {
            return count < FULL_SIZE;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new IllegalStateException();
            }

            int result = data[x][y];

            count++;
            switch (direction) {
                case ROW:
                    y++;
                    break;
                case COLUMN:
                    x++;
                    break;
                case SQUARE:
                    if (count % SQUARE_SIZE == 0) {
                        x -= SQUARE_SIZE - 1;
                        y++;
                    } else {
                        x++;
                    }
                    break;
            }

            return result;
        }
    }
}
