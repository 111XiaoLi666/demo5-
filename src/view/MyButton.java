package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class MyButton extends Button {
    public static Font btnFont = Font.font("Verdana", 12);
    public Color desert = Color.rgb(255,165,0);
    public CornerRadii cornerRadii=new CornerRadii(0.1,0.1,0.1,0.1,true);
    public MyButton(String text) {
        super(text);
        setAlignment(Pos.CENTER);
        setFont(btnFont);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);//需要根据界面大小调整按钮大小
        setOpacity(1);
        setBackground(new Background(new BackgroundFill(desert,cornerRadii,null)));
    }
}
