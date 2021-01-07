package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import model.Entry.FriendEntry;
import model.Entry.FriendRequestEntry;
import model.FriendRequest;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class FriendsMenuController extends Controller {
    private final Player player;

    public FriendsMenuController() {
        this.player = Objects.requireNonNull((Player) loggedIn,
                "Player object passed to FriendsMenuController is null.");
    }


    public ObservableList<TreeItem<FriendEntry>> getFriends() {
        ObservableList<TreeItem<FriendEntry>> result = FXCollections.observableArrayList();
        for (Player friend : player.getFriends()) {
            result.add(new TreeItem<>(new FriendEntry(friend,player)));
        }
        return result;
    }


    public ObservableList<TreeItem<FriendRequestEntry>> getFriendRequest() {
        ObservableList<TreeItem<FriendRequestEntry>> result = FXCollections.observableArrayList();
        for (FriendRequest friendRequest : player.getReceivedFriendRequests()){
            result.add(new TreeItem<>(new FriendRequestEntry(friendRequest)));
        }
        return result;
    }

    public ArrayList<String> showFriends() {
        //returns the list of player's friends' usernames.
        ArrayList<String> result = new ArrayList<>();
        for (Player friend : player.getFriends()) {
            result.add(friend.getUsername());
        }
        return result;
    }
/*
    public void removeFriend(String username) {
        //removes username from friends of player and removes the player from the friends of username.
        //throws NullPointerError if username doesn't exist in friends list of player.
        Player friend = Objects.requireNonNull(player.getFriendByUsername(username),
                "Username passed to FriendsMenuController.removeFriend doesn't exist in friends list.");
        friend.removeFriend(player);
        player.removeFriend(friend);
    }
*/
/*
    public boolean friendExists(String username) {
        //returns true if username is a friend of player.
        for (Player friend : player.getFriends())
            if (friend.getUsername().equals(username))
                return true;
        return false;
    }
    public String showUserProfile(String username) {
        //returns username.toString().
        //throws NullPointerError if username doesn't exist in friends list of player.
        return Objects.requireNonNull(player.getFriendByUsername(username),
                "Username passed to FriendsMenuController.showUserProfile doesn't exist in friends list.")
                .toString();
    }
*/
  /*
    public void addFriend(String username) {
        //sends a FriendRequest to username
        //throws NullPointerError if username doesn't exist.
        Player friend = Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to FriendsMenuController.addFriend doesn't exist.");
        FriendRequest friendRequest = new FriendRequest(friend, player, generateId());
        friendRequest.sendRequest();
    }

    public ArrayList<String> showFriendRequests() {
        //returns the username of those who send a friendRequest to player.
        ArrayList<String> result = new ArrayList<>();
        for (FriendRequest friendRequest : player.getReceivedFriendRequests())
            result.add(friendRequest.getPlayer().getUsername());
        return result;
    }

    public boolean hasSentFriendRequest(String username) {
        //checks if the username send a friend request
        for (FriendRequest friendRequest : player.getReceivedFriendRequests())
            if (friendRequest.getPlayer().getUsername().equals(username))
                return true;
        return false;
    }


    public void acceptFriendRequest(String username) {
        //accepts the request of username and adds him to friends. adds player to friends of username.
        //throws NullPointerException if no FriendRequest from username was sent to player.
        FriendRequest friendRequest = Objects.requireNonNull(player.getFriendRequestByUsername(username),
                "Username passed to FriendsRequest.acceptFriendRequest didn't sent a friend request.");
        friendRequest.acceptRequest();
    }

    public void declineFriendRequest(String username) {
        //declines the request.
        //throws NullPointerException if no FriendRequest from username was sent to player.
        FriendRequest friendRequest = Objects.requireNonNull(player.getFriendRequestByUsername(username),
                "Username passed to FriendsRequest.acceptFriendRequest didn't sent a friend request.");
        friendRequest.declineRequest();
    }*/
}