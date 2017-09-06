package eugene.codewars.whitespace;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class InterpreterTest {

    @Test
    public void testPush() {
        System.out.println("Testing push, output of numbers 0 through 3");
        String[][] tests = {
                {"   \t\n\t\n \t\n\n\n", "1"},
                {"   \t \n\t\n \t\n\n\n", "2"},
                {"   \t\t\n\t\n \t\n\n\n", "3"},
                {"    \n\t\n \t\n\n\n", "0"}
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], null, null));
        }
    }

    @Test
    public void testOutNumbers() {
        System.out.println("Testing output of numbers -1 through -3");
        String[][] tests = {
                {"  \t\t\n\t\n \t\n\n\n", "-1"},
                {"  \t\t \n\t\n \t\n\n\n", "-2"},
                {"  \t\t\t\n\t\n \t\n\n\n", "-3"},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], null));
        }
    }

    @Test(expected = Exception.class)
    public void testFlowEdge() {
        System.out.println("Testing simple flow control edge case");
        Interpreter.execute("", null);
    }

    @Test
    public void testOutLetters() {
        System.out.println("Testing output of letters A through C");
        String[][] tests = {
                {"   \t     \t\n\t\n  \n\n\n", "A"},
                {"   \t    \t \n\t\n  \n\n\n", "B"},
                {"   \t    \t\t\n\t\n  \n\n\n", "C"},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], null));
        }
    }

    @Test
    public void testOutLettersWithComments() {
        System.out.println("Testing output of letters A through C with comments");
        String[][] tests = {
                {"blahhhh   \targgggghhh     \t\n\t\n  \n\n\n", "A"},
                {" I heart \t  cats  \t \n\t\n  \n\n\n", "B"},
                {"   \t  welcome  \t\t\n\t\n to the\nnew\nworld\n", "C"},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], null));
        }
    }

    @Test
    public void testStack() {
        System.out.println("Testing stack functionality");
        String[][] tests = {
                {"   \t\t\n   \t\t\n\t\n \t\t\n \t\n\n\n", "33"},
                {"   \t\t\n \n \t\n \t\t\n \t\n\n\n", "33"},
                {"   \t\n   \t \n   \t\t\n \t  \t \n\t\n \t\n\n\n", "1"},
                {"   \t\n   \t \n   \t\t\n \t  \t\n\t\n \t\n\n\n", "2"},
                {"   \t\n   \t \n   \t\t\n \t   \n\t\n \t\n\n\n", "3"},
                {"   \t\t\n   \t \n \n\t\t\n \t\t\n \t\n\n\n", "32"},
                {"   \t\t\n   \t \n \n\t \n\n\t\n \t\n\n\n", "2"},
                {"   \t\t\n   \t \n   \t\n   \t  \n   \t\t \n   \t \t\n   \t\t\t\n \n\t \t\n \t\t\n\t\n \t\t\n \t\t\n \t\t\n \t\n\n\n", "5123"},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], null));
        }
    }

    @Test
    public void testJump() {
        System.out.println("Testing jumps");
        String[][] tests = {
                {"   \t\n   \t\t\n   \n   \t \n   \n   \t\n\n  \n\t\n \t\n\t \n\n\n\n", "123"},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], new ByteInputStream()));
        }
    }

    @Test
    public void testExitProgram() {
        System.out.println("Testing exit program");
        String[][] tests = {
                {"\n\n\n", ""},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], new ByteInputStream(), new ByteArrayOutputStream()));
        }
    }

    @Test(expected = Exception.class)
    public void testUncleanTermination() {
        System.out.println("Testing unclean termination");
        String[][] tests = {
                {"   \t\n   \t \n   \t\t\n\t\n \t\n \n\n\t\n \t\t\n \t\n  \n   \t\n   \t\n \n\n\n   \n", "3"},
        };
        for (String[] test : tests) {
            assertEquals(test[1], Interpreter.execute(test[0], null));
        }
    }

    @Test(expected = Exception.class)
    public void testDuplicatedMarks() {
        System.out.println("Testing duplicated marks");
        Interpreter.execute("   \t\n   \t \n   \t\t\n\t\n \t\n \n\n\t\n \t\t\n \t\n  \n\n  \n\n\n\n", null);
    }
}