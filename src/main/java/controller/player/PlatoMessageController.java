package controller.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ClientInfo;
import model.Account;
import model.Entry.PlatoMessageEntry;
import model.Message;
import model.Player;

import java.time.LocalDate;
import java.util.*;

public class PlatoMessageController extends PlayerMainMenuLayoutController {
    private final Player player;

    public PlatoMessageController(ClientInfo clientInfo) {
        super(clientInfo);
        this.player = Objects.requireNonNull( loggedIn,
                "Player passed to PlatoMessageController is null.");
    }
    public ArrayList<Message> platoBotsMessages() {
        //returns the list of messages send to player.
        return player.getMessages();
    }
    public Boolean hasMessage() {
        return !player.getMessages().isEmpty();
    }
}
