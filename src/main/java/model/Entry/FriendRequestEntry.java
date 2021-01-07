package model.Entry;

import javafx.scene.control.Hyperlink;
import model.FriendRequest;
import view.ViewHandler;

import java.io.IOException;

public class FriendRequestEntry {
    private String name;
    private Hyperlink accept ;
    private Hyperlink decline;
    public FriendRequestEntry(FriendRequest friendRequest)
    {
        name = friendRequest.getPlayer().getUsername();
        accept = new Hyperlink("accept");
        decline = new Hyperlink("decline");
        accept.setOnAction(event -> {
            friendRequest.acceptRequest();
            try {
                ViewHandler.getViewHandler().refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("Accepting must be implemented");
        });
        decline.setOnAction(event -> {
            friendRequest.declineRequest();
            try {
                ViewHandler.getViewHandler().refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("Declining must be implemented");
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
