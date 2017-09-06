package eugene.codewars;

/*
    Directions Reduction
    https://www.codewars.com/kata/550f22f4d758534c1100025a

Once upon a time, on a way through the old wild west,…
… a man was given directions to go from one point to another. The directions were "NORTH", "SOUTH", "WEST", "EAST".
Clearly "NORTH" and "SOUTH" are opposite, "WEST" and "EAST" too. Going to one direction and coming back the opposite
direction is a needless effort. Since this is the wild west, with dreadfull weather and not much water, it's important
to save yourself some energy, otherwise you might die of thirst!

HOW I CROSSED THE DESERT THE SMART WAY.

The directions given to the man are, for example, the following:
    { "NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST" };

You can immediately see that going "NORTH" and then "SOUTH" is not reasonable, better stay to the same place! So the
task is to give to the man a simplified version of the plan. A better plan in this case is simply:
    { "WEST" }

OTHER EXAMPLES:

In ["NORTH", "SOUTH", "EAST", "WEST"], the direction "NORTH" + "SOUTH" is going north and coming back right away.
What a waste of time! Better to do nothing.

The path becomes ["EAST", "WEST"], now "EAST" and "WEST" annihilate each other, therefore, the final result is {}.

In ["NORTH", "EAST", "WEST", "SOUTH", "WEST", "WEST"], "NORTH" and "SOUTH" are not directly opposite but they become
directly opposite after the reduction of "EAST" and "WEST" so the whole path is reducible to ["WEST", "WEST"].

TASK
Write a function dirReduc which will take an array of strings and returns an array of strings with the needless
directions removed (W<->E or S<->N side by side).

EXAMPLES
dirReduc(["NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST"]) => ["WEST"]
dirReduc(["NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH"]) => []
See more examples in "Example Tests"

NOTE
Not all paths can be made simpler. The path ["NORTH", "WEST", "SOUTH", "EAST"] is not reducible.
"NORTH" and "WEST", "WEST" and "SOUTH", "SOUTH" and "EAST" are not directly opposite of each other and can't become such.
Hence the result path is itself : ["NORTH", "WEST", "SOUTH", "EAST"].
*/

import java.util.ArrayList;
import java.util.List;

public class DirReduction {

    public static String[] dirReduc(String[] arr) {
        List<String> unique = new ArrayList<>();

        for (String s : arr) {
            if (unique.isEmpty() || !isOpposite(unique.get(unique.size() - 1), s)) {
                unique.add(s);
            } else {
                unique.remove(unique.size() - 1);
            }
        }

        return unique.toArray(new String[unique.size()]);
    }

    private static boolean isOpposite(String a, String b) {
        return (a.equals("WEST") && b.equals("EAST")) ||
                (a.equals("EAST") && b.equals("WEST")) ||
                (a.equals("SOUTH") && b.equals("NORTH")) ||
                (a.equals("NORTH") && b.equals("SOUTH"));
    }
}
