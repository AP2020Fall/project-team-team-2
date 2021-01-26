package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
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
    private ClientMasterController()
    {}
    public static ClientMasterController getController()
    {
        if(controller == null)
            return controller = new ClientMasterController();
        else
            return controller;
    }

    public void endConnection() {
        Command command = new Command("endConnection","",new ArrayList<>(),Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean checkPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkPhoneNumber","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean checkEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkEmail","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean checkMoney(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkNumber","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean usernameExist(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("usernameExist","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean createAccount(String text, String text1, ArrayList<String> additionalInfo) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(additionalInfo);
        Command command = new Command("createAccount","controller.login.RegisterController",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean usernameAndPasswordMatch(String text, String text1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        Command command = new Command("usernameAndPasswordMatch","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean login(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("login","controller.login.LoginController",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public boolean isUsernamePlayer(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("isUsernamePlayer","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public void delete(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("delete","controller.login.LoginController",params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Player searchPlayer(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("searchPlayer","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Player.class);
    }

    public ObservableList<MenuItem> getSearchQuery(String searchQuery) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(searchQuery);
        Command command = new Command("usernameFuzzySearchTop5","controller.Controller",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> top5Players = new Gson().fromJson(answer,  new TypeToken<ArrayList<Player>>() {}.getType());
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        for(Player player: top5Players)
        {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> TabHandler.getTabHandler().push(new PlayerProfileView(player)));
            menuItem.setText(player.getUsername());
            result.add(menuItem);
        }
        if(result.isEmpty())
        {
            result.add(new MenuItem("No similar user found."));
        }
        return result;
    }


    public String showPoints() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showPoints","controller.player.PlayerMainMenuController",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String getMoney() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getMoney","controller.player.PlayerMainMenuController",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public ArrayList<GameEntry> favoriteGames() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("favoriteGames","controller.player.PlayerMainMenuController",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> favouriteGames =  new Gson().fromJson(answer,  new TypeToken<ArrayList<Game>>() {}.getType());
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game : favouriteGames)
            result.add(new GameEntry(game));
        return result;
    }

    public GameEntry lastGamePlayed() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("lastGamePlayed", "controller.player.PlayerMainMenuController", params,Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        Game lastPlayed = new Gson().fromJson(answer, Game.class);
        if (lastPlayed == null)
            return new GameEntry("No game has been played");
        else
            return new GameEntry(lastPlayed);
    }

    public ArrayList<GameEntry> adminsSuggestions() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("adminsSuggestions","controller.player.PlayerMainMenuController",params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> suggested =  new Gson().fromJson(answer, new TypeToken<ArrayList<Game>>() {}.getType());
        ArrayList<GameEntry> result = new ArrayList<>();
        for (Game game :suggested)
            result.add(new GameEntry(game));
        return result;
    }

    public ObservableList<GameEntry> getGames() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGames","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Game> games = new Gson().fromJson(answer,new TypeToken<ArrayList<Game>>() {}.getType());
        ObservableList<GameEntry> result = FXCollections.observableArrayList();
        for(Game game: games)
            result.add(new GameEntry(game));
        return result;
    }

    public ArrayList<EventEntry> getEvents() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEvents","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Event> events = new Gson().fromJson(answer,new TypeToken<ArrayList<Event>>() {}.getType());
        ArrayList<EventEntry> result = new ArrayList<>();
        for(Event event: events)
            result.add(new EventEntry(event));
        return result;
    }

    public Event getEvent(EventEntry eventEntry) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(eventEntry.getEventId());
        Command command = new Command("getEvent","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Event.class);
    }

    public Game getGame(String gameName) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(gameName);
        Command command = new Command("getGame","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Game.class);
    }

    public boolean isFavorite() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("isFavorite","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public void removeFromFavorites() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("removeFromFavorites","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void addToFavorites() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addToFavorites","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getDetails() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDetails","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getPlayedFrequency() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPlayedFrequency","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getWinsCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getWinsCount","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public Image getGameImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGame","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Game.class).getImage();
    }

    public boolean hasPlayedGame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("hasPlayedGame","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
    }

    public  ArrayList<GameLogEntry> getGameLog() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameLog","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<GameLog> gameLogs = new Gson().fromJson(answer,new TypeToken<ArrayList<GameLog>>() {}.getType());
        ArrayList<GameLogEntry> result = new ArrayList<>();
        for (GameLog gameLog : gameLogs) {
            result.add(new GameLogEntry(gameLog));
        }
        return result;
    }

    public ObservableList<ScoreboardEntry> getScoreboard() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getScoreboard","controller.player.PlayerGameMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Scoreboard.Entry> scoreboard = new Gson().fromJson(answer,new TypeToken<ArrayList<Scoreboard.Entry>>() {}.getType());
        ObservableList<ScoreboardEntry> result = FXCollections.observableArrayList();
        for(Scoreboard.Entry entry:scoreboard)
        {
            result.add(new ScoreboardEntry(entry));
        }
        return result;
    }

    public Player getPlayer(ScoreboardEntry scoreboardEntry) {
        return searchPlayer(scoreboardEntry.getPlayerName());
    }

    public ObservableList<TreeItem<FriendEntry>> getFriends() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriends","controller.player.PlayerFriendsMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<Player> friends = new Gson().fromJson(answer,new TypeToken<ArrayList<Player>>() {}.getType());
        ObservableList<TreeItem<FriendEntry>> result = FXCollections.observableArrayList();
        for (Player friend : friends) {
            result.add(new TreeItem<>(new FriendEntry(friend, (Player) Client.getClientInfo().getLoggedIn())));
        }
        return result;
    }

    public ObservableList<FriendRequestEntry> getFriendRequests() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriendRequests","controller.player.PlayerFriendsMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        ArrayList<FriendRequest> friendRequests = new Gson().fromJson(answer,new TypeToken<ArrayList<FriendRequest>>() {}.getType());
        ObservableList<FriendRequestEntry> result = FXCollections.observableArrayList();
        for (FriendRequest friendRequest : friendRequests){

            result.add(new FriendRequestEntry(friendRequest));
        }
        return result;
    }
    public void acceptRequest(String friendRequestId) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(friendRequestId);
        Command command = new Command("acceptRequest","controller.player.PlayerFriendsMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String getFriendRequestPlayerName(String friendRequestId) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(friendRequestId);
        Command command = new Command("getFriendRequestPlayerName","controller.player.PlayerFriendsMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public void declineRequest(String friendRequestId) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(friendRequestId);
        Command command = new Command("declineRequest","controller.player.PlayerFriendsMenuController"
                , params,Client.getClientInfo());
          Client.getConnector().serverQuery(command.toJson());
    }

    public Player getFriend(String name) {
        return searchPlayer(name);
    }

    public String getGameName() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameName","controller.player.PlayerEventMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getStartDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getStartDate","controller.player.PlayerEventMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getEndDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEndDate","controller.player.PlayerEventMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getScore() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getScore","controller.player.PlayerEventMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getComment() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getComment","controller.player.PlayerEventMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public Image getEventImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getEvent","controller.player.PlayerEventMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Event.class).getImage();
    }

    public void setUsername(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setUsername","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());

    }

    public void setPassword(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPassword","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setBio(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setBio","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setFirstName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setFirstName","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setLastName(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setLastName","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setPhoneNumber","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }


    public void setEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("setEmail","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayerImage(String givenImage) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(givenImage);
        Command command = new Command("setPlayerImage","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Image getPlayerImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPlayer","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Player.class).getImage();
    }

    public String getDate() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDate","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getWins() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getWins","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
    }

    public String getFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFriendCount","controller.player.PlayerMainMenuController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,String.class);
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

    public Image getViewPlayerImage() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPlayer","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Player.class).getImage();
    }

    public String getViewPlayerFriendCount() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getViewPlayerFriendCount","controller.player.PlayerProfileViewController"
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

    public boolean areFriends() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("areFriends","controller.player.PlayerProfileViewController"
                , params,Client.getClientInfo());
        String answer =  Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer,Boolean.class);
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

    public ObservableList<MenuItem> getSearchQuery(JFXTextField textField, String searchQuery,
                                                   PlayerRunGameView controller) {
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
