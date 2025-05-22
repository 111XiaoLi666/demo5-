package model;


import java.io.Serializable;
import java.util.List;
import javafx.scene.paint.Color;
import view.*;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

            // 初始布局
       // 玩家走过的每一步：棋子
    private final List<String>pieceNames;
    private final List<Direction> directions;   // 玩家走过的每一步：方向

    public GameProgress(
                        List<String> pieceNames,
                        List<Direction> directions) {

       this.pieceNames = pieceNames;
        this.directions    = directions;
    }


//    public Setting getSetting() {
//        return setting;
//    }

    public List<String> getPieceNames() {
        return pieceNames;
    }

    public List<Direction> getDirections() {
        return directions;
    }
}

