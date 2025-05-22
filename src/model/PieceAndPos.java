package model;

import java.io.Serializable;

public class PieceAndPos implements Serializable {
    private static final long serialVersionUID = 1L;
    public Piece piece;
    public int h, w;

    public PieceAndPos(Piece piece, int h, int w) {
        this.piece = piece;
        this.h = h;
        this.w = w;
    }

    public String toString() {
        return piece.name + " (" + w + ", " + h + ")";
    }

    public String Location(int w, int h) {
        return "(" + w + ", " + h + ")";
    }
    public static boolean same(PieceAndPos a, PieceAndPos b) {
        return a.piece.equals(b.piece) && a.w == b.w && a.h == b.h;
    }
}
