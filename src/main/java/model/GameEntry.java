package model;

public class GameEntry {
    String name;
    //ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
    }

    public GameEntry(String title)
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
