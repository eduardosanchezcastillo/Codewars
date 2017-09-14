package eugene.codewars.checkAndMate;

import java.util.*;
import java.util.stream.Stream;

class CheckAndMate {

    static Set<PieceConfig> isCheck(final PieceConfig[] arrPieces, int player) {
        Board board = new Board(arrPieces);
        return board.isCheck(player);
    }

    static boolean isMate(final PieceConfig[] arrPieces, int player) {
        Board board = new Board(arrPieces);
        return board.isMate(player);
    }
}

class Board {

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

    private static final Target[] LOOKUP_CONFIG = {
            Target.Factory.ranged("rook", MOVE_STRAIGHT),
            Target.Factory.ranged("bishop", MOVE_DIAGONAL),
            Target.Factory.ranged("queen", MOVE_STRAIGHT, MOVE_DIAGONAL),
            Target.Factory.melee("king", MOVE_STRAIGHT, MOVE_DIAGONAL),
            Target.Factory.melee("knight", MOVE_KNIGHT),
            Target.Factory.meleeForward("pawn", MOVE_DIAGONAL),
            // TODO: pawn simple movement
    };

    private final PieceConfig[] pieces;
    private final Map<Position, PieceConfig> pieceMap = new HashMap<>();

    Board(final PieceConfig[] pieces) {
        this.pieces = pieces;
        for (PieceConfig pc : pieces) {
            pieceMap.put(new Position(pc.getX(), pc.getY()), pc);
        }
    }

    Set<PieceConfig> isCheck(int player) {
        final PieceConfig king = findKing(pieces, player);
        final int opponent = 1 - player;

        Set<PieceConfig> result = new HashSet<>();
        for (Target target : LOOKUP_CONFIG) {
            for (Move move : target.moves) {
                findByMove(king.getX(), king.getY(), move, target, opponent)
                        .ifPresent(result::add);
            }
        }

        return result;
    }

    boolean isMate(int player) {
        Set<PieceConfig> threatPieces = isCheck(player);
        return threatPieces.size() > 1;
    }

    private Optional<PieceConfig> findByMove(int startX, int startY, Move move, Target target, int owner) {
        Position pos = new Position(startX, startY);
        int direction = owner == 0 ? 1 : -1;

        // move till we find ANY piece or reach the board end
        while (pos.apply(move, direction, target.isForwardOnly)) {
            PieceConfig piece = pieceMap.get(pos);
            if (piece != null) {
                if (piece.getOwner() == owner && Objects.equals(piece.getPiece(), target.piece)) {
                    return Optional.of(piece);
                }
                break;
            }

            // melee pieces can only move once at a time
            if (target.isMelee) {
                break;
            }
        }
        return Optional.empty();
    }

    private static PieceConfig findKing(final PieceConfig[] arrPieces, int player) {
        return Arrays.stream(arrPieces)
                .filter(p -> p.getOwner() == player && p.getPiece().equals("king"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find King of player " + player));
    }
}

class Target {
    final String piece;
    final boolean isMelee;
    final boolean isForwardOnly;
    final Move moves[];

    private Target(String piece, boolean melee, boolean forwardOnly, Move[]... moves) {
        this.piece = piece;
        this.isMelee = melee;
        this.isForwardOnly = forwardOnly;
        this.moves = Stream.of(moves)
                .flatMap(Stream::of)
                .toArray(Move[]::new);
    }

    static class Factory {
        static Target ranged(String piece, Move[]... moves) {
            return new Target(piece, false, false, moves);
        }

        static Target melee(String piece, Move[]... moves) {
            return new Target(piece, true, false, moves);
        }

        static Target meleeForward(String piece, Move[]... moves) {
            return new Target(piece, true, true, moves);
        }
    }
}

class Move {
    final int dX;
    final int dY;

    Move(int dX, int dY) {
        this.dX = dX;
        this.dY = dY;
    }
}

class Position {

    private static final int BOARD_SIZE = 8;

    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean apply(Move move, int direction, boolean forwardOnly) {
        if (forwardOnly && move.dY * direction < 0) {
            return false;
        }
        x += move.dX;
        y += move.dY;
        return validate();
    }

    private boolean validate() {
        return x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
