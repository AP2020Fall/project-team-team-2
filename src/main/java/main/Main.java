package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import view.StartGameView;
import view.ViewHandler;
import view.login.WelcomeMenu;

public class Main extends Application {
    public static Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setOnCloseRequest(event -> Platform.exit());
        openFiles();
        ViewHandler viewHandler = ViewHandler.getViewHandler();
        viewHandler.push(new WelcomeMenu());
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

}

