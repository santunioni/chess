package chess.pieces;

import chess.board.path.BoardPathDirection;
import chess.board.path.BoardPathWalker;
import chess.plays.Displacement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knight extends Piece {

    public Knight(Color color) {
        super(color, Type.KNIGHT);
    }

    @Override
    public Set<Displacement> getValidMoves() {
        var movements = new HashSet<Displacement>();

        var walker = new BoardPathWalker(this.getPosition());

        for (var firstStepSize : List.of(2, 1)) {
            var lastStepSize = 3 - firstStepSize;
            for (var firstStepDirection : List.of(BoardPathDirection.HORIZONTAL_LEFT, BoardPathDirection.HORIZONTAL_RIGHT)) {
                walker.walk(firstStepSize, firstStepDirection).ifPresent(h -> {
                    h.walk(lastStepSize, BoardPathDirection.VERTICAL_UP).ifPresent(u -> movements.add(new Displacement(this.getPosition(), u.getPosition())));
                    h.walk(lastStepSize, BoardPathDirection.VERTICAL_DOWN).ifPresent(d -> movements.add(new Displacement(this.getPosition(), d.getPosition())));
                });
            }
        }

        return movements;
    }
}
