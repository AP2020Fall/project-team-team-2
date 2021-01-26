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
    public ArrayList<ArrayList<PlatoMessageEntry>> platoBotsMessages() {
        //returns the list of messages send to player.
        ArrayList<ArrayList<PlatoMessageEntry>> result = new   ArrayList<>();


        Map<LocalDate, List<Message>> map = new HashMap<LocalDate, List<Message>>();
        for (Message item : player.getMessages()) {
            List<Message> list = map.get(item.getTime().toLocalDate());
            if (list == null) {
                list = new ArrayList<Message>();
                map.put(item.getTime().toLocalDate(), list);
            }
            list.add(item);
        }
        for(Map.Entry<LocalDate,List<Message>> mapEntry: map.entrySet())
        {

            ArrayList<PlatoMessageEntry> platoMessageEntries = new ArrayList<>();
            platoMessageEntries.add(new PlatoMessageEntry(mapEntry.getKey()));
            for(Message message: mapEntry.getValue())
                platoMessageEntries.add(new PlatoMessageEntry(message));
            result.add(platoMessageEntries);
        }

        return result;
    }
    public PlatoMessageEntry getMessageRoot()
    {
        return new PlatoMessageEntry();
    }
    public boolean hasMessage() {
        return !player.getMessages().isEmpty();
    }
}
