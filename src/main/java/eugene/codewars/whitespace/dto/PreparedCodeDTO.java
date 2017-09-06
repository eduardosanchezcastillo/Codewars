package eugene.codewars.whitespace.dto;

import eugene.codewars.whitespace.language.WhitespaceCommandPath;

import java.util.Map;

public class PreparedCodeDTO {

    private final String code;

    private final Map<String, Integer> marks;

    private final WhitespaceCommandPath commandPath;

    public PreparedCodeDTO(String code, Map<String, Integer> marks, WhitespaceCommandPath commandPath) {
        this.code = code;
        this.marks = marks;
        this.commandPath = commandPath;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Integer> getMarks() {
        return marks;
    }

    public WhitespaceCommandPath getCommandPath() {
        return commandPath;
    }
}
