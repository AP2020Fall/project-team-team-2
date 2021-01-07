package model;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FriendRequest {
    private static final ArrayList<FriendRequest> friendRequests = new ArrayList<>();
    private final String friendId;
    private final String playerId;
    private FriendRequestState accepted;
    private final String friendRequestId;

    public static void open() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "friendsRequests");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                friendRequests.add(openFriendRequest(file));
            }
        }

    }

    private static FriendRequest openFriendRequest(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), FriendRequest.class);

    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) json.append(reader.nextLine());
        reader.close();
        return json;
    }

    public static void save() throws IOException {
        for (FriendRequest friendRequest : friendRequests) {
            save(friendRequest);
        }
    }

    public void delete() {
        File file = new File("database" + "\\" + "friendsRequests" + "\\" + this.friendRequestId + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }
        friendRequests.remove(this);

    }


    private static void save(FriendRequest friendRequest) throws IOException {
        String jsonFriendRequest = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
                .toJson(friendRequest);
        FileWriter file = new FileWriter("database" + "\\" + "friendsRequests" + "\\" + friendRequest.getFriendRequestId()
                + ".json");
        file.write(jsonFriendRequest);
        file.close();    }

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

}
