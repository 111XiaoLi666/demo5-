package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard implements Serializable {
    public boolean[][] occupancyMap;
    public ArrayList<PieceAndPos> piecesAndPoses = new ArrayList<>();

    public GameBoard(int height, int width) {
        occupancyMap = new boolean[height][width];;
        clear();
    }

    public void clear() {
        for (boolean[] row : occupancyMap) {
            Arrays.fill(row, false);
        }
        piecesAndPoses.clear();
    }

    public void put(Piece piece, int h, int w) throws Exception {
        // 推荐做法：只用 setting 里的 Piece，不要 new Piece()
        piecesAndPoses.removeIf(pap -> pap.piece.name.equals(piece.name));
        if (ableToPut(piece, h, w)) {
            piecesAndPoses.add(new PieceAndPos(piece, h, w));
            System.out.printf("尝试放置 %s 在 (%d,%d)，尺寸 %dx%d%n",
                    piece.name, h, w, piece.height, piece.width);
            for (int i = 0; i < piece.height; i++) {
                for (int j = 0; j < piece.width; j++) {
                    occupancyMap[i + h][j + w] = true;
                }
            }
        } else {
            throw new Exception(String.format("无法放置棋子 %s 在 (%d,%d)：越界或已被占用",
                    piece.name, h, w));
        }    }


    public PieceAndPos remove(Piece piece) throws Exception{
        for (PieceAndPos pieceAndPos : piecesAndPoses) {
            if (pieceAndPos.piece.equals(piece)) {
                piecesAndPoses.remove(pieceAndPos);
                for (int i = 0; i < piece.height; i++) {
                    for (int j = 0; j < piece.width; j++) {
                        occupancyMap[i + pieceAndPos.h][j + pieceAndPos.w] = false;
                    }
                }
                return pieceAndPos;
            }
        }
        throw new Exception("棋子未找到！");
    }

    private boolean ableToPut(Piece piece, int h, int w) {
        for (int i = 0; i < piece.height; i++) {
            for (int j = 0; j < piece.width; j++) {
                if (occupied(h + i, w + j)) {
                    return false;
                }
            }
        }
        return true;
    }
    public ArrayList<PieceAndDirection> getAllPossibleMoves() throws Exception {
        ArrayList<PieceAndDirection> possibleMoves = new ArrayList<>();
        ArrayList<PieceAndPos> piecesAndPosesCopy = new ArrayList<>(piecesAndPoses);
        for (PieceAndPos pieceAndPos : piecesAndPosesCopy) {
            Piece piece = pieceAndPos.piece;
            for (Direction direction : Direction.ALL_DIRECTIONS) {
                if (ableToMove(piece, direction)) {
                    possibleMoves.add(new PieceAndDirection(piece, direction));
                }
            }
        }
        return possibleMoves;
    }
    private boolean occupied(int h, int w) {
        if (h >= occupancyMap.length || h < 0 || w >= occupancyMap[0].length || w < 0) {
            return true;
        }
        return occupancyMap[h][w];
    }



    public boolean ableToMove(Piece piece, Direction direction) throws Exception {
        PieceAndPos pieceAndPos = remove(piece);
        boolean ableToMove = ableToPut(pieceAndPos.piece, pieceAndPos.h + direction.h, pieceAndPos.w + direction.w);
        put(pieceAndPos.piece, pieceAndPos.h, pieceAndPos.w);
        return ableToMove;
    }

    public void move(Piece piece, Direction direction) throws Exception {

        PieceAndPos pieceAndPos = remove(piece);
        put(pieceAndPos.piece, pieceAndPos.h + direction.h, pieceAndPos.w + direction.w);//controller中添加用键盘和界面上的按钮改变的方式调用
    }

    public boolean pieceAtPos(Piece piece, int h, int w) {
        for (PieceAndPos pieceAndPos : piecesAndPoses) {
            if (pieceAndPos.piece.equals(piece) && pieceAndPos.h == h && pieceAndPos.w == w) {
                return true;
            }
        }
        return false;
    }

}
