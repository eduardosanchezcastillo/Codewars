package eugene.codewars.mineSweeper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MineSweeperTests {
    /* Data structure for one test:
     *      0 -> name or comment of the test
     *      1 -> original map
     *      2 -> map sent to the user
     *      3 -> expected solution
     */

    @Parameter(value = 0)
    public String testName;

    @Parameter(value = 1)
    public String originalMap;

    @Parameter(value = 2)
    public String inputMap;

    @Parameter(value = 3)
    public String expectedResult;

    @Parameterized.Parameters
    public static Collection getData() {
        return Arrays.asList(new String[][]{
                {"Simple map 1",
                        "1 x 1 1 x 1\n2 2 2 1 2 2\n2 x 2 0 1 x\n2 x 2 1 2 2\n1 1 1 1 x 1\n0 0 0 1 1 1",
                        "? ? ? ? ? ?\n? ? ? ? ? ?\n? ? ? 0 ? ?\n? ? ? ? ? ?\n? ? ? ? ? ?\n0 0 0 ? ? ?",
                        "1 x 1 1 x 1\n2 2 2 1 2 2\n2 x 2 0 1 x\n2 x 2 1 2 2\n1 1 1 1 x 1\n0 0 0 1 1 1"},

                {"Simple map 2",
                        "0 2 x\n0 2 x",
                        "0 ? ?\n0 ? ?",
                        "0 2 x\n0 2 x"},

                {"Simple unsolvable map",
                        "0 1 x\n0 1 1",
                        "0 ? ?\n0 ? ?",
                        "?"},

                {"Simple map 3",
                        "1 x x 1 0 0 0\n2 3 3 1 0 1 1\n1 x 1 0 0 1 x\n1 1 1 0 0 1 1\n0 1 1 1 0 0 0\n0 1 x 1 0 0 0\n0 1 1 1 0 1 1\n0 0 0 0 0 1 x\n0 0 0 0 0 1 1",
                        "? ? ? ? 0 0 0\n? ? ? ? 0 ? ?\n? ? ? 0 0 ? ?\n? ? ? 0 0 ? ?\n0 ? ? ? 0 0 0\n0 ? ? ? 0 0 0\n0 ? ? ? 0 ? ?\n0 0 0 0 0 ? ?\n0 0 0 0 0 ? ?",
                        "1 x x 1 0 0 0\n2 3 3 1 0 1 1\n1 x 1 0 0 1 x\n1 1 1 0 0 1 1\n0 1 1 1 0 0 0\n0 1 x 1 0 0 0\n0 1 1 1 0 1 1\n0 0 0 0 0 1 x\n0 0 0 0 0 1 1"},

                {"Various unsolvable map - 1",
                        "1 1 0 1 1 1 0 0 1 1 1 0 0 0 0 1 1 1 0\nx 1 0 1 x 1 0 0 2 x 2 0 0 0 0 1 x 2 1\n1 1 0 2 3 3 1 1 3 x 2 0 0 0 0 1 2 x 1\n0 1 1 2 x x 1 2 x 3 1 0 0 0 0 0 1 1 1\n0 1 x 2 2 2 1 3 x 3 0 0 0 0 0 0 0 0 0\n0 1 1 1 0 0 0 2 x 2 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 1 1 1 1 2 2 1 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 1 x x 1 0 0 0 0 0\n0 0 1 1 1 0 1 1 1 0 1 2 2 1 0 0 0 0 0\n0 0 1 x 2 1 3 x 2 0 0 0 0 0 0 1 1 1 0\n0 0 1 1 2 x 3 x 3 1 1 0 0 0 0 1 x 1 0\n0 0 0 0 1 2 3 2 2 x 1 0 0 0 0 1 1 1 0\n0 0 0 0 0 1 x 1 1 1 1 0 0 0 0 0 1 1 1\n0 0 1 1 2 2 2 1 0 0 0 0 0 0 0 0 1 x 1\n0 0 1 x 2 x 2 1 1 0 0 0 0 0 0 0 1 1 1\n0 0 1 1 2 1 3 x 3 1 0 0 0 0 0 0 0 1 1\n0 0 0 0 0 0 2 x x 1 0 0 0 1 1 1 0 1 x\n0 0 0 1 1 1 1 2 2 1 0 0 0 1 x 1 1 2 2\n0 0 0 1 x 3 2 1 0 0 0 1 1 2 1 1 1 x 2\n0 0 0 1 2 x x 1 0 0 0 1 x 1 0 1 2 3 x\n0 0 0 0 1 2 2 1 1 1 1 1 1 1 0 1 x 3 2\n0 0 0 0 1 1 1 1 2 x 1 1 1 1 0 2 3 x 2\n0 0 0 0 1 x 1 1 x 2 1 1 x 1 0 1 x 3 x",
                        "? ? 0 ? ? ? 0 0 ? ? ? 0 0 0 0 ? ? ? 0\n? ? 0 ? ? ? 0 0 ? ? ? 0 0 0 0 ? ? ? ?\n? ? 0 ? ? ? ? ? ? ? ? 0 0 0 0 ? ? ? ?\n0 ? ? ? ? ? ? ? ? ? ? 0 0 0 0 0 ? ? ?\n0 ? ? ? ? ? ? ? ? ? 0 0 0 0 0 0 0 0 0\n0 ? ? ? 0 0 0 ? ? ? 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 ? ? ? ? ? ? ? 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 ? ? ? ? 0 0 0 0 0\n0 0 ? ? ? 0 ? ? ? 0 ? ? ? ? 0 0 0 0 0\n0 0 ? ? ? ? ? ? ? 0 0 0 0 0 0 ? ? ? 0\n0 0 ? ? ? ? ? ? ? ? ? 0 0 0 0 ? ? ? 0\n0 0 0 0 ? ? ? ? ? ? ? 0 0 0 0 ? ? ? 0\n0 0 0 0 0 ? ? ? ? ? ? 0 0 0 0 0 ? ? ?\n0 0 ? ? ? ? ? ? 0 0 0 0 0 0 0 0 ? ? ?\n0 0 ? ? ? ? ? ? ? 0 0 0 0 0 0 0 ? ? ?\n0 0 ? ? ? ? ? ? ? ? 0 0 0 0 0 0 0 ? ?\n0 0 0 0 0 0 ? ? ? ? 0 0 0 ? ? ? 0 ? ?\n0 0 0 ? ? ? ? ? ? ? 0 0 0 ? ? ? ? ? ?\n0 0 0 ? ? ? ? ? 0 0 0 ? ? ? ? ? ? ? ?\n0 0 0 ? ? ? ? ? 0 0 0 ? ? ? 0 ? ? ? ?\n0 0 0 0 ? ? ? ? ? ? ? ? ? ? 0 ? ? ? ?\n0 0 0 0 ? ? ? ? ? ? ? ? ? ? 0 ? ? ? ?\n0 0 0 0 ? ? ? ? ? ? ? ? ? ? 0 ? ? ? ?",
                        "?"},

                {"Various unsolvable map - 2",
                        "0 0 0 0 0 0 0 0 1 x x 2 1 0 1 x 1 0 1 2 x\n0 0 0 0 0 0 0 0 1 2 3 x 1 0 2 2 2 1 2 x 2\n0 0 0 0 0 0 0 0 0 0 2 2 2 0 1 x 1 1 x 2 1\n0 0 0 0 0 1 1 1 0 0 1 x 1 0 1 2 2 2 1 1 0\n1 1 0 0 0 1 x 1 0 1 2 2 1 0 0 1 x 1 1 1 1\nx 1 0 0 0 1 1 1 0 1 x 1 0 0 0 1 1 1 1 x 1\n2 2 1 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 1 1 1\n1 x 1 0 0 0 0 0 0 0 1 2 2 1 0 0 1 1 1 0 0\n1 1 1 0 0 0 0 0 0 0 1 x x 1 0 0 1 x 1 0 0",
                        "0 0 0 0 0 0 0 0 ? ? ? ? ? 0 ? ? ? 0 ? ? ?\n0 0 0 0 0 0 0 0 ? ? ? ? ? 0 ? ? ? ? ? ? ?\n0 0 0 0 0 0 0 0 0 0 ? ? ? 0 ? ? ? ? ? ? ?\n0 0 0 0 0 ? ? ? 0 0 ? ? ? 0 ? ? ? ? ? ? 0\n? ? 0 0 0 ? ? ? 0 ? ? ? ? 0 0 ? ? ? ? ? ?\n? ? 0 0 0 ? ? ? 0 ? ? ? 0 0 0 ? ? ? ? ? ?\n? ? ? 0 0 0 0 0 0 ? ? ? 0 0 0 0 0 0 ? ? ?\n? ? ? 0 0 0 0 0 0 0 ? ? ? ? 0 0 ? ? ? 0 0\n? ? ? 0 0 0 0 0 0 0 ? ? ? ? 0 0 ? ? ? 0 0",
                        "?"},

                {"Map with guesses - 1",
                        "1 2 x 2\nx 2 2 x\n1 1 1 1\n0 0 0 0\n0 0 0 0\n1 1 0 0\nx 1 0 0\n1 1 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n1 1 1 0\n1 x 1 0\n2 2 2 0\n1 x 1 0\n2 2 2 0\n1 x 1 0\n1 1 1 0\n1 1 1 0\n1 x 1 0\n1 1 1 0\n1 2 1 1\nx 2 x 1\n1 2 1 1",
                        "? ? ? ?\n? ? ? ?\n? ? ? ?\n0 0 0 0\n0 0 0 0\n? ? 0 0\n? ? 0 0\n? ? 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? 0\n? ? ? ?\n? ? ? ?\n? ? ? ?",
                        "1 2 x 2\nx 2 2 x\n1 1 1 1\n0 0 0 0\n0 0 0 0\n1 1 0 0\nx 1 0 0\n1 1 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n1 1 1 0\n1 x 1 0\n2 2 2 0\n1 x 1 0\n2 2 2 0\n1 x 1 0\n1 1 1 0\n1 1 1 0\n1 x 1 0\n1 1 1 0\n1 2 1 1\nx 2 x 1\n1 2 1 1"},

                {"Map from serious tests - 1",
                        "0 0 1 x\n0 0 1 1\n0 0 1 1\n0 0 1 x\n0 0 1 1\n0 0 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n1 2 2 1\n1 x x 1\n1 2 2 1\n0 0 0 0\n1 1 1 0\n1 x 1 0\n3 4 3 1\nx x x 1\n2 3 2 1\n0 0 1 1\n0 0 1 x\n1 1 2 1\n1 x 1 0\n1 1 1 0\n0 0 0 0",
                        "0 0 ? ?\n0 0 ? ?\n0 0 ? ?\n0 0 ? ?\n0 0 ? ?\n0 0 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n? ? ? ?\n? ? ? ?\n? ? ? ?\n0 0 0 0\n? ? ? 0\n? ? ? 0\n? ? ? ?\n? ? ? ?\n? ? ? ?\n0 0 ? ?\n0 0 ? ?\n? ? ? ?\n? ? ? 0\n? ? ? 0\n0 0 0 0",
                        "0 0 1 x\n0 0 1 1\n0 0 1 1\n0 0 1 x\n0 0 1 1\n0 0 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0\n1 2 2 1\n1 x x 1\n1 2 2 1\n0 0 0 0\n1 1 1 0\n1 x 1 0\n3 4 3 1\nx x x 1\n2 3 2 1\n0 0 1 1\n0 0 1 x\n1 1 2 1\n1 x 1 0\n1 1 1 0\n0 0 0 0"},

                {"Map from serious tests - 2",
                        "0 0 0 1 x 1 1 x 1 0 0 0 0 0 1 1 1 0 0 1 x 3 x 3 1 2 1\n1 1 0 1 1 1 1 1 1 0 0 0 0 0 1 x 1 1 1 2 1 3 x 3 x 2 x\nx 2 1 1 0 0 0 0 0 0 1 1 1 0 1 1 1 1 x 1 0 2 2 3 1 3 2\n1 2 x 1 0 0 0 0 0 0 1 x 1 0 0 0 0 1 1 1 0 1 x 2 1 2 x\n0 1 1 1 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 1 2 3 x 2 1\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 x 2 1 0",
                        "0 0 0 ? ? ? ? ? ? 0 0 0 0 0 ? ? ? 0 0 ? ? ? ? ? ? ? ?\n? ? 0 ? ? ? ? ? ? 0 0 0 0 0 ? ? ? ? ? ? ? ? ? ? ? ? ?\n? ? ? ? 0 0 0 0 0 0 ? ? ? 0 ? ? ? ? ? ? 0 ? ? ? ? ? ?\n? ? ? ? 0 0 0 0 0 0 ? ? ? 0 0 0 0 ? ? ? 0 ? ? ? ? ? ?\n0 ? ? ? 0 0 0 0 0 0 ? ? ? 0 0 0 0 0 0 0 0 ? ? ? ? ? ?\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 ? ? ? ? 0",
                        "0 0 0 1 x 1 1 x 1 0 0 0 0 0 1 1 1 0 0 1 x 3 x 3 1 2 1\n1 1 0 1 1 1 1 1 1 0 0 0 0 0 1 x 1 1 1 2 1 3 x 3 x 2 x\nx 2 1 1 0 0 0 0 0 0 1 1 1 0 1 1 1 1 x 1 0 2 2 3 1 3 2\n1 2 x 1 0 0 0 0 0 0 1 x 1 0 0 0 0 1 1 1 0 1 x 2 1 2 x\n0 1 1 1 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 1 2 3 x 2 1\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 x 2 1 0"},

                {"Map from serious tests - 3",
                        "0 0 0 0\n0 0 1 1\n0 0 1 x\n0 0 1 1\n0 0 0 0\n0 0 0 0\n0 0 0 0\n1 2 1 1\nx 3 x 2\n1 3 x 2\n0 1 1 1",
                        "0 0 0 0\n0 0 ? ?\n0 0 ? ?\n0 0 ? ?\n0 0 0 0\n0 0 0 0\n0 0 0 0\n? ? ? ?\n? ? ? ?\n? ? ? ?\n0 ? ? ?",
                        "0 0 0 0\n0 0 1 1\n0 0 1 x\n0 0 1 1\n0 0 0 0\n0 0 0 0\n0 0 0 0\n1 2 1 1\nx 3 x 2\n1 3 x 2\n0 1 1 1"},

                {"Map from serious tests - 4",
                        "0 0 0 0 0 0 0 1 1 1\n1 1 1 1 1 1 0 2 x 2\n1 x 2 2 x 1 0 2 x 2\n1 1 2 x 2 1 0 1 1 1\n0 0 2 2 2 1 1 1 0 0\n0 0 1 x 1 1 x 2 1 1\n0 0 1 1 2 2 2 3 x 2\n0 0 0 0 1 x 1 2 x 2\n0 0 0 0 1 1 1 1 1 1\n0 0 0 1 2 2 1 0 0 0\n0 0 0 1 x x 1 0 0 0\n0 0 0 1 2 2 1 0 0 0\n0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0\n1 1 0 1 1 1 0 0 0 0\nx 1 0 1 x 1 0 0 0 0\n2 3 1 3 2 2 1 1 1 0\nx 2 x 2 x 1 1 x 2 1\n1 2 1 2 1 1 1 2 x 1\n0 0 1 1 1 0 0 1 1 1\n0 0 1 x 1 1 1 2 2 2\n0 0 1 1 1 1 x 2 x x\n0 0 0 0 0 1 1 2 2 2",
                        "0 0 0 0 0 0 0 ? ? ?\n? ? ? ? ? ? 0 ? ? ?\n? ? ? ? ? ? 0 ? ? ?\n? ? ? ? ? ? 0 ? ? ?\n0 0 ? ? ? ? ? ? 0 0\n0 0 ? ? ? ? ? ? ? ?\n0 0 ? ? ? ? ? ? ? ?\n0 0 0 0 ? ? ? ? ? ?\n0 0 0 0 ? ? ? ? ? ?\n0 0 0 ? ? ? ? 0 0 0\n0 0 0 ? ? ? ? 0 0 0\n0 0 0 ? ? ? ? 0 0 0\n0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0\n? ? 0 ? ? ? 0 0 0 0\n? ? 0 ? ? ? 0 0 0 0\n? ? ? ? ? ? ? ? ? 0\n? ? ? ? ? ? ? ? ? ?\n? ? ? ? ? ? ? ? ? ?\n0 0 ? ? ? 0 0 ? ? ?\n0 0 ? ? ? ? ? ? ? ?\n0 0 ? ? ? ? ? ? ? ?\n0 0 0 0 0 ? ? ? ? ?",
                        "0 0 0 0 0 0 0 1 1 1\n1 1 1 1 1 1 0 2 x 2\n1 x 2 2 x 1 0 2 x 2\n1 1 2 x 2 1 0 1 1 1\n0 0 2 2 2 1 1 1 0 0\n0 0 1 x 1 1 x 2 1 1\n0 0 1 1 2 2 2 3 x 2\n0 0 0 0 1 x 1 2 x 2\n0 0 0 0 1 1 1 1 1 1\n0 0 0 1 2 2 1 0 0 0\n0 0 0 1 x x 1 0 0 0\n0 0 0 1 2 2 1 0 0 0\n0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0\n1 1 0 1 1 1 0 0 0 0\nx 1 0 1 x 1 0 0 0 0\n2 3 1 3 2 2 1 1 1 0\nx 2 x 2 x 1 1 x 2 1\n1 2 1 2 1 1 1 2 x 1\n0 0 1 1 1 0 0 1 1 1\n0 0 1 x 1 1 1 2 2 2\n0 0 1 1 1 1 x 2 x x\n0 0 0 0 0 1 1 2 2 2"},

                {"Map from serious tests - 5",
                        "1 x 1 0 0 2 x 2 1 x 1 0 0 1 x x 1\n1 1 1 0 0 2 x 3 2 1 1 0 0 1 3 4 3\n0 0 0 0 0 1 2 x 1 0 0 0 0 0 1 x x\n0 0 0 0 0 0 1 1 1 0 0 0 0 0 1 2 2\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1\n1 1 1 0 0 0 0 0 0 0 0 0 0 1 2 x 1\n1 x 1 0 0 0 0 0 0 0 0 0 0 1 x 2 1",
                        "? ? ? 0 0 ? ? ? ? ? ? 0 0 ? ? ? ?\n? ? ? 0 0 ? ? ? ? ? ? 0 0 ? ? ? ?\n0 0 0 0 0 ? ? ? ? 0 0 0 0 0 ? ? ?\n0 0 0 0 0 0 ? ? ? 0 0 0 0 0 ? ? ?\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 ? ? ?\n? ? ? 0 0 0 0 0 0 0 0 0 0 ? ? ? ?\n? ? ? 0 0 0 0 0 0 0 0 0 0 ? ? ? ?",
                        "1 x 1 0 0 2 x 2 1 x 1 0 0 1 x x 1\n1 1 1 0 0 2 x 3 2 1 1 0 0 1 3 4 3\n0 0 0 0 0 1 2 x 1 0 0 0 0 0 1 x x\n0 0 0 0 0 0 1 1 1 0 0 0 0 0 1 2 2\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 1\n1 1 1 0 0 0 0 0 0 0 0 0 0 1 2 x 1\n1 x 1 0 0 0 0 0 0 0 0 0 0 1 x 2 1"},

                {"Map from serious tests - 6",
                        "3 x 3 2 2\nx x 3 x x\n2 2 2 2 2\n0 0 0 0 0\n0 0 0 1 1\n0 0 0 1 x\n0 0 0 1 1\n0 1 1 1 0\n0 1 x 1 0\n0 1 1 1 0\n0 0 0 0 0\n0 0 0 0 0\n0 0 0 0 0\n0 1 1 1 0\n0 1 x 1 0\n1 2 2 1 0\n1 x 1 0 0\n1 1 1 0 0\n0 0 0 0 0\n1 1 0 0 0\nx 2 0 0 0\nx 2 0 0 0\n1 1 0 0 0\n0 0 0 0 0\n1 1 0 1 1\nx 1 0 1 x\n1 1 0 1 1",
                        "? ? ? ? ?\n? ? ? ? ?\n? ? ? ? ?\n0 0 0 0 0\n0 0 0 ? ?\n0 0 0 ? ?\n0 0 0 ? ?\n0 ? ? ? 0\n0 ? ? ? 0\n0 ? ? ? 0\n0 0 0 0 0\n0 0 0 0 0\n0 0 0 0 0\n0 ? ? ? 0\n0 ? ? ? 0\n? ? ? ? 0\n? ? ? 0 0\n? ? ? 0 0\n0 0 0 0 0\n? ? 0 0 0\n? ? 0 0 0\n? ? 0 0 0\n? ? 0 0 0\n0 0 0 0 0\n? ? 0 ? ?\n? ? 0 ? ?\n? ? 0 ? ?",
                        "3 x 3 2 2\nx x 3 x x\n2 2 2 2 2\n0 0 0 0 0\n0 0 0 1 1\n0 0 0 1 x\n0 0 0 1 1\n0 1 1 1 0\n0 1 x 1 0\n0 1 1 1 0\n0 0 0 0 0\n0 0 0 0 0\n0 0 0 0 0\n0 1 1 1 0\n0 1 x 1 0\n1 2 2 1 0\n1 x 1 0 0\n1 1 1 0 0\n0 0 0 0 0\n1 1 0 0 0\nx 2 0 0 0\nx 2 0 0 0\n1 1 0 0 0\n0 0 0 0 0\n1 1 0 1 1\nx 1 0 1 x\n1 1 0 1 1"},

                {"Map from serious tests - 7",
                        "0 0 0 0\n0 0 0 0\n1 1 0 0\nx 2 1 1\nx 3 1 x\nx 2 1 1\n1 1 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0",
                        "0 0 0 0\n0 0 0 0\n? ? 0 0\n? ? ? ?\n? ? ? ?\n? ? ? ?\n? ? 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0",
                        "0 0 0 0\n0 0 0 0\n1 1 0 0\nx 2 1 1\nx 3 1 x\nx 2 1 1\n1 1 0 0\n0 0 0 0\n0 0 0 0\n0 0 0 0"},
        });
    }

    private void makeAssertion_AndDisplay(String message, String expected, String actual) {
        if (!expected.equals(actual)) {
            if (message.equals("")) message = "Failed test!!";
            System.out.println(" \n" + message + "\n\nExpected:\n" + expected + "\n\nBut was:\n" + actual);
        }
        assertEquals(message, expected, actual);
    }

    @Test
    public void test() {
        Game.newGame(originalMap);
        makeAssertion_AndDisplay(testName, expectedResult, new MineSweeper(inputMap, Game.getMinesN()).solve());
    }
}
