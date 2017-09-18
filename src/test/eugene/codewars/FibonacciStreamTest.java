package eugene.codewars;

import org.junit.Test;

import java.util.PrimitiveIterator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class FibonacciStreamTest {

    @Test
    public void testThatTheFirstThirtyElementsAreCorrect() {
        assertArrayEquals("The first thirty elements are incorrect!",
                new int[]{1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040},
                FibonacciStream.generateFibonacciSequence().limit(30).toArray());
    }

    @Test(timeout = 30)
    public void testRecursivePropertyInStreamByRandomLeaps() {
        for (int i = 0; i < 3; i++) { // Repeat three times
            final PrimitiveIterator.OfInt iterator =
                    FibonacciStream.generateFibonacciSequence()
                            .skip((int) (Math.random() * 900)) // Begin leap
                            .limit(20) // End leap
                            .iterator();
            int previous = iterator.nextInt(),
                    current = iterator.nextInt();
            while (iterator.hasNext()) {
                final int next = iterator.next();
                if (next != previous + current)
                    fail(String.format("Elements %s, %s are followed by %s!", previous, current, next));
                previous = current;
                current = next;
            }
        }
    }
}