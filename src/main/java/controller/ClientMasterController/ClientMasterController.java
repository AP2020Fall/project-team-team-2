package controller.ClientMasterController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Client;
import main.Command;
import model.*;
import model.Entry.*;
import org.controlsfx.control.Notifications;
import view.TabHandler;
import view.admin.AdminProfileView;
import view.player.PlayerProfileView;
import view.player.PlayerRunGameView;
import view.risk.RiskGameView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.Map;

public class ClientMasterController {
    private static ClientMasterController controller;

    private ClientMasterController() {
    }

    public static ClientMasterController getController() {
        if (controller == null)
            return controller = new ClientMasterController();
        else
            return controller;
    }

    public void endConnection() {
        Command command = new Command("endConnection", "", new ArrayList<>(), Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void removeFriend(String playerUsername, String friendUsername) {
    }


    public Player getFriend(String name) {
        return searchPlayer(name);
    }

    //######################## Controller Commands ########################\\

    public boolean checkPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkPhoneNumber", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean checkEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkEmail", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean checkMoney(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkNumber", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean usernameExist(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("usernameExist", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean usernameAndPasswordMatch(String text, String text1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        Command command = new Command("usernameAndPasswordMatch", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean isUsernamePlayer(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("isUsernamePlayer", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public Player searchPlayer(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("searchPlayer", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Player.class);
    }

    public boolean checkStartDate(LocalDate value) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(value);
        Command command = new Command("checkStartDate", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean checkEndDate(LocalDate value) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(value);
        Command command = new Command("checkEndDate", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean checkRelativeDate(LocalDate value, LocalDate value1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(value);
        params.add(value1);
        Command command = new Command("checkRelativeDate", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean checkNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkNumber", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public ObservableList<MenuItem> getSearchQuery(String searchQuery) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(searchQuery);
        Command command = new Command("usernameFuzzySearchTop5", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> top5Players = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {
        }.getType());
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        for (Player player : top5Players) {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> TabHandler.getTabHandler().push(new PlayerProfileView(player.getUsername())));
            menuItem.setText(player.getUsername());
            result.add(menuItem);
        }
        if (result.isEmpty()) {
            result.add(new MenuItem("No similar user found."));
        }
        return result;
    }

    public ObservableList<MenuItem> getSearchQuery(JFXTextField textField, String searchQuery, PlayerRunGameView controller) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(searchQuery);
        Command command = new Command("usernameFuzzySearchTop5", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> top5Players = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {
        }.getType());
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        for (Player player : top5Players) {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> {
                textField.setText(player.getUsername());
                controller.update(textField);
            });
            menuItem.setText(player.getUsername());
            result.add(menuItem);
        }
        if (result.isEmpty()) {
            result.add(new MenuItem("No similar user found."));
        }
        return result;
    }

    public ObservableList<MenuItem> getAdminSearchQuery(String searchQuery) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(searchQuery);
        Command command = new Command("usernameFuzzySearchTop5", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> top5Players = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {
        }.getType());
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        for (Player player : top5Players) {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> TabHandler.getTabHandler().push(new AdminProfileView(player.getUsername())));
            menuItem.setText(player.getUsername());
            result.add(menuItem);
        }
        if (result.isEmpty()) {
            result.add(new MenuItem("No similar user found."));
        }
        return result;
    }

