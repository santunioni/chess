package chess.game.rules;

import chess.game.board.BoardHistory;
import chess.game.board.BoardState;
import chess.game.plays.Play;
import chess.game.plays.validation.PlayValidationError;
import chess.game.rules.validation.IlegalBoardStateError;

import java.util.List;

public class ChessRulesPlayValidator {
    private final BoardState boardState;
    private final BoardHistory boardHistory;

    public ChessRulesPlayValidator(BoardState boardState, BoardHistory boardHistory) {
        this.boardState = boardState;
        this.boardHistory = boardHistory;
    }

    public void validatePlayAgainstChessRules(Play play) throws PlayValidationError, IlegalBoardStateError {
        var newState = this.boardState.copy();
        var newHistory = this.boardHistory.copy();
        play.actUpon(newState);
        newHistory.push(play);

        var rules = List.of(new CantPutOwnKingInCheckValidation(newState));

        for (var rule : rules) {
            rule.validateStateAfterPlay(play);
        }
    }
}
