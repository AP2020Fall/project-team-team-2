package main;

import com.google.gson.Gson;
import controller.ServerMasterController.SQLConnector;
import controller.ServerMasterController.ServerMasterController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Application {
    private static final int PORT_NUMBER = 2220;
    private static final Server server = new Server();
    private static final ArrayList<Pair<ClientHandler,DataOutputStream>> clients = new ArrayList<>();
    public Server() {
    }

    public static Server getInstance() {
        return server;
    }



    //todo make it non javafx
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public static void updateMap(DataOutputStream value) {
        try {
            Pair<String, String> answer = new Pair<>("updateMap", "discard");
            value.writeUTF(new Gson().toJson(answer));
            value.flush();
        } catch (Exception e) {
            System.err.println("[SERVER]: couldn't refresh the client " + e);
        }
    }

    public static void updateCurrentTimestamp(DataOutputStream value) {
        try {
            Pair<String, String> answer = new Pair<>("updateCurrentTimestamp", "discard");
            value.writeUTF(new Gson().toJson(answer));
            value.flush();
        } catch (Exception e) {
            System.err.println("[SERVER]: couldn't refresh the client " + e);
        }
    }

    public static void exitTheGame(DataOutputStream value) {
        try {
            Pair<String, String> answer = new Pair<>("exitTheGame", "discard");
            value.writeUTF(new Gson().toJson(answer));
            value.flush();
        } catch (Exception e) {
            System.err.println("[SERVER]: couldn't refresh the client " + e);
        }
    }


    private void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("[SERVER]: ServerSocket created successfully.");
        while (true) {
            Socket clientSocket;
            Socket clientReadingSocket;
            try {
                System.out.println("[SERVER]: Waiting for Client...");
                clientSocket = serverSocket.accept();
                System.out.println("[SERVER]: A client Connected!");
                clientReadingSocket = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream ReadingDataOutputStream = new DataOutputStream(new BufferedOutputStream(clientReadingSocket.getOutputStream()));
                ClientHandler clientHandler = new ClientHandler(clientSocket, dataOutputStream, dataInputStream);
                clients.add(new Pair<>(clientHandler,ReadingDataOutputStream));
                clientHandler.start();
            } catch (Exception e) {
                System.err.println("[SERVER]: Error in accepting client!");
                break;
            }
        }
    }

    public static void refresh(DataOutputStream dataOutputStream) {
        try {
            Pair<String, String> answer = new Pair<>("refresh", "discard");
            dataOutputStream.writeUTF(new Gson().toJson(answer));
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("[SERVER]: couldn't refresh the client");
        }
    }

    public static void notify(DataOutputStream dataOutputStream,String header, String notification) {
        try {
            Pair<String, String> answer = new Pair<>("notify", header + "#" + notification);
            dataOutputStream.writeUTF(new Gson().toJson(answer));
            dataOutputStream.flush();
        } catch (Exception e) {
            System.out.println("[SERVER]: couldn't notify the client");
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        (getInstance()).run();
    }

    public static class ClientHandler extends Thread {
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
                    Triplet<String, String, String> answer;
                    input = dataInputStream.readUTF();
                    synchronized (getInstance()) {
                        answer = controller.takeAction(input, getCurrentClient(this));
                        dataOutputStream.writeUTF(new Gson().toJson(answer));
                        dataOutputStream.flush();
                    }
//                    System.out.println("[SERVER]: Command: " + input + " was sent.");
//                    System.out.println("[SERVER]: result: " + answer + " is sent.");
                    if (answer.equals("Connection is terminated.")) {
                        System.out.println("[SERVER]: Connection closed!");
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("[SERVER]: Connection to the client is lost!");
                    clients.remove(this);
                    Thread.currentThread().interrupt();
                }

            }
        }
        public static Pair<ClientHandler,DataOutputStream> getCurrentClient(ClientHandler clientHandler)
        {
            for(Pair<ClientHandler,DataOutputStream> clientHandlerDataOutputStreamPair:clients)
                if(clientHandlerDataOutputStreamPair.getValue0().equals(clientHandler))
                    return clientHandlerDataOutputStreamPair;
                return null;
        }


    }
}
