package eugene.codewars;

/*
    Sortable Poker Hands
    https://www.codewars.com/kata/586423aa39c5abfcec0001e6

A famous casino is suddenly faced with a sharp decline of their revenues. They decide to offer Texas hold'em also online.
Can you help them by writing an algorithm that can rank poker hands?

TASK

Create a poker hand that has a constructor that accepts a string containing 5 cards:
    PokerHand hand = new PokerHand("KS 2H 5C JD TD");

The characteristics of the string of cards are:
    - A space is used as card seperator
    - Each card consists of two characters
    - The first character is the value of the card, valid characters are:
        2, 3, 4, 5, 6, 7, 8, 9, T(en), J(ack), Q(ueen), K(ing), A(ce)
    - The second character represents the suit, valid characters are:
        S(pades), H(earts), D(iamonds), C(lubs)

The poker hands must be sortable by rank, the highest rank first:
    ArrayList<PokerHand> hands = new ArrayList<PokerHand>();
    hands.add(new PokerHand("KS 2H 5C JD TD"));
    hands.add(new PokerHand("2C 3C AC 4C 5C"));
    Collections.sort(hands);

Apply the Texas Hold'em rules for ranking the cards.
There is no ranking for the suits.
An ace can either rank high or rank low in a straight or straight flush. Example of a straight with a low ace:
    "5C 4D 3C 2S AS"
*/

import java.util.*;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {

    public enum Result {TIE, WIN, LOSS;}

    private final List<Card> cards;

    private final String display;

    private final int sortedValues[];

    private final Combination combination;

    PokerHand(String hand) {
        this.display = hand;
        cards = Arrays.stream(hand.split(" "))
                .map(Card::new)
                .collect(Collectors.toList());

        CombinationDTO combinationDTO = Combination.findCombination(cards);
        combination = combinationDTO.combination;
        sortedValues = combinationDTO.values;
    }

    @Override
    public int compareTo(PokerHand o) {
        int c = combination.compareTo(o.combination);
        if (c < 0) {
            return -1;
        } else if (c > 0) {
            return 1;
        } else {
            // Both arrays will have identical length because the Hand Combination is the same.
            for (int i = 0; i < sortedValues.length; i++) {
                if (sortedValues[i] > o.sortedValues[i]) {
                    return -1;
                } else if (sortedValues[i] < o.sortedValues[i]) {
                    return 1;
                }
            }
            return 0;
        }
    }

    Result compareWith(PokerHand otherHand) {
        switch (compareTo(otherHand)) {
            case -1: return Result.WIN;
            case 1: return Result.LOSS;
            default: return Result.TIE;
        }
    }

    @Override
    public String toString() {
        return "PokerHand{" + display + " - " + combination + " - " + Arrays.toString(sortedValues) + '}';
    }
}

class CombinationDTO {

    final Combination combination;

    final int values[];

    CombinationDTO(Combination combination, int[] values) {
        this.combination = combination;
        this.values = values;
    }
}

@FunctionalInterface
interface CombinationTest {
    int[] call(Combination.CardsDenorm denorm);
}

enum Combination {
    STRAIGHT_OF_FLUSH(Combination::getStraightOfFlush),
    FOUR_OF_A_KIND(Combination::getFourOfAKind),
    FULL_HOUSE(Combination::getFullHouse),
    FLUSH(Combination::getFlush),
    STRAIGHT(Combination::getStraight),
    THREE_OF_A_KIND(Combination::getThreeOfAKind),
    TWO_PAIR(Combination::getTwoPair),
    ONE_PAIR(Combination::getOnePair),
    HIGH_CARD(Combination::getHighCard);

    private final CombinationTest getValues;

    Combination(CombinationTest getValues) {
        this.getValues = getValues;
    }

    static class CardsDenorm {
        final List<Card> sortedCards;
        final List<Card> sortedCardsAceLow;
        final Map<Integer, List<Card>> valueCountMap = new HashMap<>();
        final Map<Integer, List<Card>> suitCountMap = new HashMap<>();

        CardsDenorm(List<Card> cards) {
            sortedCards = new ArrayList<>(cards);
            Collections.sort(sortedCards);

            sortedCardsAceLow = cards.stream()
                    .map(Card::convertIfAce)
                    .sorted()
                    .collect(Collectors.toList());

            Map<Integer, List<Card>> mapByValue = cards.stream()
                    .collect(Collectors.groupingBy(Card::getValue));
            for (Map.Entry<Integer, List<Card>> entry : mapByValue.entrySet()) {
                valueCountMap.merge(
                        entry.getValue().size(),
                        entry.getValue(),
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        });
            }

