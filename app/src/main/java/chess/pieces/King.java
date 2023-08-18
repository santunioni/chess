package chess.pieces;

import chess.board.position.Position;

public class King extends Piece {

    public King(Color color) {
        super(color, Type.KING);
    }

    public boolean canMoveTo(Position position) {
        return this.board.getMyPosition().isNeighborTo(position);
    }

}
