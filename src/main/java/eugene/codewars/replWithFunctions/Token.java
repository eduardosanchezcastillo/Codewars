package eugene.codewars.replWithFunctions;

class Token {
    final TokenType type;
    final Double value;
    final String name;
    final int index;

    Token(TokenType type) {
        this.type = type;
        this.value = null;
        this.name = null;
        this.index = 0;
    }

    private Token(TokenType type, Double value, String name, int index) {
        this.type = type;
        this.value = value;
        this.name = name;
        this.index = index;
    }

    static Token createNumber(Double value) {
        return new Token(TokenType.NUMBER, value, null, 0);
    }

    static Token createIdentifier(String name) {
        return new Token(TokenType.IDENTIFIER, null, name, 0);
    }

    static Token createFunctionParameter(int index) {
        return new Token(TokenType.FUNCTION_PARAMETER, null, null, index);
    }

    static Token createFunctionCall(String name) {
        return new Token(TokenType.FUNCTION_CALL, null, name, 0);
    }

    static Token createOperator(String name) {
        return new Token(TokenType.OPERATOR, null, name, 0);
    }
}
