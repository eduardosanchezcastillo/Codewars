package eugene.codewars;

/*
    Battle ships: Sunk damaged or not touched?
    https://www.codewars.com/kata/58d06bfbc43d20767e000074


 Your task in the kata is to determine how many boats are sunk damaged and untouched from a set amount of attacks.
 You will need to create a function that takes two arguments, the playing board and the attacks.

 Example Game

 The board

 Y \ X| 1 2 3 4 5 6
 -----+------------
 4    | 0 0 2 2 2 2
 3    | 0 3 0 0 0 0
 2    | 0 3 0 1 0 0
 1    | 0 3 0 1 0 0


Boats are places either horizontally, vertically or diagonally on the board. 0 represents a space not occupied by a boat.
Digits 1-3 represent boats which vary in length 1-4 spaces long. There will always be at least 1 boat up to a maximum
of 3 in any one game. Boat sizes and board dimensions will vary from game to game.

Attacks

Attacks are calculated from the bottom left, first the X coordinate then the Y. There will be at least one attack per game.

{ {2, 1}, {1, 3}, {4, 2} };
    First attack  [2, 1] = 3
    Second attack [1, 3] = 0
    Third attack  [4, 2] = 1

Function Initialization
int[][] board   = new int[][] {new int[] {0,0,1,0},
                               new int[] {0,0,1,0},
                               new int[] {0,0,1,0}};
int[][] attacks = new int[][] {new int[] {3,1},new int[] {3,2},new int[] {3,3}};
BattleShips.damagedOrSunk(board, attacks);


Scoring
    1 point for every whole boat sank.
    0.5 points for each boat hit at least once (not including boats that are sunk).
    -1 point for each whole boat that was not hit at least once.

Sunk or Damaged
    sunk = all boats that are sunk
    damaged = all boats that have been hit at least once but not sunk
    notTouched/not_touched = all boats that have not been hit at least once

Output
You should return a hash with the following data

Example Game Output in our above example..
    First attack: boat 3 was damaged, which increases the points by 0.5
    Second attack: miss nothing happens
    Third attack: boat 1 was damaged, which increases the points by 0.5
    boat 2 was untouched so points -1 and notTouched +1 in Javascript/Java/C# and not_touched +1 in Python/Ruby.
    No whole boats sank

Return Hash
*/

import java.util.HashMap;
import java.util.Map;

public class BattleShips {

    private static final Map<Integer, Ship> shipMap = new HashMap<>();

    public static Map<String, Double> damagedOrSunk(final int[][] board, final int[][] attacks) {
        // parse ships
        shipMap.clear();
        for (int[] row : board) {
            for (int val : row) {
                if (val > 0) {
                    shipMap.compute(val, (id, ship) ->
                            ship == null
                                    ? new Ship(id)
                                    : ship.incrementSize()
                    );
                }
            }
        }

        // process attacks
        for (int[] pos : attacks) {
            int i = board.length - pos[1];
            int j = pos[0] - 1;
            int id = board[i][j];
            if (id > 0) {
                shipMap.get(id).hit();
                board[i][j] = 0;
            }
        }

        // calculate result
        Score score = new Score();
        shipMap.forEach((id, ship) -> {
            System.out.println("Calculating result: " + ship);
            if (ship.isSunk()) {
                score.sunk++;
            } else if (ship.isDamaged()) {
                score.damaged++;
            } else {
                score.notTouched++;
            }
        });
        score.points = score.sunk + score.damaged * 0.5 - score.notTouched;

        Map<String, Double> result = new HashMap<>();
        result.put("sunk", score.sunk);
        result.put("damaged", score.damaged);
        result.put("notTouched", score.notTouched);
        result.put("points", score.points);
        return result;
    }

}

class Ship {
    private int id;
    private int size;
    private int hits;

    Ship(int id) {
        this.id = id;
        size = 1;
    }

    Ship incrementSize() {
        size++;
        return this;
    }

    void hit() {
        hits++;
    }

    boolean isSunk() {
        return hits == size;
    }

    boolean isDamaged() {
        return hits > 0;
    }
}

class Score {
    double sunk = 0;
    double damaged = 0;
    double notTouched = 0;
    double points = 0;
}