package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Account;
import model.AccountGSON;
import model.Game;
import org.javatuples.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnector {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ClientMasterController controller;

    public void connect(String proxy, int port) {
        try {
            socket = new Socket(proxy,port);
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public String serverQuery(String userInput)
    {
        try {
            dataOutputStream.writeUTF(userInput);
            dataOutputStream.flush();
            String response = dataInputStream.readUTF();
            System.out.println("[CLIENT]: Server responded : "+ response + " to: " + userInput);
            Pair<String,String> query = new Gson().fromJson(response,  new TypeToken<Pair<String,String>>() {}.getType());
            Client.updateClientInfo(new GsonBuilder().registerTypeAdapter(Account.class, new AccountGSON()).create().
                    fromJson(query.getValue1(),ClientInfo.class));
            return query.getValue0();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }
    public ClientMasterController getController()
    {
        return controller;
    }
}
