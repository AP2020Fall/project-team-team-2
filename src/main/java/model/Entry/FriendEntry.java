package model.Entry;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import model.Player;
import view.GameMenu;
import view.ProfileView;
import view.ViewHandler;

import java.io.IOException;

public class FriendEntry {
    private String name;
    private Hyperlink remove = new Hyperlink("Remove");
    private Hyperlink view = new Hyperlink("View");
    public FriendEntry(Player friend,Player player)
    {
        name = friend.getUsername();
        remove.setOnAction(event -> {
            //System.out.println("Removing must be implemented");
            player.removeFriend(friend);
            friend.removeFriend(player);
            try {
                ViewHandler.getViewHandler().refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        view.setOnAction(event -> {
            //System.out.println("Viewing must be implemented");
            try {
                ViewHandler.getViewHandler().push(new ProfileView(friend));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public String getName() {
        return name;
    }

    public Hyperlink getRemove() {
        return remove;
    }

    public Hyperlink getView() {
        return view;
    }
}
