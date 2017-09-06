package eugene.codewars.whitespace.runner;

import eugene.codewars.whitespace.CommandRunner;
import eugene.codewars.whitespace.dto.PreparedCodeDTO;
import eugene.codewars.whitespace.exception.WhitespaceParserException;
import eugene.codewars.whitespace.exception.WhitespaceRuntimeException;
import eugene.codewars.whitespace.language.WhitespaceCommandPath;
import eugene.codewars.whitespace.language.WhitespaceCommandPathNode;
import eugene.codewars.whitespace.language.WhitespaceHeap;
import eugene.codewars.whitespace.language.WhitespaceStack;
import eugene.codewars.whitespace.parser.CodeParser;
import eugene.codewars.whitespace.util.CommandReference;

import java.io.*;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;

public class CommandRunnerCodeWars implements CommandRunner {

    // IO
    private final Scanner inputScanner;

    private final Writer outputWriter;

    private final StringBuilder outputSB = new StringBuilder();

    // Memory
    private final WhitespaceStack stack = new WhitespaceStack();

    private final WhitespaceHeap heap = new WhitespaceHeap();

    // Code and execution
    private final CodeParser codeParser;

    private final Map<String, Integer> markPositionsMap;

    private final WhitespaceCommandPath commandPath;

    private final Stack<Integer> functionStack = new Stack<>();

    private boolean exit = false;

    public CommandRunnerCodeWars(PreparedCodeDTO preparedCodeDTO, InputStream inputStream, OutputStream outputStream) {
        codeParser = new CodeParser(preparedCodeDTO.getCode());
        commandPath = preparedCodeDTO.getCommandPath();
        markPositionsMap = preparedCodeDTO.getMarks();
        outputWriter = new PrintWriter(outputStream);

        if (inputStream == null) {
            inputScanner = new Scanner("");
        } else {
            inputScanner = new Scanner(inputStream);
        }
        inputScanner.useDelimiter("\n");
    }

    @Override
    public String run() {
        try {
            while (!exit) {
                CommandReference commandReference = nextCommand(commandPath.get(codeParser.nextSignificantChar()));
                commandReference.accept(this);
            }
            return outputSB.toString();
        } finally {
            try {
                outputWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CommandReference nextCommand(WhitespaceCommandPathNode node) {
        if (node == null) {
            throw new WhitespaceParserException("Invalid command at position " + codeParser.getPosition());
        }

        if (node.isCommand()) {
            return node.getCommand();
        } else {
            return nextCommand(node.getSubPath().get(codeParser.nextSignificantChar()));
        }
    }

    @Override
    public Map<String, Integer> getMarks() {
        return markPositionsMap;
    }

    @Override
    public void stackPush() {
        stack.push(codeParser.nextNumber());
    }

    @Override
    public void stackDuplicateNth() {
        int index = codeParser.nextNumber();
        stack.push(stack.getAt(stack.size() - index - 1));
    }

    @Override
    public void stackDiscardTopN() {
        Integer topValue = stack.pop();

        int count = codeParser.nextNumber();
        if (count < 0 || count > stack.size()) {
            count = stack.size();
        }
        while (count-- > 0) {
            stack.pop();
        }

        stack.push(topValue);
    }

    @Override
    public void stackDuplicateTop() {
        stack.push(stack.peek());
    }

    @Override
    public void stackSwap() {
        Integer a = stack.pop();
        Integer b = stack.pop();
        stack.push(a);
        stack.push(b);
    }

    @Override
    public void stackDiscardTop() {
        stack.pop();
    }

    @Override
    public void arithmeticAdd() {
        arithmeticOperation(Math::addExact);
    }

    @Override
    public void arithmeticSubtract() {
        arithmeticOperation(Math::subtractExact);
    }

    @Override
    public void arithmeticMultiply() {
        arithmeticOperation(Math::multiplyExact);
    }

    @Override
    public void arithmeticDivide() {
        arithmeticOperation(Math::floorDiv);
    }

    @Override
    public void arithmeticModulo() {
        arithmeticOperation(Math::floorMod);
    }

    private void arithmeticOperation(BinaryOperator<Integer> operator) {
        Integer a = stack.pop();
        Integer b = stack.pop();
        stack.push(operator.apply(b, a));
    }

    @Override
    public void heapWrite() {
        Integer value = stack.pop();
        Integer address = stack.pop();
        heap.write(address, value);
    }

    @Override
    public void heapRead() {
        Integer value = heap.read(stack.pop());
        stack.push(value);
    }

    @Override
    public void outputChar() {
        char ch = (char) stack.pop().intValue();
        try {
            outputSB.append(ch);
            outputWriter.append(ch);
        } catch (IOException e) {
            throw new WhitespaceOperationException("Error while outputting char.", e);
        }
    }

    @Override
    public void outputNumber() {
        Integer value = stack.pop();
        try {
            outputSB.append(value);
            outputWriter.append(value.toString());
        } catch (IOException e) {
            throw new WhitespaceOperationException("Error while outputting number.", e);
        }
    }

    @Override
    public void inputChar() {
        Integer address = stack.pop();
        int value = inputScanner.findInLine(".").charAt(0);
        heap.write(address, value);
    }

    @Override
    public void inputNumber() {
        Integer address = stack.pop();
        int value = inputScanner.nextInt();
        heap.write(address, value);
    }

    @Override
    public void flowMark() {
        String label = codeParser.nextLabel();
        int position = codeParser.getPosition();

        // make sure this is a unique label
        if (!Objects.equals(markPositionsMap.get(label), position)) {
            throw new WhitespaceOperationException(String.format("Invalid label statement: label '%s' is already defined.", label));
        }
    }

    @Override
    public void flowCallFunction() {
        functionStack.push(codeParser.getPosition());
        flowJumpWithPredicate(() -> true, true);
    }

    @Override
    public void flowJump() {
        flowJumpWithPredicate(() -> true, false);
    }

    @Override
    public void flowJumpIfZero() {
        flowJumpWithPredicate(() -> stack.pop().equals(0), false);
    }

    @Override
    public void flowJumpIfNegative() {
        flowJumpWithPredicate(() -> stack.pop() < 0, false);
    }

    private void flowJumpWithPredicate(BooleanSupplier condition, boolean isFunctionCall) {
        String label = codeParser.nextLabel();

        if (!condition.getAsBoolean()) {
            return;
        }

        Integer position = markPositionsMap.get(label);
        if (position == null) {
            throw new WhitespaceParserException(String.format("Unknown mark: '%s'.", label));
        }

        if (isFunctionCall) {
            functionStack.push(codeParser.getPosition());
        }
        codeParser.setPosition(position);
    }

    @Override
    public void flowExitFunction() {
        if (functionStack.isEmpty()) {
            throw new WhitespaceOperationException("Invalid command to exit subroutine outside of a subroutine.");
        }
        int position = functionStack.pop();
        codeParser.setPosition(position);
    }

    @Override
    public void flowExitProgram() {
        exit = true;
    }

    class WhitespaceOperationException extends WhitespaceRuntimeException {
        WhitespaceOperationException(String message) {
            super(message);
        }

        WhitespaceOperationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
