package ClientMasterController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import main.Client;
import main.Command;
import model.*;
import model.Entry.*;
import view.TabHandler;
import view.player.PlayerProfileView;
import view.player.PlayerRunGameView;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

    public ObservableList<MenuItem> getSearchQuery(String searchQuery) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(searchQuery);
        Command command = new Command("usernameFuzzySearchTop5", "controller.Controller", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> top5Players = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {}.getType());
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
        Command command = new Command("usernameFuzzySearchTop5","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> top5Players = new Gson().fromJson(answer,  new TypeToken<ArrayList<Player>>() {}.getType());
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        for(Player player: top5Players)
        {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> {
                textField.setText(player.getUsername());
                controller.update(textField);
            });
            menuItem.setText(player.getUsername());
            result.add(menuItem);
        }
        if(result.isEmpty())
        {
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
        Command command = new Command("getCasualEvent","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
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
        Command command = new Command("getUsername","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getPlayerPassword() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPassword", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerFirstName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFirstName", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerLastName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getLastName", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerBio() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getBio", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerPhoneNumber() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPhoneNumber", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerEmail() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEmail", "controller.player.PlayerMainMenuController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDate","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getPlayerWins() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getWins","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getPlayerMoney() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getMoney", "controller.player.PlayerMainMenuController", params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getPlayerFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriendCount","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
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

    public void setPlayerUsername(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setUsername","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerPassword(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPassword","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerBio(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setBio","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerFirstName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setFirstName","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerLastName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setLastName","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPhoneNumber","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setEmail","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerImage(String givenImage) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(givenImage);
        Command command = new Command("setImage","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
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
        Command command = new Command("getGameHistory","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<GameLogSummary> gameLogSummaries = new Gson().fromJson(answer,new TypeToken<ArrayList<GameLogSummary>>() {}.getType());
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
        Command command = new Command("getAdmin","controller.Controller"
                , params,Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new PlatoMessageEntry( new Gson().fromJson(answer,Admin.class).getImage());
    }

    public boolean hasMessage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("hasMessage","controller.player.PlatoMessageController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public ArrayList<ArrayList<PlatoMessageEntry>> platoBotsMessages() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("platoBotsMessages","controller.player.PlatoMessageController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Message> messages = new Gson().fromJson(answer,new TypeToken<ArrayList<Message>>() {}.getType());
        ArrayList<ArrayList<PlatoMessageEntry>> result = new   ArrayList<>();
        java.util.Map<LocalDate, List<Message>> map = new HashMap<LocalDate, List<Message>>();
        for (Message item : messages) {
            List<Message> list = map.get(item.getTime().toLocalDate());
            if (list == null) {
                list = new ArrayList<Message>();
                map.put(item.getTime().toLocalDate(), list);
            }
            list.add(item);
        }
        for(Map.Entry<LocalDate,List<Message>> mapEntry: map.entrySet())
        {

            ArrayList<PlatoMessageEntry> platoMessageEntries = new ArrayList<>();
            platoMessageEntries.add(new PlatoMessageEntry(mapEntry.getKey()));
            for(Message message: mapEntry.getValue())
                platoMessageEntries.add(new PlatoMessageEntry(message));
            result.add(platoMessageEntries);
        }

        return result;
    }

    //######################## PlayerProfileView Commands ########################\\

    public String getViewPlayerUsername() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerUsername","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerFirstName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerFirstName","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerLastName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerLastName","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerBio() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerBio","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerPhoneNumber() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerPhoneNumber","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerEmail() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerEmail","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerDaysPassed() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerDaysPassed","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerWins() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerWins","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getViewPlayerFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerFriendCount","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public Image getViewPlayerImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerImage","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        String url = new Gson().fromJson(answer,String.class);
        return new Image(url);
    }

    public boolean areFriends() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("areFriends","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public void addFriend() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addFriend","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void removeFriend() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("removeFriend","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean HasFriendRequestBeenSent() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("HasFriendRequestBeenSent","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean areTheSame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("areTheSame","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    //######################## PlayerRunGameController Commands ########################\\

    public void runGame(ArrayList<String> usernames) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(usernames);
        Command command = new Command("getEventMode","controller.player.PlayerRunGameController"
                , params,Client.getClientInfo());
         Client.getConnector().serverQuery(command.toJson());
    }

    public String getEventMode() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEventMode","controller.player.PlayerRunGameController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public Image getUsernameImage(String username) {
        return searchPlayer(username).getImage();
    }



}
