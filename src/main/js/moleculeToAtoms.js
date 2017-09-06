/*
    Molecule to atoms
    https://www.codewars.com/kata/52f831fa9d332c6591000511

For a given chemical formula represented by a string, count the number of atoms of each element contained in the
molecule and return an object (associative array in PHP, Dictionary<string, int> in C#).

For example:
    var water = 'H2O';
    parseMolecule(water); // return {H: 2, O: 1}

    var magnesiumHydroxide = 'Mg(OH)2';
    parseMolecule(magnesiumHydroxide); // return {Mg: 1, O: 2, H: 2}

    var fremySalt = 'K4[ON(SO3)2]2';
    parseMolecule(fremySalt); // return {K: 4, O: 14, N: 2, S: 4}

As you can see, some formulas have brackets in them. The index outside the brackets tells you that you have to multiply
count of each atom inside the bracket on this index. For example, in Fe(NO3)2 you have one iron atom, two nitrogen atoms
and six oxygen atoms.

Note that brackets may be round, square or curly and can also be nested. Index after the braces is optional.
*/

function isUpLetter(ch) {
  return /[A-Z]/.test(ch);
}

function isLowLetter(ch) {
  return /[a-z]/.test(ch);
}

function isDigit(ch) {
  return /\d/.test(ch);
}

function parseMolecule(formula) {
  const TYPE = {
    ATOM: 0,
    COUNT: 1,
    OPEN: 2,
    CLOSE: 3
  };

  function nextToken(chars) {
    const ch = chars.shift();
    const token = {};
    if (isUpLetter(ch)) {
      token.type = TYPE.ATOM;
      token.value = ch;
      if (chars.length > 0 && isLowLetter(chars[0])) {
        token.value += chars.shift();
      }
    }
    else if (isDigit(ch)) {
      token.type = TYPE.COUNT;
      token.value = ch;
      while (chars.length > 0 && isDigit(chars[0])) {
        token.value += chars.shift();
        break;
      }
      token.value = parseInt(token.value);
    }
    else if (ch === '(' || ch === '[' || ch === '{') {
      token.type = TYPE.OPEN;
    }
    else if (ch === ')' || ch === ']' || ch === '}') {
      token.type = TYPE.CLOSE;
    }
    return token;
  }

  const chars = formula.split('');
  let atoms = [];
  let brackets = [];
  let scopeIndex = -1;

  while (chars.length > 0) {
    const token = nextToken(chars);
    switch (token.type) {
      case TYPE.ATOM:
        atoms.push({
          name: token.value,
          count: 1
        });
        scopeIndex = atoms.length - 1;
        break;

      case TYPE.COUNT:
        atoms.slice(scopeIndex)
          .forEach(a => a.count *= token.value);
        break;

      case TYPE.OPEN:
        brackets.push(atoms.length); // next element will be the beginning of a new scope
        break;

      case TYPE.CLOSE:
        scopeIndex = brackets.pop();
        break;
    }
  }

  const res = {};
  for (a of atoms) {
    if (res[a.name]) {
      res[a.name] += a.count;
    } else {
      res[a.name] = a.count;
    }
  }

  return res;
}