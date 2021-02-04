package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.ClientMasterController.ClientListenerController;
import controller.ClientMasterController.ClientMasterController;
import controller.ServerMasterController.ServerMasterController;
import model.Account;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.*;
import java.net.Socket;

public class ClientConnector {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ClientMasterController controller;

    public void connect(String proxy, int port) {
        try {
            socket = new Socket(proxy, port);
            System.out.println("[Client]: Successfully connected to server.");
            initialize();
            System.out.println("[Client]: Setup successfully finished.");
        } catch (IOException e) {
            System.err.println("[Client]: Error starting client!");
        }
    }

    private void initialize() {
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            controller = ClientMasterController.getController();
            //(new ServerListener(socket,dataOutputStream,dataInputStream)).start();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String serverQuery(String userInput) {
        synchronized (Client.getConnector()) {
            try {
                dataOutputStream.writeUTF(userInput);
                dataOutputStream.flush();
                String response = dataInputStream.readUTF();
                Triplet<String, String, String> query = new Gson().fromJson(response, new TypeToken<Triplet<String, String, String>>() {
                }.getType());
                System.out.println("[CLIENT]: Server responded : " + query.getValue1() + "\n to: " + userInput + "\n with token: " +
                        query.getValue2());
                Client.updateClientInfo(new Gson().fromJson(query.getValue1(), ClientInfo.class));
                Client.updateToken(query.getValue2());
                return query.getValue0();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return "";
            }
        }
    }


    public ClientMasterController getController() {
        return controller;
    }




    static class ServerListener extends Thread {
        Socket clientSocket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ClientListenerController controller;


        public ServerListener(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.clientSocket = clientSocket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            controller = new ClientListenerController();
        }


        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    String input;
                    String answer;
                    input = dataInputStream.readUTF();

                    answer = controller.takeAction(input);

                    System.out.println("[LISTENER]: Command: " + input + " was sent.");
                    System.out.println("[LISTENER]: result: " + answer + " is sent.");
                    if (answer.equals("Terminate")) {
                        System.out.println("[LISTENER]: Connection closed!");
                        Thread.currentThread().interrupt();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("[LISTER]: Connection to the server is lost!");
                    Thread.currentThread().interrupt();
                }

            }
        }

    }



}
