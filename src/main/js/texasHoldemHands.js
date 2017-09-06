/*
    Texas Hold'em Hands
    https://www.codewars.com/kata/524c74f855025e2495000262

Texas Hold'em is a Poker variant in which each player is given two "hole cards". Players then proceed to make a series
of bets while five "community cards" are dealt. If there are more than one player remaining when the betting stops, a
showdown takes place in which players reveal their cards. Each player makes the best poker hand possible using five of
the seven available cards (community cards + the player's hole cards).

Possible hands are, in descending order of value:
    - Straight-flush (five consecutive ranks of the same suit). Higher rank is better.
    - Four-of-a-kind (four cards with the same rank). Tiebreaker is first the rank, then the rank of the remaining card.
    - Full house (three cards with the same rank, two with another). Tiebreaker is first the rank of the three cards,
      then rank of the pair.
    - Flush (five cards of the same suit). Higher ranks are better, compared from high to low rank.
    - Straight (five consecutive ranks). Higher rank is better.
    - Three-of-a-kind (three cards of the same rank). Tiebreaker is first the rank of the three cards, then the highest
      other rank, then the second highest other rank.
    - Two pair (two cards of the same rank, two cards of another rank). Tiebreaker is first the rank of the high pair,
      then the rank of the low pair and then the rank of the remaining card.
    - Pair (two cards of the same rank). Tiebreaker is first the rank of the two cards, then the three other ranks.
    - Nothing. Tiebreaker is the rank of the cards from high to low.

Given hole cards and community cards, complete the function hand to return the type of hand (as written above, you can
ignore case) and a list of ranks in decreasing order of significance, to use for comparison against other hands of the
same type, of the best possible hand.

    hand(["A♠", "A♦"], ["J♣", "5♥", "10♥", "2♥", "3♦"])
    // ...should return {type: "pair", ranks: ["A", "J", "10", "5"]}

    hand(["A♠", "K♦"], ["J♥", "5♥", "10♥", "Q♥", "3♥"])
    // ...should return {type: "flush", ranks: ["Q", "J", "10", "5", "3"]}

EDIT: for Straights with an Ace, only the ace-high straight is accepted. An ace-low straight is invalid
(ie. A,2,3,4,5 is invalid). This is consistent with the author's reference solution. ~docgunthrop
*/

const RANK_TO_NUMBER = {
  '2': 2,
  '3': 3,
  '4': 4,
  '5': 5,
  '6': 6,
  '7': 7,
  '8': 8,
  '9': 9,
  '10': 10,
  'J': 11,
  'Q': 12,
  'K': 13,
  'A': 14
};

function parseCard(card) {
  return {
    rank: card.slice(0, card.length - 1),
    suit: card[card.length - 1]
  };
}

function addToArrayInMap(map, key, value) {
  if (!map.has(key)) {
    map.set(key, []);
  }
  map.get(key).push(value);
}

function groupCards(cards) {
  const groups = {
    all: [],
    byRank: [],
    bySuit: []
  };

  const rankMap = new Map();
  const suitMap = new Map();
  cards.forEach(v => {
    const card = parseCard(v);
    groups.all.push(card);
    addToArrayInMap(rankMap, card.rank, card.suit);
    addToArrayInMap(suitMap, card.suit, card.rank);
  });

  const SORT_PREDICATE_BY_RANK_PROPERTY = (a, b) => RANK_TO_NUMBER[b.rank] - RANK_TO_NUMBER[a.rank];
  const SORT_PREDICATE_BY_RANK = (a, b) => RANK_TO_NUMBER[b] - RANK_TO_NUMBER[a];

  for (let pair of rankMap) {
    groups.byRank.push({
      rank: pair[0],
      suits: pair[1]
    });
  }
  groups.byRank.sort(SORT_PREDICATE_BY_RANK_PROPERTY);

  for (let pair of suitMap) {
    groups.bySuit.push({
      suit: pair[0],
      ranks: pair[1].sort(SORT_PREDICATE_BY_RANK)
    });
  }

  groups.all.sort(SORT_PREDICATE_BY_RANK_PROPERTY);

  return groups;
}

