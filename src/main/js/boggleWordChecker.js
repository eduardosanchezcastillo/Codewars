/*
    Boggle Word Checker
    https://www.codewars.com/kata/57680d0128ed87c94f000bfd

Write a function that determines whether a string is a valid guess in a Boggle board, as per the rules of Boggle.
A Boggle board is a 2D array of individual characters, e.g.:
    [ ["I","L","A","W"],
      ["B","N","G","E"],
      ["I","U","A","O"],
      ["A","S","R","L"] ]

Valid guesses are strings which can be formed by connecting adjacent cells (horizontally, vertically, or diagonally)
without re-using any previously used cells.

For example, in the above board "BINGO", "LINGO", and "ILNBIA" would all be valid guesses, while "BUNGIE", "BINS", and
"SINUS" would not.

Your function should take two arguments (a 2D array and a string) and return true or false depending on whether the
string is found in the array as per Boggle rules.

Test cases will provide various array and string sizes. The array will always be a square 2D array of single capitalized
characters, and the string will always be a single capitalized word.

You do not have to check whether the string is a real word or not, only if it's a valid guess.
*/

const SHIFTS = [
  [-1, -1],
  [-1, 0],
  [-1, 1],
  [0, -1],
  [0, 1],
  [1, -1],
  [1, 0],
  [1, 1]
];

function findRecursive(board, ii, jj, letters, usedPositions) {
  if (letters.length === 0) {
    return true;
  }

  for (shift of SHIFTS) {
    const kk = ii + shift[0];
    const ll = jj + shift[1];
    const key = `${kk}:${ll}`;
    if (usedPositions.indexOf(key) >= 0) {
      continue;
    }

    if (board[kk] && board[kk][ll] === letters[0]) {
      usedPositions.push(key);
      if (findRecursive(board, kk, ll, letters.slice(1), usedPositions)) {
        return true;
      }
      usedPositions.pop();
    }
  }

  return false;
}

function checkWord(board, word) {
  const letters = word.split('').slice(1);
  const SIZE = board.length;
  for (let ii = 0; ii < SIZE; ii++) {
    for (let jj = 0; jj < SIZE; jj++) {
      if (board[ii][jj] === word[0] && findRecursive(board, ii, jj, letters, [])) {
        return true;
      }
    }
  }

  return false;
}