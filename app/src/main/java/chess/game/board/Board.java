package chess.game.board;

import chess.game.board.pieces.Piece;
import chess.game.grid.Position;
import chess.game.plays.Play;
import chess.game.plays.validation.PlayValidationError;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class Board implements ReadonlyBoard {
  private final String id;
  private final BiMap<Position, Piece> currentPositionToPiece;
  private final List<Play> stack;

  public Board(String id, BiMap<Position, Piece> currentPositionToPiece, List<Play> stack) {
    this.id = id;
    this.currentPositionToPiece = currentPositionToPiece;
    this.stack = stack;
  }

  public Board() {
    this.id = UUID.randomUUID().toString();
    this.currentPositionToPiece = HashBiMap.create();
    this.stack = new ArrayList<>();
  }

  public Optional<Piece> getPieceAt(Position position) {
    return Optional.ofNullable(currentPositionToPiece.get(position));
  }

  public Stream<Map.Entry<Position, Piece>> getPieces() {
    return this.currentPositionToPiece.entrySet().stream();
  }

  public void placePiece(Position position, Piece piece) {
    this.removePieceFromSquare(position);
    this.currentPositionToPiece.put(position, piece);
    piece.placeInBoard(this);
  }

  public void placePiece(String position, Piece piece) {
    this.placePiece(new Position(position), piece);
  }

  public void removePieceFromSquare(Position position) {
    if (this.currentPositionToPiece.containsKey(position)) {
      var piece = this.currentPositionToPiece.get(position);
      this.currentPositionToPiece.remove(position);
      piece.placeInBoard(null);
    }
  }

  public Position getPositionOf(Piece piece) {
    return this.currentPositionToPiece.inverse().get(piece);
  }

  public Board copy() {
    var newState = new Board();
    this.currentPositionToPiece.forEach(
        ((position, piece) -> newState.placePiece(position, piece.copy())));
    return newState;
  }

  public boolean equals(Object that) {
    if (that instanceof Board) {
      return this.id.equals(((Board) that).id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  public void makePlay(Play play) throws PlayValidationError {
    play.validateAndGetAction(this).run();
    this.stack.add(play);
  }

  public Optional<Play> getLastPlay() {
    if (!this.stack.isEmpty()) {
      return Optional.of(this.stack.get(this.stack.size() - 1));
    }
    return Optional.empty();
  }

  public Iterable<Play> history() {
    return this.stack;
  }
}
