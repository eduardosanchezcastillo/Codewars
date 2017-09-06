/*
    Make a spiral
    https://www.codewars.com/kata/534e01fbbb17187c7e0000c6

Your task, is to create a NxN spiral with a given size.

For example, spiral with size 5 should look like this:

00000
....0
000.0
0...0
00000
and with the size 10:

0000000000
.........0
00000000.0
0......0.0
0.0000.0.0
0.0..0.0.0
0.0....0.0
0.000000.0
0........0
0000000000
Return value should contain array of arrays, of 0 and 1, for example for given size 5 result should be:

[[1,1,1,1,1],[0,0,0,0,1],[1,1,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
Because of the edge-cases for tiny spirals, the size will be at least 5.

General rule-of-a-thumb is, that the snake made with '1' cannot touch to itself.
*/

function turnRight(dir) {
  if (dir.dx === 1) {
    dir.dx = 0;
    dir.dy = 1;
  }
  else if (dir.dx === -1) {
    dir.dx = 0;
    dir.dy = -1;
  }
  else if (dir.dy === 1) {
    dir.dx = -1;
    dir.dy = 0;
  }
  else if (dir.dy === -1) {
    dir.dx = 1;
    dir.dy = 0;
  }
}

function getNext(data, pos, dir) {
  if (data[pos.y+dir.dy])
    return data[pos.y+dir.dy][pos.x+dir.dx];
}

function getNextNext(data, pos, dir) {
  if (data[pos.y+2*dir.dy])
    return data[pos.y+2*dir.dy][pos.x+2*dir.dx];
}

function spiralize(size) {
  const EMPTY = 0;
  const FILLED = 1;

  const data = [];
  for (let i = 0; i < size; i++) {
    const row = new Array(size);
    row.fill(EMPTY);
    data.push(row);
  }

  const dir = {
    dx: 1,
    dy: 0
  };

  const pos = {
    x: 0,
    y: 0
  }

  let length = 0;
  while (true) {
    data[pos.y][pos.x] = FILLED;
    length++;
    if (getNext(data, pos, dir) === undefined || getNextNext(data, pos, dir) === FILLED) {
      if (length <= 2) {
        break;
      }
      length = 0;
      turnRight(dir);
    }
    else {
      pos.x += dir.dx;
      pos.y += dir.dy;
    }
  }

  return data;
}