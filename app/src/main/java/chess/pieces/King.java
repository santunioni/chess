package chess.pieces;

import chess.board.path.BoardPath;
import chess.board.path.BoardPathDirection;
import chess.board.position.Position;
import chess.plays.Displacement;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(Color color) {
        super(color, Type.KING);
    }

    public boolean threatens(Position enemyPosition) {
        return this.board.getMyPosition().isNeighborTo(enemyPosition);
    }

    public Set<Displacement> getValidMoves() {
        var movements = new HashSet<Displacement>();

        for (var kingPathDirection : BoardPathDirection.allDirections()) {
            var path = new BoardPath(this.board.getMyPosition(), kingPathDirection);
            var targetPosition = path.getNextPosition();
            if (targetPosition.isPresent()) {
                var pieceAtTargetPosition = this.board.getPieceAt(targetPosition.get());
                if (pieceAtTargetPosition.isEmpty()) {
                    movements.add(new Displacement(this.board.getMyPosition(), targetPosition.get()));
                }
            }
        }

        return Collections.unmodifiableSet(movements);
    }
}
