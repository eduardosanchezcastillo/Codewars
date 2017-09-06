package eugene.codewars.replWithFunctions;

import eugene.codewars.replWithFunctions.operation.Arithmetic;
import eugene.codewars.replWithFunctions.operation.Assignment;
import eugene.codewars.replWithFunctions.operation.FunctionCall;
import eugene.codewars.replWithFunctions.operation.Operation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\s*fn\\s+([a-zA-Z_][\\w]*)((\\s+[a-zA-Z_][\\w]*)*)\\s*=>(.+)");

    private final Storage storage = new Storage();

    private static final Map<String, Operation> OPERATIONS = new HashMap<>();

    static {
        OPERATIONS.put("*", new Arithmetic(0, (l, r) -> l * r));
        OPERATIONS.put("/", new Arithmetic(0, (l, r) -> l / r));
        OPERATIONS.put("%", new Arithmetic(0, (l, r) -> l % r));
        OPERATIONS.put("+", new Arithmetic(1, (l, r) -> l + r));
        OPERATIONS.put("-", new Arithmetic(1, (l, r) -> l - r));
        OPERATIONS.put("$", new FunctionCall(2));
        OPERATIONS.put("=", new Assignment(3));
    }

    public Double input(String input) {
        System.out.print(input + " --> ");

        // Function definition
        Matcher matcher = FUNCTION_PATTERN.matcher(input);
        if (matcher.matches()) {
            defineFunction(matcher.group(1), matcher.group(2), matcher.group(4));
            return null;
        }

        // Regular expression
        try {
            Double result = evaluateExpression(input);
            System.out.println(result);
            return result;
        } catch (InterpreterException e) {
            System.out.println("ERROR: " + e.getMessage());
            throw e;
        }
    }

    private void defineFunction(String name, String parameters, String body) {
        parameters = parameters.trim();
        List<String> parameterList = parameters.isEmpty()
                ? new ArrayList<>()
                : Arrays.asList(parameters.split("\\s"));

        // check duplicates
        if (new HashSet<>(parameterList).size() != parameterList.size()) {
            throw new InterpreterException("Duplicate function parameters found.");
        }

        Deque<Token> bodyTokens = parseExpression(body, parameterList);
        storage.setFunc(name, parameterList.size(), bodyTokens);

        System.out.println("Function '" + name + "' is defined.");
    }

    private Double evaluateExpression(String expression) {
        Deque<Token> tokenStack = parseExpression(expression, null);
        ExecutionContext context = new ExecutionContext(storage);
        return evaluateExpression(tokenStack, context);
    }

    private Double evaluateExpression(Deque<Token> tokenStack, ExecutionContext context) {
        if (tokenStack.isEmpty()) {
            return null;
        }

        while (!tokenStack.isEmpty()) {
            Token token = tokenStack.removeLast();
            switch (token.type) {
                case OPERATOR:
                    applyOperation(token.name, context);
                    break;

                case FUNCTION_CALL:
                    context.push(evaluateFunction(token.name, context));
                    break;

                default:
                    context.push(token);
            }
        }

        if (context.stackSize() != 1) {
            throw new InterpreterException("Invalid expression");
        }

        return context.popValueAsNumber();
    }

    private Double evaluateFunction(String name, ExecutionContext context) {
        FunctionDefinition func = storage.getFunc(name);
        if (func == null) {
            throw new InterpreterException("Unknown function '" + name + "'");
        }

        Token arguments[] = new Token[func.parameterCount];
        for (int i = arguments.length - 1; i >= 0; i--) {
            arguments[i] = context.pop();
        }

        return evaluateExpression(
                new ArrayDeque<>(func.bodyTokens),  // need to preserve the original function body, so making a copy here
                new ExecutionContext(context.getStorage(), arguments)  // and a brand new context as well
        );
    }

    private Deque<Token> parseExpression(String expression, List<String> functionParameterList) {
        Deque<Token> operatorStack = new ArrayDeque<>();
        Deque<Token> tokenStack = new ArrayDeque<>();

        List<String> tokenList = tokenize(expression);
        for (String token : tokenList) {
            switch (getTokenType(token)) {
                case IDENTIFIER:
                    if (functionParameterList == null) {
                        if (storage.getFunc(token) != null) {
                            operatorStack.push(Token.createFunctionCall(token));
                        } else {
                            tokenStack.push(Token.createIdentifier(token));
                        }
                    } else {
                        int index = functionParameterList.indexOf(token);
                        if (index == -1) {
                            throw new InterpreterException("Unknown identifier '" + token + "'");
                        }
                        tokenStack.push(Token.createFunctionParameter(index));
                    }
                    break;

                case NUMBER:
                    tokenStack.push(Token.createNumber(Double.valueOf(token)));
                    break;

                case OPERATOR:
                    Operation operation = OPERATIONS.get(token);
                    while (!operatorStack.isEmpty()) {
                        Token operator = operatorStack.peek();
                        if (operator.type != TokenType.OPERATOR || operation.higherThan(OPERATIONS.get(operator.name))) {
                            break;
                        }
                        tokenStack.push(operatorStack.pop());
                    }
                    operatorStack.push(Token.createOperator(token));
                    break;

                case PARENTHESIS_OPEN:
                    operatorStack.push(new Token(TokenType.PARENTHESIS_OPEN));
                    break;

                case PARENTHESIS_CLOSE:
                    while (true) {
                        if (operatorStack.isEmpty()) {
                            throw new InterpreterException("Unexpected closing parenthesis");
                        }

                        Token operator = operatorStack.pop();
                        if (operator.type == TokenType.PARENTHESIS_OPEN) {
                            break;
                        } else {
                            tokenStack.push(operator);
                        }
                    }
                    break;
            }
        }

        while (!operatorStack.isEmpty()) {
            tokenStack.push(operatorStack.pop());
        }

        return tokenStack;
    }

    private void applyOperation(String name, ExecutionContext context) {
        Operation operation = OPERATIONS.get(name);
        if (operation == null) {
            throw new InterpreterException("Unhandled operator '" + name + "'");
        }
        operation.run(context);
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
}
