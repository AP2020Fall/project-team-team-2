package controller.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import model.Entry.FriendEntry;
import model.Entry.FriendRequestEntry;
import model.Entry.PlatoMessageEntry;
import model.Event;
import model.FriendRequest;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerFriendsMenuController extends PlayerMainMenuLayoutController {
    private final Player player;

    public PlayerFriendsMenuController() {
        this.player = Objects.requireNonNull(loggedIn,
                "Player object passed to FriendsMenuController is null.");
    }

    public ArrayList<String> showFriends() {
        //returns the list of player's friends' usernames.
        ArrayList<String> result = new ArrayList<>();
        for (Player friend : player.getFriends()) {
            result.add(friend.getUsername());
        }
        return result;
    }

    public ObservableList<TreeItem<FriendEntry>> getFriends() {
        ObservableList<TreeItem<FriendEntry>> result = FXCollections.observableArrayList();
        for (Player friend : player.getFriends()) {
            result.add(new TreeItem<>(new FriendEntry(friend,player)));
        }
        return result;
    }

    public ObservableList<FriendRequestEntry> getFriendRequests() {
        ObservableList<FriendRequestEntry> result = FXCollections.observableArrayList();
        for (FriendRequest friendRequest : player.getReceivedFriendRequests()){
            result.add(new FriendRequestEntry(friendRequest));
        }
        return result;
    }

    public Player getFriend(FriendEntry friendEntry) {
        return Player.getPlayerByUsername(friendEntry.getName());
    }
    public Player getFriend(FriendRequestEntry friendRequestEntry)
    {
        return Player.getPlayerByUsername(friendRequestEntry.getName());
    }
}