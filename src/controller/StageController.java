package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class StageController {
    private final Stage stage;
    private final Scene scene;
    private final Scene loginScene;
    private final Scene registerScene;

    public StageController(Stage stage,LoginController login,Client client) {
        this.stage=stage;
        StartFrame startFrame=new StartFrame(stage,this,client,login);
        LoginFrame loginFrame=new LoginFrame(stage,this,client,login);
        RegisterFrame registerFrame=new RegisterFrame(stage,this,client,login);
        scene=new Scene(startFrame.getGridPane(), 800, 600);
        loginScene=new Scene(loginFrame.getGridPane(),800,600);
        registerScene=new Scene(registerFrame.getGridPane(),800,600);
    }

    public void start() {
        stage.setTitle("开始界面");
        stage.setScene(scene);
        stage.show();
    }

    public void showLogin() {
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }

    public void showRegister() {
        stage.setTitle("Register");
        stage.setScene(registerScene);
        stage.show();
    }

    public void showGame(GameFrame gameFrame) {
        stage.setTitle("游戏界面");
        Scene gameScene = new Scene(gameFrame, 800, 600);
        stage.setScene(gameScene);
        stage.show();
    }


    }

