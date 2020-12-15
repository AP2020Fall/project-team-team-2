package model;

public class FriendRequest {
    private String friendId;
    private String playerId;

    public FriendRequest(Player friend, Player player) {
        this.friendId = friend.getAccountId();
        this.playerId = player.getAccountId();
    }

    public Player getFriend() {
        return Player.getPlayerById(friendId);
    }

    public Player getPlayer() {
        return Player.getPlayerById(playerId);
    }

    public void sendRequest() {
        Player.getPlayerById(friendId).getFriendRequests().add(this);
    }

    public void acceptRequest() {
        Player player =  Player.getPlayerById(playerId);
        Player friend =  Player.getPlayerById(friendId);
        player.addFriend(friend);
        friend.addFriend(player);
        friend.getFriendRequests().remove(this);
        player.getFriendRequests().removeIf(o->o.getPlayer().getAccountId().equals(friend.getAccountId()));
    }

    public void declineRequest() {
        Player.getPlayerById(friendId).getFriendRequests().remove(this);
    }

}
