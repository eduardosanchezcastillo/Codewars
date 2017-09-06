package eugene.codewars.whitespace.parser;

import eugene.codewars.whitespace.exception.WhitespaceParserException;
import eugene.codewars.whitespace.language.WhitespaceChar;

public class CodeParser {

    private final CodeTraverse codeTraverse;

    public CodeParser(String code) {
        codeTraverse = new CodeTraverse(code);
    }

    public String nextLabel() {
        StringBuilder sb = new StringBuilder();
        WhitespaceChar ch = codeTraverse.nextSignificantChar();
        while (ch == WhitespaceChar.TAB || ch == WhitespaceChar.SPACE) {
            sb.append(ch);
            ch = codeTraverse.nextSignificantChar();
        }

        if (ch != WhitespaceChar.LINE_FEED) {
            throw new WhitespaceParserException("Invalid label statement: unexpected character.");
        }

        return sb.toString();
    }

    public Integer nextNumber() {
        WhitespaceChar ch = codeTraverse.nextSignificantChar();
        if (ch != WhitespaceChar.SPACE && ch != WhitespaceChar.TAB) {
            throw new WhitespaceParserException("Invalid number statement: sign token is missing.");
        }
        boolean isNegative = ch == WhitespaceChar.TAB;

        int number = 0;
        for (ch = codeTraverse.nextSignificantChar(); ch != WhitespaceChar.LINE_FEED; ch = codeTraverse.nextSignificantChar()) {
            switch (ch) {
                case SPACE:
                    number <<= 1;
                    break;
                case TAB:
                    number <<= 1;
                    number++;
                    break;
                default:
                    throw new WhitespaceParserException("Invalid number statement: unexpected character.");
            }
        }

        return isNegative ? -number : number;
    }

    public WhitespaceChar nextSignificantChar() {
        return codeTraverse.nextSignificantChar();
    }

    public int getPosition() {
        return codeTraverse.getPosition();
    }

    public void setPosition(int position) {
        codeTraverse.setPosition(position);
    }

    public boolean isFinished() {
        return codeTraverse.isFinished();
    }

    public String getCode() {
        return codeTraverse.getCode();
    }
}
