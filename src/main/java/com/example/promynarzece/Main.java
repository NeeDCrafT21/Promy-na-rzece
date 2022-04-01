package com.example.promynarzece;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static AnchorPane root;
    static AnchorPane menu;
    static SplitPane splitPane;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        splitPane = fxmlLoader.load();
        menu = (AnchorPane)splitPane.getItems().get(0);
        root = (AnchorPane)splitPane.getItems().get(1);
        Scene scene = new Scene(splitPane);
        stage.setResizable(false);
        stage.setTitle("Promy w Przystaniach");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
