package eugene.codewars;

/*
    The Wrong-Way Cow
    https://www.codewars.com/kata/57d7536d950d8474f6000a06

Have you ever noticed that cows in a field are always facing in the same direction?
Reference: http://bfy.tw/7fgf

Well.... not quite always.
One stubborn cow wants to be different from the rest of the herd - it's that damn Wrong-Way Cow!

TASK
Given a field of cows find which one is the Wrong-Way Cow and return her position.

NOTES:
There are always at least 3 cows in a herd
There is only 1 Wrong-Way Cow!
Fields are rectangular
The cow position is zero-based [x,y] of her head (i.e. the letter c)

EXAMPLES

Ex1
cow.cow.cow.cow.cow
cow.cow.cow.cow.cow
cow.woc.cow.cow.cow
cow.cow.cow.cow.cow
Answer: [6,2]

Ex2
c..........
o...c......
w...o.c....
....w.o....
......w.cow
Answer: [8,4]

NOTES
The test cases will NOT test any situations where there are "imaginary" cows, so your solution does not need to worry
about such things!
To explain - Yes, I recognise that there are certain configurations where an "imaginary" cow may appear that in fact is
just made of three other "real" cows. In the following field you can see there are 4 real cows (3 are facing south and
1 is facing north). There are also 2 imaginary cows (facing east and west).

But such a field will never be tested by this Kata.
...w...
..cow..
.woco..
.ow.c..
.c.....
*/

public class Dinglemouse {

    private static final String COW_FORWARD = "cow";
    private static final String COW_BACKWARD = "woc";
    private static final int COW_SHIFT = COW_FORWARD.length() - 1;

    public static int[] findWrongWayCow(final char[][] field) {
        final int FIELD_WIDTH = field[0].length + 1;
        final int FIELD_HEIGHT = field.length;

        // Horizontal
        StringBuilder sb = new StringBuilder();
        for (char[] row : field) {
            sb.append(row).append(' ');
        }
        String fieldA = sb.toString();

        int pos = fieldA.indexOf(COW_FORWARD);
        if (pos >= 0 && pos == fieldA.lastIndexOf(COW_FORWARD)) {
            return new int[]{pos % FIELD_WIDTH, pos / FIELD_WIDTH};
        }

        pos = fieldA.indexOf(COW_BACKWARD);
        if (pos >= 0 && pos == fieldA.lastIndexOf(COW_BACKWARD)) {
            return new int[]{pos % FIELD_WIDTH + COW_SHIFT, pos / FIELD_WIDTH};
        }

        // Vertical
        sb = new StringBuilder();
        for (int x = 0; x < FIELD_WIDTH - 1; x++) {
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                sb.append(field[y][x]);
            }
        }
        String fieldB = sb.toString();

        pos = fieldB.indexOf(COW_FORWARD);
        if (pos >= 0 && pos == fieldB.lastIndexOf(COW_FORWARD)) {
            return new int[]{pos / FIELD_HEIGHT, pos % FIELD_HEIGHT};
        }

        pos = fieldB.indexOf(COW_BACKWARD);
        if (pos >= 0 && pos == fieldB.lastIndexOf(COW_BACKWARD)) {
            return new int[]{pos / FIELD_HEIGHT, pos % FIELD_HEIGHT + COW_SHIFT};
        }

        return new int[]{0, 0};
    }
}
