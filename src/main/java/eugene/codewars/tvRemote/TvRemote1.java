package eugene.codewars.tvRemote;

/*
    TV Remote
    https://www.codewars.com/kata/tv-remote/java

    Background:
    My TV remote control has arrow buttons and an OK button.
    I can use these to move a "cursor" on a logical screen keyboard to type words...

    The screen "keyboard" layout looks like this
        a b c d e 1 2 3
        f g h i j 4 5 6
        k l m n o 7 8 9
        p q r s t . @ 0
        u v w x y z _ /

    Kata task:
    How many button presses on my remote are required to type a given word?

    Notes:
    - The cursor always starts on the letter a (top left)
    - Remember to also press OK to "accept" each letter.
    - Take a direct route from one letter to the next
    - The cursor does not wrap (e.g. you cannot leave one edge and reappear on the opposite edge)

    Example:
        word = codewars
        c => a-b-c-OK = 3
        o => c-d-e-j-o-OK = 5
        d => o-j-e-d-OK = 4
        e => d-e-OK = 2
        w => e-j-o-t-y-x-w-OK = 7
        a => w-r-m-h-c-b-a-OK = 7
        r => a-f-k-p-q-r-OK = 6
        s => r-s-OK = 2
    Answer = 3 + 5 + 4 + 2 + 7 + 7 + 6 + 2 = 36
 */

public class TvRemote1 {

    private static final String LAYOUT = "abcde123fghij456klmno789pqrst.@0uvwxyz_/";
    private static final int ROW_SIZE = 8;

    public static int tvRemote(final String word) {
        Position pos = new Position(0, 0);      // always start from the top-left position
        int length = 0;
        for (int i = 0; i < word.length(); i++) {
            Position newPos = findChar(word.charAt(i));
            length += pos.distanceTo(newPos) + 1;   // "+1" to press the OK button
            pos = newPos;
        }
        return length;
    }

    private static Position findChar(char ch) {
        int index = LAYOUT.indexOf(ch);
        if (index < 0) {
            throw new IllegalArgumentException("Invalid char: " + ch);
        }
        return new Position(index / ROW_SIZE, index % ROW_SIZE);
    }

    private static class Position {
        final int x;
        final int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int distanceTo(Position other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
