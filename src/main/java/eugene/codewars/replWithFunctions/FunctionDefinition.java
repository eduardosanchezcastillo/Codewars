package eugene.codewars.replWithFunctions;

import java.util.Deque;

class FunctionDefinition {
    final int parameterCount;
    final Deque<Token> bodyTokens;

    FunctionDefinition(int parameterCount, Deque<Token> bodyTokens) {
        this.parameterCount = parameterCount;
        this.bodyTokens = bodyTokens;
    }
}
