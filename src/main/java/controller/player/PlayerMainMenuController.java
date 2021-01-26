package controller.player;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import main.ClientInfo;
import model.*;
import model.Entry.*;
import view.TabHandler;
import view.admin.AdminProfileView;
import view.player.PlayerProfileView;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerMainMenuController extends PlayerMainMenuLayoutController {

    private final Player player;

    public PlayerMainMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.player = Objects.requireNonNull(loggedIn,
                "Player passed to PlayerMainMenuController is null.");
    }


    public ArrayList<Game> favoriteGames() {
        //returns the names of player's favourite games.
        /*ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game : player.getFavouriteGames())
            result.add(new GameEntry(game));
        return result;*/
        return player.getFavouriteGames();
    }

    public Game lastGamePlayed() {
        //returns the last game played by player.
        //throws NullPointerException if gameLogs is empty.
        GameLogSummary gameLogSummary = player.getGameLogLastGamePlayed();
        if (gameLogSummary == null) {
            return null;
        }
        /*else
        {
            return new GameEntry(Objects.requireNonNull(Game.getGameByGameName(gameLogSummary.getGameName()),
                    "Game doesn't exist"));
        }*/
        return Objects.requireNonNull(Game.getGameByGameName(gameLogSummary.getGameName()),
                "Game doesn't exist");
    }

    public ArrayList<Game> adminsSuggestions() {
        //returns the list of suggested games to player.
        /*ArrayList<GameEntry> result = new ArrayList<>();
        for (Suggestion suggestion : player.getSuggestions())
            result.add(new GameEntry(suggestion.getGame()));
        return result;*/
        ArrayList<Game> result = new ArrayList<>();
        for (Suggestion suggestion : player.getSuggestions())
            result.add(suggestion.getGame());
        return result;
    }

    public ArrayList<Game> getGames() {
        return Game.getGames();
    }

    public ArrayList<Event> getEvents()
    {
        return Event.getEvents();
    }
    public Event getEvent(String eventId) {
        return Event.getEventById(eventId);
    }
    public Game getGame(String gameName) {
        return Game.getGameByGameName(gameName);
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
    public String getFriendCount() {
        return String.valueOf(player.getFriends().size());
    }
    public String getUsername(){
        return player.getUsername();
    }
    public String getPassword(){
        return player.getPassword();
    }
    public String getPhoneNumber() {
        return player.getPhoneNumber();
    }
    public String getEmail()
    {
        return player.getEmail();
    }
    public String getFirstName()
    {
        return player.getFirstName();
    }
    public String getLastName()
    {
        return player.getLastName();
    }

    public ObservableList<GameLogSummaryEntry> getGameHistory() {
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : player.getGameLogSummaries())
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }

    public String showPoints() {
        //returns the score of player.
        return String.valueOf( player.getScore());
    }

    public void setUsername(String username) {
        player.setUsername(username);
    }
    public void setPassword(String password) {
        player.setPassword(password);
    }
    public void setFirstName(String firstName) {
        player.setFirstName(firstName);
    }
    public void setLastName(String lastName) {
        player.setLastName(lastName);
    }
    public void setEmail(String email) {
        player.setEmail(email);
    }
    public void setPhoneNumber(String phoneNumber) {
        player.setPhoneNumber(phoneNumber);
    }
    public void setBio(String bio) {
        player.setBio(bio);
    }

    public void setPlayerImage(Image givenImage) {
        player.setImage(givenImage);

    }
    public Image getPlayerImage() {
        return player.getImage();
    }


    public Game getGame(GameLogSummaryEntry gameEntry) {
        return Game.getGameByGameName(gameEntry.getGameName());
    }

}
