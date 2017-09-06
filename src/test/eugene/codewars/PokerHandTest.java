package eugene.codewars;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PokerHandTest {
    private PokerHand.Result loss = PokerHand.Result.LOSS;
    private PokerHand.Result win = PokerHand.Result.WIN;
    private PokerHand.Result tie = PokerHand.Result.TIE;

    @Test
    public void PokerHandRankingTest() {
        Test("Highest straight flush wins", loss, "2H 3H 4H 5H 6H", "KS AS TS QS JS");
        Test("Straight flush wins of 4 of a kind", win, "2H 3H 4H 5H 6H", "AS AD AC AH JD");
        Test("Highest 4 of a kind wins", win, "AS AH 2H AD AC", "JS JD JC JH 3D");
        Test("4 Of a kind wins of full house", loss, "2S AH 2H AS AC", "JS JD JC JH AD");
        Test("Full house wins of flush", win, "2S AH 2H AS AC", "2H 3H 5H 6H 7H");
        Test("Highest flush wins", win, "AS 3S 4S 8S 2S", "2H 3H 5H 6H 7H");
        Test("Flush wins of straight", win, "2H 3H 5H 6H 7H", "2S 3H 4H 5S 6C");
        Test("Equal straight is tie", tie, "2S 3H 4H 5S 6C", "3D 4C 5H 6H 2S");
        Test("Straight wins of three of a kind", win, "2S 3H 4H 5S 6C", "AH AC 5H 6H AS");
        Test("3 Of a kind wins of two pair", loss, "2S 2H 4H 5S 4C", "AH AC 5H 6H AS");
        Test("2 Pair wins of pair", win, "2S 2H 4H 5S 4C", "AH AC 5H 6H 7S");
        Test("Highest pair wins", loss, "6S AD 7H 4S AS", "AH AC 5H 6H 7S");
        Test("Pair wins of nothing", loss, "2S AH 4H 5S KC", "AH AC 5H 6H 7S");
        Test("Highest card loses", loss, "2S 3H 6H 7S 9C", "7H 3C TH 6H 9S");
        Test("Highest card wins", win, "4S 5H 6H TS AC", "3S 5H 6H TS AC");
        Test("Equal cards is tie", tie, "2S AH 4H 5S 6C", "AD 4C 5H 6H 2C");
    }

    @Test
    public void Test1() {
        Test("", win, "7C 7S KH 2H 7H", "7C 7S 3S 7H 5S");
    }

    @Test
    public void Test2() {
        Test("2 Pair wins of pair", win, "2S 2H 4H 5S 4C", "AH AC 5H 6H 7S");
    }

    @Test
    public void Test3() {
        Test("Ace low straight should win", win, "2D AC 3H 4H 5S", "AH AC 5H 6H AS");
    }

    @Test
    public void pokerHandSortTest() {
        // Arrange
        ArrayList<PokerHand> expected = new ArrayList<>();
        expected.add(new PokerHand("KS AS TS QS JS"));
        expected.add(new PokerHand("2H 3H 4H 5H 6H"));
        expected.add(new PokerHand("AS AD AC AH JD"));
        expected.add(new PokerHand("JS JD JC JH 3D"));
        expected.add(new PokerHand("2S AH 2H AS AC"));
        expected.add(new PokerHand("AS 3S 4S 8S 2S"));
        expected.add(new PokerHand("2H 3H 5H 6H 7H"));
        expected.add(new PokerHand("2S 3H 4H 5S 6C"));
        expected.add(new PokerHand("2D AC 3H 4H 5S"));
        expected.add(new PokerHand("AH AC 5H 6H AS"));
        expected.add(new PokerHand("2S 2H 4H 5S 4C"));
        expected.add(new PokerHand("AH AC 5H 6H 7S"));
        expected.add(new PokerHand("AH AC 4H 6H 7S"));
        expected.add(new PokerHand("2S AH 4H 5S KC"));
        expected.add(new PokerHand("2S 3H 6H 7S 9C"));

        Random random = new Random();
        ArrayList<PokerHand> actual = createRandomOrderedList(random, expected);

        // Act
        Collections.sort(actual);

        // Assert
        Iterator a = actual.iterator();
        for (PokerHand e : expected) {
            assertEquals(e, a.next());
        }
    }

    @Test
    public void pokerHardSortTest() {
        // Arrange
        ArrayList<PokerHand> expected = new ArrayList<>();
        expected.add(new PokerHand("JH AH TH KH QH"));
        expected.add(new PokerHand("JH 9H TH KH QH"));
        expected.add(new PokerHand("5C 6C 3C 7C 4C"));
        expected.add(new PokerHand("2D 6D 3D 4D 5D"));
        expected.add(new PokerHand("2C 3C AC 4C 5C"));
        expected.add(new PokerHand("AS AC AH KS AS"));
        expected.add(new PokerHand("JC 7H JS JD JH"));
        expected.add(new PokerHand("JC 6H JS JD JH"));
        expected.add(new PokerHand("KH KC 3S 3H 3D"));
        expected.add(new PokerHand("2H 2C 3S 3H 3D"));
        expected.add(new PokerHand("3D 2H 3H 2C 2D"));
        expected.add(new PokerHand("JH 8H AH KH QH"));
        expected.add(new PokerHand("4C 5C 9C 8C KC"));
        expected.add(new PokerHand("3S 8S 9S 5S KS"));
        expected.add(new PokerHand("8C 9C 5C 3C TC"));
        expected.add(new PokerHand("QC KH TS JS AH"));
        expected.add(new PokerHand("JS QS 9H TS KH"));
        expected.add(new PokerHand("6S 8S 7S 5H 9H"));
        expected.add(new PokerHand("3C 5C 4C 2C 6H"));
        expected.add(new PokerHand("2C 3H AS 4S 5H"));
        expected.add(new PokerHand("AC KH QH AH AS"));
        expected.add(new PokerHand("7C 7S KH 2H 7H"));
        expected.add(new PokerHand("7C 7S 3S 7H 5S"));
        expected.add(new PokerHand("6C 6S 3S 6H 5S"));
        expected.add(new PokerHand("3C KH 5D 5S KC"));
        expected.add(new PokerHand("5S 5D 2C KH KC"));
        expected.add(new PokerHand("3H 4C 4H 3S 2H"));
        expected.add(new PokerHand("AH 8S AH KC JH"));
        expected.add(new PokerHand("KD 4S KD 3H 8S"));
        expected.add(new PokerHand("KC 4H KS 2H 8D"));
        expected.add(new PokerHand("QH 8H KD JH 8S"));
        expected.add(new PokerHand("8C 4S KH JS 4D"));
        expected.add(new PokerHand("KS 8D 4D 9S 4S"));
        expected.add(new PokerHand("KD 6S 9D TH AD"));
        expected.add(new PokerHand("TS KS 5S 9S AC"));
        expected.add(new PokerHand("JH 8S TH AH QH"));
        expected.add(new PokerHand("TC 8C 2S JH 6C"));
        expected.add(new PokerHand("2D 6D 9D TH 7D"));
        expected.add(new PokerHand("9D 8H 2C 6S 7H"));
        expected.add(new PokerHand("4S 3H 2C 7S 5H"));

        List<PokerHand> actual = new ArrayList<>(expected);

        // Act
        Collections.sort(actual);

        // Show comparison
        System.out.println();
        System.out.println();
        for (int i = 0; i < expected.size(); i++) {
            System.out.println(String.format("%-60s - %-60s", expected.get(i), actual.get(i)));
        }

        // Assert
        Iterator a = actual.iterator();
        for (PokerHand e : expected) {
            assertEquals(e, a.next());
        }
    }

    private ArrayList<PokerHand> createRandomOrderedList(Random random, ArrayList<PokerHand> expected){
        ArrayList<PokerHand> actual = new ArrayList<>();
        for (PokerHand pokerHand : expected) {
            int j = random.nextInt(actual.size()+1);
            actual.add(j, pokerHand);
        }
        return actual;
    }

    private void Test(String description, PokerHand.Result expected, String playerHand, String opponentHand) {
        PokerHand player = new PokerHand(playerHand);
        PokerHand opponent = new PokerHand(opponentHand);
        assertEquals(description + ":", expected, player.compareWith(opponent));
    }
}
