package chess.game.plays;

import static chess.game.plays.PlayFunctions.isPositionThreatenedBy;

import chess.game.board.BoardHistory;
import chess.game.board.BoardState;
import chess.game.grid.BoardPath;
import chess.game.grid.BoardPathDirection;
import chess.game.grid.BoardPathReachabilityAnalyzer;
import chess.game.grid.Position;
import chess.game.pieces.Color;
import chess.game.pieces.King;
import chess.game.pieces.Piece;
import chess.game.pieces.Type;
import chess.game.plays.validation.CantCastleOnKingThatAlreadyMoved;
import chess.game.plays.validation.CantCastleOnRookThatAlreadyMoved;
import chess.game.plays.validation.CantCastleOverOccupiedSquares;
import chess.game.plays.validation.CantCastleToInvalidPosition;
import chess.game.plays.validation.CantCastleWhileInCheck;
import chess.game.plays.validation.CantCastleWhilePassingThroughCheck;
import chess.game.plays.validation.PlayValidationError;
import java.util.Iterator;
import java.util.Set;

public record Castle(Color color, Position to) implements Play {

  private Position getKingPosition(BoardState state) throws CantCastleOnKingThatAlreadyMoved {
    var kingPosition = King.initialPosition(this.color);
    var kingOptional = state.getPieceAt(kingPosition);
    if (kingOptional.isEmpty()
        || kingOptional.get().getType() != Type.KING
        || kingOptional.get().getColor() != this.color) {
      throw new CantCastleOnKingThatAlreadyMoved(this.color);
    }
    return kingPosition;
  }

  private Position getRookPosition(BoardState state)
      throws CantCastleToInvalidPosition, CantCastleOnRookThatAlreadyMoved {
    if ((this.color == Color.WHITE
        && !this.to.equals(new Position("a1"))
        && !new Position("h1").equals(this.to))
        || (this.color == Color.BLACK
        && !this.to.equals(new Position("a8"))
        && !new Position("h8").equals(this.to))) {
      throw new CantCastleToInvalidPosition(color, this.to);
    }

    var rookOptional = state.getPieceAt(this.to);
    if (rookOptional.isEmpty() || rookOptional.get().getType() != Type.ROOK
        || rookOptional.get().getColor() != this.color) {
      throw new CantCastleOnRookThatAlreadyMoved(this.color, this.to);
    }

    return this.to;
  }

  public Runnable validateAndGetAction(BoardState boardState, BoardHistory boardHistory)
      throws PlayValidationError {
    final Position rookPosition = this.getRookPosition(boardState);
    final Position kingPosition = this.getKingPosition(boardState);
    final BoardPathDirection direction = kingPosition.directionTo(rookPosition).orElseThrow();

    final Iterator<Position> kingPathIterator = new BoardPath(kingPosition, direction).iterator();
    final Position kingFirstStep = kingPathIterator.next();
    final Position kingSecondStep = kingPathIterator.next();

    final Piece king = boardState.getPieceAt(kingPosition).orElseThrow();
    final Piece rook = boardState.getPieceAt(rookPosition).orElseThrow();

    if (!new BoardPathReachabilityAnalyzer(boardState).isReachableWalkingInOneOfDirections(
        kingPosition, Set.of(direction), rookPosition)) {
      throw new CantCastleOverOccupiedSquares(this.color, this.to);
    }

    if (isPositionThreatenedBy(boardState, kingPosition, this.color.opposite())) {
      throw new CantCastleWhileInCheck(this.color);
    }

    if (isPositionThreatenedBy(boardState, kingFirstStep, this.color.opposite())) {
      throw new CantCastleWhilePassingThroughCheck(this.color, this.to);
    }

    for (Play oldPlay : boardHistory) {
      PlayDto oldPlayDto = oldPlay.toDto();
      if (oldPlayDto.getFrom().equals(kingPosition)) {
        throw new CantCastleOnKingThatAlreadyMoved(this.color);
      }
      if (oldPlayDto.getFrom().equals(rookPosition)) {
        throw new CantCastleOnRookThatAlreadyMoved(this.color, rookPosition);
      }
    }

    return () -> {
      boardState.removePieceFromSquare(kingPosition);
      boardState.removePieceFromSquare(rookPosition);
      boardState.placePiece(kingFirstStep, rook);
      boardState.placePiece(kingSecondStep, king);
      boardHistory.push(this);
    };
  }

  public Color getPlayerColor() {
    return this.color;
  }

  public PlayDto toDto() {
    return new PlayDto() {
      public PlayName getName() {
        return PlayName.CASTLE;
      }

      public Position getFrom() {
        return King.initialPosition(Castle.this.color);
      }

      public Position getTo() {
        return Castle.this.to;
      }
    };
  }
}
