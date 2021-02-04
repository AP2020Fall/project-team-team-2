package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import view.ViewHandler;
import view.login.WelcomeMenu;


public class Client extends Application {
    private static ClientConnector connector;
    private static ClientInfo clientInfo;
    private static String token;
    public static void updateClientInfo(ClientInfo newClientInfo) {
        clientInfo = newClientInfo;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void initializeConnector() {
        connector = new ClientConnector();
        int PORT_NUMBER = 2220;
        connector.connect("localhost", PORT_NUMBER);
    }

    public static ClientConnector getConnector() {
        return connector;
    }

    public static ClientInfo getClientInfo() {
        return clientInfo;
    }

    public static String getToken() {
        return token;
    }

    public static void updateToken(String newToken) {
        token = newToken;
    }

    @Override
    public void start(Stage primaryStage) {
        clientInfo = new ClientInfo();
        token = new Token().encrypt();
        initializeConnector();
        primaryStage.setOnCloseRequest(event -> {
            connector.getController().endConnection();
            Platform.exit();
        });
        ViewHandler.getViewHandler().setWindow(primaryStage);
        ViewHandler.getViewHandler().push(new WelcomeMenu());
        primaryStage.show();
    }
}
