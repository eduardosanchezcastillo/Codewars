/*
    Conway's Game of Life - Unlimited Edition
    https://www.codewars.com/kata/52423db9add6f6fc39000354

Given a 2D array and a number of generations, compute n timesteps of Conway's Game of Life.

The rules of the game are:
    1. Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.
    2. Any live cell with more than three live neighbours dies, as if by overcrowding.
    3. Any live cell with two or three live neighbours lives on to the next generation.
    4. Any dead cell with exactly three live neighbours becomes a live cell.

Each cell's neighborhood is the 8 cells immediately around it (i.e. Moore Neighborhood). The universe is infinite in
both the x and y dimensions and all cells are initially dead - except for those specified in the arguments. The return
value should be a 2d array cropped around all of the living cells. (If there are no living cells, then return [[]].)

For illustration purposes, 0 and 1 will be represented as ░░ and ▓▓ blocks respectively (PHP: plain black and white
squares). You can take advantage of the htmlize function to get a text representation of the universe: eg:
    System.out.println(LifeDebug.htmlize(cells));
*/

class World {
  constructor(data) {
    this.data = new Map();
    if (data) {
      if (data.length > 0) {
        this.copyFromArray(data);
      }
      else if (data.constructor.name === this.constructor.name) {
        this.copyFromOther(other);
      }
    }
  }

  copyFromArray(arr) {
    for (let x = 0; x < arr.length; x++) {
      for (let y = 0; y < arr[x].length; y++) {
        if (arr[x][y] > 0) {
          this.setCell(x, y, 1);
        }
      }
    }
  }

  copyFromOther(other) {
    for (pair of other.data) {
      this.data.set(pair[0], new Set(pair[1]));
    }
  }

  getCell(x, y) {
    if (this.data.has(x) && this.data.get(x).has(y)) {
      return this.data.get(x).has(y) ? 1 : 0;
    }
    return 0;
  }

  setCell(x, y, val) {
    if (!this.data.has(x)) {
      if (val === 0) return;
      this.data.set(x, new Set());
    }

    if (val === 0) {
      this.data.get(x).delete(y);
    } else {
      this.data.get(x).add(y);
    }
  }

  getArray() {
    const size = this.getSize();

    let arr = [];
    let row = [];
    for (let x = size.minX; x <= size.maxX; x++) {
      for (let y = size.minY; y <= size.maxY; y++) {
        row.push(this.getCell(x, y));
      }
      arr.push(row);
      row = [];
    }
    return arr;
  }

  getNeighboursCount(x, y) {
    let count = 0;
    for (let ii = x-1; ii <= x+1; ii++) {
      for (let jj = y-1; jj <= y+1; jj++) {
        if (ii !== x || jj !== y) {
          count += this.getCell(ii, jj);
        }
      }
    }
    return count;
  }

  newGeneration() {
    const newWorld = new World();
    const size = this.getSize();

    for (let x = size.minX-1; x <= size.maxX+1; x++) {
      for (let y = size.minY-1; y <= size.maxY+1; y++) {
        let cell = this.getCell(x, y);
        const aliveNeighbours = this.getNeighboursCount(x, y);

        if (cell === 1 && (aliveNeighbours < 2 || aliveNeighbours > 3)) cell = 0;    // cases 1 & 2
        else if (cell === 0 && aliveNeighbours === 3) cell = 1;                      // case 4

        newWorld.setCell(x, y, cell);
      }
    }

    return newWorld;
  }

  getMinX() {
    return Math.min(...this.data.keys());
  }

  getMaxX() {
    return Math.max(...this.data.keys());
  }

  getMinY() {
    let arr = [];
    for (let set of this.data.values()) {
      arr.push(Math.min(...set.keys()));
    }
    return Math.min(...arr);
  }

  getMaxY() {
    let arr = [];
    for (let set of this.data.values()) {
      arr.push(Math.max(...set.keys()));
    }
    return Math.max(...arr);
  }

  getSize() {
    return {
      minX: this.getMinX(),
      maxX: this.getMaxX(),
      minY: this.getMinY(),
      maxY: this.getMaxY()
    }
  }
}

function getGeneration(cells, generations) {
  let oldWorld;
  let world = new World(cells);
  while (generations-- > 0) {
    oldWorld = world;
    world = world.newGeneration();
  }
  return world.getArray();
}