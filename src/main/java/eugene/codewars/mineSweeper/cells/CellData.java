package eugene.codewars.mineSweeper.cells;

import eugene.codewars.mineSweeper.Board;
import eugene.codewars.mineSweeper.SurroundingCells;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates all necessary data for particular cell using provided board view.
 * Basically, contains info about current cell and all its surrounding cells.
 */
public class CellData {

    public final List<CellPosition> unknownCells = new ArrayList<>();

    public final List<CellPosition> numberCells = new ArrayList<>();

    public final int remainingMinesCount;

    public CellData(Board board, int x, int y) {
        int value = board.getValue(x, y);

        int minesCount = 0;
        SurroundingCells surroundingCells = new SurroundingCells(x, y, board.getWidth(), board.getHeight(), 1);
        for (CellPosition cellPosition : surroundingCells) {
            if (cellPosition.x == x && cellPosition.y == y) {
                continue;
            }
            switch (board.getCellType(cellPosition.x, cellPosition.y)) {
                case KNOWN_NUMBER:
                    numberCells.add(cellPosition);
                    break;
                case MINE:
                    minesCount++;
                    break;
                case UNKNOWN:
                    unknownCells.add(cellPosition);
                    break;
            }
        }

        remainingMinesCount = value - minesCount;
    }

    public CellData(Board board, CellPosition cellPos) {
        this(board, cellPos.x, cellPos.y);
    }
}
