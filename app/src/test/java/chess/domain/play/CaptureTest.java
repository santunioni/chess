package chess.domain.play;

import chess.domain.board.Board;
import chess.domain.board.PieceColor;
import chess.domain.board.PieceFactory;
import chess.domain.board.PieceType;
import chess.domain.grid.Position;
import chess.domain.play.validation.PlayValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CaptureTest {
    private final PieceFactory pieceFactory = new PieceFactory();
    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board();
    }

    @Test
    void shouldAllowWhitePawnDiagonalUpLeftAttack() throws PlayValidationError {
        var pawn = this.pieceFactory.createPawns(PieceColor.WHITE).get(0);
        board.placePieceAt("e2", pawn);
        board.placePieceAt("d3", this.pieceFactory.createPawns(PieceColor.BLACK).get(0));

        var capture =
                new Capture(PieceType.PAWN, PieceColor.WHITE, new Position("e2"), new Position("d3"));
        board.makePlay(capture);

        assertTrue(board.getPieceAt("e2").isEmpty());
        assertEquals(pawn, board.getPieceAt("d3").orElseThrow());
    }

    @Test
    void shouldNotAllowWhitePawnVerticalAttack() {
        var pawn = this.pieceFactory.createPawns(PieceColor.WHITE).get(0);
        board.placePieceAt("e2", pawn);
        board.placePieceAt("e3", this.pieceFactory.createPawns(PieceColor.BLACK).get(0));

        var capture =
                new Capture(PieceType.PAWN, PieceColor.WHITE, new Position("e2"), new Position("e3"));

        assertFalse(capture.canActOnCurrentState(board));
    }

    @Test
    void shouldNotAllowWhitePawnDiagonalDownLeftAttack() {
        var pawn = this.pieceFactory.createPawns(PieceColor.WHITE).get(0);
        board.placePieceAt("e2", pawn);
        board.placePieceAt("d1", this.pieceFactory.createPawns(PieceColor.BLACK).get(0));

        var capture =
                new Capture(PieceType.PAWN, PieceColor.WHITE, new Position("e2"), new Position("d1"));

        assertFalse(capture.canActOnCurrentState(board));
    }

    @Test
    void shouldAllowQueenVerticalAttack() throws PlayValidationError {
        var queen = this.pieceFactory.createQueen(PieceColor.WHITE);
        board.placePieceAt("e2", queen);
        board.placePieceAt("e7", this.pieceFactory.createPawns(PieceColor.BLACK).get(0));

        var capture =
                new Capture(PieceType.QUEEN, PieceColor.WHITE, new Position("e2"), new Position("e7"));
        board.makePlay(capture);

        assertTrue(board.getPieceAt("e2").isEmpty());
        assertEquals(queen, board.getPieceAt("e7").orElseThrow());
    }

    @Test
    void shouldNotAttackAlly() {
        var queen = this.pieceFactory.createQueen(PieceColor.WHITE);
        board.placePieceAt("e2", queen);
        board.placePieceAt("e7", this.pieceFactory.createPawns(PieceColor.WHITE).get(0));

        var capture =
                new Capture(PieceType.QUEEN, PieceColor.WHITE, new Position("e2"), new Position("e7"));

        assertFalse(capture.canActOnCurrentState(board));
    }

    @Test
    void shouldNotAttackEmptyPositions() {
        var queen = this.pieceFactory.createQueen(PieceColor.WHITE);
        board.placePieceAt("e2", queen);
        var capture =
                new Capture(PieceType.QUEEN, PieceColor.WHITE, new Position("e2"), new Position("e7"));

        assertFalse(capture.canActOnCurrentState(board));
    }

    @Test
    void shouldReturnAlgebraicNotation() {
        var capture =
                new Capture(PieceType.QUEEN, PieceColor.BLACK, new Position("e2"), new Position("e4"));
        assertEquals("Qe2xe4", capture.toDto().algebraicNotation());
    }
}
