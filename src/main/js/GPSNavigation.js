/*
    GPS Navigation
    https://www.codewars.com/kata/5244ab738978478c1800002e

Your GPS is broken and you need to get somewhere!

Luckily you've got a map and your algorithm skills intact. Your map is a rather strange one too: all the intersections
are labeled with distinct integers and the roads connecting them are labeled with the time they take to drive in minutes.

You are standing at the intersection labeled start and your destination is the intersection labeled finish.

You will be provided the total number of intersections and an array of roads, each with the properties from, to (both
intersection labels; integers less than the number of intersections) and drivingTime. Roads may only be used to go from
from to to. If there is no road going the other way it is a one-way street.

Complete the function navigate(numberOfIntersections, roads, start, finish) so that it returns an array of intersections
on the fastest route from start to finish (including start and finish).

If there are several such routes, pick any. If there are no such routes, return null.

For example:
    var roads = [
        {from: 0, to: 1, drivingTime: 5},
        {from: 0, to: 2, drivingTime: 10},
        {from: 1, to: 2, drivingTime: 10},
        {from: 1, to: 3, drivingTime: 2},
        {from: 2, to: 3, drivingTime: 2},
        {from: 2, to: 4, drivingTime: 5},
        {from: 3, to: 2, drivingTime: 2},
        {from: 3, to: 4, drivingTime: 10}
    ];
    navigate(5, roads, 0, 4);
    // should return [0, 1, 3, 2, 4]. Fastest time is 5 + 2 + 2 + 5 = 14 minutes
*/

function navigate(numberOfIntersections, roads, start, finish) {
  const nodes = [];
  for (let i = 0; i < numberOfIntersections; i++) {
    nodes.push({
      name: i,
      visited: false,
      time: Infinity,
      in: [],
      out: [],
      cameFrom: null
    });
  }

  roads.forEach(edge => {
    nodes[edge.from].out.push({
      node: nodes[edge.to],
      edgeTime: edge.drivingTime
    });
    nodes[edge.to].in.push({
      node: nodes[edge.from],
      edgeTime: edge.drivingTime
    });
  });

  let currNode = nodes[start];
  currNode.time = 0;
  while(true) {
    currNode.visited = true;

    // update adjacent nodes
    currNode.out
      .filter(v => !v.node.visited)
      .forEach(v => {
        if (v.node.time > currNode.time + v.edgeTime) {
          v.node.time = currNode.time + v.edgeTime;
          v.node.cameFrom = currNode;
        }
      });

    // closest non-visited node
    currNode = nodes
      .filter(v => !v.visited)
      .sort((a, b) => a.time - b.time)
      [0];

    if (!currNode || currNode.time === Infinity) {
      break;
    }
  }

  if (nodes[finish].time === Infinity) {
    return null;
  }

  const path = [];
  currNode = nodes[finish];
  while (currNode.cameFrom) {
    path.push(currNode.name);
    currNode = currNode.cameFrom;
  }
  path.push(start);

  return path.reverse();
}