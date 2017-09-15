package eugene.codewars.checkAndMate;

import java.util.*;

class Board {

    private final PieceConfig[] piecesInitial;
    private final Map<Position, PieceConfig> pieceMap = new HashMap<>();

    private final UndoableMove undoable = new UndoableMove();

    Board(final PieceConfig[] pieces) {
        this.piecesInitial = pieces;
        for (PieceConfig pc : pieces) {
            pieceMap.put(new Position(pc.getX(), pc.getY()), pc);
        }
    }

    Set<TargetTrace> findKingThreat(int player) {
        final PieceConfig king = getKing(player);
        return findThreat(player, king.getX(), king.getY());
    }

    private Set<TargetTrace> findThreat(int player, int x, int y) {
        final int opponent = getOpponent(player);

        Set<TargetTrace> result = new HashSet<>();
        for (Target target : Rules.TARGET_CONFIG) {
            for (Move move : target.moves) {
                getTrace(x, y, move, target, opponent)
                        .ifPresent(result::add);
            }
        }

        return result;
    }

    boolean isMate(int player) {
        Set<TargetTrace> threatPieces = findKingThreat(player);
        return !threatPieces.isEmpty()
                && !isKingCanBeBlocked(player, threatPieces)
                && !isKingCanHide(player);
    }

    private boolean isKingCanBeBlocked(int player, Set<TargetTrace> threatPieces) {
        if (threatPieces.size() != 1) {
            return false;
        }

        // Try to kill the threatening piece, or block it.
        final TargetTrace threat = threatPieces.iterator().next();
        final PieceConfig king = getKing(player);
        final int opponent = getOpponent(player);

        for (Position threatPos : threat.trace) {
            // See if we can move some piece to this position. This will save the king
            Set<TargetTrace> saviorSet = findThreat(opponent, threatPos.x, threatPos.y);
            for (TargetTrace savior : saviorSet) {
                if (isSavior(savior.piece, threatPos.x, threatPos.y, king)) {
                    return true;  // the king can be saved!
                }
            }
        }

        // En passant (https://en.wikipedia.org/wiki/En_passant)
        if (isPawn(threat.piece) && justMadeDoubleMove(threat.piece)) {
            // the pawn just moved two ranks forward and can be intercepted by opponent's pawn
            int newX = threat.piece.getX();
            int newY = threat.piece.getY() + getDirection(player);
            PieceConfig left = pieceMap.get(new Position(threat.piece.getX() - 1, threat.piece.getY()));
            PieceConfig right = pieceMap.get(new Position(threat.piece.getX() + 1, threat.piece.getY()));
            if (isEnPassant(left, threat.piece, newX, newY, king) || isEnPassant(right, threat.piece, newX, newY, king)) {
                return true;
            }
        }

        return false;
    }

    private boolean justMadeDoubleMove(PieceConfig piece) {
        return piece.hasPrevious()
                && piece.getX() == piece.getPrevX()
                && Math.abs(piece.getY() - piece.getPrevY()) == 2;
    }

    private boolean isKingCanHide(int player) {
        final PieceConfig king = getKing(player);

        for (Move move : Rules.KING_CONFIG.moves) {
            final int newX = king.getX() + move.dX;
            final int newY = king.getY() + move.dY;
            if (isSavior(king, newX, newY, king)) {
                return true;
            }
        }

        return false;
    }

    private boolean isEnPassant(PieceConfig savior, PieceConfig threat, int x, int y, PieceConfig king) {
        if (savior == null || !isPawn(savior)) {
            return false;
        }

        boolean result = false;
        if (undoable.move(savior, x, y, threat.getX(), threat.getY())) {
            result = findThreat(king.getOwner(), king.getX(), king.getY()).isEmpty();
        }
        undoable.undo();

        return result;
    }

    private boolean isSavior(PieceConfig savior, int x, int y, PieceConfig king) {
        boolean result = false;

        if (undoable.move(savior, x, y)) {
            int kingX, kingY;
            if (isKing(savior)) {
                // the king has just moved; use the new position
                kingX = x;
                kingY = y;
            } else {
                kingX = king.getX();
                kingY = king.getY();
            }
            result = findThreat(king.getOwner(), kingX, kingY).isEmpty();
        }
        undoable.undo();

        return result;
    }

    private Optional<TargetTrace> getTrace(int startX, int startY, Move move, Target target, int owner) {
        Position pos = new Position(startX, startY);
        TargetTrace trace = new TargetTrace();

        // check special limitations
        if (target.isForwardOnly) {
            if (move.dY * getDirection(owner) > 0) {  /* we need an opposite direction here because we are moving
                                                         backward towards a possible enemy */
                return Optional.empty();
            }
        }
        if (target.pieceCondition != null) {
            PieceConfig startPiece = pieceMap.get(pos);
            if (!target.pieceCondition.test(startPiece)) {
                return Optional.empty();
            }
        }

        // move till we find ANY piece or reach the board end
        while (pos.apply(move)) {
            trace.addPosition(pos.copy());

            if (target.positionCondition == null || target.positionCondition.test(pos)) {
                PieceConfig piece = pieceMap.get(pos);
                if (piece != null) {
                    if (piece.getOwner() == owner && Objects.equals(piece.getPiece(), target.piece)) {
                        trace.piece = piece;
                        return Optional.of(trace);
                    }
                    break;
                }
            }

            // melee pieces can only move once at a time
            if (target.isMelee) {
                break;
            }
        }
        return Optional.empty();
    }

    private PieceConfig getKing(int player) {
        return Arrays.stream(piecesInitial)
                .filter(p -> p.getOwner() == player && isKing(p))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find King of player " + player));
    }

    private boolean isKing(PieceConfig piece) {
        return piece.getPiece().equals("king");
    }

    private boolean isPawn(PieceConfig piece) {
        return piece.getPiece().equals("pawn");
    }

    private int getOpponent(int player) {
        return 1 - player;
    }

    private int getDirection(int player) {
        return player == 0 ? -1 : 1;
    }

    class UndoableMove {
        private boolean moved = false;

        private Position oldPos;
        private Position newPos;
        private Position killedPos;

        private PieceConfig killedPiece;
        private PieceConfig movedPiece;

        boolean move(PieceConfig piece, int newX, int newY, int killedX, int killedY) {
            if (moved) {
                throw new RuntimeException("Can't apply more than one move");
            }

            oldPos = new Position(piece.getX(), piece.getY());
            newPos = new Position(newX, newY);
            killedPos = new Position(killedX, killedY);
            if (!newPos.isValid() || !killedPos.isValid()) {
                return false;
            }

            movedPiece = piece;
            killedPiece = pieceMap.get(killedPos);     // if there is no piece, then "null" is just fine
            if (killedPiece != null && killedPiece.getOwner() == piece.getOwner()) {
                return false;   // can't kill our own piece
            }

            pieceMap.remove(oldPos);
            pieceMap.remove(killedPos);
            pieceMap.put(newPos,
                    new PieceConfig(
                            piece.getPiece(),
                            piece.getOwner(),
                            newX, newY,
                            piece.getX(), piece.getY()
                    )
            );

            moved = true;
            return true;
        }

        boolean move(PieceConfig piece, int newX, int newY) {
            return move(piece, newX, newY, newX, newY);
        }

        void undo() {
            if (!moved) {
                return;
            }
            moved = false;

            // redo the map changes
            pieceMap.put(oldPos, movedPiece);
            pieceMap.remove(newPos);
            pieceMap.remove(killedPos);
            if (killedPiece != null) {
                pieceMap.put(killedPos, killedPiece);
            }
        }
    }
}
