package eugene.codewars;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    private static Random rand = new Random();

    @Test
    public void simpleLiteral() {
        Double d = (double) rand.nextInt();
        assertEquals("simple literal", d, Calculator.evaluate(d.toString()));
    }

    @Test
    public void subtractionAndAddition() {
        Double a = (double) rand.nextInt();
        Double b = (double) rand.nextInt();
        Double c = (double) rand.nextInt();
        assertEquals("addition", new Double(a + b), Calculator.evaluate(a.intValue() + " + " + b.intValue()));
        assertEquals("subtraction", new Double(a - b - c), Calculator.evaluate(a.intValue() + " - " + b.intValue() + " - " + c.intValue()));
    }

    @Test
    public void divisionAndMultiplication() {
        assertEquals("mixed division and multiplication", new Double(25), Calculator.evaluate("10 * 5 / 2"));
    }

    @Test
    public void allMixed() {
        assertEquals("mixed 1", new Double(7), Calculator.evaluate("2 / 2 + 3 * 4 - 6"));
        assertEquals("mixed 2", new Double(8), Calculator.evaluate("2 + 3 * 4 / 3 - 6 / 3 * 3 + 8"));
    }

    @Test
    public void floats() {
        assertEquals("floats 1", new Double(6.6), Calculator.evaluate("1.1 + 2.2 + 3.3"));
        assertEquals("floats 2", new Double(7.986), Calculator.evaluate("1.1 * 2.2 * 3.3"));
    }
}