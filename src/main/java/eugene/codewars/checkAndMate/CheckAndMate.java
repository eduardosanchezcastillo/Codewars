package eugene.codewars.checkAndMate;

import java.util.Set;
import java.util.stream.Collectors;

class CheckAndMate {

    static Set<PieceConfig> isCheck(final PieceConfig[] arrPieces, int player) {
        return new Board(arrPieces)
                .findKingThreat(player)
                .stream()
                .map(tt -> tt.piece)
                .collect(Collectors.toSet());
    }

    static boolean isMate(final PieceConfig[] arrPieces, int player) {
        return new Board(arrPieces)
                .isMate(player);
    }
}
