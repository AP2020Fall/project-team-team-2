package controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import main.ClientInfo;
import model.Entry.GameLogSummaryEntry;
import model.Event;
import model.FriendRequest;
import model.GameLogSummary;
import model.Player;

import java.util.Objects;

public class AdminProfileViewController extends AdminMainMenuLayoutController{
    private final Player player;

    public AdminProfileViewController(ClientInfo clientInfo)
    {
        super(clientInfo);
        this.player = Player.getPlayerByUsername(clientInfo.getPlayerUsername());
        if(player == null)
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

    public String getViewAdminEmail() {
        return player.getEmail();
    }//done

    public String getViewAdminPhoneNumber() {
        return player.getPhoneNumber();
    }//done

    public String getViewAdminDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }//done

    public String getViewAdminBio(){
        return player.getBio();
    }//done


    public ObservableList<GameLogSummaryEntry> getGameHistory() {
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : player.getGameLogSummaries())
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }
    public String getViewAdminWins() {
        return String.valueOf(player.getNumberOfWins());
    }//done
    public String getViewAdminFriendCount() {
        return String.valueOf(player.getFriends().size());
    }//done

    public String getViewAdminPlayerImage() {
        return player.getImageURL();
    }//done
}
