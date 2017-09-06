package eugene.codewars;

/*
    Optimized Pathfinding Algorithm
    https://www.codewars.com/kata/57b4d2dad2a31c75f7000223

 Imagine a game level represented as a two dimensional array containing fields which the player can traverse.
 The player can move up through the rows and sideways through the columns. A 4x4 level might look like this:

 rows
 3 |__|__|__|__|
 2 |__|__|__|__|
 1 |__|__|PL|__|              PL = Player position
 0 |__|__|__|__|
 0  1  2  3  columns
 In this example the player is at row: 1, column: 2. As mentioned, you can move straight up (to row: 2, column: 2)
 or sideways (e.g. to row: 1, column: 3), but never down.

 OK? Good.
 It gets more complicated; some of the fields are blocked and the player has to navigate around them
 (bet you weren't expecting that ay?):

 rows
 3 |□□|__|__|□□|
 2 |__|__|__|__|
 1 |__|__|PL|□□|              □□ = Blocked field
 0 |__|□□|□□|__|
 0  1  2  3  columns

 Damn those pesky level designers.. they've asked you to type up some magical algorithm that can check whether a given
 level is beatable i.e. that there is at least one valid path from the player's position all the way to the top row.

 An un-winnable level:

 3 |__|__|__|__|
 2 |□□|□□|□□|□□|
 1 |__|__|__|__|         "That's bullsh*t!" - Player
 0 |__|PL|__|__|
 0  1  2  3

 Your task is to write a function that will return the number of reachable fields in the last/top row.
 int GetNumberOfReachableFields(bool[][] grid, int rows, int columns, int startRow, int startColumn)

 INPUT
 grid: A 2d array of boolean values; true means a field is traversable, false means it's blocked.
 Access the array using grid[row][col].
    rows: Number of rows in the grid/level. (1 <= rows <= 2000)
    columns: Number of columns in the grid/level. (1 <= columns <= 500)
    startRow: The row of the player's starting position. (0 <= startRow < rows)
    startColumn: The column of the player's starting position. (0 <= startColumn < columns)

 OUTPUT
 Return, as an integer value greater or equal to zero, the number of unique fields in row: grid[rows-1] that the player
 can reach using the aforementioned moves (step forward, left or right).

 More level examples:

 RE = Reachable field

 4 |RE|RE|RE|                           4 |RE|RE|□□|RE|
 3 |__|__|__|                           3 |□□|__|__|__|
 2 |__|□□|□□|      2 |__|__|□□|□□|      2 |__|__|□□|□□|        2 |RE|
 1 |__|__|__|      1 |□□|□□|__|__|      1 |□□|PL|__|__|        1 |__|
 0 |__|PL|__|      0 |__|□□|__|PL|      0 |□□|□□|__|□□|        0 |PL|
 0  1  2           0  1  2  3           0  1  2  3             0
 Output: 3           Output: 0            Output: 3         Output: 1

 NOTES
 • The player cannot step onto a blocked field and diagonal moves are not allowed.
 • The player will never start on a blocked field - you don't need to validate this.
 • The grid array will always contain at least one element.
 • Watch out for performance and note that you don't need to remember the paths the player took.
*/

import java.util.Arrays;

public class PathValidator {
    public static int getNumberOfReachableFields(boolean[][] input, int rows, int columns, int startRow, int startColumn) {
        int[][] grid = createGrid(input, rows, columns);
        expand(grid, startRow, startColumn);
        return (int) Arrays.stream(grid[grid.length - 1]).filter(v -> v > 0).count();
    }

    private static int[][] createGrid(boolean[][] input, int rows, int columns) {
        int[][] grid = new int[rows][columns];
        for (int ii = 0; ii < input.length; ii++) {
            for (int jj = 0; jj < input[ii].length; jj++) {
                grid[ii][jj] = input[ii][jj] ? 0 : -1;
            }
        }
        return grid;
    }

    private static void expand(int[][] grid, int ii, int jj) {
        if (ii < 0 || ii >= grid.length || jj < 0 || jj >= grid[0].length || grid[ii][jj] != 0) {
            return;
        }

        grid[ii][jj] = 1;
        expand(grid, ii, jj - 1);
        expand(grid, ii, jj + 1);
        expand(grid, ii + 1, jj);
    }
}
