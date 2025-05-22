package model;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Setting implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String,Piece> prototypeMap=new HashMap<>();

    public ArrayList<PieceAndPos> piecesAndPoses;
    public PieceAndPos winCondition;
    public int height, width;
    public Piece caoCao = new Piece("曹操", 2, 2, Color.RED);
    public Piece zhangFei = new Piece("张飞", 1, 2, Color.BLUE);
    public Piece zhaoYun = new Piece("赵云", 1, 2, Color.GREEN);
    public Piece maChao = new Piece("马超", 1, 2, Color.YELLOW);
    public Piece huangZhong = new Piece("黄忠", 1, 2, Color.ORANGE);
    public Piece guanYu = new Piece("关羽", 2, 1, Color.PURPLE);
    public Piece xiaoBing1 = new Piece("1号小兵", 1, 1, Color.CYAN);
    public Piece xiaoBing2 = new Piece("2号小兵", 1, 1, Color.BEIGE);
    public Piece xiaoBing3 = new Piece("3号小兵", 1, 1, Color.GRAY);
    public Piece xiaoBing4 = new Piece("4号小兵", 1, 1, Color.BLACK);


    public Setting(String name) throws Exception {
        piecesAndPoses = new ArrayList<>();

        if (name.equals("横刀立马")) {
            height = 5;
            width = 4;

            piecesAndPoses.add(new PieceAndPos(zhangFei, 0, 0));
            piecesAndPoses.add(new PieceAndPos(caoCao, 0, 1));
            piecesAndPoses.add(new PieceAndPos(zhaoYun, 0, 3));
            piecesAndPoses.add(new PieceAndPos(maChao, 2, 0));
            piecesAndPoses.add(new PieceAndPos(huangZhong, 2, 3));
            piecesAndPoses.add(new PieceAndPos(guanYu, 2, 1));
            piecesAndPoses.add(new PieceAndPos(xiaoBing1, 4, 0));
            piecesAndPoses.add(new PieceAndPos(xiaoBing2, 3, 1));
            piecesAndPoses.add(new PieceAndPos(xiaoBing3, 3, 2));
            piecesAndPoses.add(new PieceAndPos(xiaoBing4, 4, 3));

            winCondition = new PieceAndPos(caoCao, 3, 1);

            prototypeMap.put(caoCao.getId(),     caoCao);
            prototypeMap.put(zhangFei.getId(),   zhangFei);
            prototypeMap.put(zhaoYun.getId(),    zhaoYun);
            prototypeMap.put(maChao.getId(),     maChao);
            prototypeMap.put(huangZhong.getId(), huangZhong);
            prototypeMap.put(guanYu.getId(),     guanYu);
            prototypeMap.put(xiaoBing1.getId(),  xiaoBing1);
            prototypeMap.put(xiaoBing2.getId(),  xiaoBing2);
            prototypeMap.put(xiaoBing3.getId(),  xiaoBing3);
            prototypeMap.put(xiaoBing4.getId(),  xiaoBing4);

        } else {
            throw new Exception("未找到该名称的游戏设置！");
        }
    }
    public Piece createPieceById(String id) {
        Piece proto = prototypeMap.get(id);
        if (proto == null) {
            throw new IllegalArgumentException("未知的棋子 ID: " + id);
        }
        // 这里我们 new 一份深拷贝，这样回放动画不会影响原型：
        return new Piece(proto.getId(),
                proto.getHeight(),
                proto.getWidth(),
                proto.getColor());
    }


}
