package main;

import com.google.gson.Gson;
import controller.ServerMasterController.ServerMasterController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import org.javatuples.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Application {
    private static final int PORT_NUMBER = 6660;
    private static Server server;

    public Server() {
    }

    public static Server getInstance() {
        if (server == null)
            return server = new Server();
        return server;
    }

    //todo make it non javafx
    public static void main(String[] args) throws IOException {
        getInstance();
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

    private void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("[SERVER]: ServerSocket created successfully.");
        while (true) {
            Socket clientSocket;
            try {
                System.out.println("[SERVER]: Waiting for Client...");
                clientSocket = serverSocket.accept();
                System.out.println("[SERVER]: A client Connected!");
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new ClientHandler(clientSocket, dataOutputStream, dataInputStream).start();
            } catch (Exception e) {
                System.err.println("[SERVER]: Error in accepting client!");
                break;
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Server::saveFiles));
        (new Server()).run();
    }

    static class ClientHandler extends Thread {
        Socket clientSocket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ServerMasterController controller;


        public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.clientSocket = clientSocket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            controller = new ServerMasterController();
        }


        @Override
        public void run() {
            String input;
            while (!Thread.interrupted()) {
                try {
                    input = dataInputStream.readUTF();
                    Pair<String, String> answer = controller.takeAction(input);
                    dataOutputStream.writeUTF(new Gson().toJson(answer));
                    dataOutputStream.flush();
                    System.out.println("[SERVER]: Command: " + input + " was sent.");
                    System.out.println("[SERVER]: result: " + answer + " is sent.");
                    if (answer.equals("Connection is terminated.")) {
                        System.out.println("[SERVER]: Connection closed!");
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("[SERVER]: Connection to the client is lost!");
                    Thread.currentThread().interrupt();
                }
            }
        }

    }
}
