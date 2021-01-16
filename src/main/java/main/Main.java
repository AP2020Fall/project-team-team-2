package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.*;
import view.RiskGameView;
import view.StartGameView;
import view.ViewHandler;
import view.login.WelcomeMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends Application {
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setOnCloseRequest(event -> Platform.exit());
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        ViewHandler.getViewHandler().push(new WelcomeMenu());
        Main.window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void openFiles() {
        try {
            Account.open();
            Event.open();
            Suggestion.open();
            Game.open();
            FriendRequest.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void saveFiles() {
        try {
            Account.save();
            Event.save();
            Suggestion.save();
            Game.save();
            FriendRequest.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}