    public boolean adminExists() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("adminExists", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean doesGameExist(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("doesGameExist", "controller.Controller"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }
    //######################## RegisterMenuController Commands ########################\\

    public boolean createAccount(String text, String text1, ArrayList<String> additionalInfo) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(additionalInfo);
        Command command = new Command("createAccount", "controller.login.RegisterController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    //######################## LoginMenuController Commands ########################\\

    public boolean login(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("login", "controller.login.LoginController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public void delete(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("delete", "controller.login.LoginController", params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    //######################## PlayerGameMenuController Commands ########################\\

    public boolean isFavorite() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("isFavorite", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public void removeFromFavorites() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("removeFromFavorites", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void addToFavorites() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addToFavorites", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getGameDetails() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDetails", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayedFrequency() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPlayedFrequency", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getWinsCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getWinsCount", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getGameImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGame", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Game.class).getImage();
    }

    public boolean hasPlayedGame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("hasPlayedGame", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public ArrayList<GameLogEntry> getGameLog() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameLog", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<GameLog> gameLogs = new Gson().fromJson(answer, new TypeToken<ArrayList<GameLog>>() {
        }.getType());
        ArrayList<GameLogEntry> result = new ArrayList<>();
        for (GameLog gameLog : gameLogs) {
            result.add(new GameLogEntry(gameLog));
        }
        return result;
    }

    public ObservableList<ScoreboardEntry> getScoreboard() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getScoreboard", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Scoreboard.Entry> scoreboard = new Gson().fromJson(answer, new TypeToken<ArrayList<Scoreboard.Entry>>() {
        }.getType());
        ObservableList<ScoreboardEntry> result = FXCollections.observableArrayList();
        for (Scoreboard.Entry entry : scoreboard) {
            result.add(new ScoreboardEntry(entry));
        }
        return result;
    }

    public String getCasualEvent() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCasualEvent", "controller.player.PlayerGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    //######################## PlayerFriendsMenu Commands ########################\\

    public ObservableList<TreeItem<FriendEntry>> getFriends() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriends", "controller.player.PlayerFriendsMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> friends = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {
        }.getType());
        ObservableList<TreeItem<FriendEntry>> result = FXCollections.observableArrayList();
        for (Player friend : friends) {
            result.add(new TreeItem<>(new FriendEntry(friend.getUsername(), Client.getClientInfo().getLoggedInUsername())));
        }
        return result;
    }

    public ObservableList<FriendRequestEntry> getFriendRequests() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriendRequests", "controller.player.PlayerFriendsMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<FriendRequest> friendRequests = new Gson().fromJson(answer, new TypeToken<ArrayList<FriendRequest>>() {
        }.getType());
        ObservableList<FriendRequestEntry> result = FXCollections.observableArrayList();
        for (FriendRequest friendRequest : friendRequests) {

            result.add(new FriendRequestEntry(friendRequest));
        }
        return result;
    }

    public void acceptRequest(String friendRequestId) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(friendRequestId);
        Command command = new Command("acceptRequest", "controller.player.PlayerFriendsMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getFriendRequestPlayerName(String friendRequestId) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(friendRequestId);
        Command command = new Command("getFriendRequestPlayerName", "controller.player.PlayerFriendsMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void declineRequest(String friendRequestId) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(friendRequestId);
        Command command = new Command("declineRequest", "controller.player.PlayerFriendsMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    //######################## PlayerEventMenu Commands ########################\\

    public String getEventGameName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameName", "controller.player.PlayerEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getEventStartDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getStartDate", "controller.player.PlayerEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getEventEndDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEndDate", "controller.player.PlayerEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getEventScore() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getScore", "controller.player.PlayerEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getEventComment() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getComment", "controller.player.PlayerEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getEventImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getImage", "controller.player.PlayerEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    //######################## PlayerMainMenuController Commands ########################\\

    public String getPlayerUsername() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getUsername", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerUsername(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setUsername", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerPassword() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPassword", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerPassword(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPassword", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerFirstName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFirstName", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerFirstName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setFirstName", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerLastName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getLastName", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerLastName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setLastName", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerBio() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getBio", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerBio(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setBio", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerPhoneNumber() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPhoneNumber", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPhoneNumber", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerEmail() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEmail", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setPlayerEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setEmail", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getPlayerDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDate", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerWins() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getWins", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerMoney() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getMoney", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriendCount", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerPoints() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPoints", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getPlayerImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getImage", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    public void setPlayerImage(String givenImage) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(givenImage);
        Command command = new Command("setImage", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public ArrayList<GameEntry> favoriteGames() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("favoriteGames", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> favouriteGames = new Gson().fromJson(answer, new TypeToken<ArrayList<Game>>() {
        }.getType());
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game : favouriteGames)
            result.add(new GameEntry(game));
        return result;
    }

    public GameEntry lastGamePlayed() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("lastGamePlayed", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        Game lastPlayed = new Gson().fromJson(answer, Game.class);
        if (lastPlayed == null)
            return new GameEntry("No game has been played");
        else
            return new GameEntry(lastPlayed);
    }

    public ArrayList<GameEntry> adminsSuggestions() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("adminsSuggestions", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> suggested = new Gson().fromJson(answer, new TypeToken<ArrayList<Game>>() {
        }.getType());
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game : suggested)
            result.add(new GameEntry(game));
        return result;
    }

    public ObservableList<GameLogSummaryEntry> getGameHistory() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameHistory", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<GameLogSummary> gameLogSummaries = new Gson().fromJson(answer, new TypeToken<ArrayList<GameLogSummary>>() {
        }.getType());
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : gameLogSummaries)
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }

    public ObservableList<GameEntry> getGames() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGames", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> games = new Gson().fromJson(answer, new TypeToken<ArrayList<Game>>() {
        }.getType());
        ObservableList<GameEntry> result = FXCollections.observableArrayList();
        for (Game game : games)
            result.add(new GameEntry(game));
        return result;
    }

    public ArrayList<EventEntry> getEvents() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEvents", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Event> events = new Gson().fromJson(answer, new TypeToken<ArrayList<Event>>() {
        }.getType());
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : events)
            result.add(new EventEntry(event));
        return result;
    }

    //######################## PlayerPlatoMessageController Commands ########################\\

    public PlatoMessageEntry getMessageRoot() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getAdmin", "controller.Controller"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new PlatoMessageEntry(new Gson().fromJson(answer, Admin.class).getImage());
    }

    public boolean hasMessage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("hasMessage", "controller.player.PlatoMessageController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public ArrayList<ArrayList<PlatoMessageEntry>> platoBotsMessages() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("platoBotsMessages", "controller.player.PlatoMessageController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Message> messages = new Gson().fromJson(answer, new TypeToken<ArrayList<Message>>() {
        }.getType());
        ArrayList<ArrayList<PlatoMessageEntry>> result = new ArrayList<>();
        java.util.Map<LocalDate, List<Message>> map = new HashMap<LocalDate, List<Message>>();
        for (Message item : messages) {
            List<Message> list = map.get(item.getTime().toLocalDate());
            if (list == null) {
                list = new ArrayList<Message>();
                map.put(item.getTime().toLocalDate(), list);
            }
            list.add(item);
        }
        for (Map.Entry<LocalDate, List<Message>> mapEntry : map.entrySet()) {

            ArrayList<PlatoMessageEntry> platoMessageEntries = new ArrayList<>();
            platoMessageEntries.add(new PlatoMessageEntry(mapEntry.getKey()));
            for (Message message : mapEntry.getValue())
                platoMessageEntries.add(new PlatoMessageEntry(message));
            result.add(platoMessageEntries);
        }

        return result;
    }

    //######################## PlayerProfileView Commands ########################\\

    public String getViewPlayerUsername() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerUsername", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerFirstName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerFirstName", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerLastName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerLastName", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerBio() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerBio", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerPhoneNumber() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerPhoneNumber", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerEmail() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerEmail", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerDaysPassed() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerDaysPassed", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerWins() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerWins", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewPlayerFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerFriendCount", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getViewPlayerImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerImage", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    public boolean areFriends() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("areFriends", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public void addFriend() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addFriend", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void removeFriend() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("removeFriend", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean HasFriendRequestBeenSent() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("HasFriendRequestBeenSent", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public boolean areTheSame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("areTheSame", "controller.player.PlayerProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    //######################## PlayerRunGameController Commands ########################\\

    public void runGame(ArrayList<String> usernames) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(usernames);
        Command command = new Command("getEventMode", "controller.player.PlayerRunGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getEventMode() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEventMode", "controller.player.PlayerRunGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getUsernameImage(String username) {
        return searchPlayer(username).getImage();
    }

    //######################## AdminEventMenu Commands ########################\\

    public String getAdminEventGameName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameName", "controller.admin.AdminEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getAdminEventScore() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getScore", "controller.admin.AdminEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public LocalDate getAdminEventStartDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getStartDate", "controller.admin.AdminEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, LocalDate.class);
    }


    public LocalDate getAdminEventEndDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEndDate", "controller.admin.AdminEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, LocalDate.class);
    }

    public Image getAdminEventImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getImage", "controller.admin.AdminEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    public String getAdminEventComment() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getComment", "controller.admin.AdminEventMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }


    public void addEvent(String text, LocalDate value, LocalDate value1, int parseInt, String text1, Image image) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(image);
        Command command = new Command("addGame", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void editEvent(String text, int parseInt, Image image, String text1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(image);
        Command command = new Command("editGame", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    //######################## AdminGameMenuController Commands ########################\\

    public Image getAdminGameImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getImage", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    public String getAdminGameName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameName", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getAdminGameDetails() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameDetail", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void addGame(String text, String text1, String image) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(image);
        Command command = new Command("addGame", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void editGame(String text, String text1, String image) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(image);
        Command command = new Command("editGame", "controller.admin.AdminGameMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    //######################## AdminGamesMenuController Commands ########################\\

    public ObservableList<GameEntry> getAdminGames() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGames", "controller.admin.AdminGamesMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> games = new Gson().fromJson(answer, new TypeToken<ArrayList<Game>>() {
        }.getType());
        ObservableList<GameEntry> result = FXCollections.observableArrayList();
        for (Game game : games)
            result.add(new GameEntry(game));
        return result;
    }

    public void deleteAdminGame(GameEntry gameEntry) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(gameEntry.getName());
        Command command = new Command("deleteGame", "controller.admin.AdminGamesMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void deleteAdminEvent(EventEntry eventEntry) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(eventEntry.getEventId());
        Command command = new Command("deleteEvent", "controller.admin.AdminGamesMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public ArrayList<EventEntry> getOngoingEvents() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getOngoingEvents", "controller.admin.AdminGamesMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Event> events = new Gson().fromJson(answer, new TypeToken<ArrayList<Event>>() {
        }.getType());
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : events)
            result.add(new EventEntry(event));
        return result;
    }

    public ArrayList<EventEntry> getUpcomingEvents() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getUpcomingEvents", "controller.admin.AdminGamesMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Event> events = new Gson().fromJson(answer, new TypeToken<ArrayList<Event>>() {
        }.getType());
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : events)
            result.add(new EventEntry(event));
        return result;
    }

    public ArrayList<EventEntry>  getPastEvents() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPastEvents", "controller.admin.AdminGamesMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Event> events = new Gson().fromJson(answer, new TypeToken<ArrayList<Event>>() {
        }.getType());
        ArrayList<EventEntry> result = new ArrayList<>();
        for (Event event : events)
            result.add(new EventEntry(event));
        return result;
    }

    //######################## AdminMainMenu Commands ########################\\

    public String getAdminUsername() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getUsername", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setAdminUsername(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setUsername", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getAdminPassword() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPassword", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setAdminPassword(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPassword", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getAdminFirstName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFirstName", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setAdminFirstName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setFirstName", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getAdminLastName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getLastName", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setAdminLastName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setLastName", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getAdminPhoneNumber() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPhoneNumber", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setAdminPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPhoneNumber", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getAdminEmail() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEmail", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setAdminEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setEmail", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getAdminDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDate", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getAdminImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getImage", "controller.admin.AdminMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    public void setAdminImage(String givenImage) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(givenImage);
        Command command = new Command("setImage", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public ObservableList<PlayerEntry> getPlayers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getAllPlayers", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> players = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {
        }.getType());
        ObservableList<PlayerEntry> result = FXCollections.observableArrayList();
        for (Player player : players) {
            result.add(new PlayerEntry(player));
        }
        return result;
    }

    public ObservableList<SuggestionEntry> getSuggestions() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getSuggestions", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Suggestion> suggestions = new Gson().fromJson(answer, new TypeToken<ArrayList<Suggestion>>() {
        }.getType());
        ObservableList<SuggestionEntry> result = FXCollections.observableArrayList();
        for (Suggestion suggestion : suggestions)
            result.add(new SuggestionEntry(suggestion));
        return result;
    }

    public void deleteSuggestion(SuggestionEntry suggestionEntry) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(suggestionEntry.getSuggestionId());
        Command command = new Command("deleteSuggestion", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void addSuggestion(String text, String text1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        Command command = new Command("addSuggestion", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean playerBeenSuggested(String text, String text1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        Command command = new Command("playerBeenSuggested", "controller.admin.AdminMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    //######################## AdminMessageViewController Commands ########################\\

    public void sendMessage(String text) {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("sendMessage", "controller.admin.AdminMessageViewController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    //######################## AdminProfileView Commands ########################\\

    public String getViewAdminUsername() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminUsername", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminFirstName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminFirstName", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminLastName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminLastName", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminBio() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminBio", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminEmail() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminEmail", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminPhoneNumber() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminPhoneNumber", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminDaysPassed() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminDaysPassed", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }


    public String getViewAdminWins() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminWins", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getViewAdminFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminFriendCount", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Image getViewAdminPlayerImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewAdminPlayerImage", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer, String.class);
        return new Image(url);
    }

    public ObservableList<GameLogSummaryEntry> getAdminGameHistory() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameHistory", "controller.admin.AdminProfileViewController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<GameLogSummary> gameLogSummaries = new Gson().fromJson(answer, new TypeToken<ArrayList<GameLogSummary>>() {
        }.getType());
        ObservableList<GameLogSummaryEntry> result = FXCollections.observableArrayList();
        for (GameLogSummary gameLog : gameLogSummaries)
            result.add(new GameLogSummaryEntry(gameLog));
        return result;
    }


    //######################## AdminPlayerListMenu Commands ########################\\



    //######################## MatchCardController Commands ########################\\

    public void incPlayerSoldier(Player player, int soldierNumber) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        params.add(soldierNumber);
        Command command = new Command("incPlayerSoldier", "controller.risk.MatchCardController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }


    //######################## RiskGameController Commands ########################\\

    public void shapeMap() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("shapeMap", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public List<List<Country>> getGameCountries() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameCountries", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, List<List<>>.class);
    }

    public void makeRobotPlayers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("makeRobotPlayers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Boolean getGameIsPlaying() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameIsPlaying", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public Boolean getFogStatus() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFogStatus", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public int getRemainSoldiers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getRemainSoldiers", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int.class);
    }

    public void setStartSoldiers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("setStartSoldiers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String draft(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("draft", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String beginDraft(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("beginDraft", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }


    /*
    public void checkAllPlayersAdded() {
        boolean toCheck = true;
        outerLoop:
        for (List<Country> countries : gameCountries) {
            for (Country country : countries) {
                if (country.getSoldiers() == 0) {
                    toCheck = false;
                    break outerLoop;
                }
            }
        }
        allPlayersAddedSoldier = toCheck;
    }

    public boolean getAllPlayersAdded() {
        return allPlayersAddedSoldier;
    }

    public String attack(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        String toPrint = "";
        boolean isFriend = false;
        if (!attackDone) {
            boolean sourceCountryValid = false;
            boolean destinationCountryValid = false;
            Country source = getCountryByDetails(sourceI, sourceJ);
            Country destination = getCountryByDetails(destI, destJ);
            isFriend = checkCountryIsAlliance(destination);
            boolean errorFound = false;
            if (!source.getName().equals("")) {
                if (source.getOwner().equals(currentPlayer)) {
                    sourceCountryValid = true;
                }
            }
            if (!destination.getName().equals("")) {
                if (destination.getOwner() != null) {
                    if (!destination.getOwner().equals(currentPlayer)) {
                        destinationCountryValid = true;
                    }
                } else {
                    destination.setOwner(currentPlayer);
                    this.attackWon = true;
                    this.attackDestination = destination;
                    toPrint += "\nYou now should add one to " + (source.getSoldiers() - 1) + " soldiers to "
                            + destination.getName();
                    sourceCountryWinner = source;
                }
            }
            if (!sourceCountryValid && !errorFound) {
                toPrint = "Source country is not valid";
                errorFound = true;
            }
            if (sourceCountryValid && (!destinationCountryValid || destination.getBlizzard()) && !errorFound) {
                toPrint = "Destination country is not valid";
                errorFound = true;
            }
            if (sourceCountryValid && destinationCountryValid && (soldiers > source.getSoldiers() || soldiers < 0
                    || source.getSoldiers() <= 1) && !errorFound) {
                toPrint = "Soldiers are not enough or not valid";
                errorFound = true;
            }
            if (!draftDone && !errorFound) {
                toPrint = "Draft didn't completed yet";
                errorFound = true;
            }
            if (isFriend && !errorFound) {
                toPrint = "This Country is Alliance";
                errorFound = true;
            }
            if (errorFound) {

            } else if (attackNeighbourhoodCheck(source, destination)) {
                boolean inWar = true;
                audioClip.play();
                String result = "";
                do {
                    int randomNumberSource = (int) (Math.random() * (6 - 0 + 1)) + 0;
                    int randomNumberDestination = (int) (Math.random() * (6 - 0 + 1)) + 0;
                    toPrint = "Source Dice : " + randomNumberSource + " - Destination Dice : " + randomNumberDestination;
                    if (randomNumberSource > randomNumberDestination) {
                        destination.addSoldiers(-1);
                        toPrint = toPrint + " Destination Country Lost 1 soldier! , Destination Soldiers "
                                + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                    } else if (randomNumberDestination > randomNumberSource) {
                        source.addSoldiers(-1);
                        soldiers--;
                        toPrint = toPrint + " Source Country Lost 1 soldier! , Destination Soldiers "
                                + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                    } else {
                        source.addSoldiers(-1);
                        soldiers--;
                        toPrint = toPrint + " Source Country Lost 1 soldier! , Destination Soldiers "
                                + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                    }
                    if (soldiers == 0 || destination.getSoldiers() == 0 || source.getSoldiers() == 1) {
                        inWar = false;
                        if (source.getSoldiers() == 1 || soldiers == 0) {
                            toPrint = "attack failed";
                            result = "Failed";
                        } else {
                            toPrint = "attack was successful";
                            result = "Successful";
                            if (!gotCards) {
                                toPrint += "\nYou Got new Card!\n : Card " + addCard() + " \nhas been added to you";
                                gotCards = true;
                            }
                            if (source.getSoldiers() == 2) {
                                source.addSoldiers(-1);
                                destination.addSoldiers(+1);
                                Player tempPlayer = destination.getOwner();
                                destination.setOwner(currentPlayer);
                                boolean playerDone = checkAdditionalPlayers(tempPlayer);
                                if (playerDone) {
                                    addDestinationCardsToSource(source.getOwner(), tempPlayer);
                                }

                            } else {
                                Player tempPlayer = destination.getOwner();
                                destination.setOwner(currentPlayer);
                                boolean playerDone = checkAdditionalPlayers(tempPlayer);
                                if (playerDone) {
                                    addDestinationCardsToSource(source.getOwner(), tempPlayer);
                                }
                                this.soldierPlacedAfterWin = false;
                                this.attackWon = true;
                                this.attackDestination = destination;
                                toPrint += "\nYou now should add one to \n" + (source.getSoldiers() - 1) + " soldiers to \n"
                                        + destination.getName();
                                sourceCountryWinner = source;
                                draftDone = false;
                            }
                        }
                        attackAnimation(result);
                        i = null;
                        j = null;
                        deselect();
                    }
                } while (inWar);
            } else {
                toPrint = "there is not any path between source and destination country";
            }
        } else {
            toPrint = "Attack has been done";
        }
        return toPrint;
    }

    public boolean getSoldierPlaced() {
        return soldierPlacedAfterWin;
    }

    private void addDestinationCardsToSource(Player sourcePlayer, Player destinationPlayer) {
        for (Card card : destinationPlayer.getCards()) {
            sourcePlayer.addCard(card);
            System.out.println("Card of destination player added to current player. Card : " + card);
        }
    }

    public String fortify(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        String toPrint = "";
        if (!fortifyDone) {
            boolean sourceCountryValid = false;
            boolean destinationCountryValid = false;
            Country source = getCountryByDetails(sourceI, sourceJ);
            Country destination = getCountryByDetails(destI, destJ);
            if (source.getOwner() != null && source.getOwner().equals(currentPlayer)) {
                sourceCountryValid = true;
            }
            if (destination.getOwner() != null && destination.getOwner().equals(currentPlayer)) {
                destinationCountryValid = true;
            }
            if (!sourceCountryValid) {
                toPrint = "Source country is not valid";
            } else if (sourceCountryValid && (!destinationCountryValid || destination.getBlizzard())) {
                toPrint = "Destination country is not valid";
            } else if (sourceCountryValid && destinationCountryValid && (soldiers > (source.getSoldiers() - 1) || soldiers < 1)) {
                toPrint = "Soldiers are not enough or not valid";
            } else if (fortifyNeighbourhoodCheck(source, destination)) {
                turnDone = true;
                source.addSoldiers(-1 * soldiers);
                destination.addSoldiers(soldiers);
                toPrint = "Move " + soldiers + " soldiers from " + source.getName() + " to " + destination.getName();
                setFortifyDone(true);
                i = null;
                j = null;
            } else {
                toPrint = "there is not any path between source and destination country";
            }
        } else {
            toPrint = "Fortify has been done";
        }
        return toPrint;
    }

    public String next() {
        String toPrint = "";
        audioClip.stop();
        if (getPlacementFinished()) {
            if (soldierPlacedAfterWin) {
                if (!draftDone) {
                    toPrint = "Next part, Start Attacking";
                    draftDone = true;
                } else if (!attackDone) {
                    toPrint = "Next part, Start Fortifying";
                    attackDone = true;
                } else if (!fortifyDone) {
                    fortifyDone = true;
                    toPrint = "Next part, Please try `turn over` to go to next turn";
                } else {
                    toPrint = "Try `turn over`";
                }
            } else {
                toPrint = "Please First try to draft in destination country";
            }
        } else {
            if (beginDraftDone) {
                toPrint = "Draft done, please try next turn icon";
            } else {
                toPrint = "You didnt draft any soldier please try draft some";
            }
        }
        return toPrint;
    }

    public void mainChangeTurn() {
        int currentTurnIndex = this.players.indexOf(this.currentPlayer);
        if (currentTurnIndex != this.players.size() - 1) {
            this.currentPlayer = this.players.get(currentTurnIndex + 1);
        } else {
            this.currentPlayer = this.players.get(0);
        }
        if (!getPlacementFinished()) {
            checkPlacementFinished();
        }
        RiskGameView.currentTimeStamp = System.currentTimeMillis() / 1000L;
        currentTimeStamp = System.currentTimeMillis() / 1000L;
        setDraftDone(false);
        setAttackDone(false);
        setFortifyDone(false);
        attackDestination = null;
        attackWon = false;
        resetNotif();
        beginDraftDone = false;
    }

    public String changeTurn() {
        String toPrint;
        boolean checkWinner = checkWinner();
        if (checkWinner) {
            if (this.winner != null) {
                toPrint = "Game has been finished." + " " + currentPlayer.getUsername() + " is this winner";
            } else {
                toPrint = "Game has been finished in draw.";
            }
            return toPrint;
        }
        if (!getPlacementFinished()) {
            if (beginDraftDone) {
                mainChangeTurn();
                toPrint = "Next Turn done successfully, It's " + currentPlayer.getUsername() + " turn";
                setDraftDone(false);
                setAttackDone(false);
                setFortifyDone(false);
                gotCards = false;
            } else {
                toPrint = "You didn't place any soldier, please first try to place a soldier in remain countries.";
            }
        } else {
            if (draftDone) {
                /*Todo: attack doesn't need to be checked(?)
                if (attackDone) {
                    if (fortifyDone) {
                        turnDone = false;
                        mainChangeTurn();
                        toPrint = "Next Turn done successfully, It's " + currentPlayer.getUsername() + " turn";
                        setDraftDone(false);
                        setAttackDone(false);
                        setFortifyDone(false);
                        gotCards = false;
                    } else {
                        toPrint = "You didn't fortify yet.";
                    }
                } else {
                    toPrint = "You didn't attack yet.";
                }
            } else {
                toPrint = "You didn't place any soldier, please first try to place a soldier in your countries.";
            }
        }
        return toPrint;
    }

    public void checkPlacementFinished() {
        boolean toCheck = true;
        for (Player player : getPlayers()) {
            if (player.getDraftSoldiers() != 0) {
                toCheck = false;
                break;
            }
        }
        if (toCheck) {
            placementFinished = true;
        }
    }

    public static java.util.Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }

    public boolean getPlacementFinished() {
        return this.placementFinished;
    }

    public String matchCards(int type) {
        String toPrint = "";
        switch (type) {
            case 1:
                toPrint = Card.matchCard(currentPlayer.getCards(), 1, currentPlayer);
                break;
            case 2:
                toPrint = Card.matchCard(currentPlayer.getCards(), 2, currentPlayer);
                break;
            case 3:
                toPrint = Card.matchCard(currentPlayer.getCards(), 3, currentPlayer);
                break;
            case 4:
                toPrint = Card.matchCard(currentPlayer.getCards(), 4, currentPlayer);
                break;
        }
        return toPrint;
    }

    public String showMap() {
        String lineString = "";
        for (List<Country> listCountries : gameCountries) {
            for (Country country : listCountries) {
                if (!country.equals(listCountries.get(listCountries.size() - 1))) {
                    lineString = lineString + country.toString() + " | ";
                } else {
                    lineString = lineString + country.toString() + "\n";
                }
            }
        }
        return lineString.trim();
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String placeSoldier(int i, int j, int soldiers) {
        String toPrint = "";
        if (soldiers > getCurrentPlayer().getDraftSoldiers()) {
            toPrint = "You do not have enough soldiers";
            return toPrint;
        }
        Country toCheckCountry = this.getCountryByDetails(i, j);
        if (!this.getDraftDone()) {
            if (toCheckCountry.getName().equals("") || toCheckCountry.getBlizzard()) {
                toPrint = "Chosen country is invalid. Please try again";
            } else {
                if (toCheckCountry.getOwner() == null || toCheckCountry.getOwner().equals(currentPlayer)) {
                    toCheckCountry.setOwner(currentPlayer);
                    toCheckCountry.addSoldiers(soldiers);
                    toPrint = currentPlayer.getPlayerNumber() + " add " + soldiers + " soldier to "
                            + toCheckCountry.getName();
                } else {
                    toPrint = "Please choose a country that is yours or no one has been chosen it yet";
                }
            }
        } else {
            toPrint = "You have been done your draft turn.";
        }
        if (getPlacementFinished() == false) {
            boolean allDone = false;
            for (Player player : players) {
                if (player.getDraftSoldiers() != 3) {
                    allDone = false;
                    break;
                } else {
                    allDone = true;
                }
            }
            if (allDone == true) {
                setPlacementFinished(true);
                toPrint += "\nGame has been started";
                mainChangeTurn();
                toPrint += "\nit's player " + currentPlayer.getUsername() + " turn to draft";
                draftDone = false;
            }
        }
        return toPrint;
    }

    public String leaveTheGame() {
        Player prevPlayer = currentPlayer;
        checkWinner();
        if (gameIsPlaying) {
            mainChangeTurn();
            players.remove(prevPlayer);
            makeCountryEmpty(prevPlayer);
            return "Player " + prevPlayer.getUsername() + " Exit The Game";
        } else {
            return "Player " + prevPlayer.getUsername() + " Won";
        }
    }

    public void makeCountryEmpty(Player player) {
        for (List<Country> countries : gameCountries) {
            for (Country country : countries) {
                if (country.getOwner() != null) {
                    if (country.getOwner().equals(player)) {
                        country.emptyCountry();
                    }
                }
            }
        }
    }

    public boolean getDraftDone() {
        return draftDone;
    }

    public void setDraftDone(boolean status) {
        draftDone = status;
    }

    public boolean getTurnDone() {
        return turnDone;
    }


    public void setAttackDone(boolean status) {
        attackDone = status;
    }

    public void setFortifyDone(boolean status) {
        this.fortifyDone = status;
    }

    public void matchCardAddSoldiers(int soldiersNumber) {
        matchCardController.incPlayerSoldier(currentPlayer, soldiersNumber);
    }

    public Country getCountryByDetails(int i, int j) {
        return gameCountries.get(i - 1).get(j - 1);
//        Country toReturnCountry = new Country();
//        for (List<Country> countries : this.gameCountries) {
//            for (Country country : countries) {
//                if (country.getNumberOfContinentCountry() == countryContinentNumber && country.getContinent()
//                        .substring(0, 2).toUpperCase().equals(shortName)) {
//
//                    toReturnCountry = country;
//                }
//            }
//        }
    }

    public String showTurn() {
        String toPrint = "It's " + currentPlayer.getUsername() + " Player";
        return toPrint;
    }

    public Player getTurn() {
        return currentPlayer;
    }

    public String getStatus() {
        String toPrint = "";
        if (!draftDone) {
            toPrint = "Please draft your soldiers in one of available countries";
        } else if (!attackDone) {
            toPrint = "Please attack to one of the valid countries";
        } else if (!fortifyDone) {
            toPrint = "if you want to move your soldiers, you can try now!";
        }
        return toPrint;
    }

    public void autoPlace() {
        do {
            boolean allDone = false;
            for (Player player : players) {
                if (player.getDraftSoldiers() != 3) {
                    allDone = false;
                    break;
                } else {
                    allDone = true;
                }
            }

            if (allDone == true) {
                break;
            }

            if (currentPlayer.getDraftSoldiers() == 3) {
                continue;
            }
            int rows = gameCountries.size() - 1;
            int columns = gameCountries.get(0).size() - 1;
            int randomRow = (int) (Math.random() * (rows - 0 + 1) + 0);
            int randomColumn = (int) (Math.random() * (columns + 1));
            Country getRandomCountry = gameCountries.get(randomRow).get(randomColumn);
            if ((getRandomCountry.getOwner() == null || getRandomCountry.getOwner().equals(currentPlayer)) && !getRandomCountry.getBlizzard()) {
                getRandomCountry.setOwner(currentPlayer);
                getRandomCountry.addSoldiers(1);
                currentPlayer.addDraftSoldier(-1);
                mainChangeTurn();
            }
        } while (true);
        setPlacementFinished(true);
    }

    public void setPlacementFinished(boolean placementFinished) {
        this.placementFinished = placementFinished;
    }

    public int[][] getFogOfWarMap(Player currentPlayer) {
        int row = gameCountries.size();
        int column = gameCountries.get(0).size();
        int[][] countryNumbers = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                countryNumbers[i][j] = 0;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameCountries.get(i).get(j).getOwner() != null) {
                    if (gameCountries.get(i).get(j).getOwner().getUsername().equals(currentPlayer.getUsername())
                            || gameCountries.get(i).get(j).getOwner().getFriends().contains(currentPlayer)
                    ) {
                        countryNumbers[i][j] = 1;
                        changeNumberElement(i - 1, j - 1, countryNumbers, 2);
                        changeNumberElement(i - 1, j, countryNumbers, 2);
                        changeNumberElement(i - 1, j + 1, countryNumbers, 2);
                        changeNumberElement(i, j - 1, countryNumbers, 2);
                        changeNumberElement(i, j + 1, countryNumbers, 2);
                        changeNumberElement(i + 1, j - 1, countryNumbers, 2);
                        changeNumberElement(i + 1, j, countryNumbers, 2);
                        changeNumberElement(i + 1, j + 1, countryNumbers, 2);
                    }
                }
            }
        }

        return countryNumbers;
    }

    public void changeNumberElement(int i, int j, int[][] inputArray, int number) {
        try {
            if (inputArray[i][j] != 1) {
                inputArray[i][j] = number;
            }
        } catch (Exception e) {
        }
    }

    /*
        int row = gameCountries.size();
        int column = gameCountries.get(0).size();
        int[][] countryNumbers = new int[row][column];

        countryNumbers = setFogOfWarMap(currentPlayer);

    public void setBlizzard() {
        if ((boolean) primitiveSettings.get("Blizzards")) {
            int rndRow = new Random().nextInt(gameCountries.size());
            int rndCol = new Random().nextInt(gameCountries.get(0).size());
            gameCountries.get(rndRow).get(rndCol).enableBlizzard();
        }
    }

    public boolean isPath(int CountryNumbers[][], int n, int m) {
        // Defining visited array to keep
        // track of already visited indexes
        boolean visited[][]
                = new boolean[n][m];

        // Flag to indicate whether the
        // path exists or not
        boolean flag = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // if matrix[i][j] is source
                // and it is not visited
                if (CountryNumbers[i][j] == 1 && !visited[i][j])
                    // Starting from i, j and
                    // then finding the path
                    if (isPath(CountryNumbers, i, j, visited)) {
                        // if path exists
                        flag = true;
                        break;
                    }
            }
        }
        if (flag)
            return true;
        else
            return false;
    }

    // Method for checking boundries
    public boolean isSafe(int i, int j, int CountryNumbers[][]) {

        if (i >= 0 && i < CountryNumbers.length && j >= 0 && j < CountryNumbers[0].length)
            return true;
        return false;
    }

    // Returns true if there is a
    // path from a source (a
    // cell with value 1) to a
    // destination (a cell with
    // value 2)
    public boolean isPath(int CountryNumbers[][], int i, int j, boolean visited[][]) {

        // Checking the boundries, walls and
        // whether the cell is unvisited
        if (isSafe(i, j, CountryNumbers) && CountryNumbers[i][j] != 0 && !visited[i][j]) {
            // Make the cell visited
            visited[i][j] = true;

            // if the cell is the required
            // destination then return true
            if (CountryNumbers[i][j] == 2)
                return true;

            // traverse up
            boolean up = isPath(CountryNumbers, i - 1, j, visited);

            // if path is found in up
            // direction return true
            if (up)
                return true;

            // traverse up
            boolean upRight = isPath(CountryNumbers, i - 1, j + 1, visited);

            // if path is found in up
            // direction return true
            if (upRight)
                return true;

            // traverse up
            boolean upLeft = isPath(CountryNumbers, i - 1, j - 1, visited);

            // if path is found in up
            // direction return true
            if (upLeft)
                return true;

            // traverse left
            boolean left = isPath(CountryNumbers, i, j - 1, visited);

            // if path is found in left
            // direction return true
            if (left)
                return true;

            // traverse right
            boolean right = isPath(CountryNumbers, i, j + 1, visited);

            // if path is found in right
            // direction return true
            if (right)
                return true;

            // traverse down
            boolean down = isPath(CountryNumbers, i + 1, j, visited);

            // if path is found in down
            // direction return true
            if (down)
                return true;

            // traverse down
            boolean downRight = isPath(CountryNumbers, i + 1, j + 1, visited);

            // if path is found in down
            // direction return true
            if (downRight)
                return true;

            // traverse down
            boolean downLeft = isPath(CountryNumbers, i + 1, j - 1, visited);

            // if path is found in down
            // direction return true
            if (downLeft)
                return true;

        }
        // no path has been found
        return false;
    }

    public int[][] attackMakeCountryNumbers(Country sourceCountry, Country destinationCountry) {
        int row = gameCountries.size();
        int column = gameCountries.get(0).size();
        int[][] countryNumbers = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameCountries.get(i).get(j).getName().equals(sourceCountry.getName())) {
                    countryNumbers[i][j] = 1;
                } else if (gameCountries.get(i).get(j).getName().equals(destinationCountry.getName())) {
                    countryNumbers[i][j] = 2;
                } else {
                    countryNumbers[i][j] = 0;
                }
            }
        }

        return countryNumbers;
    }

    public int[][] fortifyMakeCountryNumbers(Country sourceCountry, Country destinationCountry) {
        int row = gameCountries.size();
        int column = gameCountries.get(0).size();
        int[][] countryNumbers = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (!gameCountries.get(i).get(j).getBlizzard()) {
                    if (gameCountries.get(i).get(j).getOwner() != null) {
                        if (gameCountries.get(i).get(j).getName().equals(sourceCountry.getName())) {
                            countryNumbers[i][j] = 1;
                        } else if (gameCountries.get(i).get(j).getName().equals(destinationCountry.getName())) {
                            countryNumbers[i][j] = 2;
                        } else if (gameCountries.get(i).get(j).getOwner().getUsername().equals(sourceCountry.getOwner().getUsername())) {
                            countryNumbers[i][j] = 3;
                        } else {
                            countryNumbers[i][j] = 0;
                        }
                    }
                } else {
                    countryNumbers[i][j] = 0;
                }
            }
        }

        return countryNumbers;
    }

    public boolean attackNeighbourhoodCheck(Country sourceCountry, Country destinationCountry) {
        int row = gameCountries.size();
        int column = gameCountries.get(0).size();
        int[][] countryNumbers = attackMakeCountryNumbers(sourceCountry, destinationCountry);

        return isPath(countryNumbers, row, column);
    }

    public boolean fortifyNeighbourhoodCheck(Country sourceCountry, Country destinationCountry) {
        int row = gameCountries.size();
        int column = gameCountries.get(0).size();
        int[][] countryNumbers = fortifyMakeCountryNumbers(sourceCountry, destinationCountry);

        return isPath(countryNumbers, row, column);
    }

    public String nextPart() {
        String toPrint = "";
        if (getPlacementFinished()) {
            if (draftDone && !attackDone && !fortifyDone) {
                toPrint = "Draft done, Start Attacking";
                if (attackDone && !fortifyDone) {
                    toPrint = "Attack done, Start fortifying";
                    if (fortifyDone) {
                        toPrint = "Fortify done, try turn over";
                    } else {
                        toPrint = "Please first try to fortify , then use `turn over`";
                    }
                } else {
                    toPrint = "Please first Attack to a country then try next";
                }
            } else {

            }
        } else {
            if (draftDone) {
                toPrint = "wanna change your turn? you should use 'turn over'";
            } else {
                toPrint = "You should draft your ";
            }
        }
        return toPrint;
    }

    public boolean checkWinner() {
        boolean finished = true;
        boolean toCheck = false;
        if (players.size() == 1) {
            toCheck = true;
        }
        if (!getPlacementFinished() && !toCheck) {
            return false;
        }
        for (List<Country> countries : gameCountries) {
            for (Country country : countries) {
                if (country.getOwner() != null) {
                    if (!country.getOwner().equals(currentPlayer) && !toCheck) {
                        finished = false;
                        break;
                    }
                }
            }
        }
        if (finished) {
            this.winner = currentPlayer;
            this.gameIsPlaying = false;
            for (Player player : players) {
                player.resetRequestAndFriends();
            }

            Player.addGameLog(originalPlayers, Objects.requireNonNull(Game.getGameByGameName("Risk"),
                    "Game \"Risk\" @RiskGameController doesn't exist."), GameStates.WON, this.winner,
                    3 + event.getScore(), 1 + event.getScore() / 2, 0);
            return true;
            /*GameLogSummary gameLog = currentPlayer.getGameHistory("Risk");
            if (gameLog == null) {
                gameLog = new GameLogSummary("Risk", generateId());
                currentPlayer.addGameLog(gameLog);
            }
            gameLog.updateForWin(3, LocalDateTime.now());
            Game game = Objects.requireNonNull(Game.getGameByGameName("Risk"),
                    "Game \"Risk\" @RiskGameController doesn't exist.");
            PlayLog playLog = new PlayLog("Risk", players, currentPlayer, LocalDateTime.now());
            game.addPlayLog(playLog);
            for (Player player : players) {
                if (player.equals(currentPlayer)) {
                    continue;
                }
                gameLog = player.getGameHistory("Risk");
                if (gameLog == null) {
                    gameLog = new GameLog("Risk", generateId());
                    player.addGameLog(gameLog);
                }
                gameLog.updateForLoss(0, LocalDateTime.now());

            }
            if (finished) {
                return finished;
            }
        }
        if (!finished) {
            finished = true;
            for (List<Country> countries : gameCountries) {
                for (Country country : countries) {
                    if (country.getSoldiers() != 1 && country.getSoldiers() != 0) {
                        finished = false;
                        break;
                    }
                }
            }
        }
        if (finished) {
            for (Player player : players) {
                player.resetRequestAndFriends();
            }
            this.gameIsPlaying = false;
            Player.addGameLog(originalPlayers, Objects.requireNonNull(Game.getGameByGameName("Risk"),
                    "Game \"Risk\" @RiskGameController doesn't exist."), GameStates.DRAWN, null,
                    3 + event.getScore(), 1 + event.getScore() / 2, 0);
            /*Game game = Objects.requireNonNull(Game.getGameByGameName("Risk"),
                    "Game \"Risk\" @RiskGameController doesn't exist.");
            PlayLog playLog = new PlayLog("Risk", players, null, LocalDateTime.now());
            game.addPlayLog(playLog);
            for (Player player : players) {
                GameLog gameLog = player.getGameHistory("Risk");
                if (gameLog == null) {
                    gameLog = new GameLog("Risk", generateId());
                    player.addGameLog(gameLog);
                }
                gameLog.updateForWin(1, LocalDateTime.now());
            }
        }
        ;
        return finished;
    }

    public boolean checkAdditionalPlayers(Player player) {
        boolean toCheck = true;
        outerLoop:
        for (List<Country> countries : gameCountries) {
            for (Country country : countries) {
                if (country.getOwner() != null) {
                    if (country.getOwner().equals(player)) {
                        toCheck = false;
                        break outerLoop;
                    }
                }
            }
        }
        if (toCheck) {
            players.remove(player);
        }
        return toCheck;
    }

    public boolean getAttackWon() {
        return this.attackWon;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayer.getPlayerNumber();
    }

    public String draftAfterWin(int i, int j, int soldiers) {
        String toPrint = "";
        Country destination = getCountryByDetails(i, j);
        if (soldiers > sourceCountryWinner.getSoldiers() - 1 || soldiers < 1) {
            toPrint = "Soldiers are not enough or invalid, Please try between one and "
                    + (sourceCountryWinner.getSoldiers() - 1);
        } else if (destination.getName().equals("")) {
            toPrint = "Destination country is not valid";
        } else {
            if (!destination.getOwner().equals(getCurrentPlayer())) {
                toPrint = "This country is not yours";
            } else {
                if (destination.equals(this.attackDestination)) {
                    destination.addSoldiers(soldiers);
                    sourceCountryWinner.addSoldiers(-1 * soldiers);
                    toPrint = "" + soldiers + " soldiers added to " + destination.getName() + " successfully";
                    attackWon = false;
                    attackDestination = null;
                    sourceCountryWinner = null;
                    draftDone = true;
                    soldierPlacedAfterWin = true;
                } else {
                    toPrint = "Please try the previous destination country, not others";
                }
            }
        }
        return toPrint;
    }

    public boolean checkCountryIsYours(int i, int j) {
        if (currentPlayer.equals(gameCountries.get(i - 1).get(j - 1).getOwner())) {
            return true;
        } else {
            return false;
        }
    }

    public String addCard() {
        int rnd = new Random().nextInt(Card.values().length);
        Card toGetCard = Card.values()[rnd];
        currentPlayer.addCard(toGetCard);
        return toGetCard.name();
    }

    public String showMatchOptions() {
        String toPrint = "1-type1,type1,type1 score:4" + "\n" +
                "2-type2,type2,type2 score:6" + "\n" +
                "3-type3,type3,type3 score:8" + "\n" +
                "4-type1,type2,type3 score:10" + "\n";
        return toPrint;
    }

    public String showWhatToDo() {
        String toPrint = "";
        if (!draftDone) {
            toPrint += "Draft";
        } else if (!attackDone) {
            toPrint += "Attack";
        } else if (!fortifyDone) {
            toPrint += "Fortify";
        }
        return toPrint;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public void deselect() {
        i = null;
        j = null;
    }

    public boolean getCheckRequests() {
        return currentPlayer.checkPlayerHasRequest() && (!draftDone || !beginDraftDone);
    }

    public String addRequest(Player player) {
        if (!player.getRequests().contains(currentPlayer)) {
            player.addGameRequest(currentPlayer);
            return "Request sent successfully";
        } else {
            return "You have been requested to this player";
        }
    }

    public void addFriend(Player player) {
        player.addGameFriend(player);
    }

    public void rejectRequest(Player player) {
        player.rejectRequest(player);
    }

    public Player getPlayerByPlayerNumber(int number) {
        for (Player player : players) {
            if (player.getPlayerNumber() == number) {
                return player;
            }
        }
        return null;
    }

    public void notifSent() {
        notifSent = true;
    }

    public void resetNotif() {
        notifSent = false;
    }

    public boolean getNotifSent() {
        return notifSent;
    }

    public boolean checkCountryIsAlliance(Country destination) {
        if (destination.getOwner() != null) {
            if (currentPlayer.getGameFriends().contains(destination.getOwner())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkTime() {
        if (System.currentTimeMillis() / 1000L - currentTimeStamp > duration) {
            mainChangeTurn();
            currentTimeStamp = System.currentTimeMillis() / 1000L;
            return false;
        } else {
            return true;
        }
    }

    public void updateCurrentTime() {
        currentTimeStamp = System.currentTimeMillis() / 1000L;
    }

    public void getProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
//        timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                double progressed = Double.valueOf(System.currentTimeMillis() / 1000L - currentTimeStamp) / Double.valueOf(duration);
//                progressBar.setProgress(progressed);
//                if (progressed >= 1) {
//                    mainChangeTurn();
//                    progressBar.setProgress(0);
//                }
//            }
//        };
//        timer.start();
    }

    public void attackAnimation(String result) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Notifications notify = null;
                Image img = new Image(String.valueOf(getClass().getResource("/images/attack.png")));
                System.out.println(img);
                notify = Notifications.create().title("Attack!")
                        .graphic(new ImageView(img))
                        .text(result)
                        .hideAfter(javafx.util.Duration.seconds(2))
                        .position(Pos.TOP_CENTER);
                notify.darkStyle();
                notify.showInformation();
                notify.graphic(new ImageView(img));
            }
        });
    }
}


*/

}
