package chess.game.pieces;

import chess.game.board.BoardState;
import chess.game.grid.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnThreatensTest {
    private BoardState board;

    @BeforeEach
    void setUp() {
        this.board = new BoardState();
    }

    @Test
    void shouldThreatenDiagonallyUpWhenWhitePawn() {
        var pawn = new Pawn(Color.WHITE);
        this.board.placePiece("c2", pawn);

        assertTrue(pawn.couldAttackIfOccupiedByEnemy(new Position("d3")));
        assertTrue(pawn.couldAttackIfOccupiedByEnemy(new Position("b3")));
    }

    @Test
    void shouldThreatenDiagonallyDownWhenBlackPawn() {
        var pawn = new Pawn(Color.BLACK);
        this.board.placePiece("c7", pawn);

        assertTrue(pawn.couldAttackIfOccupiedByEnemy(new Position("d6")));
        assertTrue(pawn.couldAttackIfOccupiedByEnemy(new Position("b6")));
    }

    @Test
    void shouldNotThreatenDiagonallyUpWhenBlackPawn() {
        var pawn = new Pawn(Color.BLACK);
        this.board.placePiece("c2", pawn);

        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("d3")));
        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("b3")));
    }

    @Test
    void shouldNotThreatenDiagonallyDownWheWhitePawn() {
        var pawn = new Pawn(Color.WHITE);
        this.board.placePiece("c7", pawn);

        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("d6")));
        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("b6")));
    }

    @Test
    void shouldNotThreatenHorizontally() {
        var pawn = new Pawn(Color.WHITE);
        this.board.placePiece("a2", pawn);

        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("b2")));
    }

    @Test
    void shouldNotThreatenNonDiagonalPosition() {
        var pawn = new Pawn(Color.WHITE);
        this.board.placePiece("a1", pawn);

        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("b3")));
    }

    @Test
    void sholdNotThreatenNotNeighbor() {
        var pawn = new Pawn(Color.WHITE);
        this.board.placePiece("a1", pawn);

        assertFalse(pawn.couldAttackIfOccupiedByEnemy(new Position("c3")));
    }
}