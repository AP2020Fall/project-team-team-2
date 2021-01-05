package model.Entry;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import model.Game;
import view.GameMenu;

public class GameEntry {
   private String name;
   private Hyperlink link;
    //ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
        link = new Hyperlink("Open");
        link.setOnAction(event ->
        {
            System.out.println("Open game must be implemented.");
        });
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

    public Hyperlink getLink() {
        return link;
    }
}
