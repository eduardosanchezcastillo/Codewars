/*
    Sum Strings as Numbers
    https://www.codewars.com/kata/5324945e2ece5e1f32000370

Given the string representations of two integers, return the string representation of the sum of those integers.

For example:
    sumStrings('1','2') // => '3'

A string representation of an integer will contain no characters besides the ten numerals "0" to "9".
*/

function toArr(val, length) {
  let arr = val.split('').map(v => parseInt(v));
  if (arr.length < length) {
    arr = new Array(length - val.length)
      .fill(0)
      .concat(arr);
  }
  return arr;
}

function sumStrings(a,b) {
  const length = Math.max(a.length, b.length);
  const val1 = toArr(a, length);
  const val2 = toArr(b, length);
  const res = [];

  let mem = 0;
  for (let i = length - 1; i >= 0; i--) {
    const sum = val1[i] + val2[i] + mem;
    res.push(sum % 10);
    mem = Math.floor(sum / 10);
  }
  res.push(mem);

  while (res[res.length - 1] === 0) {
    res.pop();
  }

  return res.reverse().join('');
}