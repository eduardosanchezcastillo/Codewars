package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.*;

public class PaintfuckTest {
    @Test
    public void examples() {
        assertEquals("Your interpreter should initialize all cells in the datagrid to 0", "000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000", Paintfuck.interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 0, 6, 9));
        assertEquals("Your interpreter should adhere to the number of iterations specified", "111100\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000", Paintfuck.interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 7, 6, 9));
        assertEquals("Your interpreter should traverse the 2D datagrid correctly", "111100\r\n000010\r\n000001\r\n000010\r\n000100\r\n000000\r\n000000\r\n000000\r\n000000", Paintfuck.interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 19, 6, 9));
        assertEquals("Your interpreter should traverse the 2D datagrid correctly for all of the \"n\", \"e\", \"s\" and \"w\" commands", "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000", Paintfuck.interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 42, 6, 9));
        assertEquals("Your interpreter should terminate normally and return a representation of the final state of the 2D datagrid when all commands have been considered from left to right even if the number of iterations specified have not been fully performed", "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000", Paintfuck.interpreter("*e*e*e*es*es*ws*ws*w*w*w*n*n*n*ssss*s*s*s*", 100, 6, 9));
    }

    @Test
    public void ignoreNonCommandCharacters() {
        assertEquals("Your interpreter should simply ignore all letters that are not one of \"nesw\" and all punctuation that are not asterisks", "111100\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000", Paintfuck.interpreter("o*e*eq*reqrqp*ppooqqeaqqsr*yqaooqqqfqarppppfffpppppygesfffffffffu*wrs*agwpffffst*w*uqrw*qyaprrrrrw*nuiiiid???ii*n*ynyy??ayd*r:rq????qq::tqaq:y???ss:rqsr?s*qs:q*?qs*tr??qst?q*r", 7, 6, 9));
        assertEquals("Your interpreter should ignore all newlines, tabs and spaces", "111100\r\n000010\r\n000001\r\n000010\r\n000100\r\n000000\r\n000000\r\n000000\r\n000000", Paintfuck.interpreter("*e*e*e\n\t\n\t\n\t\n\t*es  *es  *ws*w      s*w*w*w*    n*n*      n*sss               s*s*               s    *s*", 19, 6, 9));
        assertEquals("Your interpreter should not recognise any of \",\", \".\", \"<\", \">\", \"+\", \"-\" (valid Brainfuck commands), \";\" (valid Boolfuck command), \"^\" and \"v\" (valid Befunge commands) as valid Paintfuck commands", "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000", Paintfuck.interpreter("*e..*,;e+*e*e-s;<<<<<>>>*,,,e--+s*w+>>>>>>>><;;s*<><<>w.>><>>><<<<><<>><^^^^vvv^v^vv^vv^v^^><>><>.s--*w;;*w>><<>*+^^v^vvv^++w*-+-;;;;+---++-+n;..*n*n++--;;+++-;*ssss.*s*s*s.*", 42, 6, 9));
        assertEquals("Your interpreter should not treat uppercase \"NESW\" as valid commands", "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000", Paintfuck.interpreter("*e*eNNESNENSNESNWWEWKSDLFJMCVXNIOWE*e*es*IOWEORUWKVDSVOIRUSKVKLVes*wsIWUENNSLNDKLNSIRIOEDSKKLNSV*ws*w*w*wIOWEURNLSVM,NXVC,MSIWOEU*n*n*n*ssSSEEWWss*s*s*s*EEWWEEWEWSSSNNSWWE", 100, 6, 9));
    }

    @Test
    public void initializeGridsOfCorrectSize() {
        assertEquals("Your interpreter should work correctly for a grid of size 1x1", "0", Paintfuck.interpreter("", 0, 1, 1));
        assertEquals("Your interpreter should work correctly for other square grids of side length > 1", "000000\r\n000000\r\n000000\r\n000000\r\n000000\r\n000000", Paintfuck.interpreter("", 0, 6, 6));
        assertEquals("Your interpreter should work correctly for other square grids of side length > 1", "0000000000\r\n0000000000\r\n0000000000\r\n0000000000\r\n0000000000\r\n0000000000\r\n0000000000\r\n0000000000\r\n0000000000\r\n0000000000", Paintfuck.interpreter("", 0, 10, 10));
        assertEquals("Your interpreter should properly initialize grids of height 1", "000", Paintfuck.interpreter("", 0, 3, 1));
        assertEquals("Your interpreter should properly initialize grids of height 1", "000000000000000", Paintfuck.interpreter("", 0, 15, 1));
        assertEquals("Your interpreter should properly initialize grids of width 1", "0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0", Paintfuck.interpreter("", 0, 1, 8));
        assertEquals("Your interpreter should properly initialize grids of width 1", "0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0", Paintfuck.interpreter("", 0, 1, 11));
        assertEquals("Your interpreter should properly initialize grids of any valid dimensions", "000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000\r\n000000000000000", Paintfuck.interpreter("", 0, 15, 20));
    }

    @Test
    public void toroidalBehaviour() {
        assertEquals("Your data grid should exhibit toroidal (wrapping) behaviour", "00011000\r\n00011000\r\n00011000\r\n11111111\r\n11111111\r\n00011000\r\n00011000\r\n00011000\r\n00011000\r\n00011000", Paintfuck.interpreter("eee*s*s*s*w*w*w*w*w*w*w*n*n*n*n*n*n*n*n*n*e*e*e*e*e*e*e*s*s*s*s*s*", 1000, 8, 10));
        assertEquals("00011000\r\n00011000\r\n00011000\r\n11111111\r\n00000000\r\n00001000\r\n00001000\r\n00001000\r\n00001000\r\n00001000", Paintfuck.interpreter("eee*s*s*s*w*w*w*w*w*w*w*n*n*n*n*n*n*n*n*n*e*e*e*e*e*e*e*s*s*s*s*s*", 40, 8, 10));
        assertEquals("00011000\r\n00011000\r\n00011000\r\n11111111\r\n11111111\r\n00011000\r\n00011000\r\n00011000\r\n00011000\r\n00011000", Paintfuck.interpreter("eee*s*s*s*w*w*w*w*w*w*w*n*n*n*n*n*n*n*n*n*e*e*e*e*e*e*e*s*s*s*s*s*", 66, 8, 10));
        assertEquals("Your data grid should exhibit toroidal (wrapping) behaviour, and not just for one example", "111100\r\n100010\r\n100001\r\n100010\r\n111100\r\n100000\r\n100000\r\n100000\r\n100000", Paintfuck.interpreter("sssss*s*s*s*s*www*w*w*seee*ee*s*w*sw*sw*eee*n*es*e*", 1000, 6, 9));
    }

    @Test
    public void simpleAndNestedLoops() {
        assertEquals("Your interpreter should not enter the loop on the first iteration of this program", "10000\r\n00000\r\n00000\r\n00000\r\n00000\r\n00000", Paintfuck.interpreter("*[es*]", 1, 5, 6));
        assertEquals("Your interpreter should just have executed the last command of the loop and about to approach the matching \"]\"", "10000\r\n01000\r\n00000\r\n00000\r\n00000\r\n00000", Paintfuck.interpreter("*[es*]", 5, 5, 6));
        assertEquals("Your interpreter should jump to the command straight *after* the matching \"[\" on the iteration where it hits the \"]\" and *not* the matching \"[\" itself", "10000\r\n01000\r\n00100\r\n00000\r\n00000\r\n00000", Paintfuck.interpreter("*[es*]", 9, 5, 6));
        assertEquals("Your interpreter should should exhibit toroidal behaviour ;)", "11000\r\n01100\r\n00110\r\n00011\r\n00001\r\n10000", Paintfuck.interpreter("*[es*]", 37, 5, 6));
        assertEquals("Your interpreter should exit the loop at the correct conditions", "01111\r\n11111\r\n11111\r\n11111\r\n11111\r\n11111", Paintfuck.interpreter("*[es*]", 1000, 5, 6));
        assertEquals("Your interpreter should exit the loop at the correct conditions", "11111\r\n11111\r\n11111\r\n11111\r\n11111\r\n11111", Paintfuck.interpreter("*[es*]*", 3000, 5, 6));
        assertEquals("Your interpreter should also work with nested loops", "10000\r\n10000\r\n00000\r\n00000\r\n00000", Paintfuck.interpreter("*[s[e]*]", 5, 5, 5));
        assertEquals("Your interpreter should also work with nested loops", "10000\r\n10000\r\n10000\r\n00000\r\n00000", Paintfuck.interpreter("*[s[e]*]", 9, 5, 5));
        assertEquals("Your interpreter should also work with nested loops", "11000\r\n10000\r\n10000\r\n10000\r\n10000", Paintfuck.interpreter("*[s[e]*]", 23, 5, 5));
        assertEquals("Your interpreter should also work with nested loops", "11000\r\n11000\r\n11000\r\n11000\r\n11000", Paintfuck.interpreter("*[s[e]*]", 39, 5, 5));
        assertEquals("Your interpreter should also work with nested loops", "11100\r\n11100\r\n11000\r\n11000\r\n11000", Paintfuck.interpreter("*[s[e]*]", 49, 5, 5));
    }

//    @Test
//    public void random() {
//        char commands[] = "nesw*".toCharArray();
//        int iterations = (int)(1001 * Math.random());
//        char randomPaintfuckProgram[] = new char[100 + (int)(901 * Math.random())];
//        for (int i = 0; i < randomPaintfuckProgram.length; i++) {
//            randomPaintfuckProgram[i] = commands[(int)(5 * Math.random())];
//        }
//        String code = new String(randomPaintfuckProgram);
//        assertEquals(GgmoySolution.interpreter(code, iterations, 10, 10), Paintfuck.interpreter(code, iterations, 10, 10));
//    }
}