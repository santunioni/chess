package chess.domain.grid;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    @Test
    void shouldReturnDiagonalUpRightFromA1() {
        var path = new Path(new Position(File.A, Rank.ONE),
                Direction.DIAGONAL_UP_RIGHT).toPositionList();

        var expectedPath = List.of(
                new Position(File.B, Rank.TWO),
                new Position(File.C, Rank.THREE),
                new Position(File.D, Rank.FOUR),
                new Position(File.E, Rank.FIVE),
                new Position(File.F, Rank.SIX),
                new Position(File.G, Rank.SEVEN),
                new Position(File.H, Rank.EIGHT)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnDiagonalUpLeftFromH2() {
        var path = new Path(new Position(File.H, Rank.TWO),
                Direction.DIAGONAL_UP_LEFT).toPositionList();

        var expectedPath = List.of(
                new Position(File.G, Rank.THREE),
                new Position(File.F, Rank.FOUR),
                new Position(File.E, Rank.FIVE),
                new Position(File.D, Rank.SIX),
                new Position(File.C, Rank.SEVEN),
                new Position(File.B, Rank.EIGHT)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnDiagonalDownLeftFromF8() {
        var path = new Path(new Position(File.F, Rank.EIGHT),
                Direction.DIAGONAL_DOWN_LEFT).toPositionList();

        var expectedPath = List.of(
                new Position(File.E, Rank.SEVEN),
                new Position(File.D, Rank.SIX),
                new Position(File.C, Rank.FIVE),
                new Position(File.B, Rank.FOUR),
                new Position(File.A, Rank.THREE)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnDiagonalDownRightFromA5() {
        var path = new Path(new Position(File.A, Rank.FIVE),
                Direction.DIAGONAL_DOWN_RIGHT).toPositionList();

        var expectedPath = List.of(
                new Position(File.B, Rank.FOUR),
                new Position(File.C, Rank.THREE),
                new Position(File.D, Rank.TWO),
                new Position(File.E, Rank.ONE)
        );

        assertEquals(expectedPath, path);
    }


    @Test
    void shouldReturnVerticalUpFromA1() {
        var path = new Path(new Position(File.A, Rank.ONE),
                Direction.VERTICAL_UP).toPositionList();

        var expectedPath = List.of(
                new Position(File.A, Rank.TWO),
                new Position(File.A, Rank.THREE),
                new Position(File.A, Rank.FOUR),
                new Position(File.A, Rank.FIVE),
                new Position(File.A, Rank.SIX),
                new Position(File.A, Rank.SEVEN),
                new Position(File.A, Rank.EIGHT)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnVerticalDownFromB5() {
        var path = new Path(new Position(File.B, Rank.FIVE),
                Direction.VERTICAL_DOWN).toPositionList();

        var expectedPath = List.of(
                new Position(File.B, Rank.FOUR),
                new Position(File.B, Rank.THREE),
                new Position(File.B, Rank.TWO),
                new Position(File.B, Rank.ONE)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnHorizontalRightFromA1() {
        var path = new Path(new Position(File.A, Rank.ONE),
                Direction.HORIZONTAL_RIGHT).toPositionList();

        var expectedPath = List.of(
                new Position(File.B, Rank.ONE),
                new Position(File.C, Rank.ONE),
                new Position(File.D, Rank.ONE),
                new Position(File.E, Rank.ONE),
                new Position(File.F, Rank.ONE),
                new Position(File.G, Rank.ONE),
                new Position(File.H, Rank.ONE)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnHorizontalLeftFromE2() {
        var path = new Path(new Position(File.E, Rank.TWO),
                Direction.HORIZONTAL_LEFT).toPositionList();

        var expectedPath = List.of(
                new Position(File.D, Rank.TWO),
                new Position(File.C, Rank.TWO),
                new Position(File.B, Rank.TWO),
                new Position(File.A, Rank.TWO)
        );

        assertEquals(expectedPath, path);
    }

    @Test
    void shouldReturnEmptyPathToLeftOfA1T() {
        var path = new Path(new Position(File.A, Rank.ONE),
                Direction.HORIZONTAL_LEFT).toPositionList();

        assertEquals(0, path.size());
    }
}
