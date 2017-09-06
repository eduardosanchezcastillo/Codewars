package eugene.codewars.mineSweeper;

class Game {

    private static String board;

    private static int boardMap[][];

    private static int minesN;

    private static final int MINE = -1;

    static void newGame(String board) {
        Game.board = board;
        Game.boardMap = Board.parseBoard(Game.board);
        Game.minesN = (int) Game.board.chars()
                .filter(c -> c == 'x')
                .count();
    }

    static int open(int x, int y) {
        if (boardMap[x][y] == MINE) {
            throw new RuntimeException("Game over!");
        }
        return boardMap[x][y];
    }

    static int getMinesN() {
        return Game.minesN;
    }
}
