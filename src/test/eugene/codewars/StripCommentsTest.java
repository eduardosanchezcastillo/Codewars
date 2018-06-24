package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StripCommentsTest {

    @Test
    public void case1() throws Exception {
        assertEquals(
                "apples, pears\ngrapes\nbananas",
                StripComments.stripComments("apples, pears # and bananas\ngrapes\nbananas !apples", new String[]{"#", "!"})
        );
    }

    @Test
    public void case2() throws Exception {
        assertEquals(
                "a\nc\nd",
                StripComments.stripComments("a #b\nc\nd $e f g", new String[]{"#", "$"})
        );
    }

    @Test
    public void case3() throws Exception {
        assertEquals(
                "a\n b\nc",
                StripComments.stripComments("a \n b \nc ", new String[]{"#", "$"})
        );
    }

    @Test
    public void case4() throws Exception {
        assertEquals(
                "a\n\nc",
                StripComments.stripComments("a \n\nc ", new String[]{"#", "$"})
        );
    }
}
