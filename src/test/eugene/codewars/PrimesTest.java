package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PrimesTest {

    @Test
    public void test_0_10() {
        test(0, 10, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    }

    @Test
    public void test_10_9() {
        test(10, 9, 31, 37, 41, 43, 47, 53, 59, 61, 67);
    }

    @Test
    public void test_100_10() {
        test(100, 10, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601);
    }

    @Test
    public void test_1000_10() {
        test(1000, 10, 7927, 7933, 7937, 7949, 7951, 7963, 7993, 8009, 8011, 8017);
    }

    @Test
    public void test_10000_10() {
        test(10000, 10, 104743, 104759, 104761, 104773, 104779, 104789, 104801, 104803, 104827, 104831);
    }

    @Test
    public void test_100000_10() {
        test(100000, 10, 1299721, 1299743, 1299763, 1299791, 1299811, 1299817, 1299821, 1299827, 1299833, 1299841);
    }

    @Test
    public void test_1000000_10() {
        test(1000000, 10, 15485867, 15485917, 15485927, 15485933, 15485941, 15485959, 15485989, 15485993, 15486013, 15486041);
    }

    private void test(int skip, int limit, int... expect) {
        int[] found = Primes.stream().skip(skip).limit(limit).toArray();
        assertArrayEquals(expect, found);
    }

}