package view;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class MyTitle extends Label {
    public MyTitle(String text) {
        super(text);
        setAlignment(Pos.CENTER);
        setFont(MyButton.btnFont);
    }

}
