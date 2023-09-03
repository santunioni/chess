package chess.game.board;

import chess.game.grid.Position;
import chess.game.pieces.Piece;
import chess.game.pieces.PieceProperties;
import chess.game.plays.*;
import chess.game.plays.validation.NotYourTurnValidationError;
import chess.game.plays.validation.PlayValidationError;
import chess.game.rules.ChessRulesPlayValidator;
import chess.game.rules.validation.IlegalBoardStateError;

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
                if (playingPiece.couldCaptureEnemyAt(target) && targetPiece.isEnemyOf(playingPiece)) {
                    plays.add(new Capture(playingPiece.getColor(), position, target).toDTO());
                }
            }
        }

        return plays;
    }

    public void makePlay(PlayDTO playDTO) throws PlayValidationError, IlegalBoardStateError {
        Play play = new PlayDTOToPlayMapper(this.boardState).createPlayFromDTO(playDTO);
        if (play.getPlayerColor() != this.boardHistory.nextTurnPlayerColor()) {
            throw new NotYourTurnValidationError(play);
        }

        new ChessRulesPlayValidator(this.boardState, this.boardHistory).validatePlayAgainstChessRules(play);

        play.actUpon(this.boardState);
        this.boardHistory.push(play);
    }
}
