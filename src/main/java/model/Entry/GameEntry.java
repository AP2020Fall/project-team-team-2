package model.Entry;

import javafx.scene.control.Hyperlink;
import model.Game;
import view.TabHandler;
import view.player.PlayerGameMenu;

public class GameEntry {
   private  String name;
   private Hyperlink link;
   private  String detail;
    //ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
        detail = game.getDetails();
        link = new Hyperlink("Open");
        link.setOnAction(event ->
        {
            TabHandler.getTabHandler().push(new PlayerGameMenu(game));
        });
    }


    public GameEntry(String title)
    {
        name = title;
        detail = "";
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

    public String getDetail() {
        return detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

