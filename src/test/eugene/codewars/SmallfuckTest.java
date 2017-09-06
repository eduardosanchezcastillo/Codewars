package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.*;

public class SmallfuckTest {
    @Test
    public void testExamples() {
        // Flips the leftmost cell of the tape
        assertEquals("10101100", Smallfuck.interpreter("*", "00101100"));

        // Flips the second and third cell of the tape
        assertEquals("01001100", Smallfuck.interpreter(">*>*", "00101100"));

        // Flips all the bits in the tape
        assertEquals("11010011", Smallfuck.interpreter("*>*>*>*>*>*>*>*", "00101100"));

        // Flips all the bits that are initialized to 0
        assertEquals("11111111", Smallfuck.interpreter("*>*>>*>>>*>*", "00101100"));

        // Goes somewhere to the right of the tape and then flips all bits that are initialized to 1, progressing leftwards through the tape
        assertEquals("00000000", Smallfuck.interpreter(">>>>>*<*<<*", "00101100"));
    }

    @Test
    public void testIgnoreNonCommandsCharacters() {
        assertEquals("Your interpreter should ignore all non-command characters", "10101100", Smallfuck.interpreter("iwmlis *!BOSS 333 ^v$#@", "00101100"));
        assertEquals("Your interpreter should not treat any of \"+\", \"-\", \",\", \".\" (valid brainfuck commands) and \";\" as valid command characters", "01001100", Smallfuck.interpreter(">*>*;;;.!.,+-++--!!-!!!-", "00101100"));
        assertEquals("Your interpreter should ignore all tabs, newlines and spaces", "11010011", Smallfuck.interpreter("    *  >\n    *           >\n    \n*>*lskdfjsdklfj>*;;+--+--+++--+-+-  lskjfiom,x  \n>*sdfsdf>sdfsfsdfsdfwervbnbvn*,.,.,,.,.  >\n\n\n*", "00101100"));
        assertEquals("11111111", Smallfuck.interpreter("*,,...,..,..++-->++++-*>--+>*>+++->>..,+-,*>*", "00101100"));
        assertEquals("Your interpreter should not recognise any of \"n\", \"e\", \"s\", \"w\" (all valid Paintfuck commands) as valid commands", "00000000", Smallfuck.interpreter(">>nssewww>>wwess>*<nnn*<<ee*", "00101100"));
    }

    @Test
    public void testPointerGoingOutOfBounds() {
        // "should return the final state of the tape immediately if the pointer ever goes out of bounds"
        assertEquals("Your interpreter should return the final state of the tape immediately when the pointer moves too far to the right", "1001101000000111", Smallfuck.interpreter("*>>>*>*>>*>>>>>>>*>*>*>*>>>**>>**", "0000000000000000"));
        assertEquals("Your interpreter should immediately return the final state of the tape at the first instance where the pointer goes out of bounds to the left even if it resumes to a valid position later in the program", "0000000000000000", Smallfuck.interpreter("<<<*>*>*>*>*>>>*>>>>>*>*", "0000000000000000"));
        assertEquals("00011011110111111111111111111111", Smallfuck.interpreter("*>*>*>>>*>>>>>*<<<<<<<<<<<<<<<<<<<<<*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*>*>*", "11111111111111111111111111111111"));
        assertEquals("Your interpreter should not follow through any command after the pointer goes out of bounds for the first time", "1110", Smallfuck.interpreter(">>*>*>*<<*<*<<*>*", "1101"));
    }

    @Test
    public void testSimpleAndNestedLoops() {
        // "should work for some simple and nested loops"
        assertEquals("Your interpreter should evaluate a simple non-nested loop properly", new String(new char[256]).replace('\0', '1'), Smallfuck.interpreter("*[>*]", new String(new char[256]).replace('\0', '0')) );
        assertEquals("Your interpreter should jump to the matching \"]\" when it encounters a \"[\" and the bit under the pointer is 0", new String(new char[256]).replace('\0', '0'), Smallfuck.interpreter("[>*]", (new String(new char[256])).replace('\0', '0')));
        assertEquals("Your interpreter should jump to the matching \"]\" when it encounters a \"[\" and the bit under the pointer is 0", "11001100001" + (new String(new char[245])).replace('\0', '0'), Smallfuck.interpreter("*>*>>>*>*>>>>>*>[>*]", (new String(new char[256])).replace('\0', '0')));
        assertEquals("Your interpreter should jump back to the matching \"[\" when it encounters a \"]\" and the bit under the pointer is nonzero", "11001100001" + (new String(new char[245])).replace('\0', '1'), Smallfuck.interpreter("*>*>>>*>*>>>>>*[>*]", (new String(new char[256])).replace('\0', '0')));
        assertEquals("Your interpreter should also work properly with nested loops", "1" + new String(new char[255]).replace('\0', '0'), Smallfuck.interpreter("*[>[*]]", new String(new char[256]).replace('\0', '0')) );
        assertEquals("Your interpreter should also work properly with nested loops", "0" + new String(new char[255]).replace('\0', '1'), Smallfuck.interpreter("*[>[*]]", new String(new char[256]).replace('\0', '1')) );
        assertEquals("Your interpreter should also work properly with nested loops", "000", Smallfuck.interpreter("[[]*>*>*>]", "000") );
        assertEquals("Your interpreter should also work properly with nested loops", "100", Smallfuck.interpreter("*>[[]*>]<*", "100") );
        assertEquals("Your interpreter should also work properly with nested loops", "01100", Smallfuck.interpreter("[*>[>*>]>]", "11001") );
        assertEquals("Your interpreter should also work properly with nested loops", "10101", Smallfuck.interpreter("[>[*>*>*>]>]", "10110") );
    }

//    @Test
//    public void testRandomlyGeneratedPrograms() {
//        for (int i = 0; i < 100; i++) {
//            String code = (new String(new char[500])).replace('\0', '>') + randomProgram();
//            String tape = randomTape();
//            String expected = GgmoySolution.interpreter(code, tape);
//            String actual = Smallfuck.interpreter(code, tape);
//            assertEquals(expected, actual);
//        }
//    }
//
//    private String randomProgram() {
//        char cmds[] = "<*>".toCharArray();
//        char code[] = new char[100 + (int)(901 * Math.random())];
//        for (int i = 0; i < code.length; i++) {
//            code[i] = cmds[(int)(cmds.length * Math.random())];
//        }
//        return new String(code);
//    }
//
//    private String randomTape() {
//        char tape[] = new char[(int)1e3];
//        for (int i = 0; i < tape.length; i++) {
//            tape[i] = (char)(48 + (int)(2 * Math.random()));
//        }
//        return new String(tape);
//    }
}