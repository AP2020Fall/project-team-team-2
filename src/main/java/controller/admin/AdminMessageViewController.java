package controller.admin;

import controller.Controller;
import main.ClientInfo;
import model.Account;
import model.Admin;
import model.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdminMessageViewController extends Controller {
    private Admin admin;
    public AdminMessageViewController(ClientInfo clientInfo)
    {
        super(clientInfo);
        this.admin = (Admin) Account.getAccountByUsername(clientInfo.getLoggedInUsername());
        if(admin == null)
            System.err.println("Admin passed to AdminMessageViewController is null");
    }
    public ArrayList<Message> platoBotsMessages() {
        //returns the list of messages send to player.
        return admin.getSentMessages();
    }

    public boolean hasMessage() {
        return !admin.getSentMessages().isEmpty();
    }

    public void sendMessage(String messageText) {
        //sends message to all player
        Message message = new Message(messageText, generateId(), LocalDateTime.now());
        message.sendMessage();
    }
}
