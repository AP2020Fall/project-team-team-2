package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import view.GameMenu;
import view.ViewHandler;

import java.io.IOException;

public class GameEntry {
   private String name;
   private Hyperlink link;
    //ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
        link = new Hyperlink("Open");
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new GameMenu(game);
            }
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
