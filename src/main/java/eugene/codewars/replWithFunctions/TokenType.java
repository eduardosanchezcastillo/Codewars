package eugene.codewars.replWithFunctions;

import java.util.regex.Pattern;

enum TokenType {
    IDENTIFIER("[A-Za-z_][A-Za-z0-9_]*"),
    NUMBER("[0-9]*\\.?[0-9]+"),
    OPERATOR("[-+*/%=]"),
    PARENTHESIS_OPEN("\\("),
    PARENTHESIS_CLOSE("\\)"),
    FUNCTION_PARAMETER("$"),
    FUNCTION_CALL("&");

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
