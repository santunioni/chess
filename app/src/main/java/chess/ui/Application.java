package chess.ui;

import chess.game.board.BoardController;
import chess.game.board.BoardHistory;
import chess.game.board.BoardStateFactory;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGame chessGame = new ChessGame(1024, new BoardController(new BoardStateFactory().createFreshBoardState(), new BoardHistory()));
            chessGame.setTitle("Chess");
            chessGame.setVisible(true);
            chessGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
