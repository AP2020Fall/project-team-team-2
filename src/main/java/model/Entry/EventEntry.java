package model.Entry;

import javafx.scene.control.Hyperlink;
import model.Event;
import view.TabHandler;
import view.player.PlayerEventMenu;

public class EventEntry {
    String name;
    private Hyperlink link;
    //ImageView avatar;
    public EventEntry(Event event)
    {
        name= event.getGameName();
        link = new Hyperlink("join");
        link.setOnAction(events ->
        {
            TabHandler.getTabHandler().push(new PlayerEventMenu(event));
        });
    }

    public EventEntry(String title)
    {
        name = title;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
