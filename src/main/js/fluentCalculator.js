/*
    Fluent Calculator
    https://www.codewars.com/kata/5578a806350dae5b05000021

Created into a new kata because of a certain limitation the Ruby kata possesses that this kata should also have if
translated, which is what lead me to create a new one.

#Fluent Calculator Your task is to implement a simple calculator with fluent syntax
    var FluentCalculator = / * Magic * /;

FluentCalculator should be separated in two, the Values and the Operations, one can call the other, but cannot call one
of his own.

A Value can call an Operation, but cannot call a value
    FluentCalculator.one.plus
    FluentCalculator.one.one // undefined, if you may.

An Operation can call a Value, but cannot call a operation
    FluentCalculator.one.plus.two // this should have a value of 3
    FluentCalculator.one.plus.plus // If you replace 'one' with 'c', I could allow it. (undefined as well)

Pairs of Value and Operation should be stackable to infinity
    FluentCalculator.one.plus.two.plus.three.minus.one.minus.two.minus.four // Should be -1

A Value should resolve to a primitive integer
    FluentCalculator.one.plus.ten - 10 // Should be 1

#Now, the fun part... Rules
    - eval is disabled
    - Values in FluentCalculator should go from zero to ten.
    - Supported Operations are plus, minus, times, dividedBy
    - Rules mentioned above
        * FluentCalculator should be stackable to infinity
        * A Value can only call an Operation
        * An Operation can only call a Value
        * A Value should be resolvable to a primitive integer, if needed as such
*/

const NUMBERS = ['zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'ten'];

const OPERATIONS = {
  PLUS  : 'plus',
  MINUS : 'minus',
  MULT  : 'times',
  DIVIDE: 'dividedBy'
}

function Value(numCurr, operation, numPrev) {
  this.value = numCurr;
  if (typeof numPrev === 'number') {
    switch (operation) {
      case OPERATIONS.PLUS  : this.value = numPrev + numCurr; break;
      case OPERATIONS.MINUS : this.value = numPrev - numCurr; break;
      case OPERATIONS.MULT  : this.value = numPrev * numCurr; break;
      case OPERATIONS.DIVIDE: this.value = numPrev / numCurr; break;
    }
  }

  for (let op in OPERATIONS) {
    Object.defineProperty(this, OPERATIONS[op], {
      get: function() {
        return new Operation(OPERATIONS[op], this.value);
      }
    });
  }
}

Value.prototype.valueOf = function() {
  return this.value;
}

function Operation(operation, value) {
  const _self = this;
  this.operation = operation;
  this.value = value;

  NUMBERS.forEach((num, ind) => {
    Object.defineProperty(_self, num, {
      get: function() {
        return new Value(ind, _self.operation, _self.value);
      }
    });
  });
}

var FluentCalculator = new Operation();