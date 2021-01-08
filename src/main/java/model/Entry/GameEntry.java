package model.Entry;

import javafx.scene.control.Hyperlink;
import model.Game;
import view.TabHandler;
import view.player.PlayerGameMenu;

public class GameEntry {
   private final String name;
   private Hyperlink link;
    //ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
        link = new Hyperlink("Open");
        link.setOnAction(event ->
        {
            TabHandler.getTabHandler().push(new PlayerGameMenu(game));
        });
    }


    public GameEntry(String title)
    {
        name = title;
    }
    public String getName() {
        return name;
    }

    /*public void setName(String name) {
        this.name = name;
    }*/

    public Hyperlink getLink() {
        return link;
    }
}
