package eugene.codewars.mineSweeper;

class MineSweeper {

    private static final boolean PRETTY_PRINT = true;
    private static final boolean SHOW_ITERATIONS = true;

    private final Board board;

    public MineSweeper(final String board, final int nMines) {
        this.board = new Board(board, nMines);
    }

    String solve() {
        System.out.println();
        System.out.println("=========================================");
        logBoard("Input board:");

        Brain brain = new Brain(board, pos -> Game.open(pos.x, pos.y));

        // The order is important here!
        Approach approaches[] = {
                brain::processAllRemainingAreMines,
                brain::processAllRemainingAreEmpty,
                brain::processObvious,
                brain::processAlmostKnownMines,
                brain::bruteForceRemaining
        };

        int iteration = 1;
        do {
            board.reset();

            for (Approach approach : approaches) {
                approach.perform();
                if (board.isChanged()) {
                    break;
                }
            }

            if (SHOW_ITERATIONS) {
                logBoard(String.format("Iteration %d, remaining mines: %d", iteration++, board.getRemainingMinesCount()));
            }
        } while (board.isChanged() && board.getRemainingUnknownCellsCount() > 0);

        // moment of truth
        if (board.getRemainingMinesCount() == 0) {
            return board.getBoardAsString();
        } else {
            logBoard("Can't solve this. Remaining mines: " + board.getRemainingMinesCount());
            return "?";
        }
    }

    private void logBoard(String message) {
        String prettyBoard = board.getBoardAsString()
                .replaceAll("\\?", "·")
                .replaceAll("0", " ");
        if (PRETTY_PRINT) {
            prettyBoard = prettyBoard.replaceAll("x", "\u001B[31mx\u001B[0m");
        }

        System.out.println(message);
        System.out.println(prettyBoard);
        System.out.println("......................................");
    }

    @FunctionalInterface
    interface Approach {
        void perform();
    }
}
