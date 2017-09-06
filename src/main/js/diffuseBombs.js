/*
    Diffuse the bombs!
    https://www.codewars.com/kata/54d558c72a5e542c0600060f

There are a series of 10 bombs about to go off! Diffuse them all! Simple, right?
Note: This is not an ordinary Kata, but more of a series of puzzles. The point is to figure out what you are supposed to
do, then how to do it. Instructions are intentionally left vague.
*/

function showInfo(title, obj) {
  console.log('\n\n<-----------------------', title);
  console.log(obj.diffuse.toString());
  console.log(obj);
  console.log('----------------------->\n\n');
}

// FIRST
Bomb.diffuse(42);

// SECOND
Bomb.diffuse();
Bomb.diffuse();
Bomb.diffuse();
Bomb.diffuse();
Bomb.diffuse();

// THIRD
Bomb.diffuse(BombKey);

// FOURTH
function diffuseTheBomb() {
  return true;
}
Bomb.diffuse();

// FIFTH
Bomb.diffuse('3.14159');

// SIXTH
var currentDate = new Date();
currentDate.setFullYear(currentDate.getFullYear() - 4);
Bomb.diffuse(currentDate);

// SEVENTH
var key7 = {};
Object.defineProperty(key7, 'key', {
  value: 43
});
Bomb.diffuse(key7);

// EIGHTH
var key8 = {
  count: 0,
  valueOf: function () {
    return this.count++ === 0 ? 9 : 11;
  }
}
Bomb.diffuse(key8);

// NINTH
var count = 0;
Math.random = function() {
  count++;
  return count === 3 ? 0.5 : 1;
}
Bomb.diffuse(42);

// TENTH
Array.prototype.toString = function() {
  return this.reduce(function (a,b) { return a + b; }, 0);
}
Bomb.diffuse('WWVz');

showInfo('End', Bomb);