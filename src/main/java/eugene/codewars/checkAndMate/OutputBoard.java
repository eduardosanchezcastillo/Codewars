package eugene.codewars.checkAndMate;

class OutputBoard {
    private static final char EMPTY_CHAR = '♙';  // a hack to keep constant width of board cells

    static void print(PieceConfig[] game) {
        String[][] board = new String[8][8];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y] = print(x, y, EMPTY_CHAR, -1);
            }
        }

        for (PieceConfig piece : game) {
            board[piece.getY()][piece.getX()] = print(piece);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("   0  1  2  3  4  5  6  7\n");  // NOTE: a wide-space is used here: ' ', U+2003
        sb.append("   A  B  C  D  E  F  G  H\n");  // NOTE: a wide-space is used here: ' ', U+2003
        for (int i = 0; i < board.length; i++) {
            sb.append(i + 1)
                    .append(' ')
                    .append(String.join("", board[i]))
                    .append('\n');
        }

        System.out.println(sb.toString());
    }

    private static String print(PieceConfig piece) {
        return print(
                piece.getX(),
                piece.getY(),
                print(piece.getPiece(), piece.getOwner()),
                piece.getOwner());
    }

    private static char print(String piece, int owner) {
        if (owner == 0) {
            switch (piece) {
                case "pawn":
                    return '♙';
                case "rook":
                    return '♖';
                case "knight":
                    return '♘';
                case "bishop":
                    return '♗';
                case "queen":
                    return '♕';
                case "king":
                    return '♔';
            }
        } else if (owner == 1) {
            switch (piece) {
                case "pawn":
                    return '♟';
                case "rook":
                    return '♜';
                case "knight":
                    return '♞';
                case "bishop":
                    return '♝';
                case "queen":
                    return '♛';
                case "king":
                    return '♚';
            }
        }
        throw new RuntimeException("Unhandled piece!");
    }

    private static String print(int x, int y, char ch, int owner) {
        int bkColor = (x + y) % 2 == 0 ? 40 : 100;
        int color = getColor(owner, bkColor - 10);
        return "\u001B[" + color + ";" + bkColor + "m " + ch + " \u001B[0m";
    }

    private static int getColor(int owner, int def) {
        switch (owner) {
            case 0:
                return 93;
            case 1:
                return 96;
            default:
                return def;
        }
    }
}
