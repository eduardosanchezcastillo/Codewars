package eugene.codewars.whitespace.language;

import eugene.codewars.whitespace.exception.WhitespaceRuntimeException;

import java.util.Stack;

public class WhitespaceStack {

    private final Stack<Integer> stack = new Stack<>();

    public int size() {
        return stack.size();
    }

    public Integer peek() {
        if (stack.isEmpty()) {
            throw new WhitespaceStackException("Unable to peak value: Stack is empty.");
        }
        return stack.peek();
    }

    public Integer pop() {
        if (stack.isEmpty()) {
            throw new WhitespaceStackException("Unable to pop value: Stack is empty.");
        }
        return stack.pop();
    }

    public Integer getAt(int index) {
        if (index < 0 || index >= stack.size()) {
            throw new WhitespaceStackException("Stack index is out of bounds.");
        }
        return stack.elementAt(index);
    }

    public void push(Integer val) {
        stack.push(val);
    }

    class WhitespaceStackException extends WhitespaceRuntimeException {
        WhitespaceStackException(String message) {
            super(message);
        }
    }
}
