/*
    Next bigger number with the same digits
    https://www.codewars.com/kata/55983863da40caa2c900004e

You have to create a function that takes a positive integer number and returns the next bigger number formed by the same
digits:
    Kata.nextBiggerNumber(12)==21
    Kata.nextBiggerNumber(513)==531
    Kata.nextBiggerNumber(2017)==2071

If no bigger number can be composed using those digits, return -1:
    Kata.nextBiggerNumber(9)==-1
    Kata.nextBiggerNumber(111)==-1
    Kata.nextBiggerNumber(531)==-1
*/

function nextBigger(n){
  if (n < 10) return -1;

  const head = String(n).split('').map(a => parseInt(a));

  let i = head.length - 2;
  while (i >= 0 && head[i] >= head[i+1]) i--;
  if (i < 0) return -1;

  let tail = head.splice(i);
  const first = tail.filter(a => a > tail[0])
    .reduce((a, b) => Math.min(a, b));

  tail.splice(tail.indexOf(first), 1);
  tail.sort().unshift(first);

  return parseInt(head.concat(tail).join(''));
}