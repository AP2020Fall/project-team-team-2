package controller.player;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.ServerMasterController.ServerMasterController;
import javafx.scene.image.Image;
import main.ClientInfo;
import main.Server;
import model.AvailableGame;
import model.Event;
import model.Game;
import model.Player;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerRunGameController extends Controller {
    private final Game game;
    private final Event event;
    private final Player loggedIn;

    public PlayerRunGameController(ClientInfo clientInfo) {
        super(clientInfo);
        this.loggedIn = Player.getPlayerByUsername(clientInfo.getLoggedInUsername());
        if (loggedIn == null) {
            System.err.println("Player passed to PlayerRunGameController is null.");

        }
        this.game = Game.getGameByGameName(clientInfo.getGameName());
        if (game == null)
            System.err.println("Game passed to PlayerRunGameController is null.");
        this.event = Event.getEventById(clientInfo.getEventId());
        if (event == null)
            System.err.println("Player passed to PlayerRunGameController is null.");
    }

    /*public ObservableList<MenuItem> getSearchQuery(JFXTextField textField, String searchQuery,
                                                   PlayerRunGameView controller) {
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        ArrayList<Player> top5Players = usernameFuzzySearchTop5(searchQuery);
        for(Player player: top5Players)
        {
            MenuItem menuItem = new MenuItem();
            menuItem.setOnAction(event -> {
                textField.setText(player.getPlayerUsername());
                controller.update(textField);
            });
            menuItem.setText(player.getPlayerUsername());
            result.add(menuItem);
        }
        if(result.isEmpty())
        {
            result.add(new Menu("No similar user found."));
        }
        return result;
    }*/


    public String getGameImage() {
        return game.getImageURL();
    }

    public String getEventMode() {
        if (event != null)
            return event.getComment();
        return "Casual";
    }

    public Image getUsernameImage(String username) {
        return Player.getPlayerByUsername(username).getImage();
    }


    public ArrayList<AvailableGame> getAvailableGames() {
        return AvailableGame.getAvailableGames();
    }

    public String createAvailableGame(String primitiveSettingsString) {
        Map< Player, DataOutputStream> players = new HashMap<>();
        players.put( loggedIn,ServerMasterController.getCurrentClientHandler().getValue1());
        Map<String, Object> primitiveSettings = new Gson().fromJson(primitiveSettingsString,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        AvailableGame availableGame = new AvailableGame(primitiveSettings, players, game, event, generateId());
        availableGame.createGame();
        return availableGame.getAvailableGameId();
    }

    public Boolean joinAvailableGame(String availableGameId) {
        AvailableGame availableGame = AvailableGame.getAvailableGameById(availableGameId);
        if (availableGame == null) return false;
        return availableGame.playerJoin(ServerMasterController.getCurrentClientHandler().getValue1(),loggedIn);
    }
}
