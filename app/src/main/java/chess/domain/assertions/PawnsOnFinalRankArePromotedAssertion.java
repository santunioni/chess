package chess.domain.assertions;


import chess.domain.board.ReadonlyBoard;
import chess.domain.grid.File;
import chess.domain.grid.Position;
import chess.domain.grid.Rank;
import chess.domain.pieces.Color;
import chess.domain.pieces.PieceSpecification;
import chess.domain.pieces.PieceType;

public class PawnsOnFinalRankArePromotedAssertion implements BoardAssertion {

  public boolean test(ReadonlyBoard board) {
    for (var color : Color.values()) {
      var pawn = new PieceSpecification(color, PieceType.PAWN);
      var rank = color == Color.WHITE ? Rank.EIGHT : Rank.ONE;
      for (var file : File.values()) {
        if (board.getPieceAt(new Position(file, rank), pawn).isPresent()) {
          return false;
        }
      }
    }
    return true;
  }
}
