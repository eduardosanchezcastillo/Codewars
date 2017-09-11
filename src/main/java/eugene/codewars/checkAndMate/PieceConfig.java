package eugene.codewars.checkAndMate;

class PieceConfig {

    private static final int NO_VALUE = Integer.MAX_VALUE;
    private final String piece;
    private final int owner;
    private final int x;
    private final int y;
    private final int prevX;
    private final int prevY;

    PieceConfig(final String piece, final int owner, final int x, final int y, final int prevX, final int prevY) {
        this.piece = piece;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    PieceConfig(final String piece, final int owner, final int x, final int y) {
        this(piece, owner, x, y, NO_VALUE, NO_VALUE);
    }

    String getPiece() {
        return piece;
    }

    int getOwner() {
        return owner;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    private boolean hasPrevious() {
        return prevX != NO_VALUE && prevY != NO_VALUE;
    }

    // will throw a RuntimeException if invoked for an object that do not have informations about its previous move.
    int getPrevX() {
        if (hasPrevious()) {
            return prevX;
        }
        throw new RuntimeException("No previous!");
    }

    // will throw a RuntimeException if invoked for an object that do not have informations about its previous move.
    int getPrevY() {
        if (hasPrevious()) {
            return prevY;
        }
        throw new RuntimeException("No previous!");
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PieceConfig that = (PieceConfig) o;

        if (owner != that.owner) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;
        return piece.equals(that.piece);
    }

    @Override
    public int hashCode() {
        int result = piece.hashCode();
        result = 31 * result + owner;
        result = 31 * result + x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "piece=" + piece +
                ", owner=" + owner +
                ", x=" + x +
                ", y=" + y;
    }
}
