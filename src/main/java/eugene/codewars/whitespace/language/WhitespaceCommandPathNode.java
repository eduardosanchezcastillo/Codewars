package eugene.codewars.whitespace.language;

import eugene.codewars.whitespace.util.CommandReference;

public class WhitespaceCommandPathNode {
    private final CommandReference command;
    private final WhitespaceCommandPath subPath;

    WhitespaceCommandPathNode(CommandReference command) {
        this.command = command;
        subPath = null;
    }

    WhitespaceCommandPathNode(WhitespaceCommandPath subPath) {
        this.subPath = subPath;
        command = null;
    }

    public boolean isCommand() {
        return command != null;
    }

    public CommandReference getCommand() {
        return command;
    }

    public WhitespaceCommandPath getSubPath() {
        return subPath;
    }
}
