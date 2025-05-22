module demo5 {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires jdk.accessibility;

    opens view to javafx.fxml;
    exports view;

    opens ai to javafx.fxml;
    exports ai;

    opens controller to javafx.fxml;
    exports controller;

    opens model to javafx.fxml;
    exports model;

    opens Main to javafx.fxml;
    exports Main;
}