/*
    Snail
    https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1

Snail Sort

Given an n x n array, return the array elements arranged from outermost elements to the middle element, traveling
clockwise.
    array = [[1,2,3],
             [4,5,6],
             [7,8,9]]
    snail(array) #=> [1,2,3,6,9,8,7,4,5]

For better understanding, please follow the numbers of the next array consecutively:
    array = [[1,2,3],
             [8,9,4],
             [7,6,5]]
    snail(array) #=> [1,2,3,4,5,6,7,8,9]

This image will illustrate things more clearly:
    >>>>>>>>>>>>> SEE THE IMAGE ON THE WEBSITE <<<<<<<<<<<<<

NOTE: The idea is not sort the elements from the lowest value to the highest; the idea is to traverse the 2-d array in a
clockwise snailshell pattern.

NOTE 2: The 0x0 (empty matrix) is represented as [[]]
*/

const dir = [
  [0, 1],
  [1, 0],
  [0, -1],
  [-1, 0]
];

function canMoveForward(array, i, j, dirNum) {
  i += dir[dirNum][0];
  j += dir[dirNum][1];
  return !(array[i] === undefined || array[i][j] === undefined || array[i][j] === '');
}

function snail(array) {
  const res = [];
  if (array.length === 0 || array[0].length === 0) {
    return res;
  }

  let i = 0, j = 0;
  let dirNum = 0;
  while (true) {
    res.push(array[i][j]);
    array[i][j] = '';

    if (!canMoveForward(array, i, j, dirNum)) {
      dirNum = (dirNum + 1) % 4;
      if (!canMoveForward(array, i, j, dirNum)) break;
    }
    i += dir[dirNum][0];
    j += dir[dirNum][1];
  }

  return res;
}