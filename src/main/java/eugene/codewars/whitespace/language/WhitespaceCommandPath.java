package eugene.codewars.whitespace.language;

import eugene.codewars.whitespace.exception.WhitespaceRuntimeException;
import eugene.codewars.whitespace.util.CommandReference;

import java.util.EnumMap;

/**
 * Stores commands in a tree structure: character -> node. Allows to parse commands quickly.
 * Each "path" in the tree ends with an action for the parsed command.
 */
public class WhitespaceCommandPath extends EnumMap<WhitespaceChar, WhitespaceCommandPathNode> {
    public WhitespaceCommandPath() {
        super(WhitespaceChar.class);
    }

    public WhitespaceCommandPath addCommand(String commandString, CommandReference command) {
        WhitespaceChar key = WhitespaceChar.of(commandString.charAt(0));
        if (key == null) {
            throw new WhitespaceLanguageDefinitionException("Invalid command definition.");
        }

        if (commandString.length() == 1) {
            put(key, new WhitespaceCommandPathNode(command));
        } else {
            WhitespaceCommandPathNode node = this.computeIfAbsent(key, x -> new WhitespaceCommandPathNode(new WhitespaceCommandPath()));
            node.getSubPath().addCommand(commandString.substring(1), command);
        }

        return this;
    }

    class WhitespaceLanguageDefinitionException extends WhitespaceRuntimeException {
        WhitespaceLanguageDefinitionException(String message) {
            super(message);
        }
    }
}
