package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoolfuckTest {
    @Test
    public void testEmpty() {
        assertEquals(Boolfuck.interpret("", ""), "");
    }

    @Test
    public void testLoop1() {
        assertEquals(Boolfuck.interpret("+>+>+>+<<<[>]", ""), "");
    }

    @Test
    public void testLoop2() {
        assertEquals(Boolfuck.interpret("+>+>+>+<<<[>]", ""), "");
    }

    @Test
    public void testInput() {
        assertEquals(Boolfuck.interpret("", "abcdefg"), "");
    }

    @Test
    public void testSingleCommands() {
        assertEquals(Boolfuck.interpret("<", ""), "");
        assertEquals(Boolfuck.interpret(">", ""), "");
        assertEquals(Boolfuck.interpret("+", ""), "");
        assertEquals(Boolfuck.interpret(".", ""), "");
        assertEquals(Boolfuck.interpret(";", ""), "\u0000");
    }

    @Test
    public void testHelloWorld() {
        assertEquals(Boolfuck.interpret(";;;+;+;;+;+;+;+;+;+;;+;;+;;;+;;+;+;;+;;;+;;+;+;;+;+;;;;+;+;;+;;;+;;+;+;+;;;;;;;+;+;;+;;;+;+;;;+;+;;;;+;+;;+;;+;+;;+;;;+;;;+;;+;+;;+;;;+;+;;+;;+;+;+;;;;+;+;;;+;+;+;", ""), "Hello, world!\n");
    }

    @Test
    public void testBasic1() {
        assertEquals(Boolfuck.interpret(">,>,>,>,>,>,>,>,<<<<<<<[>]+<[+<]>>>>>>>>>[+]+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+]<<<<<<<<;>;>;>;>;>;>;>;<<<<<<<,>,>,>,>,>,>,>,<<<<<<<[>]+<[+<]>>>>>>>>>[+]+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]", "Codewars\u00ff"), "Codewars");
    }

    @Test
    public void testBasic2() {
        assertEquals(Boolfuck.interpret(">,>,>,>,>,>,>,>,>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>;>;>;>;>;>;>;>;>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+]+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]>,>,>,>,>,>,>,>,>+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]", "Codewars"), "Codewars");
    }

    @Test
    public void testBasic3() {
        assertEquals(Boolfuck.interpret(">,>,>,>,>,>,>,>,>>,>,>,>,>,>,>,>,<<<<<<<<+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>>>>>>>>>>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+]>[>]+<[+<]>>>>>>>>>[+]>[>]+<[+<]>>>>>>>>>[+]<<<<<<<<<<<<<<<<<<+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]>>>>>>>>>>>>>>>>>>>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+<<<<<<<<[>]+<[+<]>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>[+]<<<<<<<<<<<<<<<<<<<<<<<<<<[>]+<[+<]>>>>>>>>>[+]>>>>>>>>>>>>>>>>>>+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]<<<<<<<<<<<<<<<<<<+<<<<<<<<+[>+]<[<]>>>>>>>>>[+]+<<<<<<<<+[>+]<[<]>>>>>>>>>]<[+<]>>>>>>>>>>>>>>>>>>>;>;>;>;>;>;>;>;<<<<<<<<", "\u0008\u0009"), "\u0048");
    }
}
