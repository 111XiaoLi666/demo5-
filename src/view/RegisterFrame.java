package view;

import controller.*;
import controller.StageController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class RegisterFrame {
    public MyLabel usernameLabel = new MyLabel("用户名:");
    public TextField usernameField = new TextField();
    public MyLabel passwordLabel = new MyLabel("密码:");
    public TextField passwordField = new TextField();
    public MyButton registerButton=new MyButton("注册");
    public static String username;
    public static String password;
    public static GridPane gridPane=new GridPane();

    public RegisterFrame(Stage stage, StageController stageController,Client client, LoginController login) {
        registerButton.setOnAction(e -> {
            username = usernameField.getText();
            password = passwordField.getText();
            client.register(username, password);
            showSuccessAlert();
            new Timeline(
                    new KeyFrame(Duration.millis(500), event -> {
                        stageController.showLogin();
                    })
            ).play();
        });

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(registerButton, 1, 2);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("注册成功");
        alert.setHeaderText(null);
        alert.setContentText("注册信息已提交！");
        alert.showAndWait(); // 显示对话框并等待用户关闭
    }
}

