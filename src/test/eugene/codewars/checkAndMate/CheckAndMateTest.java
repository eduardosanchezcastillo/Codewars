package eugene.codewars.checkAndMate;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CheckAndMateTest {

    /*    SAMPLE TESTS    */

    @Test
    public void test_isCheck_PawnThreatensKing() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("pawn", 1, 5, 6)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Collections.singletonList(game[2]));
        assertEquals("Pawn threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_RookThreatensKing() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("rook", 1, 4, 1)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Collections.singletonList(game[2]));
        assertEquals("Rook threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_KnightThreatensKing() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("knight", 1, 2, 6)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Collections.singletonList(game[2]));
        assertEquals("Knight threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_BishopThreatensKing() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("bishop", 1, 0, 3)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Collections.singletonList(game[2]));
        assertEquals("Bishop threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_QueenThreatensKing1() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("queen", 1, 4, 1)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Collections.singletonList(game[2]));
        assertEquals("Queen threatens king", expected, CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test_isCheck_QueenThreatensKing2() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("queen", 1, 7, 4)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Collections.singletonList(game[2]));
        assertEquals("Queen threatens king", expected, CheckAndMate.isCheck(game, 0));
    }


    @Test
    public void test_isCheck_DoubleThreat() {
        PieceConfig[] game = new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                new PieceConfig("pawn", 0, 4, 6),
                new PieceConfig("pawn", 0, 5, 6),
                new PieceConfig("king", 0, 4, 7),
                new PieceConfig("bishop", 0, 5, 7),
                new PieceConfig("bishop", 1, 1, 4),
                new PieceConfig("rook", 1, 2, 7, 2, 5)};
        OutputBoard.print(game);
        Set<PieceConfig> expected = new HashSet<>(Arrays.asList(game[5], game[6]));
        assertEquals("Double threat", expected, CheckAndMate.isCheck(game, 0));
    }


    /*    TEST CASES    */


    private PieceConfig[][] positions = new PieceConfig[][]{
            // 0: initial configuration
            new PieceConfig[]{new PieceConfig("rook", 1, 0, 0),
                    new PieceConfig("knight", 1, 1, 0),
                    new PieceConfig("bishop", 1, 2, 0),
                    new PieceConfig("queen", 1, 3, 0),
                    new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("bishop", 1, 5, 0),
                    new PieceConfig("knight", 1, 6, 0),
                    new PieceConfig("rook", 1, 7, 0),
                    new PieceConfig("pawn", 1, 0, 1),
                    new PieceConfig("pawn", 1, 1, 1),
                    new PieceConfig("pawn", 1, 2, 1),
                    new PieceConfig("pawn", 1, 3, 1),
                    new PieceConfig("pawn", 1, 4, 1),
                    new PieceConfig("pawn", 1, 5, 1),
                    new PieceConfig("pawn", 1, 6, 1),
                    new PieceConfig("pawn", 1, 7, 1),
                    new PieceConfig("pawn", 0, 0, 6),
                    new PieceConfig("pawn", 0, 1, 6),
                    new PieceConfig("pawn", 0, 2, 6),
                    new PieceConfig("pawn", 0, 3, 6),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("pawn", 0, 6, 6),
                    new PieceConfig("pawn", 0, 7, 6),
                    new PieceConfig("rook", 0, 0, 7),
                    new PieceConfig("knight", 0, 1, 7),
                    new PieceConfig("bishop", 0, 2, 7),
                    new PieceConfig("queen", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("knight", 0, 6, 7),
                    new PieceConfig("rook", 0, 7, 7)},
            // 1: (reduced) non-mate fool
            new PieceConfig[]{new PieceConfig("pawn", 0, 6, 4),
                    new PieceConfig("pawn", 0, 5, 5),
                    new PieceConfig("pawn", 0, 3, 6),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 7, 6),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("knight", 0, 6, 7),
                    new PieceConfig("rook", 0, 7, 7),
                    new PieceConfig("queen", 1, 7, 4, 3, 0),
                    new PieceConfig("king", 1, 4, 0)},
            // 2: (reduced) fool's mate
            new PieceConfig[]{new PieceConfig("pawn", 0, 6, 4),
                    new PieceConfig("pawn", 0, 5, 5),
                    new PieceConfig("pawn", 0, 3, 6),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 7, 6),
                    new PieceConfig("queen", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("knight", 0, 6, 7),
                    new PieceConfig("rook", 0, 7, 7),
                    new PieceConfig("queen", 1, 7, 4, 3, 0),
                    new PieceConfig("king", 1, 4, 0)},
            // 3: (reduced) smothered mate, king's pawn
            new PieceConfig[]{new PieceConfig("pawn", 0, 4, 4),
                    new PieceConfig("knight", 0, 2, 5),
                    new PieceConfig("pawn", 0, 6, 5),
                    new PieceConfig("knight", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("queen", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("knight", 1, 5, 5, 3, 4),
                    new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("pawn", 1, 4, 3)},
            // 4: (reduced) smothered mate, queen's pawn
            new PieceConfig[]{new PieceConfig("knight", 0, 3, 6),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("queen", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("queen", 1, 4, 1),
                    new PieceConfig("knight", 1, 3, 5, 2, 3)},
            // 5: (reduced) Legall's mate
            new PieceConfig[]{new PieceConfig("queen", 1, 3, 0),
                    new PieceConfig("bishop", 1, 5, 0),
                    new PieceConfig("king", 1, 4, 1),
                    new PieceConfig("pawn", 1, 3, 2),
                    new PieceConfig("bishop", 0, 5, 1),
                    new PieceConfig("knight", 0, 3, 3, 2, 5),
                    new PieceConfig("knight", 0, 4, 3),
                    new PieceConfig("king", 0, 4, 7)},
            // 6: intercept test 1 (non-mate)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("bishop", 1, 1, 4, 3, 2),
                    new PieceConfig("queen", 1, 0, 7),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("rook", 0, 1, 7),
                    new PieceConfig("bishop", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("rook", 0, 5, 7)},
            // 7: intercept test 2 (mate)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("bishop", 1, 1, 4, 3, 2),
                    new PieceConfig("queen", 1, 0, 7),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("rook", 0, 1, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("rook", 0, 5, 7),
                    new PieceConfig("rook", 1, 3, 4)},
            // 8: intercept test 3 (non-mate)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("bishop", 1, 1, 4, 3, 2),
                    new PieceConfig("queen", 1, 0, 7),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("knight", 0, 1, 7),
                    new PieceConfig("bishop", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("rook", 0, 5, 7)},
            // 9: intercept test 4 (pawn move)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("bishop", 1, 1, 4, 3, 2),
                    new PieceConfig("queen", 1, 0, 7),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("bishop", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("queen", 0, 5, 7),
                    new PieceConfig("pawn", 0, 2, 6)},
            // 10: intercept test 4 (pawn double move)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("bishop", 1, 0, 3, 3, 0),
                    new PieceConfig("queen", 1, 0, 7),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("rook", 0, 3, 7),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("pawn", 0, 1, 6)},
            // 11: intercept test 5 (king captures piece)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("rook", 0, 5, 7),
                    new PieceConfig("rook", 1, 3, 7, 3, 0)},
            // 12: intercept test 6 (king can't capture)
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("rook", 0, 5, 7),
                    new PieceConfig("queen", 1, 3, 7, 2, 6),
                    new PieceConfig("rook", 1, 3, 6)},
            // 13: double threat
            new PieceConfig[]{new PieceConfig("king", 1, 4, 0),
                    new PieceConfig("pawn", 0, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("bishop", 0, 5, 7),
                    new PieceConfig("bishop", 1, 1, 4),
                    new PieceConfig("rook", 1, 2, 7, 2, 5)},
            // 14: en passant
            new PieceConfig[]{new PieceConfig("king", 1, 5, 3),
                    new PieceConfig("pawn", 0, 4, 4, 4, 6),
                    new PieceConfig("pawn", 0, 5, 6),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("knight", 0, 2, 5),
                    new PieceConfig("pawn", 1, 3, 4),
                    new PieceConfig("knight", 1, 3, 3),
                    new PieceConfig("pawn", 1, 4, 3),
                    new PieceConfig("bishop", 1, 4, 2),
                    new PieceConfig("rook", 1, 5, 2),
                    new PieceConfig("queen", 0, 6, 5)},
            // 15: en passant causes mate
            new PieceConfig[]{new PieceConfig("king", 1, 5, 3),
                    new PieceConfig("pawn", 0, 4, 4, 4, 6),
                    new PieceConfig("rook", 0, 5, 6),
                    new PieceConfig("king", 0, 4, 7),
                    new PieceConfig("knight", 0, 2, 5),
                    new PieceConfig("pawn", 1, 5, 4),
                    new PieceConfig("knight", 1, 3, 3),
                    new PieceConfig("pawn", 1, 4, 3),
                    new PieceConfig("bishop", 1, 4, 2),
                    new PieceConfig("rook", 1, 5, 2),
                    new PieceConfig("queen", 0, 6, 5)}
    };

    @Test
    public void test_InitialPosition() {
        int i = 0;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertEquals("Initial position should not be a check",
                new HashSet<PieceConfig>(),
                CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test1() {     // (reduced) non-mate fool
        int i = 1;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertEquals("Queen threatens king",
                new HashSet<>(Collections.singletonList(positions[1][9])),
                CheckAndMate.isCheck(game, 0));

        assertFalse("King can move to a safe tile",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test2() {     // (reduced) fool's mate
        int i = 2;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("Should be a mate for player 0",
                CheckAndMate.isMate(game, 0));

        assertFalse("Nothing threatens the king of player 1",
                CheckAndMate.isMate(game, 1));
    }

    @Test
    public void test3() {     // (reduced) smothered mate, king's pawn
        int i = 3;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("King cannot move to a safe tile",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test4() {     // (reduced) smothered mate, queen's pawn
        int i = 4;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("Pawn can't capture threatening knight because queen would have a clear line of sight",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test5() {     // (reduced) Legall's mate
        int i = 5;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("King can't capture bishop because of knight 1, knight 1 and bishop block free tiles",
                CheckAndMate.isMate(game, 1));
    }

    @Test
    public void test6() {     // intercept test 1 (non-mate)
        int i = 6;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertFalse("Should not be a mate for player 0",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test7() {     // intercept test 2 (mate)
        int i = 7;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("Should be a mate for player 0",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test8() {     // intercept test 3 (non-mate)
        int i = 8;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertFalse("Should not be a mate for player 0",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test9() {     // intercept test 3 (pawn move)
        int i = 9;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertFalse("Pawn should intercept by moving",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test10() {     // intercept test 3 (pawn double move)
        int i = 10;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertFalse("Pawn should intercept by double-moving",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test11() {     // intercept test 4 (king should capture)
        int i = 11;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertFalse("King should capture threatening piece",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test12() {     // king can't capture
        int i = 12;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("king can't capture",
                CheckAndMate.isMate(game, 0));
    }

    @Test
    public void test13() {     // double threat
        int i = 13;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertEquals("Double threat",
                new HashSet<>(Arrays.asList(positions[i][5], positions[i][6])),
                CheckAndMate.isCheck(game, 0));
    }

    @Test
    public void test14() {     // en-passant
        int i = 14;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertFalse("En passant",
                CheckAndMate.isMate(game, 1));
    }

    @Test
    public void test15() {     // en-passant would cause mate
        int i = 15;
        PieceConfig[] game = positions[i];
        OutputBoard.print(positions[i]);
        assertTrue("En passant would cause mate",
                CheckAndMate.isMate(game, 1));
    }
}