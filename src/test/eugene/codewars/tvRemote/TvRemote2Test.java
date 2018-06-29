package eugene.codewars.tvRemote;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TvRemote2Test {

    @Test
    public void example() {
        assertEquals(69, TvRemote2.tvRemote("Code Wars"));
    }

    @Test
    public void lower() {
        assertEquals(16, TvRemote2.tvRemote("does"));
        assertEquals(23, TvRemote2.tvRemote("your"));
        assertEquals(33, TvRemote2.tvRemote("solution"));
        assertEquals(20, TvRemote2.tvRemote("work"));
        assertEquals(12, TvRemote2.tvRemote("for"));
        assertEquals(27, TvRemote2.tvRemote("these"));
        assertEquals(25, TvRemote2.tvRemote("words"));
    }

    @Test
    public void lowerEdge() {
        assertEquals(1, TvRemote2.tvRemote("a"));
        assertEquals(34, TvRemote2.tvRemote("aadvarks"));
        assertEquals(85, TvRemote2.tvRemote("a/a/a/a/"));
        assertEquals(28, TvRemote2.tvRemote("1234567890"));
        assertEquals(35, TvRemote2.tvRemote("mississippi"));
    }

    @Test
    public void upper() {
        assertEquals(27, TvRemote2.tvRemote("DOES"));
        assertEquals(26, TvRemote2.tvRemote("YOUR"));
        assertEquals(38, TvRemote2.tvRemote("SOLUTION"));
        assertEquals(23, TvRemote2.tvRemote("WORK"));
        assertEquals(21, TvRemote2.tvRemote("FOR"));
        assertEquals(32, TvRemote2.tvRemote("THESE"));
        assertEquals(28, TvRemote2.tvRemote("WORDS"));
    }

    @Test
    public void upperEdge() {
        assertEquals(12, TvRemote2.tvRemote("A"));
        assertEquals(45, TvRemote2.tvRemote("AADVARKS"));
        assertEquals(96, TvRemote2.tvRemote("A/A/A/A/"));
        assertEquals(28, TvRemote2.tvRemote("1234567890"));
        assertEquals(42, TvRemote2.tvRemote("MISSISSIPPI"));
    }

    @Test
    public void mixed() {
        assertEquals(40, TvRemote2.tvRemote("Does"));
        assertEquals(37, TvRemote2.tvRemote("Your"));
        assertEquals(49, TvRemote2.tvRemote("Solution"));
        assertEquals(30, TvRemote2.tvRemote("Work"));
        assertEquals(28, TvRemote2.tvRemote("For"));
        assertEquals(41, TvRemote2.tvRemote("These"));
        assertEquals(35, TvRemote2.tvRemote("Words"));
    }

    @Test
    public void xox() {
        assertEquals(57, TvRemote2.tvRemote("Xoo ooo ooo"));
        assertEquals(65, TvRemote2.tvRemote("oXo ooo ooo"));
        assertEquals(53, TvRemote2.tvRemote("ooX ooo ooo"));
        assertEquals(53, TvRemote2.tvRemote("ooo Xoo ooo"));
        assertEquals(65, TvRemote2.tvRemote("ooo oXo ooo"));
        assertEquals(53, TvRemote2.tvRemote("ooo ooX ooo"));
        assertEquals(53, TvRemote2.tvRemote("ooo ooo Xoo"));
        assertEquals(65, TvRemote2.tvRemote("ooo ooo oXo"));
        assertEquals(53, TvRemote2.tvRemote("ooo ooo ooX"));
    }

    @Test
    public void sentences() {
        assertEquals(306, TvRemote2.tvRemote("The Quick Brown Fox Jumps Over A Lazy Dog."));
        assertEquals(290, TvRemote2.tvRemote("Pack My Box With Five Dozen Liquor Jugs."));
    }

    @Test
    public void spaces() {
        assertEquals(0, TvRemote2.tvRemote(""));
        assertEquals(7, TvRemote2.tvRemote(" "));
        assertEquals(9, TvRemote2.tvRemote("   "));
        assertEquals(34, TvRemote2.tvRemote("    x   X    "));
    }

    // ====================================

    private static class TvRemote2Answer20180619 {

        private static final String KB = "abcde123fghij456klmno789pqrst.@0uvwxyz_/^ ??????";

        static int tvRemote(final String words) {
            int n = 0, prevx = 0, prevy = 0;
            boolean lower = true;

            for (final char c : words.toCharArray()) {
                final char lo = Character.isAlphabetic(c) ? Character.toLowerCase(c) : c;

                if (Character.isAlphabetic(c)) {
                    if (lower != (c == lo)) {
                        // press the SHIFT key to toggle keypad letters
                        n += prevx + Math.abs(5 - prevy) + 1; // + 1 for the OK button
                        prevx = 0;
                        prevy = 5;
                        lower = !lower;
                    }
                }

                final int idx = KB.indexOf(lo);
                final int y = idx / 8, x = idx % 8;
                n += Math.abs(x - prevx) + Math.abs(y - prevy) + 1; // + 1 for the OK button
                prevx = x;
                prevy = y;
            }

            return n;
        }

    }

    // Spaces are repeated deliberately so they appear more frequently :-)
    private static final String KB_LETTERS = "abcdefghijklmnopqrstuvwxyz  ABCDEFGHIJKLMNOPQRSTUVWXSDYZ  1234567890 .@_/";

    @Test
    public void random() {
        for (int r = 0; r < 200; r++) {
            final int wlen = (int) (Math.random() * 30) + 1;
            String words = "";
            for (int i = 0; i < wlen; i++) {
                words += KB_LETTERS.charAt((int) (Math.random() * KB_LETTERS.length()));
            }
            System.out.println(String.format("Random test #%d words: %s", r + 1, words));
            final int expected = TvRemote2Answer20180619.tvRemote(words);
            assertEquals(expected, TvRemote2.tvRemote(words));
        }
    }
}