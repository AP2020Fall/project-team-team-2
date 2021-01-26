package controller.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Client;
import main.ClientInfo;
import model.Account;
import model.Entry.PlatoMessageEntry;
import model.Message;
import model.Player;

import java.time.LocalDate;
import java.util.*;

public class PlatoMessageController {
    private final Player player;

    public PlatoMessageController(ClientInfo clientInfo) {
        this.player = Player.getPlayerByUsername(clientInfo.getPlayerUsername());
        if(player == null)
            System.err.println("Player passed to PlatoMessageController is null.");
    }
    public ArrayList<Message> platoBotsMessages() {
        //returns the list of messages send to player.
        return player.getMessages();
    }
    public Boolean hasMessage() {
        return !player.getMessages().isEmpty();
    }
}
