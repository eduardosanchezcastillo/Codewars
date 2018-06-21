package eugene.codewars.pathFinder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Finder2Test {
    @Test
    public void sample1() {
        String maze = ".W.\n" +
                ".W.\n" +
                "...";
        assertEquals(maze, 4, Finder2.pathFinder(maze));
    }

    @Test
    public void sample2() {
        String maze = ".W.\n" +
                ".W.\n" +
                "W..";
        assertEquals(maze, -1, Finder2.pathFinder(maze));
    }

    @Test
    public void sample3() {
        String maze = "......\n" +
                "......\n" +
                "......\n" +
                "......\n" +
                "......\n" +
                "......";
        assertEquals(maze, 10, Finder2.pathFinder(maze));
    }

    @Test
    public void sample4() {
        String maze = "......\n" +
                "......\n" +
                "......\n" +
                "......\n" +
                ".....W\n" +
                "....W.";
        assertEquals(maze, -1, Finder2.pathFinder(maze));
    }

    @Test
    public void sample5() {
        String maze = ".....W\n" +
                "....W.\n" +
                "...WWW\n" +
                "......\n" +
                "..WWW.\n" +
                "......";
        assertEquals(maze, 10, Finder2.pathFinder(maze));
    }

    @Test
    public void sample6() {
        String maze = "...W..W.W..WW.......W.W........WW...W.....W.W...W.........W..W......W.W.W.W...W.W.WWW....WW...WW.W..\n" +
                "...W.W..WW.W................W..W..W...........W.......W.W...........W....W.W..W......WW..WW......W..\n" +
                "...WWW.W..W.....W.....WWW.....W.WW........W.W.W.WWW.W.W....W...W......WW.....WW...W.WW...W.W.W.W..W.\n" +
                "...WWW...W..WW..W..W.WW..WW..W..WWW...W..WW........W.............WW.W.....W....W....W.W.W..W.W...W..\n" +
                "W.........W.W......W.........WW...W...W.W......W...W...WW.....WW.W.W......W.W.W.WW.....WWWW..W..W..W\n" +
                "..W.W.W.WW.WW.WWW..W.WWW........W.W..WWWW...WWWW..WWW...W.W.W..WWW...W...W..W...WWW.........W.W....W\n" +
                "..W........W...........W..W.WW..W.WW...WWW.W..W.W..W..W...W.......W..W.W.WWW.W..WW..W..W..W....W....\n" +
                "W...W.W.......WW.........W........W...W.WW.WW........W.............WW..W.........W.........W.W......\n" +
                "W.WW....W...WW.................W..W..W...W.WW.W.....W...W...W.W.WWW.....WW..WW..........WW..W.W.....\n" +
                "W..WW.........WWW...W..W.....W..............W......W.....WWW..W..W.W.W.....W..W...W..WW...WW..WW....\n" +
                "W.W....WW.W.......W.WWW......WWW.W...W.........W......W...........WW.W...W.W.W.W...WW.WW.W..........\n" +
                "W.WW...WW.....WWW..W........W....WW..W..........W...W.WW.W...W.W.W......WW.WW....W..W.W..W......W.WW\n" +
                ".....WWW..WW..W............W.W............W....W..WW..WW..........WW.......W.W.W...WW.............W.\n" +
                "..WW..W.W.W...WWWWW.......W..WWWW.W.W.W...W..........W..WW...WW.W.W..W..W...W..............W...W....\n" +
                "..W...W........W.....W.WWWWWWW..WW.WWW.W..W...W.W.......W..W........W..W.W......W.....W......W......\n" +
                ".....WWWW..W...W.....WWWW..W.......W.WW............W....W.W...WW.W....W.W...W.WW.....WW.WW.........W\n" +
                "WW...WW....W.W........WWW.WWW..W....W..W......WW.....WWW..W...W.....WW...WWWW.....W....W..W....WW...\n" +
                ".W...WW...W...W.WWW.W.....WW...W.W..W........W....W.WW....W........WW....W...W..W..........W.......W\n" +
                "...W.W..W.......WW...WW.W.WW.......W....W...W.W......WWW.....W.W....W..........WW.W....W..W...W.W...\n" +
                ".WW.W.........W.......W...WWW.WW.WW..W.......W.W...W..W.WW....W.....WW...W...W.....W..W......WWW....\n" +
                "W..WW.W.......WWWWWWW...W....W.W.W.W........W.....W.W....W.W.W.WWW.W...W......WW.WW...WW.W..W..W....\n" +
                "W....W.........W.WW.....W..WW.......W.......WW.WWW.......W..W....WWW....WW..W...W....WW.WW...W..W...\n" +
                "..W...W.......W......W.W.W..W.W.W......W....W.WWWW...W....W..W...WW...WW......W.....W.W.WW.....WWW.W\n" +
                "..WW.......W.WW.WW.W..WWW..W.......W..W...W..W.WWWWWW.....W.WW......W...........W.W.W...W.....W.W..W\n" +
                "W...........W..W.W...W.W...W..WW...W.WW..W...WW..W.WW..W....W..........WW..W.........W..W.W.......W.\n" +
                "W...W......W....W...WW...........W..W.W...WWWWW...............W......WWW...W.....W..W.........W..W..\n" +
                "...W....W........W..WW.W..W..W.W.W.W...W.W..WW.......W..W.....W......W...W......WW..WW...W.....W....\n" +
                "W.........W....W.W............W.W.......W.W.....WW......W..W..W....W..W..WW......WW..W..WW.WWW....WW\n" +
                "..W..W.W...W....W........W...W..W..W...WW.W.....W.WW...W.W.....W......WW...WWW..........W....W.W....\n" +
                "W..W...W..W..W.......W...W...WW....WW......W..........W...........W......W..W.W........W...WW..WWWWW\n" +
                "W......W...W.W.W......WW...WW...W.....W.WWW......WW........W.W........WWW...W.......WW.W....W.......\n" +
                ".WW........W.WWW.......WW.W.....W.W....W....W.WW.WW..W...W......W.W.WWW...W.W.WW.W......WWW..WW..W.W\n" +
                "W...W.W.W.......WW......W....W.W.WW.........WW......W.W.W...W.W.W..W...W..W...W.....W.WW...W.W...W..\n" +
                ".WW.W.W.WW...W.W.......W....WWW...WW.WW......W..W...W......W......WW........W.......WWWWW.....W.W.WW\n" +
                "...W.........W...WW..WW..W........W..WW..WW..W...W..W.....WW....WWW....W..WW....W....W....W.....W.WW\n" +
                ".........WW...........W.WW.W..W...WW...WW.....WW...WW..W.........WW.WW.W....W.WW.W.WW.W.....W......W\n" +
                ".W.W.WW...W.W..WW..W.........W........W........W.W....W.........W...WW.W..WW..WW..W...W.............\n" +
                "..W.....W....WW........W.W..WW......WW..........W..WW...............W......W..W.................W...\n" +
                "..WW.W..W.W.W..WW...WW.W.WWW..W..W..W.WW..W........WWW...W.W...W.W.WW.W......W.W.WW.W.WWWW..W..W..WW\n" +
                "....W..W.W..W...WWW.....W..WW......W..W.W.....WW..W..W..W.....WW..W............WW....W...W.....W....\n" +
                ".W.W..W.........WWW..W..........W...W...W...W......W.WW....W.....WW.WW...WW.W.......W.W.W.....W..W.W\n" +
                "W..WWW....W.........W.....W...WW......W...WW.W..W..W...W.W........W.....W..W..W.....WW...W.W........\n" +
                ".WWW.W.W.......W..W.W.W.W....WW....WW....W.W.....W....W.W..W..WW......W.....W.....WW.W...WW.W.W..W..\n" +
                ".....WW....W.W...W...W.WWW...W...W.................WW.....W........W.W..W..........W......W...W.....\n" +
                "W.W..W.W.W.W..W.....W.........W.W.WW...W......W.....W.WW..W...W..W...W..WW..W.W......W.W...W...W....\n" +
                ".W.........WWW.....W...W......WW.......W.W.....WW.W...W.WW......W.W....W.W.WW.W..W..W.WW.W.......WW.\n" +
                ".W.WW.W....W................W............W........W..........W........W..W.W...WW.....W..W.....WW...\n" +
                "..........W...W...W.W..W.W.W.......W.W...W..........W..W...............W.W...WWW...W...W........W..W\n" +
                "W.......W.WW.W..........WW.......W....W..W.........W....W.WW.......W.....W...WW.......WW..W......W..\n" +
                "............W.WW.W.W.W....WW..W..W....W.....WW.WW...W.W..W..W......W...W....WWW.W........WW..WW.....\n" +
                ".......WW.W......WWW...W....W...W.....W.W.....W...WWW.W.W...W....WW...WW........W.........W..W......\n" +
                "W....W.....W.....WW.....W.W....W..W...WW.WW..WW....W.......W....W...W..............WWW......W......W\n" +
                ".....W....WWWWW...W..WW...........WW..W.W...........WWW..W...W....WWW.W.W..W.....W.W..W...WW.......W\n" +
                "..WW..........W..W.....W..W.W...WWWWWW...WW.......WWW...WW......W...W..WWW....W...WW....W.W..W......\n" +
                "W...W...WWW..W.W.....W....W..W....W.W.WW.....W.W..W...W.W..W..W.....W.W.W......W.WW.....WW.W.WW.W..W\n" +
                "...WW........W....WW.W...W..W.W....WWW.W.....W....WW...WW..W...W.W.....WW.W.W.......WW.....W...W....\n" +
                "...WW.WWW.......W.W.......W...WWWW....W.WW...W.W......W.WW..WW.W...W....W...W.W..W.W.W.WW.W..W...W..\n" +
                ".W..W....WW.WW..WW....W.W......W...W...WWW.W.W..W.W...W.....W....W..W.....W..W.W...........W..W.....\n" +
                "...W...W........W.WW.W.....WW.WWWW....WW.....W.W.WW...W.W.....W.W.W...W..WW.WWW.W.....W.W....W....W.\n" +
                "....W..WW......W......W........W..WWW...W..WW....WW.WW.W......W.....W...W...W.WW..W...........W.....\n" +
                "...W..W..W......WW....W..W....WW...WW...W....W.WW...WWW...WW.......WW...W....W......W.......W..W...W\n" +
                "W...WW.....WWW...W..W.........W......WW.............WW........W........WW..W..WW..W.....WWW..W.W..WW\n" +
                "W...WWW....W.........W.W.........WW.W.WW...W.....WWWWWW.W.WWW.WW.......WW.W.....W....W..W...W..W....\n" +
                "..W...WW..........W..WWW....WW....WW..WW...WW.....WW....WW..W........W..WW.....WWWW.......W.....W.W.\n" +
                ".W.W...WW.WW.......W.WW..W....W...W.........W...W.WW...WW.W.....W.....W.WW.W...........W.W......W...\n" +
                "..............W.WW.....WW.W..W.WW...W......WW..W.W...WWW.WW..W..W........W.........W...W....W.......\n" +
                "....W....WW....W.W.W.....W...W..........W.......W....WW..W.W.......WWW.W..........W.WW.....W.W......\n" +
                ".W.W...WW..W......W......W.W......WW....W..W.W......W...W.W.......W......W..........W...WW..W......W\n" +
                "...W.WW.WW.WW...W.W..W......W....WW....W..W..W.W.W....W...W.WW..W......WWWWW.W...........W...W.....W\n" +
                "..W...W...W.....WW.W.WWW.W..W..W.........W..........WW...W...W.W.....WW.....WW.W...W.W.WW.......WW..\n" +
                "W.....W..W..W...W.W....W.....W..............W.W............W..W.......WW..WW.W..W...WW.....WW..WW.WW\n" +
                ".WWWW.......W....W....W....W.......W.W...W...WWW.W......W.......WWWW.......W.W.W.W...WW...W.W.WW....\n" +
                "W.W....W.........W...W........W.......W...WW....WWW..W.WW...W.......W....W........W....WW........WW.\n" +
                "...W..W..WWWW..W.W......W......W.W.W.WW...W......W...WW....W...WW....W.W...W....WW...WW...W.WW.W..W.\n" +
                ".W.W.WW....W.WWW.WW......W.......WW.........W....W.W....W...W...W............W....WW...WWWW.....W.W.\n" +
                "...W.....W..WW.......W.W.....W..W......W...........W..WW........W.WW.....W...WW...W........W.....W.W\n" +
                "...W.......W.W....WW.WW.......W..WWWW.W..W...W..W....W..W.....W.WW....W...WW......W..W.....W..WW....\n" +
                "..W.......W.W.W.........WW...WW.W.WW.W.WWWW.W.............WW....W......W.W.W..W.....W...............\n" +
                "..........W........W..W..WW.W.WW.....W...W.............W......W.W....W.W.W.W........WW....W.WW...W..\n" +
                ".W.WW.....W.W.W.......WWW.W..W..WWWW..WW...W...W..W.WW...W.WWW..W....W.W.....WWWW...W.W..WWW.W...W..\n" +
                "W..W.........WW....W.....W...WW.WWWW...W.....WW......W.W...W.W.W.W.W.....WW...W.WW...WWW..W.W.....WW\n" +
                "...W..W.W...WW...W......................W......W.......W.W...WWW.WW.W..W....W.....W.W.W......W..W..W\n" +
                "W....WWW.W..W..W...W.....WW............W....WWW..WWWWW..W..W....W.W....W..WW.W..W...........W.....W.\n" +
                ".WW.W..W.............W....W.W....WW......W.....W..W.W..W...W.....W.W.WW.......W......W.W......W.....\n" +
                "...W.W.....W..W....W..W....W.W..W..W....W...WW.W..W....W....W............W.....WW...W...WW.W....W..W\n" +
                ".WW....WW.W....W...W.W.WW...W........WWW...W.W.....W.W...W...W.WW..W...W.W..W..WWW...W...W...W.....W\n" +
                ".W.WW..W....W...W....W...W....W.W..W....WW...........W...W.....WW...W........WW...W......W.W..W.....\n" +
                "..W...WWWW.W.W...W...W.W.WW..W.....W........W.WWW.....W..WW.WW..WW....W..W.W..W....W...W.WW......W..\n" +
                "..W....W.W.........WW...W.W...WW..W...W.W...W....WW....W.W.WW............W......W...W.......WWW.WWW.\n" +
                "....WW...WWW.W..W.WW.....WWWW.W...W...........W................W...W......W.............W....WWWW...\n" +
                "W.....W.W.WW..W.WWW........W.W........W.....W.W..W.W.........W......W.W.W.W.W...WW..W...W.....W...W.\n" +
                ".W.W....WW.........W.WW.WW...WW...WW.....W.W....W......W.W....W..WW....WW......W.......WWW.WW..WW.WW\n" +
                ".......WWW..WWWW.W.W..W......WWWW..W........W...W.W.........W..W...W....W....WW..W.W.......W........\n" +
                "W.W.....WWWW............W.W.W......WWW.W....W.W.....W...WW..W.WW...W.WWW.WWWW.......W...W.W.W.......\n" +
                ".W....W....WW.W...WW...W.W......W.....W.W..W.W.WW..W...WW......WWW...W.WW...WW.W...W....W.....W...WW\n" +
                ".WW..W.W.W..W.W.....W.WW.W.W.W.....W......WWW...W....WWWW.W.W......W...W...W...W..WWW..WW.W..WW.....\n" +
                ".W......WW..W..W..WW.....W.W.....W.......W..W.W....W....WWWW.....W..W.....W.W......WWWW.W.W.W..W..WW\n" +
                "..W......WW..WW.W.W...WW........WW.....WW.......WW...W.W.W......W.W...W........W..W...W..........WW.\n" +
                ".W.W...W..W....WWWW.......WW.....WW...W..W.............W...W..W....W.......W.W.W.WW......W..W.W.W..W\n" +
                ".W.W.WWWW..W....W..W..W.........W..W...WW.......WW....W...W........W........WWW.W..W......W.W.W..W..";
        assertEquals(maze, -1, Finder2.pathFinder(maze));
    }
}