package server;

import main.Main;
import model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        Server.runServer();
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

    private static void runServer() {
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(Server::saveFiles));
        connectToClients();

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

    private static void connectToClients() {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("waiting for client...");
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new ClientHandler(clientSocket, dataOutputStream, dataInputStream, serverSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
