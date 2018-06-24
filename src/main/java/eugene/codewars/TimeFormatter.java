package eugene.codewars;

/*
    Human readable duration format
    https://www.codewars.com/kata/human-readable-duration-format/java

    Your task in order to complete this Kata is to write a function which formats a duration, given as a number of seconds, in a human-friendly way.

    The function must accept a non-negative integer. If it is zero, it just returns "now".
    Otherwise, the duration is expressed as a combination of years, days, hours, minutes and seconds.

    It is much easier to understand with an example:
        TimeFormatter.formatDuration(62)   //returns "1 minute and 2 seconds"
        TimeFormatter.formatDuration(3662) //returns "1 hour, 1 minute and 2 seconds"

    For the purpose of this Kata, a year is 365 days and a day is 24 hours.
    Note that spaces are important.

    Detailed rules
    The resulting expression is made of components like "4 seconds", "1 year", etc.
    In general, a positive integer and one of the valid units of time, separated by a space. The unit of time is used in plural if the integer is greater than 1.

    The components are separated by a comma and a space (", ").
    Except the last component, which is separated by " and ", just like it would be written in English.

    A more significant units of time will occur before than a least significant one.
    Therefore, 1 second and 1 year is not correct, but 1 year and 1 second is.

    Different components have different unit of times. So there is not repeated units like in 5 seconds and 1 second.

    A component will not appear at all if its value happens to be zero. Hence, 1 minute and 0 seconds is not valid, but it should be just 1 minute.

    A unit of time must be used "as much as possible". It means that the function should not return 61 seconds, but 1 minute and 1 second instead.
    Formally, the duration specified by of a component must not be greater than any valid more significant unit of time.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeFormatter {

    private static final List<TimeUnit> UNIT_LIST = Arrays.asList(
            new TimeUnit("year", 60 * 60 * 24 * 365),
            new TimeUnit("day", 60 * 60 * 24),
            new TimeUnit("hour", 60 * 60),
            new TimeUnit("minute", 60),
            new TimeUnit("second", 1)
    );

    public static String formatDuration(int seconds) {
        if (seconds == 0) {
            return "now";
        }

        List<TimeToken> tokenList = new ArrayList<>();
        for (TimeUnit unit : UNIT_LIST) {
            int count = seconds / unit.seconds;         // implicitly rounding down
            if (count > 0) {
                seconds -= count * unit.seconds;
                tokenList.add(new TimeToken(count, unit));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokenList.size(); i++) {
            if (i > 0) {
                sb.append((i == tokenList.size() - 1) ? " and " : ", ");
            }
            sb.append(tokenList.get(i).toString());
        }

        return sb.toString();
    }

    private static class TimeUnit {
        final String name;
        final int seconds;

        TimeUnit(String name, int seconds) {
            this.name = name;
            this.seconds = seconds;
        }
    }

    private static class TimeToken {
        final int count;
        final TimeUnit unit;

        TimeToken(int count, TimeUnit unit) {
            this.count = count;
            this.unit = unit;
        }

        @Override
        public String toString() {
            if (count == 1) {
                return String.format("%d %s", count, unit.name);
            } else {
                return String.format("%d %ss", count, unit.name);
            }
        }
    }
}
