package eugene.codewars;

/*
    Calculator
    https://www.codewars.com/kata/calculator/java

Create a simple calculator that given a string of operators (+ - * and /) and numbers separated by spaces returns the
value of that expression

Example:
    Calculator.evaluate("2 / 2 + 3 * 4 - 6") // => 7

Remember about the order of operations! Multiplications and divisions have a higher priority and should be performed
left-to-right. Additions and subtractions have a lower priority and should also be performed left-to-right.
*/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

class Calculator {

    private static final int PRECISION = 5; // 5 digits

    private static final Map<String, OperatorInfo> OPERATOR_INFO = new HashMap<>();

    static {
        OPERATOR_INFO.put("*", new OperatorInfo(1, (l, r) -> l * r));
        OPERATOR_INFO.put("/", new OperatorInfo(1, (l, r) -> l / r));
        OPERATOR_INFO.put("+", new OperatorInfo(2, (l, r) -> l + r));
        OPERATOR_INFO.put("-", new OperatorInfo(2, (l, r) -> l - r));
    }

    static Double evaluate(String expression) {
        System.out.println(expression);
        Deque<String> operatorStack = new ArrayDeque<>();
        Deque<Double> valueStack = new ArrayDeque<>();

        String[] tokenList = expression.split(" ");
        for (String token : tokenList) {
            if (token.matches("[-+*/]")) {
                OperatorInfo operatorInfo = OPERATOR_INFO.get(token);
                while (!operatorStack.isEmpty()) {
                    OperatorInfo oldOperatorInfo = OPERATOR_INFO.get(operatorStack.peek());
                    if (oldOperatorInfo.priority > operatorInfo.priority) {
                        break;
                    }

                    applyOperator(valueStack, operatorStack.pop());
                }
                operatorStack.push(token);
            } else {
                valueStack.push(Double.valueOf(token));
            }
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(valueStack, operatorStack.pop());
        }

        final double mult = Math.pow(10, PRECISION);
        return Math.round(valueStack.pop() * mult) / mult;
    }

    private static void applyOperator(Deque<Double> valueStack, String operator) {
        Double right = valueStack.pop();
        Double left = valueStack.pop();

        OperatorInfo operatorInfo = OPERATOR_INFO.get(operator);
        Double result = operatorInfo.operator.applyAsDouble(left, right);

        valueStack.push(result);
    }

    private static class OperatorInfo {
        final int priority;
        final DoubleBinaryOperator operator;

        OperatorInfo(int priority, DoubleBinaryOperator operator) {
            this.priority = priority;
            this.operator = operator;
        }
    }
}
