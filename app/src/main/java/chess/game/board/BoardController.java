package chess.game.board;

import chess.game.grid.Position;
import chess.game.pieces.Piece;
import chess.game.pieces.PieceProperties;
import chess.game.plays.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The only way that ui should interact with the chess game.
 */
public class BoardController {
    private final BoardState boardState;
    private final BoardHistory boardHistory;

    public BoardController(BoardState boardState, BoardHistory boardHistory) {
        this.boardState = boardState;
        this.boardHistory = boardHistory;
    }

    public BoardController() {
        this(new BoardStateFactory().createFreshBoardState(), new BoardHistory());
    }

    public Optional<PieceProperties> getPieceAt(Position position) {
        Optional<Piece> pieceOptional = this.boardState.getPieceAt(position);
        if (pieceOptional.isPresent()) {
            var piece = pieceOptional.get();
            return Optional.of(piece.toProperties());
        }
        return Optional.empty();
    }

    public List<PlayDTO> getPlaysFor(Position position) {
        List<PlayDTO> plays = new ArrayList<>();

        var playingPieceOptional = this.boardState.getPieceAt(position);
        if (playingPieceOptional.isEmpty()) {
            return plays;
        }

        var playingPiece = playingPieceOptional.get();

        if (playingPiece.getColor() != this.boardHistory.nextTurnPlayerColor()) {
            return plays;
        }

        for (var target : Position.values()) {
            var targetPieceOptional = this.boardState.getPieceAt(target);
            if (targetPieceOptional.isEmpty()) {
                if (playingPiece.couldMoveToIfEmpty(target)) {
                    plays.add(new Move(playingPiece.getColor(), position, target).toDTO());
                }
            } else {
                var targetPiece = targetPieceOptional.get();
                if (playingPiece.couldAttackIfOccupiedByEnemy(target) && targetPiece.isEnemyOf(playingPiece)) {
                    plays.add(new Capture(playingPiece.getColor(), position, target).toDTO());
                }
            }
        }

        return plays;
    }

    public void makePlay(PlayDTO playDTO) throws IlegalPlay {
        Play play = new PlayDTOToPlayMapper(this.boardState).createPlayFromDTO(playDTO);
        if (play.getPlayerColor() != this.boardHistory.nextTurnPlayerColor()) {
            throw new IlegalPlay(play, "Not your turn");
        }
        play.actUpon(this.boardState);
        this.boardHistory.push(play);
    }
}