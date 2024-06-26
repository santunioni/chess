package chess.domain.pieces;

import chess.domain.board.Board;
import chess.domain.board.PieceColor;
import chess.domain.board.PieceFactory;
import chess.domain.grid.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KingThreatensTest {
    private final PieceFactory pieceFactory = new PieceFactory();
    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board();
    }

    @Test
    public void shouldThreatenVertically() {
        var king = this.pieceFactory.createKing(PieceColor.BLACK);
        board.placePieceAt("d4", king);

        assertTrue(king.threatens(new Position("d5")));
    }

    @Test
    public void shouldThreatenHorizontally() {
        var king = this.pieceFactory.createKing(PieceColor.BLACK);
        board.placePieceAt("d4", king);

        assertTrue(king.threatens(new Position("e4")));
    }

    @Test
    public void shouldThreatenDiagonally() {
        var king = this.pieceFactory.createKing(PieceColor.BLACK);
        board.placePieceAt("d4", king);

        assertTrue(king.threatens(new Position("e5")));
    }

    @Test
    public void shouldNotThreatDistantPiece() {
        var king = this.pieceFactory.createKing(PieceColor.BLACK);
        board.placePieceAt("d4", king);

        assertFalse(king.threatens(new Position("d6")));
    }

}