            Map<Card.Suit, List<Card>> mapBySuit = cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit));
            for (Map.Entry<Card.Suit, List<Card>> entry : mapBySuit.entrySet()) {
                suitCountMap.put(entry.getValue().size(), entry.getValue());
            }
        }
    }

    static CombinationDTO findCombination(List<Card> cards) {
        CardsDenorm denorm = new CardsDenorm(cards);
        for (Combination combination : Combination.values()) {
            int values[] = combination.getValues.call(denorm);
            if (values != null) {
                return new CombinationDTO(combination, values);
            }
        }

        throw new RuntimeException("Can't determine hand combination.");
    }

    private static int[] getStraightOfFlush(CardsDenorm denorm) {
        return denorm.suitCountMap.get(5) == null ? null : getStraight(denorm);
    }

    private static int[] getFourOfAKind(CardsDenorm denorm) {
        return buildValueList(denorm.valueCountMap.get(4), 4, denorm.sortedCards);
    }

    private static int[] getFullHouse(CardsDenorm denorm) {
        List<Card> three = denorm.valueCountMap.get(3);
        List<Card> two = denorm.valueCountMap.get(2);
        if (three != null && two != null) {
            return new int[]{three.get(0).getValue(), two.get(0).getValue()};
        }
        return null;
    }

    private static int[] getFlush(CardsDenorm denorm) {
        return buildValueList(denorm.suitCountMap.get(5), 5, denorm.sortedCards);
    }

    private static int[] getStraight(CardsDenorm denorm) {
        if (isStraight(denorm.sortedCards)) {
            return new int[]{denorm.sortedCards.get(0).getValue()};
        } else if (isStraight(denorm.sortedCardsAceLow)) {
            return new int[]{denorm.sortedCardsAceLow.get(0).getValue()};
        }
        return null;
    }

    private static boolean isStraight(List<Card> cards) {
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getValue() != cards.get(i - 1).getValue() - 1) {
                return false;
            }
        }
        return true;
    }

    private static int[] getThreeOfAKind(CardsDenorm denorm) {
        Optional<int[]> x;
        return buildValueList(denorm.valueCountMap.get(3), 3, denorm.sortedCards);
    }

    private static int[] getTwoPair(CardsDenorm denorm) {
        return buildValueList(denorm.valueCountMap.get(2), 4, denorm.sortedCards);
    }

    private static int[] getOnePair(CardsDenorm denorm) {
        return buildValueList(denorm.valueCountMap.get(2), 2, denorm.sortedCards);
    }

    private static int[] getHighCard(CardsDenorm denorm) {
        return buildValueList(Collections.emptyList(), 0, denorm.sortedCards);
    }

    private static int[] buildValueList(List<Card> matchingCards, int matchingCount, List<Card> allCards) {
        if (matchingCards == null || matchingCards.size() != matchingCount) {
            return null;
        }

        List<Card> values = new ArrayList<>(allCards);
        values.removeAll(matchingCards);
        values.addAll(0, uniqueByValue(matchingCards));

        return values.stream()
                .mapToInt(Card::getValue)
                .toArray();
    }

    private static List<Card> uniqueByValue(List<Card> cards) {
        Set<Integer> values = new HashSet<>();
        List<Card> result = new ArrayList<>();
        for (Card card : cards) {
            if (!values.contains(card.getValue())) {
                result.add(card);
                values.add(card.getValue());
            }
        }
        Collections.sort(result);
        return result;
    }
}

class Card implements Comparable<Card> {

    private final int value;

    private final Suit suit;

    Card(String card) {
        value = parseValue(card.charAt(0));
        suit = Suit.of(card.charAt(1));
    }

    Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    Card convertIfAce() {
        if (value == 14) {
            return new Card(1, suit);
        } return this;
    }

    int getValue() {
        return value;
    }

    Suit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card o) {
        return -Integer.compare(value, o.value);  // descending
    }

    private int parseValue(char ch) {
        switch (ch) {
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
            default:
                int value = Integer.parseInt(String.valueOf(ch));
                if (value >= 2 && value <= 9) {
                    return value;
                }
        }
        throw new RuntimeException("Invalid card value: " + ch);
    }

    enum Suit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS;

        static Suit of(char ch) {
            switch (ch) {
                case 'S':
                    return SPADES;
                case 'H':
                    return HEARTS;
                case 'D':
                    return DIAMONDS;
                case 'C':
                    return CLUBS;
            }
            throw new RuntimeException("Invalid card suit: " + ch);
        }
    }
}