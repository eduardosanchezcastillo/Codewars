package eugene.codewars;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RectanglesUnionTest {
    @Test
    public void testZeroRectangles() {
        int[][] recs = {};
        Assert.assertEquals("Zero rectangles", 0, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testOneRectangle() {
        int[][] recs = {{0, 4, 11, 6}};
        Assert.assertEquals("One rectangle [0, 4, 11, 6] => 22", 22, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testNonIntersecting() {
        int[][] recs = {
                {1, 1, 2, 2},
                {2, 2, 3, 3},
                {3, 3, 4, 4},
                {4, 4, 5, 5},
                {2, 1, 3, 2}
        };
        Assert.assertEquals("Some non-intersecting rectangles", 5, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testSimpleIntersections1() {
        int[][] recs = {
                {1, 1, 2, 2},
                {1, 4, 2, 7},
                {1, 4, 2, 6},
                {1, 4, 4, 5},
                {2, 5, 6, 7},
                {4, 3, 7, 6}
        };
        Assert.assertEquals("Simple intersecting rectangles No. 1", 21, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testSimpleIntersections2() {
        int[][] recs = {
                {1, 3, 4, 5},
                {2, 1, 4, 7},
                {3, 4, 5, 6},
                {6, 6, 8, 7},
                {5, 3, 8, 4},
                {6, 0, 7, 3}
        };
        Assert.assertEquals("Simple intersecting rectangles No. 1", 24, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testComplexCommonFacesClusters() {
        Random random = new Random();
        int stepX = random.nextInt(11) + 10,
                stepY = random.nextInt(11) + 10,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[5 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX + i * stepX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y, x + 1, y + 1});
            shuffledRecs.add(new int[]{x + 1, y, x + 3, y + 2});
            shuffledRecs.add(new int[]{x, y + 2, x + 3, y + 3});
            shuffledRecs.add(new int[]{x + 3, y, x + 4, y + 3});
            shuffledRecs.add(new int[]{x + 2, y + 3, x + 4, y + 5});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles having only common faces", 15 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testFarAwayClusters() {
        Random random = new Random();
        int stepX = random.nextInt(1001) + 1000,
                stepY = random.nextInt(1001) + 1000,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[3 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX + i * stepX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y, x + 202, y + 300});
            shuffledRecs.add(new int[]{x + 100, y + 500, x + 500, y + 765});
            shuffledRecs.add(new int[]{x + 150, y + 330, x + 170, y + 360});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles located far away", 167200 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testRectanglesOverEachOther() {
        Random random = new Random();
        int stepX = random.nextInt(191) + 10,
                stepY = random.nextInt(191) + 10,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[10 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX + i * stepX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y + 2, x + 2, y + 4});
            shuffledRecs.add(new int[]{x + 1, y + 3, x + 3, y + 5});
            shuffledRecs.add(new int[]{x + 1, y + 1, x + 3, y + 3});
            shuffledRecs.add(new int[]{x + 7, y + 3, x + 8, y + 4});
            shuffledRecs.add(new int[]{x + 8, y + 2, x + 9, y + 7});
            shuffledRecs.add(new int[]{x + 6, y + 2, x + 9, y + 7});
            shuffledRecs.add(new int[]{x + 3, y + 5, x + 10, y + 6});
            shuffledRecs.add(new int[]{x + 3, y + 2, x + 6, y + 3});
            shuffledRecs.add(new int[]{x + 2, y + 4, x + 4, y + 7});
            shuffledRecs.add(new int[]{x + 9, y, x + 10, y + 3});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles located far away", 39 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testRectanglesLongSides() {
        Random random = new Random();
        int stepX = random.nextInt(11001) + 100000,
                stepY = random.nextInt(11001) + 100000,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[3 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX + i * stepX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y, x + 30000, y + 1});
            shuffledRecs.add(new int[]{x, y + 1, x + 1, y + 30001});
            shuffledRecs.add(new int[]{x + 30000, y + 1, x + 30001, y + 30001});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles with very long sides", 90000 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testComplexCommonFacesClustersV2() {
        Random random = new Random();
        int stepY = random.nextInt(11) + 10,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[5 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y, x + 1, y + 1});
            shuffledRecs.add(new int[]{x + 1, y, x + 3, y + 2});
            shuffledRecs.add(new int[]{x, y + 2, x + 3, y + 3});
            shuffledRecs.add(new int[]{x + 3, y, x + 4, y + 3});
            shuffledRecs.add(new int[]{x + 2, y + 3, x + 4, y + 5});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles having only common faces V2", 15 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testFarAwayClustersV2() {
        Random random = new Random();
        int stepY = random.nextInt(1001) + 1000,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[3 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y, x + 202, y + 300});
            shuffledRecs.add(new int[]{x + 100, y + 500, x + 500, y + 765});
            shuffledRecs.add(new int[]{x + 150, y + 330, x + 170, y + 360});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles located far away V2", 167200 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testRectanglesOverEachOtherV2() {
        Random random = new Random();
        int stepY = random.nextInt(191) + 10,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[10 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y + 2, x + 2, y + 4});
            shuffledRecs.add(new int[]{x + 1, y + 3, x + 3, y + 5});
            shuffledRecs.add(new int[]{x + 1, y + 1, x + 3, y + 3});
            shuffledRecs.add(new int[]{x + 7, y + 3, x + 8, y + 4});
            shuffledRecs.add(new int[]{x + 8, y + 2, x + 9, y + 7});
            shuffledRecs.add(new int[]{x + 6, y + 2, x + 9, y + 7});
            shuffledRecs.add(new int[]{x + 3, y + 5, x + 10, y + 6});
            shuffledRecs.add(new int[]{x + 3, y + 2, x + 6, y + 3});
            shuffledRecs.add(new int[]{x + 2, y + 4, x + 4, y + 7});
            shuffledRecs.add(new int[]{x + 9, y, x + 10, y + 3});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles located far away V2", 39 * count, RectanglesUnion.calculateSpace(recs));
    }

    @Test
    public void testRectanglesLongSidesV2() {
        Random random = new Random();
        int stepY = random.nextInt(11001) + 100000,
                startX = random.nextInt(1001),
                startY = random.nextInt(1001),
                count = random.nextInt(501) + 1000;

        int[][] recs = new int[3 * count][];
        ArrayList<int[]> shuffledRecs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = startX;
            int y = startY + i * stepY;
            shuffledRecs.add(new int[]{x, y, x + 30000, y + 1});
            shuffledRecs.add(new int[]{x, y + 1, x + 1, y + 30001});
            shuffledRecs.add(new int[]{x + 30000, y + 1, x + 30001, y + 30001});
        }

        Collections.shuffle(shuffledRecs);
        recs = shuffledRecs.toArray(recs);

        Assert.assertEquals("Rectangles with very long sides V2", 90000 * count, RectanglesUnion.calculateSpace(recs));
    }
}