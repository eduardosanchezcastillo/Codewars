package eugene.codewars.replWithFunctions;

import java.util.ArrayDeque;
import java.util.Deque;

public class ExecutionContext {
    private final Storage storage;
    private final Deque<Token> stack = new ArrayDeque<>();

    private final boolean isFunction;
    private final Token functionArguments[];

    public ExecutionContext(Storage storage) {
        this(storage, null);
    }

    public ExecutionContext(Storage storage, Token functionArguments[]) {
        this.storage = storage;
        isFunction = functionArguments != null;
        this.functionArguments = functionArguments;
    }

    public Storage getStorage() {
        return storage;
    }

    public int stackSize() {
        return stack.size();
    }

    public void push(Token token) {
        stack.push(token);
    }

    public void push(Double value) {
        stack.push(Token.createNumber(value));
    }

    public Token pop() {
        return stack.pop();
    }

    public String popValueAsName() {
        if (stack.isEmpty()) {
            throw new InterpreterException("Invalid expression");
        }

        Token token = stack.pop();
        if (token.type == TokenType.IDENTIFIER || token.type == TokenType.FUNCTION_CALL) {
            return token.name;
        } else {
            throw new InterpreterException("Invalid expression");
        }
    }

    public Double popValueAsNumber() {
        if (stack.isEmpty()) {
            throw new InterpreterException("Invalid expression");
        }

        Token token = resolveNumber(stack.pop());
        return token.value;
    }

    private Token resolveNumber(Token token) {
        switch (token.type) {
            case NUMBER:
                return token;

            case IDENTIFIER:
                if (isFunction) {
                    throw new InterpreterException("Unknown identifier '" + token.name + "' within function");
                }

                Double value = storage.getVar(token.name);
                if (value == null) {
                    throw new InterpreterException("Unknown identifier '" + token.name + "'");
                }
                return Token.createNumber(value);

            case FUNCTION_PARAMETER:
                if (!isFunction || token.index < 0 || token.index >= functionArguments.length) {
                    throw new InterpreterException("Invalid expression");
                }
                return resolveNumber(functionArguments[token.index]);

            default:
                throw new InterpreterException("Invalid expression");
        }
    }
}
