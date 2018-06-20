package eugene.codewars.skyscrappers;

public class LineView {
    private final int startRow;
    private final int startColumn;
    private final int deltaRow;
    private final int deltaColumn;
    private final int data[][];
    private final int clue;

    public static LineView[] createFourLines(int row, int column, int[][] data, int size, int[] clues) {
        return new LineView[] {
                new LineView(row, 0, 0, 1, data, clues[(size * 4) - row - 1]),              // horizontal forward
                new LineView(row, size - 1, 0, -1, data, clues[size + row]),                // horizontal backward
                new LineView(0, column, 1, 0, data, clues[column]),                         // vertical forward
                new LineView(size - 1, column, -1, 0, data, clues[(size * 3) - column - 1]) // vertical backward
        };
    }

    public LineView(int startRow, int startColumn, int deltaRow, int deltaColumn, int[][] data, int clue) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.deltaRow = deltaRow;
        this.deltaColumn = deltaColumn;
        this.data = data;
        this.clue = clue;
    }

    public int getValue(int index) {
        int row = getRow(index);
        int column = getColumn(index);
        return data[row][column];
    }

    public void setValue(int index, int value) {
        int row = getRow(index);
        int column = getColumn(index);
        data[row][column] = value;
    }

    public int getClue() {
        return clue;
    }

    public boolean isForward() {
        return deltaRow >= 0 && deltaColumn >= 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(clue);
        sb.append(" -> [ ");

        int i = startRow;
        int j = startColumn;
        while (i >= 0 && j >= 0 && i < data.length && j < data[i].length) {
            sb.append(data[i][j]);
            sb.append(" ");
            i += deltaRow;
            j += deltaColumn;
        }

        sb.append("]");
        return sb.toString();
    }

    private int getColumn(int index) {
        return startColumn + deltaColumn * index;
    }

    private int getRow(int index) {
        return startRow + deltaRow * index;
    }
}
