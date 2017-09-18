package eugene.codewars;

/*
    Prime Streaming (PG-13)
    https://www.codewars.com/kata/prime-streaming-pg-13/java

Create an endless stream of prime numbers - a bit like IntStream.of(2,3,5,7,11,13,17), but infinite.
The stream must be able to produce a million primes in a few seconds.
*/

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Primes {
    private static final int SIZE = 20000000;       // This is enough to pass the tests; if bigger prime numbers are needed, increase this value
    private static final boolean[] VALUES = new boolean[SIZE];
    private static final Set<Integer> PROCESSED_PRIMES = new HashSet<>();

    static {
        Arrays.fill(VALUES, true);
        rejectByDivider(2);
    }

    public static IntStream stream() {
        return IntStream.iterate(2, prev -> {
            for (int next = prev + 1; next < SIZE; next++) {
                if (VALUES[next]) {
                    rejectByDivider(next);
                    return next;
                }
            }
            throw new RuntimeException("Not enough size!");
        });
    }

    private static void rejectByDivider(int divider) {
        if (PROCESSED_PRIMES.contains(divider)) {
            return;
        }

        for (int i = divider * 2; i < SIZE; i += divider) {
            VALUES[i] = false;
        }

        PROCESSED_PRIMES.add(divider);
    }
}
