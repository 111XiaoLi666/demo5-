package view;

import controller.Game;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Direction;
import model.GameBoard;
import model.Piece;

public class ControlPanel extends GridPane {
    private Piece selectedPiece;
    private GameBoard gameBoard;
    private Game game;
    private Stage stage;
    private Piece piece;
    public ControlPanel(Stage stage, Game game, GameBoard gameBoard,Piece selectedPiece) {
        this.stage = stage;
        this.gameBoard = gameBoard;
        this.game = game;
        this.selectedPiece = selectedPiece;
        Button Up=new Button("Up");
        Up.setOnAction(e -> {
            game.step(selectedPiece, Direction.UP);
        });
        Button Down=new Button("Down");
        Down.setOnAction(e -> {
            game.step(selectedPiece, Direction.DOWN);
        });
        Button Left=new Button("Left");
        Left.setOnAction(e -> {
            game.step(selectedPiece, Direction.LEFT);
        });
        Button Right=new Button("Right");
        Right.setOnAction(e -> {
            game.step(selectedPiece, Direction.RIGHT);
        });
        this.add(Up,1,0);
        this.add(Down,1,1);
        this.add(Left,0,1);
        this.add(Right,2,1);
    }
}
