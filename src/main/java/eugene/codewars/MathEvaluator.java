package eugene.codewars;

/*
    Evaluate mathematical expression
    https://www.codewars.com/kata/52a78825cdfc2cfc87000005

INSTRUCTIONS
Given a mathematical expression as a string you must return the result as a number.

NUMBERS
Number may be both whole numbers and/or decimal numbers. The same goes for the returned result.

OPERATORS
You need to support the following mathematical operators:
    Multiplication *
    Division /
    Addition +
    Subtraction -
Operators are always evaluated from left-to-right, and * and / must be evaluated before + and -.

PARENTHESES
You need to support multiple levels of nested parentheses, ex. (2 / (2 + 3.33) * 4) - -6

WHITESPACE
There may or may not be whitespace between numbers and operators.

An addition to this rule is that the minus sign (-) used for negating numbers and parentheses will never be separated
by whitespace. I.e., all of the following are valid expressions.
1-1    // 0
1 -1   // 0
1- 1   // 0
1 - 1  // 0
1- -1  // 2
1 - -1 // 2
6 + -(4)   // 2
6 + -( -4) // 10

And the following are invalid expressions
1 - - 1    // Invalid
1- - 1     // Invalid
6 + - (4)  // Invalid
6 + -(- 4) // Invalid

VALIDATION
You do not need to worry about validation - you will only receive valid mathematical expressions following the above rules.
*/

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathEvaluator {

    private static final String UNARY_MINUS = "!";

    private static final Map<String, OperatorInfo> OPERATOR_INFO = new HashMap<>();

    static {
        OPERATOR_INFO.put(UNARY_MINUS, new OperatorInfo(0, val -> -val));
        OPERATOR_INFO.put("*", new OperatorInfo(1, (l, r) -> l * r));
        OPERATOR_INFO.put("/", new OperatorInfo(1, (l, r) -> l / r));
        OPERATOR_INFO.put("%", new OperatorInfo(1, (l, r) -> l % r));
        OPERATOR_INFO.put("+", new OperatorInfo(2, (l, r) -> l + r));
        OPERATOR_INFO.put("-", new OperatorInfo(2, (l, r) -> l - r));
    }

    public double calculate(String expression) {
        System.out.print(expression + " = ");
        List<String> tokenList = tokenize(expression);

        Deque<String> operatorsStack = new ArrayDeque<>();
        Deque<Double> valueStack = new ArrayDeque<>();

        TokenType lastTokenType = null;
        for (String token : tokenList) {
            TokenType tokenType = getTokenType(token);
            switch (tokenType) {
                case NUMBER:
                    valueStack.push(Double.valueOf(token));
                    break;

                case OPERATOR:
                    if (isUnaryMinus(token, lastTokenType)) {
                        token = UNARY_MINUS;
                    }

                    int tokenPriority = OPERATOR_INFO.get(token).priority;
                    while (!operatorsStack.isEmpty()) {
                        String operator = operatorsStack.peek();
                        if (getTokenType(operator) != TokenType.OPERATOR || OPERATOR_INFO.get(operator).priority > tokenPriority) {
                            break;
                        }
                        applyOperator(valueStack, operatorsStack.pop());
                    }
                    operatorsStack.push(token);
                    break;

                case PARENTHESIS_OPEN:
                    operatorsStack.push(token);
                    break;

                case PARENTHESIS_CLOSE:
                    while (true) {
                        if (operatorsStack.isEmpty()) {
                            throw new EvaluatorException("Unexpected closing parenthesis");
                        }

                        String operator = operatorsStack.pop();
                        if (getTokenType(operator) == TokenType.PARENTHESIS_OPEN) {
                            break;
                        } else {
                            applyOperator(valueStack, operator);
                        }
                    }
                    break;
            }

            lastTokenType = tokenType;
        }

        while (!operatorsStack.isEmpty()) {
            applyOperator(valueStack, operatorsStack.pop());
        }

        if (valueStack.size() != 1) {
            throw new EvaluatorException("Invalid expression");
        }

        double result = valueStack.pop();
        System.out.println(result);

        return result;
    }

    private boolean isUnaryMinus(String operator, TokenType lastTokenType) {
        return operator.equals("-")
                && (lastTokenType == null || lastTokenType == TokenType.OPERATOR || lastTokenType == TokenType.PARENTHESIS_OPEN);
    }

    private void applyOperator(Deque<Double> valueStack, String operator) {
        OperatorInfo operatorInfo = OPERATOR_INFO.get(operator);
        if (operatorInfo == null) {
            throw new EvaluatorException("Unhandled operator '" + operator + "'");
        }

        double result;
        if (operatorInfo.isBinary) {
            if (valueStack.size() < 2) {
                throw new EvaluatorException("Invalid expression");
            }
            Double right = valueStack.pop();
            Double left = valueStack.pop();
            result = operatorInfo.apply(left, right);
        } else {
            if (valueStack.isEmpty()) {
                throw new EvaluatorException("Invalid expression");
            }
            result = operatorInfo.apply(valueStack.pop());
        }

        valueStack.push(result);
    }

    private TokenType getTokenType(String token) {
        return TokenType.getType(token);
    }

    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("[-+*/%=()]|[0-9]*\\.?[0-9]+");
        Matcher m = pattern.matcher(input);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

    private enum TokenType {
        NUMBER("[0-9]*\\.?[0-9]+"),
        OPERATOR("[-+*/%!]"),  // IMPORTANT: include unary minus here
        PARENTHESIS_OPEN("\\("),
        PARENTHESIS_CLOSE("\\)");

        private final Pattern pattern;

        TokenType(String regex) {
            pattern = Pattern.compile(regex);
        }

        static TokenType getType(String token) {
            for (TokenType type : TokenType.values()) {
                if (type.pattern.matcher(token).matches()) {
                    return type;
                }
            }
            throw new EvaluatorException("Invalid token: " + token);
        }
    }

    private static class OperatorInfo {
        final int priority;
        final boolean isBinary;
        private final DoubleUnaryOperator unaryOperator;
        private final DoubleBinaryOperator binaryOperator;

        OperatorInfo(int priority, DoubleUnaryOperator operator) {
            this.priority = priority;
            unaryOperator = operator;
            binaryOperator = null;
            isBinary = false;
        }

        OperatorInfo(int priority, DoubleBinaryOperator operator) {
            this.priority = priority;
            unaryOperator = null;
            binaryOperator = operator;
            isBinary = true;
        }

        double apply(double val) {
            return unaryOperator == null ? 0.0 : unaryOperator.applyAsDouble(val);
        }

        double apply(double left, double right) {
            return binaryOperator == null ? 0.0 : binaryOperator.applyAsDouble(left, right);
        }
    }

    private static class EvaluatorException extends RuntimeException {
        EvaluatorException(String message) {
            super(message);
        }
    }
}
