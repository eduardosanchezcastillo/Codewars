package eugene.codewars;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MathEvaluatorTest {

    private static final double DELTA = 0.01;

    @Parameterized.Parameter(value = 0)
    public String expression;

    @Parameterized.Parameter(value = 1)
    public double expected;

    @Parameterized.Parameters
    public static Collection getData() {
        return Arrays.asList(new Object[][]{
                {"1+1", 2},
                {"1 - 1", 0},
                {"1* 1", 1},
                {"1 /1", 1},
                {"-123", -123},
                {"123", 123},
                {"2 /2+3 * 4.75- -6", 21.25},
                {"12* 123", 1476},
                {"2 / (2 + 3) * 4.33 - -6", 7.732},
                {"12*-1", -12},
                {"((80 - (19)))", 61},
                {"12* 123/-(-5 + 2)", 492},
                {"(1 - 2) + -(-(-(-4)))", 3},
                {"1 - -(-(-(-4)))", -3},
                {"12* 123/(-5 + 2)", -492},
                {"(123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) - (123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) + (13 - 2)/ -(-11)", 1},
                {"((2.33 / (2.9+3.5)*4) - -6)", 7.45625},
                {"123.45*(678.90 / (-2.5+ 11.5)-(80 -19) *33.25) / 20 + 11", -12042.760875},
        });
    }

    @Test
    public void test() {
        assertEquals(expected, new MathEvaluator().calculate(expression), DELTA);
    }
}