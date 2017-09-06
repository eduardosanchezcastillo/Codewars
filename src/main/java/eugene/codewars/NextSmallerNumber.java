package eugene.codewars;

/*
    Next smaller number with the same digits
    https://www.codewars.com/kata/next-smaller-number-with-the-same-digits/java

 Write a function that takes a positive integer and returns the next smaller positive integer containing the same digits.

 For example:

 nextSmaller(21) == 12
 nextSmaller(531) == 513
 nextSmaller(2071) == 2017
 Return -1, when there is no smaller number that contains the same digits.
 Also return -1 when the next smaller number with the same digits would require the leading digit to be zero.

 nextSmaller(9) == -1
 nextSmaller(111) == -1
 nextSmaller(135) == -1
 nextSmaller(1027) == -1 // 0721 is out since we don't write numbers with leading zeros

 - some tests will include very large numbers.
 - test data only employs positive integers.
*/

import java.util.Arrays;

public class NextSmallerNumber {
    public static long nextSmaller(long n) {
        int[] digits = String.valueOf(n).chars().toArray();
        for (int i = digits.length - 2; i >= 0; i--) {
            if (digits[i] > digits[i + 1]) {
                return generateValue(digits, i);
            }
        }
        return -1;
    }

    private static long generateValue(int[] digits, int ind) {
        int swapInd = findNextSmallestDigit(digits, ind);
        swap(digits, ind, swapInd);
        Arrays.sort(digits, ind + 1, digits.length);
        reverse(digits, ind + 1);

        if (digits[0] == '0') {
            return -1;
        }
        return Long.valueOf(new String(digits, 0, digits.length));
    }

    private static int findNextSmallestDigit(int[] digits, int ind) {
        int resultInd = ind + 1;
        for (int i = resultInd + 1; i < digits.length; i++) {
            if (digits[i] < digits[ind] && digits[i] > digits[resultInd]) {
                resultInd = i;
            }
        }
        return resultInd;
    }

    private static void reverse(int[] digits, int startAt) {
        for (int i = startAt; i < (digits.length + startAt) / 2; i++) {
            swap(digits, i, digits.length - i + startAt - 1);
        }
    }

    private static void swap(int[] digits, int indA, int indB) {
        int temp = digits[indA];
        digits[indA] = digits[indB];
        digits[indB] = temp;
    }
}
