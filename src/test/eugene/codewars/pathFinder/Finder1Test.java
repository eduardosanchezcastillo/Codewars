package eugene.codewars.pathFinder;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Finder1Test {
    @Test
    public void sample1() {
        assertTrue(Finder1.pathFinder(
                ".W.\n" +
                        ".W.\n" +
                        "..."));
    }

    @Test
    public void sample2() {
        assertFalse(Finder1.pathFinder(
                ".W.\n" +
                        ".W.\n" +
                        "W.."));
    }

    @Test
    public void sample3() {
        assertTrue(Finder1.pathFinder(
                "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        "......"));
    }

    @Test
    public void sample4() {
        assertFalse(Finder1.pathFinder(
                "......\n" +
                        "......\n" +
                        "......\n" +
                        "......\n" +
                        ".....W\n" +
                        "....W."));
    }

    @Test
    public void sample5() {
        assertFalse(Finder1.pathFinder(
                ".W.W.....W.WW..W..W.W.....W...W..\n" +
                        "...WW.......W..W....W..W.........\n" +
                        "W..W....W............W..WWW......\n" +
                        "..W...W.........W..W..W..WW.W.WW.\n" +
                        ".W.W.............WW.W.W....W.W...\n" +
                        "........W.......W.W.....WW..W....\n" +
                        "W.W...W.W......W.W....W.W....WWW.\n" +
                        ".W.W.W.....W...W.W.....W...W...W.\n" +
                        "..W...WW...W........W......WWWW.W\n" +
                        "W.W.............W......WWWWW...W.\n" +
                        ".....W.W....WWW.....W....WWW.W...\n" +
                        "..WW....W.W.....WWW..W.....W.....\n" +
                        "WW...W....W......W..W...W.W...WW.\n" +
                        "..W...W....WWW..W.........W.W..WW\n" +
                        ".WW........W.....WW....W......W..\n" +
                        "..W.W....W.....W...W.W.W...W.W..W\n" +
                        "W....W...W..W..WW.......WWW......\n" +
                        "...W..W.W..W....W...W...W..WW.W..\n" +
                        "....WWW.W.....W......W.....W.WW..\n" +
                        ".W..W.WW..W.WWW...WW.....W.......\n" +
                        "........W.......W..........W..W.W\n" +
                        "WWW..W..W.....WW.WW..........W...\n" +
                        "...W...W.W..W...W...W..WWW....WW.\n" +
                        ".W..W.W....W...WW...W.W.......WWW\n" +
                        "WWW.WW.W.W.......W..WW....W....W.\n" +
                        ".W..W....W..WW...WWWWWW...W..W...\n" +
                        ".....................W...W.......\n" +
                        "W.......WW.WWW...WW.W..W.....W...\n" +
                        "........W.W.W.W.W.....W......W...\n" +
                        "W.W...W.WW.W.W.W..WW.WWW..W....W.\n" +
                        ".W...W....W........W...WW.....W.W\n" +
                        ".W....WW..WWW...W.WW..W......W..W\n" +
                        "...WW.W..W..WW....W..WW......WW.."));
    }

    @Test
    public void sample6() {
        assertTrue(Finder1.pathFinder("."));
    }
}

