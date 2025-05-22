package view;
import controller.Game;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Direction;
import model.GameBoard;
import model.Piece;

import static view.LoginFrame.username;

// 让GameFrame继承VBox，直接作为布局容器
public class GameFrame extends VBox {
    public GamePanel gamePanel;
    public ControlPanel controlPanel;
    private Game game;
    private int number;

    public GameFrame(Stage stage, GameBoard gameBoard, Game game) throws Exception {
        super();
        this.game = game;
        long seconds = System.currentTimeMillis();
        int steps = game.numberOfMoves();
        number = 3;
        stage.setTitle("Klotski");
        Label Time = new Label("时间:" + seconds);
        Label Step = new Label("步数:" + steps);
        Button reserve = new Button("撤销");
        Label Leftnumber = new Label("次数" + number);
        Label UserName = new Label("用户：" + username);
        Button restart = new Button("重置");
        Button save = new Button("存档");
        save.setDisable(false);
        save.setOnAction(e -> {
            game.saveProgress(username);
        });
        // 游戏窗口关闭时自动存档
        stage.setOnCloseRequest(event -> {
            game.saveProgress(username);
        });
        gamePanel = new GamePanel(gameBoard, game, this);
        reserve.setOnAction(e -> {
            try {
                int n = game.piecesMoved.size() - 1;
                Piece piece = game.piecesMoved.get(n);
                Direction direction = game.directionsMoved.get(n).reverse();
                game.undo();
                gamePanel.update(piece, direction);
                number--;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        this.setSpacing(10); // 设置子节点间距
        this.setPadding(new Insets(10)); // 设置内边距
        Piece piece = gamePanel.selectedPiece;
        controlPanel = new ControlPanel(stage, game, gameBoard, piece);
        restart.setOnAction(e -> {
            try {
                game.initialize(game.setting);
                Platform.runLater(() -> {
                    gamePanel.fresh(null, null, 1.0);
                });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox topBar = new HBox(20);
        topBar.getChildren().addAll(UserName, Time, Step);
        HBox re = new HBox(reserve, Leftnumber);
        VBox buttonBar = new VBox(10);
        buttonBar.getChildren().addAll(save, reserve, re, controlPanel);
        HBox P = new HBox(20);
        P.getChildren().addAll(gamePanel, buttonBar);
        this.getChildren().addAll(topBar, P);
    }
}

