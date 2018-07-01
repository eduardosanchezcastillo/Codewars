package eugene.codewars;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DecomposeTest {

    private static long[] string2LongArray(String s) {
        return Arrays.stream(s.split(" ")).mapToLong(Integer::parseInt).toArray();
    }

    private static boolean isSorted(long[] a) {
        if (a.length < 2) return false;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] >= a[i + 1]) return false;
        }
        return true;
    }

    private static boolean total(long[] a, long m) {
        int i;
        long sum = 0;
        for (i = 0; i < a.length; i++) {
            sum += a[i] * a[i];
        }
        if (sum == m) return true;
        else return false;
    }

    private static void dotest(long n, String sexpr) {
        boolean success;
        Decompose d = new Decompose();
        String sact = d.decompose(n);
        boolean st = false;
        boolean t = false;
        System.out.printf("Expected %s and got %s\n", sexpr, sact, "\n");
        if (((sact == null) && (sexpr == null)) || ((sact != null) && (sact.equals(sexpr)))) {
            System.out.printf("GOOD\n");
            success = true;
        } else {
            if (sact == null) {
                success = false;
            } else {
                long[] intarr1 = string2LongArray(sact);
                st = isSorted(intarr1);
                t = total(intarr1, n * n);
                if ((st == false) || (t == false)) {
                    System.out.printf("** Error. Not increasinly sorted or bad sum of squares\n **");
                    success = false;
                } else {
                    System.out.printf("GOOD; Increasing and total correct\n");
                    success = true;
                }
            }
        }
        assertEquals(true, success);
    }

    @Test
    public void test() {
        dotest(2, null);
        dotest(11, "1 2 4 10");
        dotest(12, "1 2 3 7 9");
        dotest(625, "2 5 8 34 624");
        dotest(7100, "2 3 5 119 7099");
        dotest(12345, "2 6 157 12344");
        dotest(1234567, "2 8 32 1571 1234566");

        dotest(7654321, "6 10 69 3912 7654320");
        dotest(7654322, "1 4 11 69 3912 7654321");
        dotest(76, "1 2 5 11 75");
        dotest(7, "2 3 6");
        dotest(4, null);

        dotest(27834, "1 21 235 27833");
        dotest(18203, "4 17 190 18202");
        dotest(42017, "1 3 5 6 21 289 42016");
        dotest(4410, "1 13 93 4409");
        dotest(39959, "1 2 8 18 282 39958");
        dotest(8832, "1 2 3 15 132 8831");
        dotest(39114, "5 19 279 39113");
        dotest(8665, "2 8 10 131 8664");
        dotest(35315, "2 20 265 35314");
        dotest(37861, "2 4 25 274 37860");

        dotest(709185, "3 12 46 1190 709184");
        dotest(754411, "2 7 28 1228 754410");
        dotest(258900, "1 2 7 28 719 258899");
        dotest(415475, "2 32 911 415474");
        dotest(518452, "1 7 23 1018 518451");
        dotest(522853, "1 8 34 1022 522852");
        dotest(72217, "1 2 3 7 27 379 72216");
        dotest(242848, "1 2 7 35 696 242847");
        dotest(500596, "1 3 5 34 1000 500595");

        dotest(9927447, "1 3 17 87 4455 9927446");
        dotest(9927447, "1 2 3 5 6 7 88 4455 9927446");
        dotest(5, "3 4");
        dotest(6, null);
        dotest(8, null);
        dotest(9, "1 4 8");
        dotest(10, "6 8");
    }

    private static int randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private String tryDecomp(long nb, long rac) {
        if (nb == 0) return "";
        String l = null;
        long i = rac;
        while (i >= (long) Math.sqrt(nb / 2.0) + 1) {
            long diff = nb - i * i;
            rac = (long) Math.sqrt(diff);
            l = tryDecomp(diff, rac);
            if (l != null) {
                return l + " " + i;
            }
            i -= 1;
        }
        return null;
    }

    public String decomposeKD(long n) {
        String l = tryDecomp(n * n, (long) Math.sqrt(n * n - 1));
        return l != null ? l.trim() : l;
    }

    @Test
    public void testA() {
        System.out.println("****** Random Tests *******");
        for (int i = 0; i < 10; i++) {
            long a = randInt(8000, 20000);
            String sol = decomposeKD(a);
            dotest(a, sol);
        }
    }

}