package chess.pieces;

import chess.board.Color;
import chess.board.path.BoardPath;
import chess.board.path.BoardPathOrientation;
import chess.board.position.File;
import chess.board.position.Movement;
import chess.board.position.Position;
import chess.board.position.Rank;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class King implements Piece {
    final Color color;
    Position position;

    King(Color color) {
        this.color = color;
        this.position = King.getInitialPosition(color);
    }

    King(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    private static Position getInitialPosition(Color color) {
        if (color == Color.WHITE) {
            return new Position(File.E, Rank.ONE);
        } else {
            return new Position(File.E, Rank.EIGHT);
        }
    }

    public Set<Movement> getValidMoves() {
        var movements = new HashSet<Movement>();

        var orientations = BoardPathOrientation.values();

        for (var orientation : orientations) {
            var path = new BoardPath(this.position, orientation);
            path.getNextPosition().ifPresent(position -> movements.add(new Movement(this.position, position)));
        }

        return Collections.unmodifiableSet(movements);
    }
}
