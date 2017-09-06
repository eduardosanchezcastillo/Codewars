package eugene.codewars.whitespace.language;

import eugene.codewars.whitespace.exception.WhitespaceRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class WhitespaceHeap {

    private final Map<Integer, Integer> heap = new HashMap<>();

    public Integer read(Integer address) {
        Integer value = heap.get(address);
        if (value == null) {
            throw new WhitespaceHeapException("Invalid heap address.");
        }
        return value;
    }

    public void write(Integer address, Integer value) {
        heap.put(address, value);
    }

    class WhitespaceHeapException extends WhitespaceRuntimeException {
        WhitespaceHeapException(String message) {
            super(message);
        }
    }
}
