package ru.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Start extends Application {

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("prop.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Chat 1.0");


        primaryStage.setScene(new Scene(root
                , screenSize.getWidth() / 3.5
                , screenSize.getHeight() / 1.5));

        primaryStage.alwaysOnTopProperty();

        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
