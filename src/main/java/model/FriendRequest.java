package model;

public class FriendRequest {
    private Player friend;
    private Player player;

    public FriendRequest(Player friend, Player player) {
        this.friend = friend;
        this.player = player;
    }

    public Player getFriend() {
        return friend;
    }

    public Player getPlayer() {
        return player;
    }

    public void sendRequest() {
        friend.getFriendRequests().add(this);
    }

    public void acceptRequest() {
        player.getFriends().add(friend);
        friend.getFriends().add(player);
        friend.getFriendRequests().remove(this);
    }

    public void declineRequest() {
        friend.getFriendRequests().remove(this);
    }

}
