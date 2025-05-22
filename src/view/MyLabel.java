package view;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class MyLabel extends Label {
    public MyLabel(String text) {
        super(text);
        setAlignment(Pos.CENTER);
        setFont(MyButton.btnFont);
    }

}
