package main;

import controller.Controller;
import controller.login.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import view.ViewHandler;
import view.login.WelcomeMenu;

import java.lang.reflect.Method;


public class Client extends Application {
    private static int PORT_NUMBER = 6660;
    private static ClientConnector connector;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeConnector();
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Client::saveFiles));
        ViewHandler.getViewHandler().setWindow(primaryStage);
        ViewHandler.getViewHandler().push(new WelcomeMenu());
        primaryStage.show();
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
        connector.getController().endConnection();
    }

    private static void initializeConnector()
    {
        connector = new ClientConnector();
        connector.connect("localhost",PORT_NUMBER);
    }
    public static ClientConnector getConnector()
    {
        return connector;
    }
}
