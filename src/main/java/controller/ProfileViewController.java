package controller;

import model.FriendRequest;
import model.Player;

import java.util.Objects;

public class ProfileViewController extends Controller {
    private final Player player;
    private final Player logged;

    public ProfileViewController(Player player) {
        this.player = Objects.requireNonNull(player,
                "Players passed to ProfileViewController is null");
        this.logged = Objects.requireNonNull(((Player) loggedIn),
                "Players passed to ProfileViewController is null");
    }

    public String getUsername() {
        return player.getUsername();
    }

    public String getFirstName() {
        return player.getFirstName();
    }

    public String getLastName() {
        return player.getLastName();
    }

    public String getEmail() {
        return player.getEmail();
    }

    public String getPhoneNumber() {
        return player.getPhoneNumber();
    }

    public String getDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }

    public boolean areFriends() {
        //checks if logged and player are friends.
        return logged.getFriends().contains(player);
    }

    public void addFriend() {
        //sends a FriendRequest to player.
        FriendRequest friendRequest = new FriendRequest(player, logged, generateId());
        friendRequest.sendRequest();
    }


    public void removeFriend() {
        //removes logged from friends of player and removes the player from the friends of logged.
        logged.removeFriend(player);
        player.removeFriend(logged);
    }

    public boolean HasFriendRequestBeenSent() {
        //checks if  logged send a friend request.
        for (FriendRequest friendRequest : player.getReceivedFriendRequests())
            if (friendRequest.getPlayer().getUsername().equals(logged.getUsername()))
                return true;
        return false;
    }
}
