package controller;

import model.*;
import view.GameFrame;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.LoginFrame;
import view.RegisterFrame;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    public GameBoard gameBoard;
    public Setting setting;
    private Stage stage;
    private GameFrame frame;
    public ArrayList<Piece> piecesMoved= new ArrayList<>();
    public ArrayList<Direction> directionsMoved = new ArrayList<>();

    public Game(){

    }

    public Game(Setting setting,GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
        this.setting = setting;
    }

    public void initialize(Setting setting) throws Exception {
        this.setting = setting;
        gameBoard.clear();
        piecesMoved.clear();
        directionsMoved.clear();

        for (PieceAndPos pieceAndPos : setting.piecesAndPoses) {
            gameBoard.put(pieceAndPos.piece, pieceAndPos.h, pieceAndPos.w);
        }
    }



    public GameFrame start(Stage stage,Client client) throws Exception {
        frame=new GameFrame(stage , gameBoard, this);
        return frame;
    }


    public void step(Piece piece, Direction direction) {
        try {
            if (gameBoard.ableToMove(piece, direction)) {
                gameBoard.move(piece, direction);
                piecesMoved.add(piece);
                directionsMoved.add(direction);

                // 更新 JavaFX 控件中的状态
                Platform.runLater(() -> {
                    frame.gamePanel.update(piece, direction);

                    // 检查是否达成胜利条件
                    PieceAndPos winCondition = setting.winCondition;
                    if (gameBoard.pieceAtPos(winCondition.piece, winCondition.h, winCondition.w)) {
                        end();
                    }
                });
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void undo() throws Exception {
        int n =piecesMoved.size()-1;
        gameBoard.move(piecesMoved.get(n),directionsMoved.get(n).reverse());
        piecesMoved.remove(n);
        directionsMoved.remove(n);
    }

    public int numberOfMoves()
    {
        return piecesMoved.size();
    }

    public void end() {
        // 使用 JavaFX 弹出提示框代替 Swing 的 JOptionPane
        Platform.runLater(() -> {
            Button btn = new Button("恭喜获胜！");
            btn.setOnAction(e -> {
                stage.close();  // 关闭窗口
            });

            StackPane endRoot = new StackPane();
            endRoot.getChildren().add(btn);
            Scene endScene = new Scene(endRoot, 300, 200);
            Stage endStage = new Stage();
            endStage.setTitle("Game Over");
            endStage.setScene(endScene);
            endStage.show();
        });
    }
    //存档用户进度
    public void saveProgress(String username)
    {
        List<String> names = (List<String>) piecesMoved.stream().map(Piece::getId);   // 或 getName(), 取决于你怎么唯一标识.collect(Collectors.toList());
        GameProgress progress = new GameProgress(names, new ArrayList<>(directionsMoved));

        //复制集合元素
        File dir=new File("saves");
        System.out.println("Directory exists: " + dir.exists()); // 检查目录是否存在
        if(!dir.exists()){
            System.out.println("Creating directory...");
            boolean created = dir.mkdirs();
            System.out.println("Directory created: " + created);

            }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(dir, username + ".sav")))) {
            System.out.println("Writing object...");
            out.writeObject(progress);
            System.out.println("Object written successfully.");
            System.out.println("存档成功: " + dir.getAbsolutePath() + "/" + username + ".sav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //从文件读回进度，没有存档就返回null
    public static GameProgress loadProgress(String username)
    {
        File f =new File("saves",username+".sav");
        if(!f.exists()) return null;
        try(ObjectInputStream in=new ObjectInputStream(new FileInputStream(f))){
            GameProgress progress = (GameProgress) in.readObject();
            System.out.println("Object read successfully.");

            return progress;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
