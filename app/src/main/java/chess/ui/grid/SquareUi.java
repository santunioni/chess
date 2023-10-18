package chess.ui.grid;

import chess.domain.grid.Position;
import java.awt.Color;
import javax.swing.JPanel;

class SquareUi extends JPanel {
  private static final Color lightSquareColor = new Color(240, 217, 181); // Light square color
  private static final Color darkSquareColor = new Color(181, 136, 99);   // Dark square color

  public SquareUi(Position position) {
    this.setBackground((position.rank().ordinal() + position.file().ordinal()) % 2 == 0
        ? SquareUi.lightSquareColor : SquareUi.darkSquareColor);
  }
}
