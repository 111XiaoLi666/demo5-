package model;

import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.Objects;

public class Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    public String name;
    public int width, height;
    public Color color;

    public Piece(String name, int width, int height, Color color) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return Objects.equals(name, piece.name); // 名称唯一性验证
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getId() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }
}
