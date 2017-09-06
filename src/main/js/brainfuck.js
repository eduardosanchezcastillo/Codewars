/*
    My smallest code interpreter (aka Brainf**k)
    https://www.codewars.com/kata/526156943dfe7ce06200063e

Inspired from real-world Brainf**k, we want to create an interpreter of that language which will support the following
instructions (the machine memory or 'data' should behave like a potentially infinite array of bytes, initialized to 0):

    > increment the data pointer (to point to the next cell to the right).
    < decrement the data pointer (to point to the next cell to the left).
    + increment (increase by one, truncate overflow: 255 + 1 = 0) the byte at the data pointer.
    - decrement (decrease by one, treat as unsigned byte: 0 - 1 = 255 ) the byte at the data pointer.
    . output the byte at the data pointer.
    , accept one byte of input, storing its value in the byte at the data pointer.
    [ if the byte at the data pointer is zero, then instead of moving the instruction pointer forward to the next
      command, jump it forward to the command after the matching ] command.
    ] if the byte at the data pointer is nonzero, then instead of moving the instruction pointer forward to the next
      command, jump it back to the command after the matching [ command.

The function will take in input...
    - the program code, a string with the sequence of machine instructions,
    - the program input, a string, eventually empty, that will be interpreted as an array of bytes using each
      character's ASCII code and will be consumed by the , instruction

... and will return ...
    - the output of the interpreted code (always as a string), produced by the . instruction.
*/

function findNextBracket(code, ind) {
  let count = 1;
  while (ind < code.length && count > 0) {
    ind++;
    if (code[ind] === '[') count++;
    if (code[ind] === ']') count--;
  }
  return ind;
}

function findPrevBracket(code, ind) {
  let count = 1;
  while (ind > 0 && count > 0) {
    ind--;
    if (code[ind] === ']') count++;
    if (code[ind] === '[') count--;
  }
  return ind;
}

function brainLuck(code, input) {
  let modifiedBytes = {}; // index : value
  function getByte(ptr) {
    return modifiedBytes[ptr] || 0;
  }
  function setByte(ptr, val) {
    if (val > 255) val = 0;
    if (val < 0) val = 255;
    modifiedBytes[ptr] = val;
  }

  let inputPtr = 0;
  let output = '';

  let ptr = 0;
  for (let ii = 0; ii < code.length; ii++) {
    switch (code[ii]) {
      case '>':
        ptr++;
        break;

      case '<':
        ptr--;
        break;

      case '+':
        setByte(ptr, getByte(ptr) + 1);
        break;

      case '-':
        setByte(ptr, getByte(ptr) - 1);
        break;

      case '.':
        output += String.fromCharCode(getByte(ptr));
        break;

      case ',':
        setByte(ptr, input.charCodeAt(inputPtr++));
        break;

      case '[':
        if (getByte(ptr) === 0) {
          ii = findNextBracket(code, ii);
        }
        break;

      case ']':
        if (getByte(ptr) !== 0) {
          ii = findPrevBracket(code, ii);
        }
        break;
    }
  }

  return output;
}