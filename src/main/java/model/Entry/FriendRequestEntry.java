package model.Entry;

import javafx.scene.control.Hyperlink;
import main.Client;
import model.FriendRequest;
import view.TabHandler;

public class FriendRequestEntry {
    private String name;
    private Hyperlink accept ;
    private Hyperlink decline;
    public FriendRequestEntry(FriendRequest friendRequest) {
        name = Client.getConnector().getController().getFriendRequestPlayerName(friendRequest.getFriendRequestId());
        accept = new Hyperlink("accept");
        decline = new Hyperlink("decline");
        accept.setOnAction(event -> {
            //friendRequest.acceptRequest();
            Client.getConnector().getController().acceptRequest(friendRequest.getFriendRequestId());
            TabHandler.getTabHandler().refresh();
            //System.out.println("Accepting must be implemented");
        });
        decline.setOnAction(event -> {
            //friendRequest.declineRequest();
            Client.getConnector().getController().declineRequest(friendRequest.getFriendRequestId());
            TabHandler.getTabHandler().refresh();
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
