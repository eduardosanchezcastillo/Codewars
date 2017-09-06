package eugene.codewars.repl;

import org.junit.Test;

import static org.junit.Assert.*;

public class InterpreterTest {

    @Test
    public void basicTests() {
        Interpreter interpreter = new Interpreter();

        // Basic arithmetic
        assertEquals(3, interpreter.input("1 + 2"), 0.0);
        assertEquals(1, interpreter.input("2 - 1"), 0.0);
        assertEquals(6, interpreter.input("2 * 3"), 0.0);
        assertEquals(2, interpreter.input("8 / 4"), 0.0);
        assertEquals(3, interpreter.input("7 % 4"), 0.0) ;

        // Invalid expressions
        assertFail("two assignments", () -> interpreter.input("x = 1 = 2"));
        assertFail("no operators", () -> interpreter.input("1 2"));
        assertFail("unknown identifier", () -> interpreter.input("1two"));

        // Complex expressions
        assertEquals(10.0, interpreter.input("4 + 2 * 3"), 0.0);
        assertEquals(6.0, interpreter.input("4 / 2 * 3"), 0.0);
        assertEquals(8.0, interpreter.input("7 % 2 * 8"), 0.0);
        assertEquals(18.0, interpreter.input("(4 + 2) * 3"), 0.0);
        assertEquals(2.0, interpreter.input("(7 + 3) / (2 * 2 + 1)"), 0.0);
        assertEquals(6.0, interpreter.input("(8 - (4 + 2)) * 3"), 0.0);
        assertEquals(15.0, interpreter.input("(10 / (8 - (4 + 2))) * 3"), 0.0);

        // Variables
        assertEquals(1, interpreter.input("x = 1"), 0.0);
        assertEquals(1, interpreter.input("x"), 0.0);
        assertEquals(4, interpreter.input("x + 3"), 0.0);
        assertNull(interpreter.input(" "));
        assertFail("input: 'y'", () -> interpreter.input("y"));
        assertEquals(6, interpreter.input("y = x + 5"), 0.0);
        assertEquals(6, interpreter.input("y"), 0.0);
    }

    private static void assertFail(String msg, Runnable runnable) {
        try {
            runnable.run();
            fail(msg);
        } catch (Exception e) {
            // Ok
        }
    }
}