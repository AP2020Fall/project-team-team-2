package model.Entry;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import model.Player;
import view.GameMenu;

public class FriendEntry {
    private String name;
    private Hyperlink remove = new Hyperlink("Remove");
    private Hyperlink view = new Hyperlink("View");
    public FriendEntry(Player friend)
    {
        name = friend.getUsername();
        remove.setOnAction(event -> {
            System.out.println("Removing must be implemented");

        });
        view.setOnAction(event -> {
            System.out.println("Viewing must be implemented");

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
