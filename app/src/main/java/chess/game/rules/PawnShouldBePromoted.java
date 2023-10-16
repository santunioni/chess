package chess.game.rules;


import chess.game.board.Board;
import chess.game.board.pieces.Pawn;
import chess.game.board.pieces.PieceType;
import chess.game.grid.File;
import chess.game.grid.Position;
import chess.game.plays.Play;
import chess.game.plays.validation.PawnShouldBePromotedValidationError;

public class PawnShouldBePromoted {

  private PawnShouldBePromoted() {
  }

  public static void validateStateAfterPlay(Board board, Play play)
      throws PawnShouldBePromotedValidationError {
    for (var file : File.values()) {
      var position = new Position(file, Pawn.getPromotionRankFor(play.getPlayerColor()));
      var pieceOptional = board.getPieceAt(position);
      if (pieceOptional.isPresent()) {
        var piece = pieceOptional.get();
        if (piece.getSpecification().pieceType() == PieceType.PAWN) {
          throw new PawnShouldBePromotedValidationError(play.getPlayerColor());
        }
      }
    }
  }
}
