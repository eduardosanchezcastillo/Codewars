package eugene.codewars;

/*
    We are the Robots d[(0)(0)]b
    https://www.codewars.com/kata/587ae98e2ab0ef32ef00004c

 ------------------------------------------------------------------
  we are programmed just to do anything you want us to
           w e  a r e  t h e  r o b o t s
 -----------------------------------------------------------[ d[(0)(0)]b]

 Task..... You will receive an array of strings such as
    a = ["We're functioning automatik d[(0)(0)]b","And we are dancing mechanik d[(0)(0)]b"]

 Count the robots represented by d[(0)(0)]b
 Unless of course the factory replaced a part on the robot.....

     d[(0)(0)]b could look a little different depending on the supplier maybe like this
     d[(0)(0)]B or d[(0)(0}]B

 It's pretty random actually but with a global supply chain it's hard to control which part you get.
 Most of the parts are made global except the ones made in the factory which do not change.

 d[(0)(0)]B

 In all robots the eyes do not change.

 d[(0)(0)]B

 ...0..0...
    ^  ^
    |  |

 The rest of the body can change at random.

 legs any in => abcdefghijklmnopqrstuvwxyz
 ...0..0...
 ^        ^
 |        |

 body any in => |};&#[]/><()*


 ...0..0...
  ^^ ^^ ^^
  || || ||


 There may be cases where a part is totally missing and of course a robot cannot function at all without a part or where
 the factory put a valid part in the wrong place and it's again not a valid robot.

 Return an array of strings with a count of each of the following tasks.
 Case insensitive count of robots in string with "automatik" or "mechanik".
 Strings do not contain both "automatik and "mechanik".

 Return an array with the count like below

 a[0] = automatik count
 a[1] = mechanik count

 ["1 robots functioning automatik", "1 robots dancing mechanik"]
 to pay tribute...respect :)

 https://en.wikipedia.org/wiki/The_Robots
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountRobots {

    private static final String FORMAT_AUTO = "%s robots functioning automatik";
    private static final String FORMAT_MECH = "%s robots dancing mechanik";

    private static final Pattern ROBOT_PATTERN = Pattern.compile("[a-z]([|};&#\\[\\]/><()*]{2}0){2}[|};&#\\[\\]/><()*]{2}[a-z]");

    public static String[] countRobots(String[] input) {
        int countAuto = 0;
        int countMech = 0;

        for (String s : input) {
            s = s.toLowerCase();
            Matcher matcher = ROBOT_PATTERN.matcher(s);

            int count = 0;
            while (matcher.find()) {
                count++;
            }

            if (count > 0) {
                if (s.contains("automatik")) {
                    countAuto += count;
                } else if (s.contains("mechanik")) {
                    countMech += count;
                }
            }
        }

        return new String[] {
                String.format(FORMAT_AUTO, countAuto),
                String.format(FORMAT_MECH, countMech)
        };
    }
}
