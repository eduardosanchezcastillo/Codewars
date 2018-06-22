package eugene.codewars;

/*
    Battleship field validator
    https://www.codewars.com/kata/battleship-field-validator/java

    TASK:
    Write a method that takes a field for well-known board game "Battleship" as an argument and returns true if it has a valid disposition of ships,
    false otherwise. Argument is guaranteed to be 10*10 two-dimension array.
    Elements in the array are numbers, 0 if the cell is free and 1 if occupied by ship.

    Battleship (also Battleships or Sea Battle) is a guessing game for two players.
    Each player has a 10x10 grid containing several "ships" and objective is to destroy enemy's forces by targeting individual cells on his field.
    The ship occupies one or more cells in the grid. Size and number of ships may differ from version to version.
    In this kata we will use Soviet/Russian version of the game.

    Before the game begins, players set up the board and place the ships accordingly to the following rules:
        - There must be single battleship (size of 4 cells), 2 cruisers (size 3), 3 destroyers (size 2) and 4 submarines (size 1).
          Any additional ships are not allowed, as well as missing ships.
        - Each ship must be a straight line, except for submarines, which are just single cell.
        - The ship cannot overlap or be in contact with any other ship, neither by edge nor by corner.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BattleshipFieldValidator {

    private static final int VERIFIED_CELL = -1;
    private static final int EMPTY_CELL = 0;
    private static final int SHIP_CELL = 1;

    public static boolean fieldValidator(int[][] field) {
        Map<Integer, Integer> sizeToCount = new HashMap<>();

        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                int value = getValue(x, y, field);
                if (value == VERIFIED_CELL || value == EMPTY_CELL) {
                    continue;
                }

                int shipSize = 1;
                field[x][y] = VERIFIED_CELL;        // all processed "ship" cells should be changed so we won't process them again later

                // Found a ship. Since we're traversing the field from left to right, and from top to bottom,
                // we can be sure that this ship goes either right or down. No need to check up/left.
                // Let's start with "right"
                while (getValue(x, y + shipSize, field) == SHIP_CELL) {
                    field[x][y + shipSize] = VERIFIED_CELL;
                    shipSize++;
                }

                if (shipSize > 1) {
                    if (hasConnectingShips(x, y, 0, 1, shipSize, field)) {
                        return false;   // touching another ship..
                    }
                }

                if (shipSize == 1) {
                    // Maybe, down? This branch also handles the single-cell ships.
                    while (getValue(x + shipSize, y, field) == SHIP_CELL) {
                        field[x + shipSize][y] = VERIFIED_CELL;
                        shipSize++;
                    }

                    if (hasConnectingShips(x, y, 1, 0, shipSize, field)) {
                        return false;   // touching another ship..
                    }
                }

                if (shipSize > 4) {
                    return false;   // can't afford such big ships..
                }

                // This is a valid-sized ship, so we need to save it
                sizeToCount.compute(shipSize, (size, count) -> count == null ? 1 : count + 1);
            }
        }

        // If we're here, then ships are not touching each other.
        // We just need to verify there's a proper number of each ship.
        return Objects.equals(sizeToCount.get(1), 4)
                && Objects.equals(sizeToCount.get(2), 3)
                && Objects.equals(sizeToCount.get(3), 2)
                && Objects.equals(sizeToCount.get(4), 1);
    }

    private static boolean hasConnectingShips(int startX, int startY, int deltaX, int deltaY, int shipSize, int[][] field) {
        for (int shift = 0; shift < shipSize; shift++) {
            int x = startX + deltaX * shift;
            int y = startY + deltaY * shift;
            if (isShipCell(x - 1, y - 1, field)
                    || isShipCell(x, y - 1, field)
                    || isShipCell(x + 1, y - 1, field)
                    || isShipCell(x + 1, y, field)
                    || isShipCell(x + 1, y + 1, field)
                    || isShipCell(x, y + 1, field)
                    || isShipCell(x - 1, y + 1, field)
                    || isShipCell(x - 1, y, field)) {
                return true;
            }
        }
        return false;
    }

    private static int getValue(int x, int y, int[][] field) {
        if (x < 0 || x >= field.length || y < 0 || y >= field.length) {
            return EMPTY_CELL;
        }
        return field[x][y];
    }

    private static boolean isShipCell(int x, int y, int[][] field) {
        return getValue(x, y, field) == SHIP_CELL;
    }
}
