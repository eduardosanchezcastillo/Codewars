package eugene.codewars.checkAndMate;

class Position {

    static final int BOARD_SIZE = 8;

    int x;
    int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean apply(Move move) {
        x += move.dX;
        y += move.dY;
        return isValid();
    }

    boolean isValid() {
        return x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE;
    }

    Position copy() {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
