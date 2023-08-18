package chess.pieces;

import chess.board.position.Position;


public class Knight extends Piece {

    public Knight(Color color) {
        super(color, Type.KNIGHT);
    }


    public boolean canMoveTo(Position position) {
        var myPosition = this.board.getMyPosition();
        var horizontalDistance = Math.abs(myPosition.file().distanceTo(position.file()));
        var verticalDistance = Math.abs(myPosition.rank().distanceTo(position.rank()));
        return (horizontalDistance == 1 && verticalDistance == 2) || (horizontalDistance == 2 && verticalDistance == 1);
    }
}
