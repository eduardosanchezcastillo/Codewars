package eugene.codewars.checkAndMate;

import java.util.Arrays;
import java.util.Objects;

class Rules {

    private static final Move[] MOVE_VERTICAL = {
            new Move(0, 1),
            new Move(0, -1),
    };

    private static final Move[] MOVE_DOUBLE_VERTICAL = {
            new Move(0, 2),
            new Move(0, -2),
    };

    private static final Move[] MOVE_STRAIGHT = {
            new Move(0, 1),
            new Move(0, -1),
            new Move(1, 0),
            new Move(-1, 0),
    };

    private static final Move[] MOVE_DIAGONAL = {
            new Move(1, 1),
            new Move(1, -1),
            new Move(-1, 1),
            new Move(-1, -1),
    };

    private static final Move[] MOVE_KNIGHT = {
            new Move(1, 2),
            new Move(2, 1),
            new Move(2, -1),
            new Move(1, -2),
            new Move(-1, 2),
            new Move(-2, 1),
            new Move(-2, -1),
            new Move(-1, -2),
    };

    static final Target[] TARGET_CONFIG = {
            Target.Factory.ranged("rook", MOVE_STRAIGHT),
            Target.Factory.ranged("bishop", MOVE_DIAGONAL),
            Target.Factory.ranged("queen", MOVE_STRAIGHT, MOVE_DIAGONAL),
            Target.Factory.melee("king", MOVE_STRAIGHT, MOVE_DIAGONAL),
            Target.Factory.melee("knight", MOVE_KNIGHT),
            Target.Factory.meleeForward("pawn", Objects::nonNull, null, MOVE_DIAGONAL),
            Target.Factory.meleeForward("pawn", Objects::isNull, null, MOVE_VERTICAL),
            Target.Factory.meleeForward("pawn", Objects::isNull,
                    pos -> pos.y == 1 || pos.y == Position.BOARD_SIZE - 2,
                    MOVE_DOUBLE_VERTICAL),
    };

    static final Target KING_CONFIG;

    static {
        KING_CONFIG = Arrays.stream(TARGET_CONFIG)
                .filter(t -> t.piece.equals("king"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Config for king is mandatory"));
    }
}
