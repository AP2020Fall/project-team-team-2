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

    public void checkAllPlayersAdded() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkAllPlayersAdded", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Boolean getAllPlayersAdded() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getAllPlayersAdded", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public String attack(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(sourceI);
        params.add(sourceJ);
        params.add(destI);
        params.add(destJ);
        params.add(soldiers);
        Command command = new Command("attack", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Boolean getSoldierPlaced() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getSoldierPlaced", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public void addDestinationCardsToSource() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addDestinationCardsToSource", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String fortify(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(sourceI);
        params.add(sourceJ);
        params.add(destI);
        params.add(destJ);
        params.add(soldiers);
        Command command = new Command("fortify", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String next() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("next", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void mainChangeTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("mainChangeTurn", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String changeTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("changeTurn", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void checkPlacementFinished() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkPlacementFinished", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Boolean getPlacementFinished() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPlacementFinished", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public String matchCards(int type) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(type);
        Command command = new Command("matchCards", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String showMap() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showMap", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(currentPlayer);
        Command command = new Command("setCurrentPlayer", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayers(ArrayList<Player> players) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(players);
        Command command = new Command("setPlayers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Player getCurrentPlayer() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCurrentPlayer", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Player.class);
    }

    public String placeSoldier(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("placeSoldier", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String leaveTheGame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("leaveTheGame", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void makeCountryEmpty(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("makeCountryEmpty", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean getDraftDone() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDraftDone", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public void setDraftDone(boolean status) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        Command command = new Command("setDraftDone", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean getTurnDone() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getTurnDone", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public void setAttackDone(boolean status) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        Command command = new Command("setAttackDone", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setFortifyDone(boolean status) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        Command command = new Command("setFortifyDone", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void matchCardAddSoldiers(int soldiersNumber) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(soldiersNumber);
        Command command = new Command("matchCardAddSoldiers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Country getCountryByDetails(int i, int j) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        Command command = new Command("getCountryByDetails", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());

        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Country.class);
    }

    public String showTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showTurn", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Player getTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getTurn", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Player.class);
    }

    public String getStatus() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getStatus", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void autoPlace() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("autoPlace", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlacementFinished(boolean placementFinished) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(placementFinished);
        Command command = new Command("setPlacementFinished", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public int[][] getCountryByDetails(Player currentPlayer) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(currentPlayer);
        Command command = new Command("getCountryByDetails", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());

        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int[][].class);
    }


    public void changeNumberElement(int i, int j, int[][] inputArray, int number) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(inputArray);
        params.add(number);
        Command command = new Command("changeNumberElement", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setBlizzard() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("setBlizzard", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String nextPart() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("nextPart", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public boolean checkWinner() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkWinner", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());

        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public boolean checkAdditionalPlayers(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("checkAdditionalPlayers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());

        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public boolean getAttackWon() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getAttackWon", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public int getCurrentPlayerIndex() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCurrentPlayerIndex", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int.class);
    }

    public String draftAfterWin(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("draftAfterWin", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public boolean checkCountryIsYours(int i, int j) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        Command command = new Command("checkCountryIsYours", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public String addCard() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addCard", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String showMatchOptions() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showMatchOptions", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String showWhatToDo() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showWhatToDo", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public boolean getCheckRequests() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCheckRequests", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public String addRequest(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("addRequest", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void addFriend(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("addFriend", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void rejectRequest(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("rejectRequest", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }
    /*

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
