package main;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.SceneManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            SceneManager sceneManager = new SceneManager(primaryStage);
            sceneManager.showMenuScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
