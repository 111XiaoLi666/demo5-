package controller;
import model.GameProgress;
import model.Setting;
import controller.Game;
import controller.StageController;
import javafx.stage.Stage;
import view.GameFrame;
import view.LoginFrame;
import view.RegisterFrame;
import view.StartFrame;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class LoginController {
    private StartFrame startFrame;
    public Client client;
    private StageController stageController;

    public LoginController(Client client, StageController stageController) {
        this.client = client;
        this.stageController = stageController;
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void onLogin(String username, Stage stage) throws Exception {
        GameProgress prog = Game.loadProgress(username);

        // 1) 一律用 defaultSetting 来构造和初始化棋盘
        Setting defaultSetting = new Setting("横刀立马");
        GameBoard board = new GameBoard(defaultSetting.height, defaultSetting.width);
        Game game = new Game(defaultSetting, board);

        // 2) 先按 defaultSetting 一次性把所有棋子放到初始位置
        game.initialize(defaultSetting);

        // 3) 创建并展示 UI
        GameFrame gameFrame = game.start(stage, client);
        stageController.showGame(gameFrame);

        ArrayList<Piece> piecesMoved= new ArrayList<>();


        // 4) 如果存档里有移动记录，就用动画“回放”每一步
        if (prog != null) {
            List<String> pieceNames = prog.getPieceNames();
            for(String name:pieceNames){
                Piece thisPiece=defaultSetting.createPieceById(name);
                piecesMoved.add(thisPiece);
            }

            var pieces = piecesMoved;
            var directions = prog.getDirections();
            Timeline timeline = new Timeline();
            for (int i = 0; i < pieces.size(); i++) {
                final int idx = i;
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                Duration.seconds(0.2 * (idx + 1)),
                                e -> game.step(pieces.get(idx), directions.get(idx))
                        )
                );
            }
            timeline.play();
        }
    }

    public void onGuest(Stage stage) throws Exception {
        Setting setting = new Setting("横刀立马");
        GameBoard board = new GameBoard(setting.height, setting.width);
        Game game = new Game(setting, board);
        game.initialize(setting);
        GameFrame gameFrame = game.start(stage, null);
        stageController.showGame(gameFrame);
    }
}

