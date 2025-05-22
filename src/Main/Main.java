package Main;
import controller.Client;
import controller.Game;
import controller.LoginController;
import controller.StageController;
import javafx.stage.Stage;
import model.Setting;
import model.UserLib;
import javafx.application.Application;
import view.*;

import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        UserLib lib = new UserLib();
        Client client = new Client(lib);
        LoginController login = new LoginController(client, null); // 先传null
        StageController stageController = new StageController(stage,login,client);
        login.setStageController(stageController);
        stageController.start();

    }
}