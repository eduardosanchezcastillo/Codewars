package eugene.codewars.whitespace.language;

public enum WhitespaceChar {
    SPACE,
    TAB,
    LINE_FEED;

    public static WhitespaceChar of(char ch) {
        switch (ch) {
            case ' ':
                return SPACE;
            case '\t':
                return TAB;
            case '\n':
                return LINE_FEED;
        }
        return null;
    }
}
