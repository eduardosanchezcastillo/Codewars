/*
    Simple Fun #27: Rectangle Rotation
    https://www.codewars.com/kata/5886e082a836a691340000c3

TASK

A rectangle with sides equal to even integers a and b is drawn on the Cartesian plane. Its center (the intersection
point of its diagonals) coincides with the point (0, 0), but the sides of the rectangle are not parallel to the axes;
instead, they are forming 45 degree angles with the axes.

How many points with integer coordinates are located inside the given rectangle (including on its sides)?


EXAMPLE
    For a = 6 and b = 4, the output should be 23
    The following picture illustrates the example, and the 23 points are marked green.
    >>>>>>>>>> SEE THE PICTURE ON THE WEBSITE <<<<<<<<<<<


INPUT/OUTPUT

    [input] integer a
        A positive even integer.
        Constraints: 2 ≤ a ≤ 10000.

    [input] integer b
        A positive even integer.
        Constraints: 2 ≤ b ≤ 10000.

    [output] an integer
        The number of inner points with integer coordinates.
*/

function rectangleRotation(a, b) {
  const SQRT_2 = Math.sqrt(2);

  const length = {
    A: a / 2 / SQRT_2,
    B: b / 2 / SQRT_2
  }

  const odd = {
    countA: 2 * Math.trunc(length.A) + 1,
    countB: 2 * Math.trunc(length.B) + 1
  }

  const even = {
    countA: 2 * Math.round(length.A),
    countB: 2 * Math.round(length.B)
  }

  return odd.countA*odd.countB + even.countA*even.countB;
}