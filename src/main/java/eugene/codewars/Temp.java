package eugene.codewars;

import java.util.ArrayDeque;
import java.util.Deque;

public class Temp {
    public static void main(String[] args) {
        Calc calc = new Calc();
        test(calc, "");
        test(calc, "1 2 3");
        test(calc, "1 2 3.5");
        test(calc, "1 3 +");
        test(calc, "1 3 *");
        test(calc, "1 3 -");
        test(calc, "4 2 /");
        test(calc, "5 1 2 + 4 * + 3 -");
    }

    private static void test(Calc calc, String input) {
        System.out.println(input + " = " + calc.evaluate(input));
    }
}

class Calc {

    public double evaluate(String expr) {
        if (expr == null || expr.isEmpty()) {
            return 0;
        }

        Deque<Double> stack = new ArrayDeque<>();

        String[] tokens = expr.split(" ");
        for (String t : tokens) {
            if (t.matches("\\d+(\\.\\d+)?")) {
                stack.push(Double.valueOf(t));
            } else {
                Double right = stack.pop();
                Double left = stack.pop();
                stack.push(calculate(t, left, right));
            }
        }

        return stack.pop();
    }

    private Double calculate(String operator, Double a, Double b) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
        }
        throw new RuntimeException("Unhandled operator '" + operator + "'");
    }
}
