package eugene.codewars.mineSweeper.cells;

public class CellHelper {
    public static boolean isNumber(int value) {
        return value >= 0;
    }

    public static boolean isUnknown(int value) {
        return value == CellType.UNKNOWN.value();
    }

    public static boolean isUnknownNumber(int value) {
        return value == CellType.UNKNOWN_NUMBER.value();
    }

    public static boolean isMine(int value) {
        return value == CellType.MINE.value();
    }

    public static CellType getCellType(int value) {
        if (isNumber(value)) return CellType.KNOWN_NUMBER;
        if (isMine(value)) return CellType.MINE;
        if (isUnknownNumber(value)) return CellType.UNKNOWN_NUMBER;
        return CellType.UNKNOWN;
    }

    public static int convertBoardCell(String cell) {
        switch (cell) {
            case "x":
                return CellType.MINE.value();
            case "?":
                return CellType.UNKNOWN.value();
            default:
                return Integer.valueOf(cell);
        }
    }

    public static String convertBoardCell(int value) {
        switch (getCellType(value)) {
            case MINE:
                return "x";
            case UNKNOWN:
                return "?";
            case UNKNOWN_NUMBER:
                return "-";
            default:
                return String.valueOf(value);
        }
    }
}
