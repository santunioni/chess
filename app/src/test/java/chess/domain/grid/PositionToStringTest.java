package chess.domain.grid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionToStringTest {
    @Test
    void shouldOutputStringRepresentationAs_a1() {
        Position position = new Position(File.A, Rank.ONE);
        assertEquals("a1", position.toString());
    }
}
