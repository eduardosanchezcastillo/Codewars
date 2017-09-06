package eugene.codewars;

import org.junit.Assert;
import org.junit.Test;

public class DirReductionTest {
    @Test
    public void testSimpleDirReduc() throws Exception {
        Assert.assertEquals("\"NORTH\", \"SOUTH\", \"SOUTH\", \"EAST\", \"WEST\", \"NORTH\", \"WEST\"",
                new String[]{"WEST"},
                DirReduction.dirReduc(new String[]{"NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST"}));

        Assert.assertEquals("\"NORTH\", \"WEST\", \"SOUTH\", \"EAST\"",
                new String[]{"NORTH", "WEST", "SOUTH", "EAST"},
                DirReduction.dirReduc(new String[]{"NORTH", "WEST", "SOUTH", "EAST"}));
    }
}
