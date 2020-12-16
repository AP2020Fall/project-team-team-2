package model;

import java.util.ArrayList;
import java.util.Objects;

public class FriendRequest {
    private static final ArrayList<FriendRequest> friendRequests = new ArrayList<>();
    private final String friendId;
    private final String playerId;
    private FriendRequestState accepted;
    private final String friendRequestId;

    public String getFriendRequestId() {
        return friendRequestId;
    }

    public FriendRequest(Player friend, Player player, String id) {
        this.friendId = friend.getAccountId();
        this.playerId = player.getAccountId();
        this.friendRequestId = id;
        this.accepted = FriendRequestState.NOTANSWERED;
        friendRequests.add(this);
    }

    public Player getFriend() {
        return Player.getPlayerById(friendId);
    }

    public Player getPlayer() {
        return Player.getPlayerById(playerId);
    }

    public void sendRequest() {
        //send the friend request to the friend
        //throws NullPointerException if there is any error
        Objects.requireNonNull(Player.getPlayerById(friendId)).addReceivedFriendRequest(this);
        Objects.requireNonNull( Player.getPlayerById(playerId)).addSentFriendRequest(this);
    }

    public void acceptRequest() {
        //friends accepts the request, removes the request from the friend but keeps it in player
        //throws NullPointerException if there is any error
        Player player = Objects.requireNonNull( Player.getPlayerById(playerId));
        Player friend = Objects.requireNonNull( Player.getPlayerById(friendId));
        player.addFriend(friend);
        friend.addFriend(player);
        friend.removeReceivedFriendRequest(this);
        player.removeReceivedFriendRequest(this);
        this.accepted = FriendRequestState.ACCEPTED;
    }

    public void declineRequest() {
        //friends declines the request, removes the request from the friend but keeps it in player
        //throws NullPointerExecption if there is any error
        Player player = Objects.requireNonNull( Player.getPlayerById(playerId));
        Player friend = Objects.requireNonNull( Player.getPlayerById(friendId));
        friend.removeReceivedFriendRequest(this);
        this.accepted = FriendRequestState.DECLINED;
    }

    public static FriendRequest getFriendRequestById(String friendRequestId) {
        for(FriendRequest friendRequest: friendRequests)
        {
            if(friendRequest.friendRequestId.equals(friendRequestId))
                return friendRequest;
        }
        return null;
    }

    public void delete()
    {
        friendRequests.remove(this);
    }
}
