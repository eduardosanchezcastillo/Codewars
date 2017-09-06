package eugene.codewars;

/*
    Catching Car Mileage Numbers
    https://www.codewars.com/kata/52c4dd683bfd3b434c000292

> "7777...8?!??!", exclaimed Bob, "I missed it again! Argh!" Every time there's an interesting number coming up, he
  notices and then promptly forgets. Who doesn't like catching those one-off interesting mileage numbers?

Let's make it so Bob never misses another interesting number. We've hacked into his car's computer, and we have a box
hooked up that reads mileage numbers. We've got a box glued to his dash that lights up yellow or green depending on
whether it receives a 1 or a 2 (respectively).

It's up to you, intrepid warrior, to glue the parts together. Write the function that parses the mileage number input,
and returns a 2 if the number is "interesting" (see below), a 1 if an interesting number occurs within the next two
miles, or a 0 if the number is not interesting.

Note: In Haskell, we use No, Almost and Yes instead of 0, 1 and 2.


"INTERESTING" NUMBERS

Interesting numbers are 3-or-more digit numbers that meet one or more of the following criteria:
    - Any digit followed by all zeros: 100, 90000
    - Every digit is the same number: 1111
    - The digits are sequential, incementing†: 1234
    - The digits are sequential, decrementing‡: 4321
    - The digits are a palindrome: 1221 or 73837
    - The digits match one of the values in the awesomePhrases array
    * For incrementing sequences, 0 should come after 9, and not before 1, as in 7890.
    * For decrementing sequences, 0 should come after 1, and not before 9, as in 3210.

So, you should expect these inputs and outputs:

    // "boring" numbers
    CarMileage.isInteresting(3, new int[]{1337, 256});    // 0
    CarMileage.isInteresting(3236, new int[]{1337, 256}); // 0

    // progress as we near an "interesting" number
    CarMileage.isInteresting(11207, new int[]{}); // 0
    CarMileage.isInteresting(11208, new int[]{}); // 0
    CarMileage.isInteresting(11209, new int[]{}); // 1
    CarMileage.isInteresting(11210, new int[]{}); // 1
    CarMileage.isInteresting(11211, new int[]{}); // 2

    // nearing a provided "awesome phrase"
    CarMileage.isInteresting(1335, new int[]{1337, 256}); // 1
    CarMileage.isInteresting(1336, new int[]{1337, 256}); // 1
    CarMileage.isInteresting(1337, new int[]{1337, 256}); // 2


ERROR CHECKING
    - A number is only interesting if it is greater than 99!
    - Input will always be an integer greater than 0, and less than 1,000,000,000.
    - The awesomePhrases array will always be provided, and will always be an array, but may be empty. (Not everyone
      thinks numbers spell funny words...)
    - You should only ever output 0, 1, or 2.
*/

public class CarMileage {
    public static int isInteresting(int number, int[] awesomePhrases) {
        if (isInterestingNow(number, awesomePhrases)) return 2;
        if (isInterestingNow(number+1, awesomePhrases) || isInterestingNow(number+2, awesomePhrases)) return 1;
        return 0;
    }

    public static boolean isInterestingNow(int number, int[] awesomePhrases) {
        if (number < 100) return false;

        // The digits match one of the values in the awesomePhrases array
        for (int awesomePhrase : awesomePhrases) {
            if (number == awesomePhrase) return true;
        }

        String s = String.valueOf(number);

        return isZeroes(s)
                || isSameDigit(s)
                || isSequentialUp(s)
                || isSequentialDown(s)
                || isPalindrome(s);
    }

    // Any digit followed by all zeros: 100, 90000
    private static boolean isZeroes(String s) {
        return s.matches("\\d0+");
    }

    // Every digit is the same number: 1111
    private static boolean isSameDigit(String s) {
        return s.matches(String.format("%c{%d}", s.charAt(0), s.length()));
    }

    // The digits are sequential, incrementing: 1234
    // 0 should come after 9, and not before 1, as in 7890.
    private static boolean isSequentialUp(String s) {
        int digit = Character.getNumericValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            digit = (digit + 1) % 10;
            if (digit != Character.getNumericValue(s.charAt(i))) return false;
        }
        return true;
    }

    // The digits are sequential, decrementing: 4321
    // 0 should come after 1, and not before 9, as in 3210
    private static boolean isSequentialDown(String s) {
        int digit = Character.getNumericValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            digit = (digit - 1) % 10;
            if (digit != Character.getNumericValue(s.charAt(i))) return false;
        }
        return true;
    }

    // The digits are a palindrome: 1221 or 73837
    private static boolean isPalindrome(String s) {
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) return false;
        }
        return true;
    }
}
