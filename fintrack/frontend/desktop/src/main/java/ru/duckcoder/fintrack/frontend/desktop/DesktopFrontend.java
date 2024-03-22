package ru.duckcoder.fintrack.frontend.desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class DesktopFrontend extends Application implements Runnable {
    private final String[] args;

    public DesktopFrontend() {
        this.args = new String[] {};
    }

    public DesktopFrontend(String[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DesktopFrontend.launch(args);
    }

    @Override
    public void run() {
        DesktopFrontend.main(this.args);
    }
}
