package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Entry.GameLogEntry;
import model.GameLog;
import model.Player;

import java.util.Objects;

public class PlayerAccountMenuController extends Controller{
    private final Player player;

    public PlayerAccountMenuController() {

        this.player = Objects.requireNonNull((Player)loggedIn, "Account passed to AccountMenuController is null.");
    }
    public String getBio() {
        return player.getBio();
    }
    public String getMoney() {
        return String.valueOf(player.getMoney());
    }
    public String getDate() {
        return String.valueOf(player.getDayOfRegister());
    }
    public String getWins() {
        return String.valueOf(player.getNumberOfWins());
    }
    public String getFriends() {
        return String.valueOf(player.getFriends().size());
    }

    public void logout()
    {
        loggedIn = null;
    }

    public ObservableList<GameLogEntry> getGameHistory() {
        ObservableList<GameLogEntry> result = FXCollections.observableArrayList();
        for (GameLog gameLog : player.getGameLogs())
            result.add(new GameLogEntry(gameLog));
        return result;
    }
}
