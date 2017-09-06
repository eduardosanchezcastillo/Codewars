package eugene.codewars.findingNeo;

/*
    Finding Neo (NC-17)
    https://www.codewars.com/kata/551886d9fd24d8f9e20001fa

Note: This version focuses on high-speed action and special effects. If you would like to get acquainted with the story
line first, have a look at Finding Neo (PG-13).

Neo is somewhere in the Matrix.
    public interface Matrix {
      public int size();
      public int get(int x, int y);
    }

You are Morpheus, and your job is to find him.
    public class Morpheus {
      public int[] find(Matrix matrix, int neo) {
        // return Neo's x and y coordinates as a two-element array
      }
    }

You will need a good search strategy - the matrix is huge! But it is controlled by machines, so it is also very orderly.
It is quadratic, and the following rules hold for all elements:
    matrix[x,y] < matrix[x+1,y]
    matrix[x,y] < matrix[x,y+1]

And of course, there will be no duplicates of Neo - he is The One.
*/

public class Morpheus {
    public int[] find(Matrix matrix, int neo) {
        int x = matrix.size() - 1;
        int y = 0;

        while (true) {
            int curr = matrix.get(x, y);
            if (curr < neo) {
                y++;
            } else if (curr > neo) {
                x--;
            } else {
                return new int[] {x, y};
            }
        }
    }
}