const parser = {
  getCombination(groups) {
    this.data = groups;
    const TESTERS = [
      this.testStraightFlush,
      this.testFourOfAKind,
      this.testFullHouse,
      this.testFlush,
      this.testStraight,
      this.testThreeOfAKind,
      this.testTwoPairs,
      this.testPair,
      this.testNothing
    ];

    for (let test of TESTERS) {
      const res = test.call(this);
      if (res) {
        return res;
      }
    }
  },

  buildRanksList(matchList, count) {
    const list = [].concat(matchList);

    this.data.all
      .filter(card => list.indexOf(card.rank) < 0)
      .slice(0, count - list.length)
      .forEach(card => list.push(card.rank));

    return list;
  },

  // STRAIGHT FLUSH
  testStraightFlush() {
    const halfMatch = this.data.bySuit
      .filter(v => v.ranks.length >= 5)
      [0];

    if (halfMatch) {
      var match = this.hasStraight(halfMatch.ranks, v => v);
      if (match) {
        return {
          type: 'straight-flush',
          ranks: this.buildRanksList(match, 5)
        };
      }
    }
  },

  // FOUR OF A KIND
  testFourOfAKind() {
    const match = this.data.byRank
      .filter(v => v.suits.length === 4)
      [0];

    if (match) {
      return {
        type: 'four-of-a-kind',
        ranks: this.buildRanksList([match.rank], 2)
      };
    }
  },

  // FULL HOUSE
  testFullHouse() {
    const match3 = this.data.byRank
      .filter(v => v.suits.length === 3)
      [0];

    const match2 = this.data.byRank
      .filter(v => v.suits.length === 2)
      [0];

    if (match3 && match2) {
      return {
        type: 'full house',
        ranks: this.buildRanksList([match3.rank, match2.rank], 2)
      };
    }
  },

  // FLUSH
  testFlush() {
    const match = this.data.bySuit
      .filter(v => v.ranks.length >= 5)
      [0];

    if (match) {
      return {
        type: 'flush',
        ranks: this.buildRanksList(match.ranks.slice(0, 5), 5)
      };
    }
  },

  // helper for 'straight'
  hasStraight(arr, getter) {
    if (arr.length < 5) return;

    for (let ii = 0; ii <= arr.length - 5; ii++) {
      let subArr = arr.slice(ii, ii + 5);
      let count = 0;
      for (let jj = 1; jj < 5; jj++) {
        const rankPrev = RANK_TO_NUMBER[getter(subArr[jj-1])];
        const rankCurr = RANK_TO_NUMBER[getter(subArr[jj])];
        if (rankPrev === rankCurr + 1) count++;
      }
      if (count === 4) {
        return subArr;
      }
    }
  },

  // STRAIGHT
  testStraight() {
    const arr = this.hasStraight(this.data.byRank, v => v.rank);
    if (arr) {
      return {
        type: 'straight',
        ranks: this.buildRanksList(arr.map(v => v.rank), 5)
      };
    }
  },

  // THREE OF A KIND
  testThreeOfAKind() {
    const match = this.data.byRank
      .filter(v => v.suits.length === 3)
      [0];

    if (match) {
      return {
        type: 'three-of-a-kind',
        ranks: this.buildRanksList([match.rank], 3)
      };
    }
  },

  // TWO PAIRS
  testTwoPairs() {
    const matches = this.data.byRank
      .filter(v => v.suits.length === 2)
      .slice(0, 2);

    if (matches && matches.length === 2) {
      return {
        type: 'two pair',
        ranks: this.buildRanksList(matches.map(v => v.rank), 3)
      };
    }
  },

  // PAIR
  testPair() {
    const match = this.data.byRank
      .filter(v => v.suits.length === 2)
      [0];

    if (match) {
      return {
        type: 'pair',
        ranks: this.buildRanksList([match.rank], 4)
      };
    }
  },

  // NOTHING
  testNothing() {
    return {
      type: 'nothing',
      ranks: this.buildRanksList([], 5)
    };
  }
};

function hand(holeCards, communityCards) {
  const allCards = holeCards.concat(communityCards);
  const groups = groupCards(allCards);

  return parser.getCombination(groups);
}