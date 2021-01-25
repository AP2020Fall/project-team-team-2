package model.Entry;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import model.Game;
import view.TabHandler;
import view.player.PlayerGameMenu;

public class GameEntry {
   private  String name;
   private  String detail;
   private transient ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
        detail = game.getDetails();
        avatar = new ImageView(game.getImage());
        System.out.println("not her");
        avatar.setFitHeight(48);
        avatar.setFitWidth(48);
    }


    public GameEntry(String title)
    {
        name = title;
        detail = "";
    }
    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}

