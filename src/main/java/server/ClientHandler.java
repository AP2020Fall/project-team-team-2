package server;

import controller.login.RegisterController;
import model.Account;
import model.Admin;
import view.AlertMaker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Account user;

    public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        user = null;
    }

    @Override
    public void run() {
        try {
            handleRequest();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest() throws IOException {
        String request = null;
        String response = null;
        request = dataInputStream.readUTF();
        System.out.println("req: " + request);
        if (request.startsWith("Register")) {
            handleRegister(request);
        } else if (request.startsWith("Submit")) {
            handleSubmit(request);
        }
    }

    private void handleSubmit(String request) throws IOException {
        RegisterController controller = new RegisterController();
        String[] splitInput = request.split("#");
        ArrayList<String> additionalInfo = new ArrayList<>();
        for (int i = 3; i < splitInput.length; i++) {
            if (splitInput[i] != null)
                additionalInfo.add(splitInput[i]);
        }
        if (controller.createAccount(splitInput[1], splitInput[2], additionalInfo)){
            dataOutputStream.writeUTF("admin");
        }else {
            dataOutputStream.writeUTF("player");
        }
        dataOutputStream.flush();
    }

    private void handleRegister(String request) throws IOException {
        String username = request.split("#")[1];
        String response;
        RegisterController controller = new RegisterController();
        if (!controller.isUsernameExist(username)) {
            if (Admin.isAdminExist()) {
                response = "Successful#player";
            } else {
                response = "Successful#admin";
            }
        } else {
            response = "failure";
        }
        dataOutputStream.writeUTF(response);
        dataOutputStream.flush();
    }
}
