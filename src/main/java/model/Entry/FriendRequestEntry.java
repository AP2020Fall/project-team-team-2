package model.Entry;

import javafx.scene.control.Hyperlink;
import model.FriendRequest;

public class FriendRequestEntry {
    private String name;
    private Hyperlink accept = new Hyperlink("accept");
    private Hyperlink decline = new Hyperlink("decline");
    public FriendRequestEntry(FriendRequest friendRequest)
    {
        name = friendRequest.getPlayer().getUsername();
        accept.setOnAction(event -> {
            System.out.println("Accepting must be implemented");
        });
        decline.setOnAction(event -> {
            System.out.println("Declining must be implemented");
        });
    }

    public String getName() {
        return name;
    }

    public Hyperlink getAccept() {
        return accept;
    }

    public Hyperlink getDecline() {
        return decline;
    }
}
