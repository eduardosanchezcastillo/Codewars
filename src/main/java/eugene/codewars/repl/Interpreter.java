package eugene.codewars.repl;

/*
    Simpler Interactive Interpreter
    https://www.codewars.com/kata/53005a7b26d12be55c000243

Simpler Interactive interpreter (or REPL)

You will create an interpreter which takes inputs described below and produces outputs, storing state in between each
input. This is a simplified version of the Simple Interactive Interpreter kata with functions removed, so if you have
fun with this kata, check out its big brother to add functions into the mix.

If you're not sure where to start with this kata, check out ankr's Evaluate Mathematical Expression kata.

Note that the eval command has been disabled.


CONCEPTS

The interpreter will take inputs in the language described under the language header below. This section will give an
overview of the language constructs.


VARIABLES

Any identifier which is not a keyword will be treated as a variable. If the identifier is on the left hand side of an
assignment operator, the result of the right hand side will be stored in the variable. If a variable occurs as part of
an expression, the value held in the variable will be substituted when the expression is evaluated.

Variables are implicitly declared the first time they are assigned to.

Example: Initializing a variable to a constant value and using the variable in another expression (Each line starting
with a '>' indicates a separate call to the input method of the interpreter, other lines represent output)
    > x = 7
      7
    > x + 6
      13

Referencing a non-existent variable will cause the interpreter to throw an error. The interpreter should be able to
continue accepting input even after throwing.

Example: Referencing a non-existent variable
    > y + 7
      ERROR: Invalid identifier. No variable with name 'y' was found."


ASSIGNMENTS

An assignment is an expression that has an identifier on left side of an = operator, and any expression on the right.
Such expressions should store the value of the right hand side in the specified variable and return the result.

Example: Assigning a constant to a variable
    > x = 7
      7

In this kata, all tests will contain only a single assignment. You do not need to consider chained or nested assignments.


OPERATOR PRECEDENCE

Operator precedence will follow the common order. There is a table in the Language section below that explicitly states
the operators and their relative precedence.


NAME CONFLICTS

Because variables are declared implicitly, no naming conflicts are possible. variable assignment will always overwrite
any existing value.


INPUT

Input will conform to the expression production in the grammar below.


OUTPUT

Output for a valid expression will be the result of the expression.
Output for input consisting entirely of whitespace will be an empty string (null in case of Java).
All other cases will throw an error.


LANGUAGE

GRAMMAR

This section specifies the grammar for the interpreter language in EBNF syntax

expression      ::= factor | expression operator expression
factor          ::= number | identifier | assignment | '(' expression ')'
assignment      ::= identifier '=' expression

operator        ::= '+' | '-' | '*' | '/' | '%'

identifier      ::= letter | '_' { identifier-char }
identifier-char ::= '_' | letter | digit

number          ::= { digit } [ '.' digit { digit } ]

letter          ::= 'a' | 'b' | ... | 'y' | 'z' | 'A' | 'B' | ... | 'Y' | 'Z'
digit           ::= '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'


OPERATOR PRECEDENCE

The following table lists the language's operators grouped in order of precedence. Operators within each group have equal precedence.

 Category	        Operators
Multiplicative	    *, /, %
Additive	        +, -
Assignment	        =
*/

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    private final Storage storage = new Storage();

    private static final Map<String, OperatorInfo> OPERATOR_INFO = new HashMap<>();

    static {
        OPERATOR_INFO.put("*", new OperatorInfo(0, (l, r) -> l * r));
        OPERATOR_INFO.put("/", new OperatorInfo(0, (l, r) -> l / r));
        OPERATOR_INFO.put("%", new OperatorInfo(0, (l, r) -> l % r));
        OPERATOR_INFO.put("+", new OperatorInfo(1, (l, r) -> l + r));
        OPERATOR_INFO.put("-", new OperatorInfo(1, (l, r) -> l - r));
    }

    public Double input(String input) {
        System.out.print(input);
        List<String> tokenList = tokenize(input);

        Double value = null;

        try {
            String varName = null;

            if (tokenList.size() > 1 && getTokenType(tokenList.get(1)) == TokenType.ASSIGNMENT) {
                // assignment mode
                TokenType type = getTokenType(tokenList.get(0));
                if (type != TokenType.IDENTIFIER) {
                    throw new InterpreterException("l-value is not an identifier.");
                }

                // identifier name
                varName = tokenList.get(0);
                tokenList = tokenList.subList(2, tokenList.size());
            }

            if (!tokenList.isEmpty()) {
                value = evaluateExpression(tokenList);
                if (varName != null) {
                    storage.set(varName, value);
                }
            }
        } catch (InterpreterException e) {
            System.out.println(" --> ERROR: " + e.getMessage());
            throw e;
        }

        System.out.println(" --> " + value);
        return value;
    }

    private Double evaluateExpression(List<String> tokenList) {
        Deque<String> operatorStack = new ArrayDeque<>();
        Deque<Double> valueStack = new ArrayDeque<>();

        for (String token : tokenList) {
            switch (getTokenType(token)) {
                case IDENTIFIER:
                    Double value = storage.get(token);
                    if (value == null) {
                        throw new InterpreterException("Unknown identifier '" + token + "'");
                    }
                    valueStack.push(value);
                    break;

                case NUMBER:
                    valueStack.push(Double.valueOf(token));
                    break;

                case ASSIGNMENT:
                    throw new InterpreterException("Unexpected operation '" + token + "'");

                case OPERATOR:
                    int tokenPriority = OPERATOR_INFO.get(token).priority;
                    while (!operatorStack.isEmpty()) {
                        String operator = operatorStack.peek();
                        if (getTokenType(operator) != TokenType.OPERATOR || OPERATOR_INFO.get(operator).priority > tokenPriority) {
                            break;
                        }
                        applyOperator(valueStack, operatorStack.pop());
                    }
                    operatorStack.push(token);
                    break;

                case PARENTHESIS_OPEN:
                    operatorStack.push(token);
                    break;

                case PARENTHESIS_CLOSE:
                    while (true) {
                        if (operatorStack.isEmpty()) {
                            throw new InterpreterException("Unexpected closing parenthesis");
                        }

                        String operator = operatorStack.pop();
                        if (getTokenType(operator) == TokenType.PARENTHESIS_OPEN) {
                            break;
                        } else {
                            applyOperator(valueStack, operator);
                        }
                    }
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(valueStack, operatorStack.pop());
        }

        if (valueStack.size() != 1) {
            throw new InterpreterException("Invalid expression");
        }

        return valueStack.pop();
    }

    private void applyOperator(Deque<Double> valueStack, String operator) {
        if (valueStack.size() < 2) {
            throw new InterpreterException("Invalid expression");
        }

        OperatorInfo operatorInfo = OPERATOR_INFO.get(operator);
        if (operatorInfo == null) {
            throw new InterpreterException("Unhandled operator '" + operator + "'");
        }

        Double right = valueStack.pop();
        Double left = valueStack.pop();
        double result = operatorInfo.apply(left, right);

        valueStack.push(result);
    }

    private TokenType getTokenType(String token) {
        return TokenType.getType(token);
    }

    private List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("=>|[-+*/%=()]|[A-Za-z_][A-Za-z0-9_]*|[0-9]*\\.?[0-9]+");
        Matcher m = pattern.matcher(input);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

    private enum TokenType {
        IDENTIFIER("[A-Za-z_][A-Za-z0-9_]*"),
        NUMBER("[0-9]*\\.?[0-9]+"),
        ASSIGNMENT("="),
        OPERATOR("[-+*/%]"),
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
            throw new InterpreterException("Invalid token: " + token);
        }
    }

    private static class OperatorInfo {
        final int priority;
        private final DoubleBinaryOperator operator;

        OperatorInfo(int priority, DoubleBinaryOperator operator) {
            this.priority = priority;
            this.operator = operator;
        }

        double apply(double left, double right) {
            return operator.applyAsDouble(left, right);
        }
    }

    private static class Storage {

        private final Map<String, Double> data = new HashMap<>();

        void set(String name, Double val) {
            data.put(name, val);
        }

        Double get(String name) {
            return data.get(name);
        }
    }

    private static class InterpreterException extends RuntimeException {
        InterpreterException(String message) {
            super(message);
        }
    }
}
