package model;

public class FriendRequest {
    private Player friend;
    private Player player;

    public FriendRequest(Player friend, Player player) {
        this.friend = friend;
        this.player = player;
    }
}
