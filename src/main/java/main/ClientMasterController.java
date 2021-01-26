package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import model.*;
import model.Entry.EventEntry;
import model.Entry.GameEntry;
import model.Entry.GameLogEntry;
import model.Entry.ScoreboardEntry;
import view.TabHandler;
import view.player.PlayerProfileView;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public Game getGame(GameEntry gameEntry) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(gameEntry.getName());
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
}
