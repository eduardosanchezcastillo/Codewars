package eugene.codewars.whitespace.parser;

import eugene.codewars.whitespace.exception.WhitespaceParserException;
import eugene.codewars.whitespace.language.WhitespaceChar;

class CodeTraverse {

    private final String code;

    private int position = 0;

    CodeTraverse(String code) {
        this.code = code;
    }

    String getCode() {
        return code;
    }

    int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    boolean isFinished() {
        return position >= code.length();
    }

    WhitespaceChar nextSignificantChar() {
        while (!isFinished()) {
            WhitespaceChar ch = WhitespaceChar.of(nextChar());
            if (ch != null) {
                return ch;
            }
        }
        throw new WhitespaceParserException("Unexpected end of code reached.");
    }

    private char nextChar() {
        if (isFinished()) {
            throw new WhitespaceParserException("Unexpected end of code reached.");
        }
        return code.charAt(position++);
    }
}
