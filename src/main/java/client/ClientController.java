package client;

import java.io.*;
import java.net.Socket;

public class ClientController {

    protected String connectToServer(String request) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        System.out.println("Successfully connected to server!");
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dataOutputStream.writeUTF(request);
        dataOutputStream.flush();
        return dataInputStream.readUTF();
    }



}
