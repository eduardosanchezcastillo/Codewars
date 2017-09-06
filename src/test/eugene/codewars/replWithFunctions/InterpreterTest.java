package eugene.codewars.replWithFunctions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InterpreterTest {
    @Test
    public void basicExpressions() {
        Interpreter interpreter = new Interpreter();

        // Should handle empty input
        assertEquals("input: ''", null, interpreter.input(""));
        assertEquals("input: ' '", null, interpreter.input(" "));

        // Should echo constants
        assertEquals("input: '9'", 9, interpreter.input("9"), 0.0);

        // Should reject invalid input
        assertFail("input: '1 2'", () -> interpreter.input("1 2"));
        assertFail("input: '1two'", () -> interpreter.input("1two"));

        // Should handle addition
        assertEquals("input: '1 + 1'", 2, interpreter.input("1 + 1"), 0.0);
        assertEquals("input: '2+2'", 4, interpreter.input("2+2"), 0.0);

        // Should handle subtraction
        assertEquals("input: '2 - 1'", 1, interpreter.input("2 - 1"), 0.0);
        assertEquals("input: '4-6'", -2, interpreter.input("4-6"), 0.0);

        // Should handle multiplication
        assertEquals("input: '2 * 3'", 6, interpreter.input("2 * 3"), 0.0);

        // Should handle division
        assertEquals("input: '8 / 4'", 2, interpreter.input("8 / 4"), 0.0);

        // Should handle modulo
        assertEquals("input: '7 % 4'", 3, interpreter.input("7 % 4"), 0.0);
    }

    @Test
    public void complexExpressions() {
        Interpreter interpreter = new Interpreter();

        // Should handle multiple operations
        assertEquals("input: '4 + 2 * 3'", 10, interpreter.input("4 + 2 * 3"), 0.0);
        assertEquals("input: '4 / 2 * 3'", 6, interpreter.input("4 / 2 * 3"), 0.0);
        assertEquals("input: '7 % 2 * 8'", 8, interpreter.input("7 % 2 * 8"), 0.0);

        // Should handle parentheses
        assertEquals("input: '(4 + 2) * 3'", 18, interpreter.input("(4 + 2) * 3"), 0.0);
        assertEquals("input: '(7 + 3) / (2 * 2 + 1)'", 2, interpreter.input("(7 + 3) / (2 * 2 + 1)"), 0.0);

        // Should handle nested parentheses
        assertEquals("input: '(8 - (4 + 2)) * 3'", 6, interpreter.input("(8 - (4 + 2)) * 3"), 0.0);
        assertEquals("input: '(10 / (8 - (4 + 2))) * 3'", 15, interpreter.input("(10 / (8 - (4 + 2))) * 3"), 0.0);
    }

    @Test
    public void variables() {
        Interpreter interpreter = new Interpreter();

        // Should assign a constant to a variable
        assertEquals("input: 'x = 7'", 7, interpreter.input("x = 7"), 0.0);

        // Should read the value of a variable
        assertEquals("input: 'x'", 7, interpreter.input("x"), 0.0);

        // Should handle variables in expressions
        assertEquals("input: 'x + 3'", 10, interpreter.input("x + 3"), 0.0);

        // Should throw an error when variables don't exist
        assertFail("input: 'y'", () -> interpreter.input("y"));

        // Should continue to function after an error is thrown
        assertEquals("input: 'y = x + 5'", 12, interpreter.input("y = x + 5"), 0.0);
        assertEquals("input: 'y'", 12, interpreter.input("y"), 0.0);

        // Should handle chained assignment
        assertEquals("input: 'x = y = 713'", 713, interpreter.input("x = y = 713"), 0.0);
        assertEquals("input: 'x'", 713, interpreter.input("x"), 0.0);
        assertEquals("input: 'y'", 713, interpreter.input("y"), 0.0);

        // Should handle nested assignment
        assertEquals("input: 'x = 29 + (y = 11)'", 40, interpreter.input("x = 29 + (y = 11)"), 0.0);
        assertEquals("input: 'x'", 40, interpreter.input("x"), 0.0);
        assertEquals("input: 'y'", 11, interpreter.input("y"), 0.0);
    }

    @Test
    public void functions() {
        Interpreter interpreter = new Interpreter();

        interpreter.input("x = 23");
        interpreter.input("y = 25");
        interpreter.input("z = 0");

        // Should declare a valid function without error
        assertEquals("input: 'fn one => 1'", null, interpreter.input("fn one => 1"));
        assertEquals("input: 'fn avg x y => (x + y) / 2'", null, interpreter.input("fn avg x y => (x + y) / 2"));
        assertEquals("input: 'fn echo x => x'", null, interpreter.input("fn echo x => x"));

        // Should throw an error when a function's expression contains invalid variable names
        assertFail("input: 'fn add x y => x + z'", () -> interpreter.input("fn add x y => x + z"));

        // Should throw an error when a function's declaration includes duplicate variable names
        assertFail("input: 'fn add x x => x + x'", () -> interpreter.input("fn add x x => x + x"));

        // Should throw an error when a function is declared within an expression
        assertFail("input: '(fn f => 1)", () -> interpreter.input("(fn f => 1)"));

        // Should call a declared function
        assertEquals("input: 'one'", 1, interpreter.input("one"), 0.0);
        assertEquals("input: 'avg 4 2'", 3, interpreter.input("avg 4 2"), 0.0);

        // Should throw an error when a function is called with too few arguments
        assertFail("input: 'avg 7'", () -> interpreter.input("avg 7"));

        // Should throw an error when a function is called with too many arguments
        assertFail("input: 'avg 7 2 4'", () -> interpreter.input("avg 7 2 4"));

        // Should call chained functions
        assertEquals("input: 'avg echo 4 echo 2'", 3, interpreter.input("avg echo 4 echo 2"), 0.0);

        // Should throw an error when chained function calls result in too few arguments
        assertFail("input: 'avg echo 7'", () -> interpreter.input("avg echo 7"));

        // Should throw an error when chained function calls result in too many arguments
        assertFail("input: 'avg echo 7 echo 2 echo 4'", () -> interpreter.input("avg echo 7 echo 2 echo 4"));
    }

    @Test
    public void conflicts() {
        Interpreter interpreter = new Interpreter();

        interpreter.input("x = 0");
        interpreter.input("fn f => 1");

        // Should throw an error when a function with the name of an existing variable is declared
        assertFail("input: 'fn x => 0'", () -> interpreter.input("fn x => 0"));

        // Should throw an error when a variable with the name of an existing function is declared
        assertFail("input: 'f = 5'", () -> interpreter.input("f = 5"));

        // Should overwrite an existing function
        assertEquals("input: 'f'", 1, interpreter.input("f"), 0.0);
        interpreter.input("fn f => 0");
        assertEquals("input: 'f'", 0, interpreter.input("f"), 0.0);
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