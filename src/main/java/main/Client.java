package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import view.ViewHandler;
import view.login.WelcomeMenu;


public class Client extends Application {
    private static ClientConnector connector;
    private static ClientInfo clientInfo;

    public static void updateClientInfo(ClientInfo NewClientInfo) {
        clientInfo = NewClientInfo;
    }

    @Override
    public void start(Stage primaryStage) {
        clientInfo = new ClientInfo();
        initializeConnector();
        primaryStage.setOnCloseRequest(event -> {
            connector.getController().endConnection();
            Platform.exit();
        });
        ViewHandler.getViewHandler().setWindow(primaryStage);
        ViewHandler.getViewHandler().push(new WelcomeMenu());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private static void initializeConnector()
    {
        connector = new ClientConnector();
        int PORT_NUMBER = 6660;
        connector.connect("localhost", PORT_NUMBER);
    }
    public static ClientConnector getConnector()
    {
        return connector;
    }

    public static ClientInfo getClientInfo() {
        return clientInfo;
    }
}
