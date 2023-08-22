package chess.ui.pieces;

import chess.pieces.Color;
import chess.pieces.Type;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;


public class PieceIconFactory {
    private static final HashMap<String, ImageIcon> cache = new HashMap<>();
    private final int size;

    PieceIconFactory(int size) {
        this.size = size;
    }

    public ImageIcon getIcon(Color color, Type type) {
        var path = "images/" +
                color.name().toLowerCase() +
                "-" +
                type.name().toLowerCase() +
                ".png";
        var cacheKey = this.size + path;

        var iconOptional = Optional.ofNullable(cache.get(cacheKey));
        if (iconOptional.isEmpty()) {
            var icon = new ImageIcon(Objects.requireNonNull(PieceIconFactory.class.getClassLoader().getResource(path)));
            icon = new ImageIcon(icon.getImage().getScaledInstance(this.size, this.size, java.awt.Image.SCALE_SMOOTH));
            cache.put(cacheKey, icon);
            iconOptional = Optional.of(icon);
        }

        return iconOptional.get();
    }
}