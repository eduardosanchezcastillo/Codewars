/*
    Counting in English one, two, three... to 2 Quadrillion (2,000,000,000,000,000)
    https://www.codewars.com/kata/5461c0e29726f66bb60010b1

Please create a function which takes a number and returns the name of the number in English as a lowercase string.
The function should support at least the numbers ±2 Quadrillion (±2,000,000,000,000,000) as well as positive and
negative infinity.

This is a harder kata than it might sound! Be sure to read the requirements thoroughly.

RULES
    - positive integers - Print number in english.
        * Numbers between 20 and 99 use hyphens. e.g. forty-five
        * Use a space to separate all other words. e.g. one hundred twenty-three
        * The word 'and' is used to separate the tens space from the hundreds space in each period
            111 -> Good: "one hundred and eleven" - Bad: "one hundred eleven"
            1,101,101 Good: "one million one hundred and one thousand one hundred and one" - Bad: "one million one
                            hundred one thousand one hundred one"
        * ...also the word 'and' is used to separate the tens and ones space from the lowest number the left of the tens
          place for numbers over 1000.
            1,001 -> Good: "one thousand and one" - Bad: "one thousand one"
            1,000,001 -> Good: "one million and one" - Bad: "one million one"
            1,000,011 -> Good: "one million and eleven" - Bad: "one million eleven"
            1,000,111 -> Good: "one million one hundred and eleven" - Bad: "one million and one hundred and eleven"
            1,011,011 -> Good: "one million eleven thousand and eleven" Bad: "one million and eleven thousand and eleven"
        * Support integers up to ±2 Quadrillion.
        * Consult this list for large number names. Always use the 'short scale' popular in the USA.
    - zero/nil - Print 'zero'.
    - negative integers - Print 'negative' before the number
    - decimal numbers - Print the number to the left of the decimal then 'point' then the numbers to the right of the
      decimal each digit at a time. (see example)
        * Support at least 5 decimal places.
    - non-numbers - Strings that evaluate to numbers should be converted to numbers. NaN values must throw an error.
    - Positive / Negative infinity - Print 'infinity' or 'negative infinity'

EXAMPLE
    > numberToEnglish(1); // -> "one"
    > numberToEnglish(11); // -> "eleven"
    > numberToEnglish(1.23); // -> "one point two three"
    > numberToEnglish(-45); // -> "negative forty-five"
    > numberToEnglish(100023999); // -> "one hundred million twenty-three thousand nine hundred and ninety-nine"

LARGE NUMBER NAMES
    - Million 10^6
    - Billion 10^9
    - Trillion 10^12
    - Quadrillion 10^15
*/

const DICT = {
  0: 'zero',
  1: 'one',
  2: 'two',
  3: 'three',
  4: 'four',
  5: 'five',
  6: 'six',
  7: 'seven',
  8: 'eight',
  9: 'nine',
  10: 'ten',
  11: 'eleven',
  12: 'twelve',
  13: 'thirteen',
  14: 'fourteen',
  15: 'fifteen',
  16: 'sixteen',
  17: 'seventeen',
  18: 'eighteen',
  19: 'nineteen',
  20: 'twenty',
  30: 'thirty',
  40: 'forty',
  50: 'fifty',
  60: 'sixty',
  70: 'seventy',
  80: 'eighty',
  90: 'ninety',
  100: 'hundred',
  1000: 'thousand',
  1000000: 'million',
  1000000000: 'billion',
  1000000000000: 'trillion',
  1000000000000000: 'quadrillion',
  negative: 'negative',
  and: 'and',
  separator: '-',
  point: 'point',
  infinity: 'infinity'
};

function parseThreeDigits(n3, words, last) {
  if (n3 >= 100) {
    const hundredNum = Math.floor(n3 / 100);
    words.push(DICT[hundredNum]);
    words.push(DICT[100]);

    n3 = n3 % 100;
    if (n3 > 0) {
      words.push(DICT.and);
    }
  }

  if (n3 >= 20) {
    const tens = n3 - n3%10;
    let word = DICT[tens];
    if (word) {
      n3 = n3 % 10;
      if (n3 > 0) {
        word += DICT.separator + DICT[n3];
        n3 = 0;
      }
      words.push(word);
    }
  }

  if (n3 > 0) {
    words.push(DICT[n3]);
  }
}

function getDecimalPart(n) {
  n = Math.abs(n);
  if (n % 1 > 0) {
    return String(n).replace(/\d+\./, '');
  }
}

//////////////////////////////////////////
function numberToEnglish(n) {
  if (n !== n) {
    throw "NaN!";
  }

  let words = [];
  const decimalStr = getDecimalPart(n);
  n = Math.trunc(n);

  if (n < 0) {
    words.push(DICT.negative);
    n = -n;
  }

  if (!isFinite(n)) {
    words.push(DICT.infinity);
    n = 0;
  }

  let moreThanHundred = n > 100;
  for (let power = 15; power > 0; power -= 3) {
    const powered = Math.pow(10, power);
    const n3 = Math.floor(n / powered);
    n = n % powered;

    if (n3 > 0) {
      parseThreeDigits(n3, words);
      words.push(DICT[powered]);
    }
  }

  if (moreThanHundred && n < 100 && n > 0) {
    words.push(DICT.and);
  }
  parseThreeDigits(n, words);

  if (n === 0 && words.length === 0) {
    words.push(DICT[0]);
  }

  if (decimalStr) {
    words.push(DICT.point);
    words = words.concat(
      decimalStr.split('')
        .map(d => DICT[d])
    );
  }

  return words.join(' ');
}