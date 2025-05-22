package ai;
import model.Direction;
import model.Piece;
public class Move {
    public final Piece piece;
    public final Direction direction;
    public Move(Piece piece, Direction direction)
    {
        this.piece = piece;this.direction = direction;
    }
    @Override
    public String toString() {
        return "Move{" + piece + " -> " + direction + "}";
    }
}
