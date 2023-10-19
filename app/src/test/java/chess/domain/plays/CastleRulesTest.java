package chess.domain.plays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chess.domain.board.Board;
import chess.domain.board.BoardInitializer;
import chess.domain.grid.Position;
import chess.domain.pieces.Color;
import chess.domain.pieces.King;
import chess.domain.pieces.PieceFactory;
import chess.domain.pieces.PieceSpecification;
import chess.domain.pieces.PieceType;
import chess.domain.plays.validation.PlayValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CastleRulesTest {
  private final PieceFactory pieceFactory = new PieceFactory();
  private Board board;

  @BeforeEach
  void setUp() {
    this.board = new BoardInitializer().placePiecesOf(PieceType.KING).placePiecesOf(PieceType.ROOK)
        .getBoard();
  }

  @Test
  void shouldMoveKingTwoSpacesRightAndPutRookOnItsLeft() throws PlayValidationError {
    // Given
    var castle = new Castle(Color.WHITE, new Position("h1"));

    // When
    board.makePlay(castle);

    // Then
    assertTrue(board.getPieceAt(King.initialPositionFor(Color.WHITE)).isEmpty());
    assertTrue(board.getPieceAt("h1").isEmpty());
    assertTrue(
        board
            .getPieceAt(new Position("g1"), new PieceSpecification(Color.WHITE, PieceType.KING))
            .isPresent()
    );
    assertTrue(
        board
            .getPieceAt(new Position("f1"), new PieceSpecification(Color.WHITE, PieceType.ROOK))
            .isPresent()
    );
  }

  @Test
  void shouldFailWhenKingAlreadyMoved() throws PlayValidationError {
    // Given
    board.makePlay(new Move(Color.WHITE, new Position("e1"), new Position("e2")));
    board.makePlay(new Move(Color.BLACK, new Position("e8"), new Position("e7")));
    board.makePlay(new Move(Color.WHITE, new Position("e2"), new Position("e1")));
    board.makePlay(new Move(Color.BLACK, new Position("e7"), new Position("e8")));

    // When
    var castle = new Castle(Color.WHITE, new Position("h1"));

    // Then
    assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
  }

  @Test
  void shouldFailWhenRookAlreadyMoved() throws PlayValidationError {
    // Given
    board.makePlay(new Move(Color.WHITE, new Position("h1"), new Position("h2")));
    board.makePlay(new Move(Color.BLACK, new Position("h8"), new Position("h7")));
    board.makePlay(new Move(Color.WHITE, new Position("h2"), new Position("h1")));
    board.makePlay(new Move(Color.BLACK, new Position("h7"), new Position("h8")));

    // When
    var castle = new Castle(Color.WHITE, new Position("h1"));

    // Then
    assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
  }

  @Test
  void shouldFailIfKingIsInCheck() {
    // Given
    board.placePiece("e2", this.pieceFactory.createRooks(Color.BLACK).get(0));

    // When
    var castle = new Castle(Color.WHITE, new Position("h1"));

    // Then
    assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
  }

  @Test
  void shouldFailIfPathBetweenKingAndRookIsBlocked() {
    // Given
    board.placePiece("f1", this.pieceFactory.createBishops(Color.WHITE).get(0));

    // When
    var castle = new Castle(Color.WHITE, new Position("h1"));

    // Then
    assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
  }

  @Test
  void shouldFailIfKingsPathIsThreatened() {
    // Given
    board.placePiece("f2", this.pieceFactory.createRooks(Color.BLACK).get(0));

    // When
    var castle = new Castle(Color.WHITE, new Position("h1"));

    // Then
    assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
  }
}