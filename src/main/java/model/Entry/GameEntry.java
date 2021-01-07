package model.Entry;

import javafx.scene.control.Hyperlink;
import model.Game;
import view.GameMenu;
import view.ViewHandler;

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
            ViewHandler.getViewHandler().push(new GameMenu(game));
           // System.out.println("Open game must be implemented.");
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
