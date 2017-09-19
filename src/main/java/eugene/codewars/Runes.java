package eugene.codewars;

/*
    Find the unknown digit
    https://www.codewars.com/kata/find-the-unknown-digit/java

To give credit where credit is due: This problem was taken from the ACMICPC-Northwest Regional Programming Contest.
Thank you problem writers.

You are helping an archaeologist decipher some runes. He knows that this ancient society used a Base 10 system, and that
they never start a number with a leading zero. He's figured out most of the digits as well as a few operators, but he
needs your help to figure out the rest.

The professor will give you a simple math expression, of the form
    [number][op][number]=[number]

He has converted all of the runes he knows into digits. The only operators he knows are addition (+),subtraction(-), and
multiplication (*), so those are the only ones that will appear. Each number will be in the range from -1000000 to
1000000, and will consist of only the digits 0-9, possibly a leading -, and maybe a few ?s. If there are ?s in an
expression, they represent a digit rune that the professor doesn't know (never an operator, and never a leading -).

All of the ?s in an expression will represent the same digit (0-9), and it won't be one of the other given digits in the
expression. No number will begin with a 0 unless the number itself is 0, therefore 00 would not be a valid number.

Given an expression, figure out the value of the rune represented by the question mark. If more than one digit works,
give the lowest one. If no digit works, well, that's bad news for the professor - it means that he's got some of his
runes wrong. output -1 in that case.

Complete the method to solve the expression to find the value of the unknown rune. The method takes a string as a
parameter representing the expression and will return an int value representing the unknown rune or -1 if no such rune
exists.
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Runes {

    private static final Map<String, IntBinaryOperator> OPERATOR_MAP = new HashMap<>();

    static {
        OPERATOR_MAP.put("+", (a, b) -> a + b);
        OPERATOR_MAP.put("-", (a, b) -> a - b);
        OPERATOR_MAP.put("*", (a, b) -> a * b);
    }

    static int solveExpression(final String expression) {
        // get all used digits
        String[] digits = expression.replaceAll("[^0-9]", "")
                .split("");
        Set<Integer> digitSet = Arrays.stream(digits)
                .mapToInt(Integer::valueOf)
                .boxed()
                .collect(Collectors.toSet());

        Pattern pattern = Pattern.compile("(-?[0-9?]+)([-+*])(-?[0-9?]+)=(-?[0-9?]+)");
        Matcher matcher = pattern.matcher(expression);
        if (!matcher.matches()) {
            return -1;
        }

        Expr expr = new Expr(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        for (int i = 0; i < 10; i++) {
            if (digitSet.contains(i)) {
                continue;
            }

            if (expr.check(i)) {
                return i;
            }
        }

        return -1;
    }

    static int calc(int a, int b, String operator) {
        return OPERATOR_MAP.get(operator).applyAsInt(a, b);
    }
}

class Expr {
    private final String left;
    private final String operator;
    private final String right;
    private final String result;

    Expr(String left, String operator, String right, String result) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        this.result = result;
    }

    boolean check(int n) {
        int a = replace(left, n);
        int b = replace(right, n);
        int c = replace(result, n);
        return Runes.calc(a, b, operator) == c;
    }

    private int replace(String s, int n) {
        if (n == 0 && s.length() > 1 && s.charAt(0) == '?') {
            return Integer.MAX_VALUE;
        }
        return Integer.valueOf(s.replaceAll("\\?", String.valueOf(n)));
    }
}