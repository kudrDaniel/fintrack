package ru.duckcoder.fintrack.frontend.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationFX extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        ApplicationFX.launch(args);
    }
}
