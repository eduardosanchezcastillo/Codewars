/*
    Elemental Words
    https://www.codewars.com/kata/56fa9cd6da8ca623f9001233

Each element in the periodic table has a symbol associated with it. For instance, the symbol for the element Yttrium
is Y. A fun thing to do is see if we can form words using symbols of elements strung together. The symbol for
Einsteinium is Es, so the symbols for Yttrium and Einsteinium together form:
    Y + Es = YEs

Yes! Ignoring capitalization, we can think of any string of letters formed by the concatenation of one or more element
symbols as an elemental word -- per the above,yes is an elemental word. There is only one combination of element symbols
that can form yes, but in general, there may be more than one combination of element symbols that can form a given
elemental word. Let's call each different combination of element symbols that can form a given elemental word word an
elemental form of word.

Your task is to implement the function elementalForms(word), which takes one parameter (the string word), and returns
an array (which we'll call forms). If word can be formed by combining element symbols from the periodic table, then
forms should contain one or more sub-arrays, each consisting of strings of the form 'elementName (elementSymbol)', for
each unique combination of elements that can form word.

###EXAMPLE

The word 'snack' can be formed by concatenating the symbols of 3 different combinations of elements:
    ----------------------------------------------------
    |       1        |       2        |       3        |
    |---------------------------------------------------
    | Sulfur    (S)  | Sulfur    (S)  | Tin       (Sn) |
    | Nitrogen  (N)  | Sodium    (Na) | Actinium  (Ac) |
    | Actinium  (Ac) | Carbon    (C)  | Potassium (K)  |
    | Potassium (K)  | Potassium (K)  |                |
    ----------------------------------------------------

So elementalForms('snack') should return the following array:
    [
      ['Sulfur (S)', 'Nitrogen (N)', 'Actinium (Ac)', 'Potassium (K)'],
      ['Sulfur (S)', 'Sodium (Na)', 'Carbon (C)', 'Potassium (K)'],
      ['Tin (Sn)', 'Actinium (Ac)', 'Potassium (K)']
    ]

###GUIDELINES
    - Capitalization does not matter:
        elementalForms('beach')
        // => [ ['Beryllium (Be)', 'Actinium (Ac)', 'Hydrogen (H)'] ]
        elementalForms('BEACH')
        // => [ ['Beryllium (Be)', 'Actinium (Ac)', 'Hydrogen (H)'] ]
    - The order of the returned sub-arrays does not matter, but the order of the strings within each sub-array does
      matter -- they should be in the order that "spells out" word.
    - If word is not an elemental word (that is, no combination of element symbols can form word), return an empty array.
    - You do not need to check the type of word. It will always be a (possibly empty) string.

Finally, the helper object ELEMENTS has been provided, which is a map from each element symbol to its corresponding full
name (e.g. ELEMENTS['Na'] === 'Sodium'). Have fun!
*/

function normalizeName(name) {
  return name.replace(/\w/g, (letter, index) =>
    index > 0 ? letter.toLowerCase() : letter.toUpperCase()
  );
}

function elementalForms(word) {
  if (word.length === 0) {
    return [];
  }

  const splitting = [];
  const currRes = [];
  const res = [];

  function pop() {
    currRes.pop();
    return splitting.pop() + 1;
  }

  const MAX_LEN = 3;
  let len = 1;

  let stop = false;
  while (!stop) {
    const sum = splitting.reduce((a, b) => a + b, 0);
    if (sum === word.length) {
      res.push(currRes.slice());
      len = pop();
    }
    else {
      const name = normalizeName(word.substr(sum, len));
      if (name.length > 0 && ELEMENTS[name]) {
        splitting.push(len);
        currRes.push(`${ELEMENTS[name]} (${name})`);
        len = 1;
        continue;
      }
    }

    len++;
    while (len > MAX_LEN) {
      if (splitting.length === 0) {
        stop = true;
        break;
      }
      len = pop();
    }
  }

  return res;
}