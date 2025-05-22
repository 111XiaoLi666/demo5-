package view;

import controller.Client;
import controller.LoginController;
import controller.StageController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.UserLib;
import controller.Game;

public class LoginFrame {
    MyLabel usernameLabel = new MyLabel("用户名:");
    TextField usernameField = new TextField();
    MyLabel passwordLabel = new MyLabel("密码:");
    TextField passwordField = new TextField();
    MyButton loginButton = new MyButton("登录");
    MyButton registerButton=new MyButton("注册");
    public static String username;
    public static String password;
    public static GridPane gridPane=new GridPane();

    public LoginFrame(Stage stage, StageController stageController, Client client, LoginController login) {
        registerButton.setOnAction(e -> {
            stageController.showRegister();
        });
        loginButton.setOnAction(e -> {
            username = usernameField.getText();
            password = passwordField.getText();
            if(client.isMatch(username,password)){
            try {
                login.onLogin(username,stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }}
        });
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(registerButton, 1, 3);
    }
        //要写一个没有账号然后去注册的程序
    public GridPane getGridPane() {
        return gridPane;
    }

}

