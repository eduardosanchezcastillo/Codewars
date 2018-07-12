package eugene.codewars;

/*
    Fruit Machine
    https://www.codewars.com/kata/fruit-machine/java

    Task
    You will be given three reels of different images and told at which index the reels stop.
    From this information your job is to return the score of the resulted reels.

    Rules
    1. There are always exactly three reels
    2. Each reel has 10 different items.
    3. The three reel inputs may be different.
    4. The spin array represents the index of where the reels finish.
    5. The three spin inputs may be different
    6. Three of the same is worth more than two of the same
    7. Two of the same plus one "Wild" is double the score.
    8. No matching items returns 0.

    Scoring
        Item    |  Three of the same   |  Two of the same   |  Two of the same plus one Wild
        --------+----------------------+--------------------+-------------------------------
        Wild    |        100           |        10          |             N/A
        Star    |         90           |         9          |              18
        Bell    |         80           |         8          |              16
        Shell   |         70           |         7          |              14
        Seven   |         60           |         6          |              12
        Cherry  |         50           |         5          |              10
        Bar     |         40           |         4          |               8
        King    |         30           |         3          |               6
        Queen   |         20           |         2          |               4
        Jack    |         10           |         1          |               2

    Returns
    Return an integer of the score.

    Example
        Initialise
            reel1 = {"Wild","Star","Bell","Shell","Seven","Cherry","Bar","King","Queen","Jack"}
            reel2 = {"Wild","Star","Bell","Shell","Seven","Cherry","Bar","King","Queen","Jack"}
            reel3 = {"Wild","Star","Bell","Shell","Seven","Cherry","Bar","King","Queen","Jack"}
            spin  = {5,5,5}
            result = fruit({reel1, reel2, reel3}, spin)

        Scoring
            reel1[5] == "Cherry"
            reel2[5] == "Cherry"
            reel3[5] == "Cherry"

            Cherry + Cherry + Cherry == 50
    Return
        result == 50
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FruitMachine {

    private static final Map<String, Integer> SCORE_MAP = new HashMap<>();

    static {
        SCORE_MAP.put("Wild", 10);
        SCORE_MAP.put("Star", 9);
        SCORE_MAP.put("Bell", 8);
        SCORE_MAP.put("Shell", 7);
        SCORE_MAP.put("Seven", 6);
        SCORE_MAP.put("Cherry", 5);
        SCORE_MAP.put("Bar", 4);
        SCORE_MAP.put("King", 3);
        SCORE_MAP.put("Queen", 2);
        SCORE_MAP.put("Jack", 1);
    }

    public static int fruit(final String[][] reels, final int[] spins) {
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            countMap.compute(reels[i][spins[i]], (reel, count) -> count == null ? 1 : count + 1);
        }

        Set<Map.Entry<String, Integer>> entrySet = countMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() == 3) {
                return SCORE_MAP.get(entry.getKey()) * 10;
            } else if (entry.getValue() == 2) {
                Integer result = SCORE_MAP.get(entry.getKey());
                if (!entry.getKey().equals("Wild") && countMap.containsKey("Wild")) {
                    result *= 2;
                }
                return result;
            }
        }

        return 0; // Code here
    }
}
