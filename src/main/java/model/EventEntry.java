package model;

public class EventEntry {
    String name;
    //ImageView avatar;
    public EventEntry(Event event)
    {
        name= event.getGameName();
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
