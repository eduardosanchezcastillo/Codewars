package eugene.codewars;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class DartsPlayer {

    private static final double BULL_EYE = 12.7 / 2;
    private static final double BULL = 31.8 / 2;
    private static final double IN_3 = 198 / 2;
    private static final double OUT_3 = 214 / 2;
    private static final double IN_2 = 324 / 2;
    private static final double OUT_2 = 340 / 2;

    private static final int SECTION_ORDER[] = {11, 8, 16, 7, 19, 3, 17, 2, 15, 10, 6, 13, 4, 18, 1, 20, 5, 12, 9, 14};

    private static final double ANGLE_DELTA = Math.PI * 2 / SECTION_ORDER.length;

    private static final int MAX_SCORE = 60;  // T20

    private static final Map<Integer, Coordinates> scoreToCoordinates = new HashMap<>();

    // init coordinates for all possible scores
    static {
        scoreToCoordinates.put(50, new Coordinates(0, 0, 0));
        scoreToCoordinates.put(25, new Coordinates(BULL_EYE, BULL, 0));

        double angle = -Math.PI;
        for (int score : SECTION_ORDER) {
            scoreToCoordinates.put(score, new Coordinates(OUT_3, IN_2, angle));  // the most preferable case: the area is very big
            scoreToCoordinates.putIfAbsent(score * 2, new Coordinates(IN_2, OUT_2, angle));
            scoreToCoordinates.putIfAbsent(score * 3, new Coordinates(IN_3, OUT_3, angle));
            angle += ANGLE_DELTA;
        }
    }

    public static void main(String[] args) {
        DartsPlayer player = new DartsPlayer();
        testScore(501, player);
        testScore(96, player);
        testScore(80, player);
        testScore(51, player);
        testScore(50, player);
        testScore(49, player);
        testScore(35, player);
        testScore(25, player);
        testScore(7, player);
    }

    private static void testScore(int score, DartsPlayer player) {
        player.getCoordinates(score);
        //System.out.println(Arrays.toString(player.getCoordinates(score)));
    }

    private static int lastScore;
    private static int lastAim;
    private static int iter;

    public double[] getCoordinates(int score) {
        /*
        if (score == 501) {
            System.out.println("----+-------------+-----------+-------------");
            DartsPlayer.iter = 1;
        } else {
            if (DartsPlayer.lastScore - score < 0) {
                // missed and rolled back
                System.out.println(String.format("%2d  |  Score: %-3d |  Aim: %-3d |  Actual: MISS", DartsPlayer.iter, DartsPlayer.lastScore, DartsPlayer.lastAim));
            } else {
                System.out.println(String.format("%2d  |  Score: %-3d |  Aim: %-3d |  Actual: %-3d", DartsPlayer.iter, DartsPlayer.lastScore, DartsPlayer.lastAim, DartsPlayer.lastScore - score));
            }
            DartsPlayer.iter++;
        }
        */

        if (score == 501) {
            System.out.println();
        }
        System.out.println(score);

        DartsPlayer.lastScore = score;

        if (score > 110) {
            int aim = Math.min(score - 50, MAX_SCORE);
            return getCoordinateForExactScore(aim);
        } else if (score > 50) {
            int aim = Math.min(score - 50, 20);  // TODO: MAGIC NUMBER
            return getCoordinateForExactScore(aim);
        } else if (score == 50) {
            return getCoordinateForExactScore(50);
        } else if (score > 25) {
            return getCoordinateForExactScore(score - 25);
        } else if (score == 25) {
            return getCoordinateForExactScore(25);
        } else {
            // we're doomed
            return getCoordinateForExactScore(20);
        }
    }

    private double[] getCoordinateForExactScore(int score) {
        while (!scoreToCoordinates.containsKey(score)) {
            score--;
        }
        DartsPlayer.lastAim = score;
        return scoreToCoordinates.get(score).toArray();
    }

    static class Coordinates {

        private final double x;

        private final double y;

        Coordinates(double minRadius, double maxRadius, double angle) {
            final double r = (maxRadius + minRadius) / 2;
            this.x = r * Math.cos(angle);
            this.y = r * Math.sin(angle);
        }

        double[] toArray() {
            return new double[]{x, y};
        }
    }
}
