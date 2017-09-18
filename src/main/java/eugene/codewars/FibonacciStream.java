package eugene.codewars;

/*
    Fibonacci Streaming
    https://www.codewars.com/kata/fibonacci-streaming/java

You're going to provide a needy programmer a utility method that generates an infinite sized, sequential IntStream which
contains all the numbers in a fibonacci sequence.

A fibonacci sequence starts with two 1s. Every element afterwards is the sum of the two previous elements. See:
    1, 1, 2, 3, 5, 8, 13, ..., 89, 144, 233, 377, ...
*/

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

class FibonacciStream {
    static IntStream generateFibonacciSequence() {
        return IntStream.iterate(1, new FibonacciOperator());
    }
}

class FibonacciOperator implements IntUnaryOperator {
    private int prev = 0;

    @Override
    public int applyAsInt(int curr) {
        int next = prev + curr;
        prev = curr;
        return next;
    }
}