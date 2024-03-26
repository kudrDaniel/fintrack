package ru.duckcoder.fintrack.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;

public class Client extends Application implements Runnable {
    private final String[] args;

    public Client() {
        this.args = new String[] {};
    }

    public Client(String[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlPath = Client.class.getClassLoader().getResource("fxml/userView.fxml");
        Parent parent = FXMLLoader.load(fxmlPath);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Users");
        stage.show();
    }

    public static void main(String[] args) {
        Thread javaFX = new Thread(
                Thread.currentThread().getThreadGroup(),
                new Client(args),
                "JavaFX Thread");
        javaFX.start();
        try {
            javaFX.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Client.launch(this.args);
    }
}
