package eugene.codewars;

/*
    Let's Play Darts!
    https://www.codewars.com/kata/5870db16056584eab0000006

Create your own mechanical dartboard that gives back your score based on the coordinates of your dart.

Task:
Use the scoring rules for a standard dartboard.

Finish method:
    public String getScore(double x, double y);

The coordinates are (x, y) are always relative to the center of the board (0, 0). The unit is millimeters. If you throw
your dart 5 centimeters to the left and 3 centimeters below, it is written as:
    String score = dartboard.getScore(-50, -30);

Possible scores are:
    - Outside of the board: "X"
    - Bull's eye: "DB"
    - Bull: "SB"
    - A single number, example: "10"
    - A triple number: "T10"
    - A double number: "D10"

A throw that ends exactly on the border of two sections results in a bounce out. You can ignore this because all the
given coordinates of the tests are within the sections.

The diameters of the circles on the dartboard are:
    - Bull's eye: 12.70 mm
    - Bull: 31.8 mm
    - Triple ring inner circle: 198 mm
    - Triple ring outer circle: 214 mm
    - Double ring inner circle: 324 mm
    - Double ring outer circle: 340 mm
*/

public class Dartboard {

    private static final double BULL_EYE = 12.7 / 2;

    private static final double BULL = 31.8 / 2;

    private static final double IN_3 = 198 / 2;

    private static final double OUT_3 = 214 / 2;

    private static final double IN_2 = 324 / 2;

    private static final double OUT_2 = 340 / 2;

    private static final int SECTION_ORDER[] = {11, 8, 16, 7, 19, 3, 17, 2, 15, 10, 6, 13, 4, 18, 1, 20, 5, 12, 9, 14};

    private static final double ANGLE_DELTA = Math.PI * 2 / SECTION_ORDER.length;

    public String getScore(double x, double y) {
        final double r = Math.sqrt(x*x + y*y);
        final double f = Math.atan2(y, x);

        String section = String.valueOf(getSectionNumber(f));

        System.out.println(x);
        System.out.println(y);
        System.out.println(r);
        System.out.println(f);
        System.out.println(section);
        System.out.println();

        if (r > OUT_2) {
            return "X";
        } else if (r > IN_2) {
            return "D" + section;
        } else if (r > OUT_3) {
            return section;
        } else if (r > IN_3) {
            return "T" + section;
        } else if (r > BULL) {
            return section;
        } else if (r > BULL_EYE) {
            return "SB";
        } else {
            return "DB";
        }
    }

    private int getSectionNumber(double f) {
        f += Math.PI + ANGLE_DELTA / 2; // adjust the angle
        int ind = (int) (f / ANGLE_DELTA);
        return SECTION_ORDER[ind % SECTION_ORDER.length];
    }
}
