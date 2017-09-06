package eugene.codewars.whitespace.util;

import eugene.codewars.whitespace.CommandRunner;

import java.util.function.Consumer;

@FunctionalInterface
public interface CommandReference extends Consumer<CommandRunner> {
}
