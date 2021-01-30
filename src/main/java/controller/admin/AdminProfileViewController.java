package controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.ClientInfo;
import model.Entry.GameLogSummaryEntry;
import model.GameLogSummary;
import model.Player;

import java.util.ArrayList;

public class AdminProfileViewController {
    private final Player player;

    public AdminProfileViewController(ClientInfo clientInfo) {
        this.player = Player.getPlayerByUsername(clientInfo.getPlayerUsername());
        if (player == null)
            System.err.println("Player passed to AdminProfileViewController is null");
    }

    public String getViewAdminUsername() {
        return player.getUsername();
    }//done

    public String getViewAdminFirstName() {
        return player.getFirstName();
    }//done

    public String getViewAdminLastName() {
        return player.getLastName();
    }//done

    public String getViewAdminBio() {
        return player.getBio();
    }//done

    public String getViewAdminEmail() {
        return player.getEmail();
    }//done

    public String getViewAdminPhoneNumber() {
        return player.getPhoneNumber();
    }//done

    public String getViewAdminDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }//done

    public String getViewAdminWins() {
        return String.valueOf(player.getNumberOfWins());
    }//done

    public String getViewAdminFriendCount() {
        return String.valueOf(player.getFriends().size());
    }//done

    public String getViewAdminPlayerImage() {
        return player.getImageURL();
    }//done

    public ArrayList<GameLogSummary> getGameHistory() {
        return player.getGameLogSummaries();
    }
}
