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
import view.AlertMaker;
import view.TabHandler;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientConnector {
    private Socket socket;
    private Socket readingSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ClientMasterController controller;

    public void connect(String proxy, int port) {
        try {
            socket = new Socket(proxy, port);
            System.out.println("[CLIENT]: Successfully connected to server.");
            readingSocket = new Socket(proxy,port);
            System.out.println("[CLIENT]: Reading Socket successfully connected to server.");
            initialize();
            System.out.println("[CLIENT]: Setup successfully finished.");
        } catch (IOException e) {
            System.err.println("[CLIENT]: Error starting client!");
        }
    }

    private void initialize() {
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            controller = ClientMasterController.getController();
            new ServerListener(readingSocket,new DataInputStream(new BufferedInputStream(readingSocket.getInputStream()))).start();

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
//                System.out.println("[CLIENT]: Server responded : " + query.getValue1() + "\n to: " + userInput + "\n with token: "
//                        ); //query.getValue2()
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

    public static void refresh()
    {
        TabHandler.getTabHandler().refresh();
    }

    public static void notify(String header,String body)
    {

    }

    static class ServerListener extends Thread {
        Socket clientSocket;
        DataInputStream dataInputStream;
        ClientListenerController controller;


        public ServerListener(Socket clientSocket, DataInputStream dataInputStream) {
            this.clientSocket = clientSocket;
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
                    System.err.println(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
                    System.out.println("[LISTENER]: Connection to the server is lost!");
                    Thread.currentThread().interrupt();
                }

            }
        }

    }



}
