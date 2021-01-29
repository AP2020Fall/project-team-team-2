package controller.player;

import main.ClientInfo;
import model.FriendRequest;
import model.Player;

import java.util.ArrayList;

public class PlayerFriendsMenuController {
    private final Player player;

    public PlayerFriendsMenuController(ClientInfo clientInfo) {
        this.player = Player.getPlayerByUsername(clientInfo.getLoggedInUsername());
        if (player == null)
            System.err.println("Player passed to FriendsMenuController is null.");
    }

    public ArrayList<Player> getFriends() {
        return player.getFriends();

    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return player.getReceivedFriendRequests();
    }

    public void acceptRequest(String friendRequestId) {
        for (FriendRequest friendRequest : player.getReceivedFriendRequests())
            if (friendRequest.getFriendRequestId().equals(friendRequestId))
                friendRequest.acceptRequest();
    }

    public String getFriendRequestPlayerName(String friendRequestId) {
        for (FriendRequest friendRequest : player.getReceivedFriendRequests())
            if (friendRequest.getFriendRequestId().equals(friendRequestId))
                return friendRequest.getPlayer().getUsername();
        return "";
    }

    public void declineRequest(String friendRequestId) {
        for (FriendRequest friendRequest : player.getReceivedFriendRequests())
            if (friendRequest.getFriendRequestId().equals(friendRequestId))
                friendRequest.declineRequest();
    }
}