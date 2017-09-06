package eugene.codewars.mineSweeper;

import eugene.codewars.mineSweeper.cells.CellData;
import eugene.codewars.mineSweeper.cells.CellPosition;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

class Brain {

    private final Board board;

    private final Set<CellPosition> finishedCells = new HashSet<>();

    private final ToIntFunction<CellPosition> cellValueProvider;

    Brain(Board board, ToIntFunction<CellPosition> cellValueProvider) {
        this.board = board;
        this.cellValueProvider = cellValueProvider;
    }

    void processObvious() {
        iterateCells(board::isNumber, this::processCell);
    }

    void processAllRemainingAreMines() {
        if (board.getRemainingMinesCount() > 0 && board.getRemainingMinesCount() == board.getRemainingUnknownCellsCount()) {
            iterateCells(board::isUnknown, pos -> board.setMine(pos.x, pos.y));
        }
    }

    void processAllRemainingAreEmpty() {
        if (board.getRemainingUnknownCellsCount() > 0 && board.getRemainingMinesCount() == 0) {
            iterateCells(board::isUnknown, pos -> board.setNumber(pos.x, pos.y, Game.open(pos.x, pos.y)));
        }
    }

    void processAlmostKnownMines() {
        List<CellPosition> numberCells = new ArrayList<>();
        iterateCells(board::isNumber, numberCells::add);

        int almostKnownCount = numberCells.stream()
                .map(pos -> new CellData(board, pos))
                .mapToInt(data -> data.remainingMinesCount)
                .sum();
        if (almostKnownCount == board.getRemainingMinesCount()) {
            // We know that all remaining mines are somewhere near the unfinished number cells.
            // Therefore, all other unknown cells are empty and can be opened safely.
            // IMPORTANT: find all of them first, and then open.
            List<CellPosition> emptyCells = new ArrayList<>();
            iterateCells(
                    board::isUnknown,
                    pos -> {
                        if (new CellData(board, pos).numberCells.isEmpty()) {
                            emptyCells.add(pos);
                        }
                    }
            );
            openAll(emptyCells);
        }
    }

    // This is our last resort. Perform a limited brute-force trying to figure out which cells are definitely empty..
    // and which ones are definitely mines.
    void bruteForceRemaining() {
        BruteForce bruteForce = new BruteForce(board);
        AssumedCells assumedCells = bruteForce.run();
        if (assumedCells != null) {
            openAll(assumedCells.numbers);
            markMines(assumedCells.mines);
        }
    }

    class BruteForce {

        private final Board board;

        private final List<CellPosition> numberCells = new ArrayList<>();

        private final Set<CellPosition> allUnknownCells;

        private final LinkedList<CellPosition> mines = new LinkedList<>();

        private AssumedCells bruteForcedCells;

        BruteForce(Board board) {
            iterateCells(board::isNumber, numberCells::add);
            allUnknownCells = numberCells.stream()
                    .map(pos -> new CellData(board, pos))
                    .flatMap(cellData -> cellData.unknownCells.stream())
                    .collect(Collectors.toSet());

            this.board = new Board(board, true);
        }

        AssumedCells run() {
            processCell(0);
            return bruteForcedCells;
        }

        // IMPORTANT: recursive algorithm
        private void processCell(int cellIndex) {
            if (cellIndex == numberCells.size()) {
                // all cells are processed and we are ready to collect the result
                saveResult();
                return;
            }

            if (isHopeless()) {
                return;
            }

            // calculate all possible mine positions for the next number cell
            CellData cellData = new CellData(board, numberCells.get(cellIndex));
            nextMine(cellData.unknownCells, 0, cellData.remainingMinesCount, cellIndex);
        }

        private void nextMine(List<CellPosition> cells, int startIndex, int remainingMinesCount, int cellIndex) {
            if (remainingMinesCount == 0) {
                processCell(cellIndex + 1);
            } else {
                for (int i = startIndex; !isHopeless() && i <= cells.size() - remainingMinesCount; i++) {
                    CellPosition pos = cells.get(i);
                    board.setMine(pos.x, pos.y);
                    mines.push(pos);

                    if (board.isValid() && board.getRemainingMinesCount() >= 0) {
                        if (remainingMinesCount > 1) {
                            // there are more mines to set up. keep going
                            nextMine(cells, i + 1, remainingMinesCount - 1, cellIndex);
                        } else {
                            // this was the last mine for current cell. proceed to the next cell now
                            processCell(cellIndex + 1);
                        }
                    }

                    // undo the last change
                    mines.pop();
                    board.setUnknown(pos.x, pos.y);
                    board.setValid();
                }
            }
        }

        private void saveResult() {
            if (board.getRemainingMinesCount() > board.getRemainingUnknownCellsCount() - (allUnknownCells.size() - mines.size())) {
                return;  // this is not a valid result; there are some mines remaining, but nowhere to put them
            }

            if (bruteForcedCells == null) {
                bruteForcedCells = new AssumedCells(new HashSet<>(mines), new HashSet<>(allUnknownCells));
            } else {
                bruteForcedCells.mines.retainAll(mines);
            }
            bruteForcedCells.numbers.removeAll(mines);
        }

        private boolean isHopeless() {
            return bruteForcedCells != null && bruteForcedCells.mines.isEmpty() && bruteForcedCells.numbers.isEmpty();
        }
    }

    static class AssumedCells {
        Set<CellPosition> mines;
        Set<CellPosition> numbers;

        AssumedCells(Set<CellPosition> mines, Set<CellPosition> numbers) {
            this.mines = mines;
            this.numbers = numbers;
        }
    }

    private void iterateCells(CellFilterFunction filterFunction, Consumer<CellPosition> cellConsumer) {
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (!filterFunction.test(x, y)) {
                    continue;
                }

                CellPosition cellPosition = new CellPosition(x, y);
                if (!finishedCells.contains(cellPosition)) {
                    cellConsumer.accept(cellPosition);
                }
            }
        }
    }

    private void processCell(CellPosition cellPos) {
        CellData cellData = new CellData(board, cellPos);
        if (cellData.remainingMinesCount == 0) {
            openAll(cellData.unknownCells);
        } else if (cellData.remainingMinesCount == cellData.unknownCells.size()) {
            markMines(cellData.unknownCells);
        }

        if (cellData.unknownCells.size() == 0) {
            finishedCells.add(cellPos);
        }
    }

    private void openAll(Collection<CellPosition> list) {
        list.forEach(pos ->
                board.setNumber(pos.x, pos.y, cellValueProvider.applyAsInt(pos))
        );
        list.clear();
    }

    private void markMines(Collection<CellPosition> list) {
        list.forEach(pos ->
                board.setMine(pos.x, pos.y)
        );
        list.clear();
    }

    @FunctionalInterface
    interface CellFilterFunction {
        boolean test(int x, int y);
    }
}
