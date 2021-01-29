package controller.player;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ClientInfo;
import model.Entry.GameLogSummaryEntry;
import model.FriendRequest;
import model.GameLogSummary;
import model.Player;

public class PlayerProfileViewController extends Controller {
    private final Player player;
    private final Player logged;

    public PlayerProfileViewController(ClientInfo clientInfo) {
        super(clientInfo);
        this.player = Player.getPlayerByUsername(clientInfo.getPlayerUsername());
        if(player == null)
            System.err.println("Player passed to PlayerProfileViewController is null");
        this.logged =  Player.getPlayerByUsername(clientInfo.getLoggedInUsername());
        if(player == null)
            System.err.println("Player passed to PlayerProfileViewController is null");
    }

    public String getViewPlayerUsername() {
        return player.getUsername();
    }

    public String getViewPlayerFirstName() {
        return player.getFirstName();
    }

    public String getViewPlayerLastName() {
        return player.getLastName();
    }

    public String getViewPlayerBio(){
        return player.getBio();
    }

    public String getViewPlayerPhoneNumber() {
        return player.getPhoneNumber();
    }

    public String getViewPlayerEmail() {
        return player.getEmail();
    }

    public String getViewPlayerDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }

    public String getViewPlayerWins() {
        return String.valueOf(player.getNumberOfWins());
    }

    public String getViewPlayerFriendCount() {
        return String.valueOf(player.getFriends().size());
    }

    public String getViewPlayerImage() {
        return player.getImageURL();
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

    public boolean areTheSame() {
        return logged.getUsername().equals(player.getUsername());
    }

    public ObservableList<GameLogSummaryEntry> getViewPlayerGameHistory() {
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : player.getGameLogSummaries())
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }

}
