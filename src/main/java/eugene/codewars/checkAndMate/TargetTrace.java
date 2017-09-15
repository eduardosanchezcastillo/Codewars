package eugene.codewars.checkAndMate;

import java.util.ArrayList;
import java.util.List;

class TargetTrace {
    PieceConfig piece;
    final List<Position> trace = new ArrayList<>();

    void addPosition(Position position) {
        trace.add(position);
    }
}
