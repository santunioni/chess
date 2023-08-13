package chess.board;


public record Movement(Position from, Position to) {

    public Movement(String from, String to) {
        this(new Position(from), new Position(to));
    }
}
