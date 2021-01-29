package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import view.View;
import view.ViewHandler;
import view.login.WelcomeMenu;

public class Main extends Application {

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        ViewHandler.getViewHandler().setWindow(primaryStage);
        ViewHandler.getViewHandler().push(new WelcomeMenu());
        primaryStage.show();
    }

}

