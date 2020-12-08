package controller;

import model.FriendRequest;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class FriendsMenuController extends Controller{
    private Player player;

    public FriendsMenuController(Player player) {
        this.player = Objects.requireNonNull( player,
                "Player object passed to FriendsMenuController is null.");
    }

    public ArrayList<String> showFriends() {
        //returns the list of player's friends' usernames.
        ArrayList<String> result = new ArrayList<>();
        for (Player friend: player.getFriends())
        {
            result.add(friend.getUsername());
        }
        return result;
    }

    public void removeFriend(String username) {
        //removes username from friends of player and removes the player from the friends of username.
        //throws NullPointerError if username doesn't exist in friends list of player.
        Player friend = Objects.requireNonNull( player.getFriendByUsername(username),
                "Username passed to FriendsMenuController.removeFriend doesn't exist in friends list.");
        friend.getFriends().removeIf(a->a.getUsername().equals(player.getUsername()));
        player.getFriends().remove(friend);
    }

    public boolean friendExists(String username)
    {
        //returns true if username is a friend of player.
        for (Player friend : player.getFriends())
            if (friend.getUsername().equals(username))
                return true;
            return false;
    }
    public String showUserProfile(String username) {
        //returns username.toString().
        //throws NullPointerError if username doesn't exist in friends list of player.
        return Objects.requireNonNull( player.getFriendByUsername(username),
                "Username passed to FriendsMenuController.showUserProfile doesn't exist in friends list.")
                .toString();
    }

    public void addFriend(String username) {
        //sends a FriendRequest to username
        //throws NullPointerError if username doesn't exist.
        Player friend = Objects.requireNonNull(Player.getPlayerByUsername(username),
                "Username passed to FriendsMenuController.addFriend doesn't exist.");
        FriendRequest friendRequest = new FriendRequest(friend,player);
        friendRequest.sendRequest();
    }

    public ArrayList<String> showFriendRequests() {
        //returns the username of those who send a friendRequest to player.
        ArrayList<String> result = new ArrayList<>();
        for(FriendRequest friendRequest: player.getFriendRequests())
            result.add(friendRequest.getFriend().getUsername());
        return result;
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
    }


}
