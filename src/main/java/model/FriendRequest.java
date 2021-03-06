package model;

import controller.ServerMasterController.SQLConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendRequest {
    private final String friendId;
    private final String playerId;
    private FriendRequestState accepted;
    private final String friendRequestId;

    public FriendRequest(Player friend, Player player, String id) {
        this.friendId = friend.getAccountId();
        this.playerId = player.getAccountId();
        this.friendRequestId = id;
        this.accepted = FriendRequestState.NOTANSWERED;
        this.add();
    }

    public FriendRequest(Map<String, Object> friendRequest) {
        friendId = (String) friendRequest.get("friend_id");
        playerId = (String) friendRequest.get("player_id");
        friendRequestId = (String) friendRequest.get("friend_request_id");
        accepted = FriendRequestState.valueOf((String) friendRequest.get("status"));
    }

    public Player getFriend() {
        return Player.getPlayerById(friendId);
    }

    public Player getPlayer() {
        return Player.getPlayerById(playerId);
    }

    public String getFriendRequestId() {
        return friendRequestId;
    }

    public void setAccepted(FriendRequestState friendRequestState)
    {
        this.accepted = friendRequestState;
        editField("status",friendRequestState);
    }

    private void add() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("friend_id", this.friendId);
        resultMap.put("player_id", this.playerId);
        resultMap.put("friend_request_id", this.friendRequestId);
        resultMap.put("status", this.accepted);
        SQLConnector.insertInDatabase(resultMap,"friend_requests");
    }

    public void editField(String field, Object value) {
        java.util.Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("friend_request_id", this.friendRequestId);
        Map<String, Object> newValueMap = new HashMap<>();
        newValueMap.put(field, value);
        SQLConnector.updateTable(conditionMap, newValueMap, "friend_requests");
    }

    private static Map<String, Object> SQLFriendRequestSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "friend_requests");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: Friend Request with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount.get(0);
    }

    public void delete() {
        Map<String,Object> event = new HashMap<>();
        event.put("friend_request_id",friendRequestId);
        if(!SQLConnector.deleteFromTable(event,"friend_requests"))
        {
            System.out.println("[MODEL]: FriendRequest with friendRequest ID = " + friendRequestId
                    + " couldn't be deleted");
        }
    }


    /*private static void save(FriendRequest friendRequest) throws IOException {
        String jsonFriendRequest = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
                .toJson(friendRequest);
        FileWriter file = new FileWriter("database" + "\\" + "friendsRequests" + "\\" + friendRequest.getFriendRequestId()
                + ".json");
        file.write(jsonFriendRequest);
        file.close();    }
*/







    public void sendRequest() {
        //send the friend request to the friend
        //throws NullPointerException if there is any error
        Player friend = Player.getPlayerById(friendId);
        Player player = Player.getPlayerById(playerId);
        if (player == null) {
            System.err.println("Player with account ID = " + playerId + " doesn't exists.");
            return;
        }
        if (friend == null) {
            System.err.println("Player with account ID = " + friendId + " doesn't exists.");
            return;
        }
        friend.addReceivedFriendRequest(this);
        player.addSentFriendRequest(this);
    }

    public void acceptRequest() {
        //friends accepts the request, removes the request from the friend but keeps it in player
        //throws NullPointerException if there is any error
        Player friend = Player.getPlayerById(friendId);
        Player player = Player.getPlayerById(playerId);
        if (player == null) {
            System.err.println("Player with account ID = " + playerId + " doesn't exists.");
            return;
        }
        if (friend == null) {
            System.err.println("Player with account ID = " + friendId + " doesn't exists.");
            return;
        }
        player.addFriend(friend);
        friend.addFriend(player);
        friend.removeReceivedFriendRequest(this);
        player.removeReceivedFriendRequest(this);
        setAccepted(FriendRequestState.ACCEPTED);
    }

    public void declineRequest() {
        //friends declines the request, removes the request from the friend but keeps it in player
        //throws NullPointerExecption if there is any error
        Player friend = Player.getPlayerById(friendId);
        Player player = Player.getPlayerById(playerId);
        if (player == null) {
            System.err.println("Player with account ID = " + playerId + " doesn't exists.");
            return;
        }
        if (friend == null) {
            System.err.println("Player with account ID = " + friendId + " doesn't exists.");
            return;
        }
        friend.removeReceivedFriendRequest(this);
        setAccepted(FriendRequestState.DECLINED);
    }

    public static FriendRequest getFriendRequestById(String friendRequestId) {
        Map<String, Object> friendRequest = SQLFriendRequestSearch("friend_request_id",
                friendRequestId);
        if(friendRequest == null || friendRequest.isEmpty())
        {
            return null;
        }
        return new FriendRequest(friendRequest);
    }

}
