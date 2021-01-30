package controller.admin;

import controller.Controller;
import main.ClientInfo;
import model.Message;

import java.time.LocalDateTime;

public class AdminMessageViewController extends Controller {
    //private Admin admin;
    public AdminMessageViewController(ClientInfo clientInfo)
    {
        super(clientInfo);
        //this.admin = (Admin) Account.getAccountByUsername(clientInfo.getLoggedInUsername());
        //if(admin == null)
         //   System.err.println("Admin passed to AdminMessageViewController is null");
    }
    /*
    public ArrayList<ArrayList<PlatoMessageEntry>> platoBotsMessages() {
        //returns the list of messages send to player.
        ArrayList<ArrayList<PlatoMessageEntry>> result = new   ArrayList<>();


        TreeMap<LocalDate, List<Message>> map = new TreeMap<>();
        for (Message item : admin.getSentMessages()) {
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

        return result;//
    }
    public PlatoMessageEntry getMessageRoot()
    {
        return new PlatoMessageEntry(getAdmin().getImage());
    }
    public boolean hasMessage() {
        return !admin.getSentMessages().isEmpty();
    }
*/
    public void sendMessage(String messageText) {
        //sends message to all player
        Message message = new Message(messageText, generateId(), LocalDateTime.now());
        message.sendMessage();
    }
}
