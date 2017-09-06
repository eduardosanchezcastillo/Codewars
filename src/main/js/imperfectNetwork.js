/*
    Imperfect Network #2 (out-of-order messages)
    https://www.codewars.com/kata/52094b0673c32d3fc5000065

You are given the outline of a network client. The network client as it is assumes that the network will never corrupt
any data, will never lose any data, will never duplicate any data, and will always deliver data in the order it was sent.

For the purposes of this Kata, assume that the network will never corrupt any data, will never lose any data, but that
it may not deliver data in the order it was sent, and that it may send duplicate data. Ensure that the callback function
is invoked with the data in-order and without duplicates. Note: there will be two instances of the network client. You
should not try to coordinate order between the clients. You should only guarantee that client B invokes its callback in
the same order that client A sends and vice-versa.

The data sent into the network client's send() method will always be a string. The data sent into the sendFunction must
also be a string. That data eventually will be received by the peer one or more times in this Kata.

Here is an example of a perfect network that would use your network class:
    function PerfectNetwork(callbackA, callbackB) {
        var network = this;
        this.clientA = new NetworkClient(
            function (data) { network.clientB.recv(data); }, callbackA);
        this.clientB = new NetworkClient(
            function (data) { network.clientA.recv(data); }, callbackB);
    };

    var network = new PerfectNetwork(
        function (data) { console.log("CLIENT-A Got: " + data); },
        function (data) { console.log("CLIENT-B Got: " + data); }
    );

    network.clientA.send("abcd");
    network.clientA.send("wxyz");
    network.clientB.send("1234");
    network.clientA.send("EOF");

This would output:
    CLIENT-B Got: abcd
    CLIENT-B Got: wxyz
    CLIENT-A Got: 1234
    CLIENT-B Got: EOF

In this Kata, the naive client outline could very well produce the following output with a less-than-perfect network for
that same sequence of send calls:
    CLIENT-B Got: abcd
    CLIENT-A Got: 1234
    CLIENT-A Got: 1234
    CLIENT-B Got: EOF
    CLIENT-B Got: wxyz
    CLIENT-B Got: EOF
*/

function NetworkClient (sendFunction, callback) {
    this.sendFunction = sendFunction;
    this.callback = callback;
    this.outId = 0;
    this.inId = 0;
    this.waitingMessages = new Map();
    this.receivedIds = new Set();
}

NetworkClient.prototype.send = function (data) {
  const message = `${this.outId}_${data}`;
  this.outId++;
  this.sendFunction(message);
};

NetworkClient.prototype.recv = function (data) {
  const id = parseInt(data);
  const message = data.replace(/\d+_/, '');

  if (this.receivedIds.has(id)) {
    return;
  }
  this.receivedIds.add(id);

  if (id === this.inId) {
    this.callback(message);
    this.inId++;

    while (this.waitingMessages.has(this.inId)) {
      this.callback(this.waitingMessages.get(this.inId));
      this.waitingMessages.delete(this.inId);
      this.inId++;
    }
  }
  else {
    this.waitingMessages.set(id, message);
  }
};