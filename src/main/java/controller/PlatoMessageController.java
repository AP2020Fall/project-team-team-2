package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Entry.PlatoMessageEntry;
import model.Message;
import model.Player;

import java.util.Collections;
import java.util.Objects;

public class PlatoMessageController extends Controller{
    private final Player player;

    public PlatoMessageController() {
        this.player = Objects.requireNonNull(((Player) loggedIn),
                "Player passed to PlatoMessageController is null.");
    }
    public ObservableList<PlatoMessageEntry> platoBotsMessages() {
        //returns the list of messages send to player.
        ObservableList<PlatoMessageEntry> result =  FXCollections.observableArrayList();
        for (Message message : player.getMessages())
            result.add(new PlatoMessageEntry(message));
        //Collections.reverse(result);
        return result;
    }
}
