package model.Entry;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import main.Client;
import model.Player;
import view.TabHandler;
import view.player.PlayerProfileView;
import view.ViewHandler;

public class FriendEntry {
    private String name;
    private Hyperlink remove = new Hyperlink("Remove");
    private ImageView avatar;
    public FriendEntry(String friendUsername,String playerUsername) {
        name = friendUsername;
        avatar = new ImageView( Client.getConnector().getController().searchPlayer(friendUsername).getImage());
        avatar.setFitHeight(48);
        avatar.setFitWidth(48);
        remove.setOnAction(event -> {
            //System.out.println("Removing must be implemented");
            //player.removeFriend(friend);
            //friend.removeFriend(player);
            Client.getConnector().getController().removeFriend(playerUsername,friendUsername);
            TabHandler.getTabHandler().refresh();
        });

    }

    public String getName() {
        return name;
    }

    public Hyperlink getRemove() {
        return remove;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
