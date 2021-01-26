package controller.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import main.Client;
import main.ClientInfo;
import model.Entry.GameLogSummaryEntry;
import model.FriendRequest;
import model.Game;
import model.GameLogSummary;
import model.Player;

import java.util.Objects;

public class PlayerProfileViewController extends PlayerMainMenuLayoutController {
    private final Player player;
    private final Player logged;

    public PlayerProfileViewController(ClientInfo clientInfo) {
        super(clientInfo);
        this.player = Objects.requireNonNull(clientInfo.getPlayer(),
                "Players passed to PlayerProfileViewController is null");
        this.logged = Objects.requireNonNull(loggedIn,
                "Logged passed to PlayerProfileViewController is null");
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

    public String getViewPlayerDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }

    public String getBio(){
        return player.getBio();
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

    public ObservableList<GameLogSummaryEntry> getGameHistory() {
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : player.getGameLogSummaries())
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }
    public String getViewPlayerWins() {
        return String.valueOf(player.getNumberOfWins());
    }
    public String getViewPlayerFriendCount() {
        return String.valueOf(player.getFriends().size());
    }

    public Player getPlayer() {
        return player;
    }
    public Game getGame(GameLogSummaryEntry gameEntry) {
        return Game.getGameByGameName(gameEntry.getGameName());
    }
}
