package eugene.codewars;

/*
    Total area covered by rectangles
    https://www.codewars.com/kata/total-area-covered-by-rectangles/java

Your task in order to complete this Kata is to write a function which calculates the area covered by a union of
rectangles.

Rectangles can have non-empty intersection, in this way simple solution: Sall = S1 + S2 + ... + Sn-1 + Sn (where n - the
quantity of rectangles) will not work.

Preconditions

    * each rectangle is represented as: [x0, y0, x1, y1]
    * (x0, y0) - coordinates of the bottom left corner
    * (x1, y1) - coordinates of the top right corner
    * xi, yi - positive integers or zeroes (0, 1, 2, 3, 4..)
    * sides of rectangles are parallel to coordinate axes
    * your input data is array of rectangles

Memory requirements

Number of rectangles in one test (not including simple tests) range from 3000 to 15000. There are 10 tests with such
range. So, your algorithm should be optimal.

Example
(See picture on the website)
    // There are three rectangles: R1 = [3,3,8,5], R2 = [6,3,8,9], R3 = [11,6,14,12]
    // S(R1) = 10, S(R2)= 12, S(R3) =  18
    // S(R1 ∩ R2) = 4, S(R1 ∩ R3) = 0,  S(R2 ∩ R3) = 0
    // S = S(R1) + S(R2) + S(R3) - S(R1 ∩ R2) - S(R1 ∩ R3) - S(R2 ∩ R3) = 36

javascript: calculate([[3,3,8,5], [6,3,8,9], [11,6,14,12]]) // returns 36
java: RectanglesUnion.calculateSpace(new int[][]{{3,3,8,5}, {6,3,8,9}, {11,6,14,12}}) // returns 36
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RectanglesUnion {
    static int calculateSpace(int[][] input) {
        List<int[]> rectangleList = new ArrayList<>(input.length * 2);  // just a guess that this will be enough
        rectangleList.addAll(Arrays.asList(input));

        // Process all combinations of rectangles.
        // For optimization purposes, don't ever delete rectangles from the list. Instead, put NULLs.
        for (int ii = 0; ii < rectangleList.size() - 1; ii++) {
            int[] rectA = rectangleList.get(ii);
            if (rectA == null) {
                continue;
            }

            for (int jj = ii + 1; jj < rectangleList.size(); jj++) {
                int[] rectB = rectangleList.get(jj);
                if (!isCollision(rectA, rectB)) {
                    continue;
                }

                if (isIncluding(rectA, rectB)) {
                    rectangleList.set(jj, null);
                    continue;
                }

                if (isIncluding(rectB, rectA)) {
                    rectangleList.set(ii, null);
                    break;      // rectA is gone; get out of here immediately
                }

                if (isSideIntersection(rectA, rectB) || isSideIntersection(rectB, rectA)) {
                    continue;
                }

                if (isCross(rectA, rectB)) {
                    rectangleList.add(new int[]{rectB[0], rectA[3], rectB[2], rectB[3]});
                    rectB[3] = rectA[1];
                }

                if (isCross(rectB, rectA)) {
                    rectangleList.add(new int[]{rectA[0], rectB[3], rectA[2], rectA[3]});
                    rectA[3] = rectB[1];
                }

                int[] rectC = getCornerIntersection(rectA, rectB);
                if (rectC != null) {
                    rectangleList.add(rectC);
                    continue;
                }

                rectC = getCornerIntersection(rectB, rectA);
                if (rectC != null) {
                    rectangleList.add(rectC);
                    // continue;
                }
            }
        }

        return area(rectangleList);
    }

    private static int area(List<int[]> rectangles) {
        int area = 0;
        for (int[] rect : rectangles) {
            if (rect != null) {
                area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
            }
        }
        return area;
    }

    /**
     * Check if there is a collision between the two rectangles.
     */
    private static boolean isCollision(int[] a, int[] b) {
        return a != null && b != null && !(a[2] <= b[0] || a[0] >= b[2] || a[3] <= b[1] || a[1] > b[3]);
    }

    /**
     * Check if first rectangle includes the entire second rectangle.
     */
    private static boolean isIncluding(int[] a, int[] b) {
        return a[0] <= b[0] && a[1] <= b[1] && a[2] >= b[2] && a[3] >= b[3];
    }

    /**
     * Check if the two rectangles are forming a cross
     */
    private static boolean isCross(int[] rectA, int[] rectB) {
        return rectA[0] < rectB[0] && rectA[2] > rectB[2] && rectA[1] > rectB[1] && rectA[3] < rectB[3];
    }

    /**
     * Check if one side of first rectangle is inside of second rectangle
     */
    private static boolean isSideIntersection(int[] a, int[] b) {
        // Left
        if (a[0] < b[0] && a[2] > b[0] && a[2] <= b[2] && a[1] >= b[1] && a[3] <= b[3]) {
            a[2] = b[0];
            return true;
        }

        // Right
        if (a[0] < b[2] && a[2] > b[2] && a[0] >= b[0] && a[1] >= b[1] && a[3] <= b[3]) {
            a[0] = b[2];
            return true;
        }

        // Bottom
        if (a[1] < b[1] && a[3] > b[1] && a[3] <= b[3] && a[0] >= b[0] && a[2] <= b[2]) {
            a[3] = b[1];
            return true;
        }

        // Top
        if (a[1] < b[3] && a[3] > b[3] && a[1] >= b[1] && a[0] >= b[0] && a[2] <= b[2]) {
            a[1] = b[3];
            return true;
        }

        return false;
    }

    /**
     * Check if one corner of first rectangle is inside second rectangle
     */
    private static int[] getCornerIntersection(int[] a, int[] b) {
        // Left-bottom
        if (/* X */ a[0] < b[0] && a[2] > b[0] && a[2] < b[2]
                && /* Y */ a[1] < b[1] && a[3] > b[1] && a[3] < b[3]) {
            int[] c = new int[]{a[0], b[1], b[0], a[3]};
            a[3] = b[1];
            return c;
        }

        // Left-top
        if (/* X */ a[0] < b[0] && a[2] > b[0] && a[2] < b[2]
                && /* Y */ a[1] < b[3] && a[3] > b[3]) {
            int[] c = new int[]{a[0], a[1], b[0], b[3]};
            a[1] = b[3];
            return c;
        }

        return null;
    }
}