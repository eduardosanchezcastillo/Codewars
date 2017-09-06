package eugene.codewars.mineSweeper.cells;

public enum CellType {
    KNOWN_NUMBER(0),
    MINE(-1),
    UNKNOWN(-2),
    UNKNOWN_NUMBER(-3);

    private final int value;

    CellType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
