package ai;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import model.GameBoard;
import model.PieceAndDirection;
import model.PieceAndPos;


public class State {
    private final List<PieceAndPos> poses;
    private State parent;
    private Move via;
    public State(List<PieceAndPos> poses)
    {
        this.poses=deepCopy(poses);
    }
    public List<Move> possibleMoves()
    {
        GameBoard board=new GameBoard(getMaxH(),getMaxW());
        for(PieceAndPos p:poses)
        {
            try {
                board.put(p.piece,p.h,p.w);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        List<Move> result=new ArrayList<>();
        try {
            for(PieceAndDirection pd:board.getAllPossibleMoves())
            {
                result.add(new Move(pd.piece,pd.direction));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    //回溯路径
    public List<Move>reconstructPath()
    {
        LinkedList<Move> path=new LinkedList<>();
        State cur=this;
        while (cur.via!=null)
        {
            path.addFirst(cur.via);//将这一步加入路径最前面
            cur=cur.parent;//回退到上一个状态
        }
        return path;
    }
    private List<PieceAndPos> deepCopy(List<PieceAndPos> input) {
        List<PieceAndPos> copy = new ArrayList<>();
        for (PieceAndPos pieceAndPos : input) {
            copy.add(new PieceAndPos(pieceAndPos.piece,pieceAndPos.h,pieceAndPos.w));
        }
        return copy;
    }

    public boolean isWin(PieceAndPos winCondition )
    {

        return poses.stream().anyMatch(p -> same(p,winCondition ));
    }

    private static boolean same(PieceAndPos a, PieceAndPos b) {
        return a.piece.height == b.piece.height &&
                a.piece.width == b.piece.width &&
                a.h == b.h && a.w == b.w;
    }
    private int getMaxH() {
        return poses.stream().mapToInt(p -> p.h + p.piece.height).max().orElse(5);
    }

    private int getMaxW() {
        return poses.stream().mapToInt(p -> p.w + p.piece.width).max().orElse(4);
    }

}
