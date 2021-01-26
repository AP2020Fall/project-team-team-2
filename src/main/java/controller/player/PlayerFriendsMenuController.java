package controller.player;

import main.ClientInfo;
import model.FriendRequest;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerFriendsMenuController extends PlayerMainMenuLayoutController {
    private final Player player;

    public PlayerFriendsMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.player = Objects.requireNonNull(loggedIn,
                "Player object passed to FriendsMenuController is null.");
    }

    public ArrayList<Player> getFriends() {
        return player.getFriends();

    }

    public ArrayList<FriendRequest> getFriendRequests() {
       return player.getReceivedFriendRequests();
    }
}