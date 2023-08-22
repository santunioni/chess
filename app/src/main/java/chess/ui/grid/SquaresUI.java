package chess.ui.grid;


import chess.board.position.Position;

import javax.swing.*;
import java.awt.*;

public class SquaresUI extends JPanel {

    public SquaresUI() {
        super(new GridLayout(8, 8));
        for (var position : Position.values()) {
            var square = new SquareUI(position);
            this.add(square);
        }
        this.setIgnoreRepaint(true);
        this.setOpaque(false);
    }

    private Integer getBoardSize() {
        return this.getWidth();
    }

    private Integer getSquareSize() {
        return this.getBoardSize() / 8;
    }

    public Rectangle getRectangleForPosition(Position position, Double ratio) {
        Point middlePoint = this.getMiddlePointForPosition(position);
        var size = this.getSquareSize() * ratio;
        return new Rectangle(
                (int) Math.round(middlePoint.x - size / 2),
                (int) Math.round(middlePoint.y - size / 2),
                (int) Math.round(size),
                (int) Math.round(size)
        );
    }

    private Point getMiddlePointForPosition(Position position) {
        int width = this.getSquareSize();
        int height = this.getSquareSize();

        var x = position.file().ordinal() * width + width / 2;
        var y = this.getBoardSize() - (position.rank().ordinal() * height + height / 2);

        return new Point(x, y);
    }
}