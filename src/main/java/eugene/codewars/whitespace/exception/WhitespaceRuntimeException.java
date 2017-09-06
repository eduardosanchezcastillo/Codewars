package eugene.codewars.whitespace.exception;

public class WhitespaceRuntimeException extends RuntimeException {
    public WhitespaceRuntimeException(String message) {
        super(message);
    }

    public WhitespaceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
