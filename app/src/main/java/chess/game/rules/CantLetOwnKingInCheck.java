package chess.game.rules;

import chess.game.board.BoardState;
import chess.game.grid.Position;
import chess.game.pieces.Color;
import chess.game.pieces.PieceProperties;
import chess.game.pieces.Type;
import chess.game.plays.Play;
import chess.game.rules.validation.IlegalPlay;

import java.util.Optional;


public class CantLetOwnKingInCheck {


    private static Optional<Position> findKing(BoardState state, Color color) {
        var possiblePositionsForKing = state.findPositionsWithPiece(new PieceProperties() {
            public Color getColor() {
                return color;
            }

            public Type getType() {
                return Type.KING;
            }
        });
        if (possiblePositionsForKing.size() != 1) {
            return Optional.empty();
        }
        Position ownKingPosition = possiblePositionsForKing.get(0);
        return Optional.of(ownKingPosition);
    }

    public static void validateStateAfterPlay(
            BoardState state,
            Play play
    ) throws IlegalPlay {
        var possiblePositionsForKing = findKing(state, play.getPlayerColor());
        if (possiblePositionsForKing.isEmpty()) {
            return;
        }
        Position ownKingPosition = possiblePositionsForKing.get();

        var enemyPieces = state.getPlayerPieces(play.getPlayerColor().opposite());
        for (var enemyPiece : enemyPieces) {
            if (enemyPiece.couldCaptureEnemyAt(ownKingPosition)) {
                throw new chess.game.rules.validation.CantLetOwnKingInCheck(play.getPlayerColor(), ownKingPosition, enemyPiece);
            }
        }
    }
}