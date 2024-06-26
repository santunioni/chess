package chess.domain.play;

import chess.domain.board.*;
import chess.domain.grid.Position;
import chess.domain.play.validation.PlayValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        var castle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);

        // When
        board.makePlay(castle);

        // Then
        assertTrue(board.getPieceAt(PieceColor.WHITE.kingInitialPosition()).isEmpty());
        assertTrue(board.getPieceAt("h1").isEmpty());
        assertTrue(board.getPieceAt(new Position("g1"), PieceColor.WHITE, PieceType.KING).isPresent());
        assertTrue(board.getPieceAt(new Position("f1"), PieceColor.WHITE, PieceType.ROOK).isPresent());
    }

    @Test
    void shouldFailWhenKingAlreadyMoved() throws PlayValidationError {
        // Given
        board.makePlay(
                new Move(PieceType.KING, PieceColor.WHITE, new Position("e1"), new Position("e2")));
        board.makePlay(
                new Move(PieceType.KING, PieceColor.BLACK, new Position("e8"), new Position("e7")));
        board.makePlay(
                new Move(PieceType.KING, PieceColor.WHITE, new Position("e2"), new Position("e1")));
        board.makePlay(
                new Move(PieceType.KING, PieceColor.BLACK, new Position("e7"), new Position("e8")));

        // When
        var castle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);

        // Then
        assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
    }

    @Test
    void shouldFailWhenRookAlreadyMoved() throws PlayValidationError {
        // Given
        board.makePlay(
                new Move(PieceType.ROOK, PieceColor.WHITE, new Position("h1"), new Position("h2")));
        board.makePlay(
                new Move(PieceType.ROOK, PieceColor.BLACK, new Position("h8"), new Position("h7")));
        board.makePlay(
                new Move(PieceType.ROOK, PieceColor.WHITE, new Position("h2"), new Position("h1")));
        board.makePlay(
                new Move(PieceType.ROOK, PieceColor.BLACK, new Position("h7"), new Position("h8")));

        // When
        var castle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);

        // Then
        assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
    }

    @Test
    void shouldFailIfKingIsInCheck() {
        // Given
        board.placePieceAt("e2", this.pieceFactory.createRooks(PieceColor.BLACK).get(0));

        // When
        var castle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);

        // Then
        assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
    }

    @Test
    void shouldFailIfPathBetweenKingAndRookIsBlocked() {
        // Given
        board.placePieceAt("f1", this.pieceFactory.createBishops(PieceColor.WHITE).get(0));

        // When
        var castle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);

        // Then
        assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
    }

    @Test
    void shouldFailIfKingsPathIsThreatened() {
        // Given
        board.placePieceAt("f2", this.pieceFactory.createRooks(PieceColor.BLACK).get(0));

        // When
        var castle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);

        // Then
        assertThrows(PlayValidationError.class, () -> board.makePlay(castle));
    }

    @Test
    void shouldReturnAlgebraicNotation() {
        var kingSideCastle = new Castle(PieceColor.WHITE, CastleSide.KING_SIDE);
        var queenSideCastle = new Castle(PieceColor.WHITE, CastleSide.QUEEN_SIDE);
        assertEquals("0-0", kingSideCastle.toDto().algebraicNotation());
        assertEquals("0-0-0", queenSideCastle.toDto().algebraicNotation());
    }
}
