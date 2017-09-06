package eugene.codewars.mineSweeper;

import eugene.codewars.mineSweeper.cells.CellData;
import eugene.codewars.mineSweeper.cells.CellHelper;
import eugene.codewars.mineSweeper.cells.CellType;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final int[][] cells;

    private int remainingMinesCount;

    private int remainingUnknownCellsCount;

    private boolean changed;

    private boolean autoValidate = false;

    private boolean valid = true;

    public Board(String board, int nMines) {
        cells = Board.parseBoard(board);
        remainingMinesCount = nMines;
        remainingUnknownCellsCount = (int) Arrays.stream(cells).flatMapToInt(Arrays::stream)
                .filter(CellHelper::isUnknown)
                .count();
        reset();
    }

    public Board(Board other, boolean autoValidate) {
        remainingMinesCount = other.remainingMinesCount;
        remainingUnknownCellsCount = other.remainingUnknownCellsCount;

        cells = new int[other.cells.length][];
        for (int i = 0; i < other.cells.length; i++) {
            cells[i] = Arrays.copyOf(other.cells[i], other.cells[i].length);
        }

        this.autoValidate = autoValidate;
    }

    public int getWidth() {
        return cells.length;
    }

    public int getHeight() {
        return cells[0].length;
    }

    public int getValue(int x, int y) {
        return cells[x][y];
    }

    public boolean isNumber(int x, int y) {
        return CellHelper.isNumber(cells[x][y]);
    }

    public boolean isUnknown(int x, int y) {
        return CellHelper.isUnknown(cells[x][y]);
    }

    public boolean isMine(int x, int y) {
        return CellHelper.isMine(cells[x][y]);
    }

    public void setMine(int x, int y) {
        set(x, y, CellType.MINE.value());

        if (autoValidate && valid) {
            // 'Valid MINE' means that all surrounding KNOWN_NUMBER cells have a valid count of mines around them.
            // If there are too many mines, the 'remaining mines' count will be negative.
            CellData cellData = new CellData(this, x, y);
            valid = cellData.numberCells.stream()
                    .map(pos -> new CellData(this, pos))
                    .noneMatch(numberCell -> numberCell.remainingMinesCount < 0);
        }
    }

    public void setNumber(int x, int y, int value) {
        if (value < 0 && value != CellType.UNKNOWN_NUMBER.value()) {
            throw new RuntimeException("Only non-negative values are allowed here!");
        }
        set(x, y, value);
    }

    public void setUnknown(int x, int y) {
        set(x, y, CellType.UNKNOWN.value());
    }

    private void set(int x, int y, int value) {
        if (cells[x][y] != value) {
            changed = true;
            if (value == CellType.UNKNOWN.value()) {
                remainingUnknownCellsCount++;
            } else if (isUnknown(x, y)) {
                remainingUnknownCellsCount--;
            }

            if (value == CellType.MINE.value()) {
                remainingMinesCount--;
            } else if (isMine(x, y)) {
                remainingMinesCount++;
            }
        }
        cells[x][y] = value;
    }

    public boolean isChanged() {
        return changed;
    }

    public void reset() {
        changed = false;
    }

    public CellType getCellType(int x, int y) {
        return CellHelper.getCellType(cells[x][y]);
    }

    public int getRemainingMinesCount() {
        return remainingMinesCount;
    }

    public int getRemainingUnknownCellsCount() {
        return remainingUnknownCellsCount;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid() {
        valid = true;
    }

    public String getBoardAsString() {
        return Arrays.stream(cells)
                .map(row -> Arrays.stream(row)
                        .mapToObj(CellHelper::convertBoardCell)
                        .collect(Collectors.joining(" "))
                )
                .collect(Collectors.joining("\n"));
    }

    static int[][] parseBoard(String board) {
        String[] rows = board.split("\n");
        int cells[][] = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            cells[i] = Stream.of(rows[i].split(" "))
                    .mapToInt(CellHelper::convertBoardCell)
                    .toArray();
        }

        return cells;
    }
}
