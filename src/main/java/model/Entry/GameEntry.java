package model.Entry;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import model.Game;
import view.TabHandler;
import view.player.PlayerGameMenu;

public class GameEntry {
   private  String name;
   private Hyperlink link;
   private  String detail;
   private ImageView avatar;
    public GameEntry(Game game)
    {
        name= game.getName();
        detail = game.getDetails();
        avatar = new ImageView(game.getImage());
        avatar.setFitHeight(48);
        avatar.setFitHeight(48);
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

    public Hyperlink getLink() {
        return link;
    }

    public String getDetail() {
        return detail;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}

