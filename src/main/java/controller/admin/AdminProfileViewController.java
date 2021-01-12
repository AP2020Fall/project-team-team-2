package controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import model.Entry.GameLogSummaryEntry;
import model.FriendRequest;
import model.GameLogSummary;
import model.Player;

import java.util.Objects;

public class AdminProfileViewController extends AdminMainMenuLayoutController{
    private final Player player;

    public AdminProfileViewController(Player player) {
        this.player = Objects.requireNonNull(player,
                "Players passed to AdminProfileViewController is null");
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

    public String getDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }

    public String getBio(){
        return player.getBio();
    }


    public ObservableList<GameLogSummaryEntry> getGameHistory() {
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : player.getGameLogSummaries())
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }
    public String getWins() {
        return String.valueOf(player.getNumberOfWins());
    }
    public String getFriendCount() {
        return String.valueOf(player.getFriends().size());
    }

    public Image getPlayerImage() {
        return player.getImage();
    }
}
