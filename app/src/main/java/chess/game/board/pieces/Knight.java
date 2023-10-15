package chess.game.board.pieces;

import chess.game.grid.Position;
import chess.game.plays.Capture;
import chess.game.plays.Move;
import chess.game.plays.Play;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Knight extends Piece {

  public Knight(Color color) {
    super(color, PieceType.KNIGHT);
  }


  public boolean couldMoveToIfEmpty(Position position) {
    var myPosition = this.currentPosition();
    var horizontalDistance = Math.abs(myPosition.file().distanceTo(position.file()));
    var verticalDistance = Math.abs(myPosition.rank().distanceTo(position.rank()));
    return (horizontalDistance == 1 && verticalDistance == 2)
        || (horizontalDistance == 2 && verticalDistance == 1);
  }

  protected Set<Play> getPossiblePlays() {
    var plays = new HashSet<Play>();

    for (var horizontalJump : List.of(-2, -1, 1, 2)) {
      for (var verticalJump : List.of(-2, -1, 1, 2)) {
        var targetPositionIndex =
            this.currentPosition().toIndex() + horizontalJump + 8 * verticalJump;
        if (0 <= targetPositionIndex && targetPositionIndex <= 63) {
          var targetPosition = Position.fromIndex(targetPositionIndex);
          if (this.couldMoveToIfEmpty(targetPosition)) {
            plays.add(new Move(this.getSpecification().color(), this.currentPosition(),
                targetPosition));
            plays.add(new Capture(this.getSpecification().color(), this.currentPosition(),
                targetPosition));
          }
        }
      }
    }

    return plays;
  }

  public Knight copy() {
    return new Knight(this.getSpecification().color());
  }
}
