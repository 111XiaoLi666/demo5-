package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.UserLib;

import view.MyLabel;
import java.util.ArrayList;

public class UserFrame extends UserLib{
    private final ArrayList<User> users;
    private Stage stage;
    Button display=new Button("用户列表");
    Button startgame=new Button("开始新游戏");
    Button file=new Button("读取存档");
    public UserFrame(Stage stage, ArrayList<User> users) {
        this.stage=stage;
        this.users=users;
    }

    public void display(Stage stage) {
        GridPane grid=new GridPane();
        MyLabel l;
        int n = 0;
        for(User u:users) {
            l=new MyLabel(u.getUsername());
            grid.add(l,n,0);
            n++;
        }
        Scene scene=new Scene(grid);
        stage.setTitle("用户列表");
        stage.setScene(scene);
        stage.show();
    }



}
