package eugene.codewars.whitespace.runner;

import eugene.codewars.whitespace.CommandRunner;
import eugene.codewars.whitespace.Interpreter;
import eugene.codewars.whitespace.language.WhitespaceCommandPath;
import eugene.codewars.whitespace.language.WhitespaceCommandPathNode;
import eugene.codewars.whitespace.parser.CodeParser;
import eugene.codewars.whitespace.util.CommandReference;

import java.util.HashMap;
import java.util.Map;

public class FirstPassRunner implements CommandRunner {

    private final WhitespaceCommandPath commandPath;

    private final CodeParser codeParser;

    private final Map<String, Integer> labelMap = new HashMap<>();

    private boolean visualizeCode;

    public FirstPassRunner(String code, WhitespaceCommandPath commandPath, boolean visualizeCode) {
        codeParser = new CodeParser(code);
        this.commandPath = commandPath;
        this.visualizeCode = visualizeCode;
    }

    @Override
    public String run() {
        if (visualizeCode) {
            logHeader();
        }

        while (!codeParser.isFinished()) {
            CommandReference commandReference = nextCommand(commandPath.get(codeParser.nextSignificantChar()));
            if (commandReference == null) {
                System.out.println("Invalid command found! Stopping visualization");
                break;
            } else {
                commandReference.accept(this);
            }
        }

        return null;
    }

    private CommandReference nextCommand(WhitespaceCommandPathNode node) {
        if (node == null) {
            return null;
        }

        if (node.isCommand()) {
            return node.getCommand();
        } else {
            return nextCommand(node.getSubPath().get(codeParser.nextSignificantChar()));
        }
    }

    @Override
    public Map<String, Integer> getMarks() {
        return labelMap;
    }

    @Override
    public void stackPush() {
        log(String.format("STACK: push (%d)", codeParser.nextNumber()));
    }

    @Override
    public void stackDuplicateNth() {
        log(String.format("STACK: duplicate %dth value", codeParser.nextNumber()));
    }

    @Override
    public void stackDiscardTopN() {
        log(String.format("STACK: discard top %d values", codeParser.nextNumber()));
    }

    @Override
    public void stackDuplicateTop() {
        log("STACK: duplicate top value");
    }

    @Override
    public void stackSwap() {
        log("STACK: swap");
    }

    @Override
    public void stackDiscardTop() {
        log("STACK: discard top value");
    }

    @Override
    public void arithmeticAdd() {
        log("ARITHMETIC: add");
    }

    @Override
    public void arithmeticSubtract() {
        log("ARITHMETIC: subtract");
    }

    @Override
    public void arithmeticMultiply() {
        log("ARITHMETIC: multiply");
    }

    @Override
    public void arithmeticDivide() {
        log("ARITHMETIC: divide");
    }

    @Override
    public void arithmeticModulo() {
        log("ARITHMETIC: mod");
    }

    @Override
    public void heapWrite() {
        log("HEAP: write");
    }

    @Override
    public void heapRead() {
        log("HEAP: read");
    }

    @Override
    public void outputChar() {
        log("IO: pop and output as char");
    }

    @Override
    public void outputNumber() {
        log("IO: pop and output as number");
    }

    @Override
    public void inputChar() {
        log("IO: input char");
    }

    @Override
    public void inputNumber() {
        log("IO: input number");
    }

    @Override
    public void flowMark() {
        String label = codeParser.nextLabel();
        int position = codeParser.getPosition();
        labelMap.putIfAbsent(label, position);  // only preserve the very first appearance of each label; duplicates will be checked later
        log(String.format("FLOW: mark as '%s'", Interpreter.unbleach(label)));
    }

    @Override
    public void flowCallFunction() {
        log(String.format("FLOW: call function '%s'", Interpreter.unbleach(codeParser.nextLabel())));
    }

    @Override
    public void flowJump() {
        log(String.format("FLOW: jump to '%s'", Interpreter.unbleach(codeParser.nextLabel())));
    }

    @Override
    public void flowJumpIfZero() {
        log(String.format("FLOW: jump to '%s' if zero", Interpreter.unbleach(codeParser.nextLabel())));
    }

    @Override
    public void flowJumpIfNegative() {
        log(String.format("FLOW: jump to '%s' if negative", Interpreter.unbleach(codeParser.nextLabel())));
    }

    @Override
    public void flowExitFunction() {
        log("FLOW: exit function");
    }

    @Override
    public void flowExitProgram() {
        log("FLOW: exit program");
    }

    private void log(String message) {
        if (visualizeCode) {
            System.out.println(message);
        }
    }

    private void logHeader() {
        log("");
        log("----------------------------------------");
        log(Interpreter.unbleach(codeParser.getCode()));
    }
}
