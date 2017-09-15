package eugene.codewars.checkAndMate;

import java.util.function.Predicate;
import java.util.stream.Stream;

class Target {
    final String piece;
    final boolean isMelee;
    final boolean isForwardOnly;
    final Predicate<PieceConfig> pieceCondition;
    final Predicate<Position> positionCondition;
    final Move moves[];

    private Target(String piece, boolean melee, boolean forwardOnly, Predicate<PieceConfig> pieceCondition,
                   Predicate<Position> positionCondition, Move[]... moves) {
        this.piece = piece;
        this.isMelee = melee;
        this.isForwardOnly = forwardOnly;
        this.pieceCondition = pieceCondition;
        this.positionCondition = positionCondition;
        this.moves = Stream.of(moves)
                .flatMap(Stream::of)
                .toArray(Move[]::new);
    }

    static class Factory {
        static Target ranged(String piece, Move[]... moves) {
            return new Target(piece, false, false, null, null, moves);
        }

        static Target melee(String piece, Move[]... moves) {
            return new Target(piece, true, false, null, null, moves);
        }

        static Target meleeForward(String piece, Predicate<PieceConfig> pieceCondition,
                                   Predicate<Position> positionCondition, Move[]... moves) {
            return new Target(piece, true, true, pieceCondition, positionCondition, moves);
        }
    }
}
