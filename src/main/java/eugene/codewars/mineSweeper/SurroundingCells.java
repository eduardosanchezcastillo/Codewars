package eugene.codewars.mineSweeper;

import eugene.codewars.mineSweeper.cells.CellPosition;

import java.util.Iterator;

public class SurroundingCells implements Iterable<CellPosition> {
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;

    public SurroundingCells(int x, int y, int boardWidth, int boardHeight, int radius) {
        xMin = Math.max(x - radius, 0);
        xMax = Math.min(x + radius, boardWidth - 1);
        yMin = Math.max(y - radius, 0);
        yMax = Math.min(y + radius, boardHeight - 1);
    }

    @Override
    public Iterator<CellPosition> iterator() {
        return new AreaIterator();
    }

    class AreaIterator implements Iterator<CellPosition> {

        private int x = SurroundingCells.this.xMin;
        private int y = SurroundingCells.this.yMin;

        @Override
        public boolean hasNext() {
            return x <= xMax && y <= yMax;
        }

        @Override
        public CellPosition next() {
            if (hasNext()) {
                CellPosition result = new CellPosition(x, y);
                if (++y > yMax) {
                    y = yMin;
                    x++;
                }
                return result;
            }

            return null;
        }
    }
}