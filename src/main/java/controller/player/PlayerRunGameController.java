package controller.player;

import controller.Controller;
import javafx.scene.image.Image;
import main.ClientInfo;
import model.AvailableGame;
import model.Event;
import model.Game;
import model.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PlayerRunGameController extends Controller {
    private final Game game;
    private final Event event;
    private final Player loggedIn;
    public PlayerRunGameController(ClientInfo clientInfo) {
        super(clientInfo);
        this.loggedIn = Player.getPlayerByUsername(clientInfo.getLoggedInUsername());
        if(loggedIn == null)
        {
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


    public Image getGameImage() {
        return game.getImage();
    }

    public String getEventMode() {
        if (event != null)
            return event.getComment();
        return "Casual";
    }

    public Image getUsernameImage(String username) {
        return Player.getPlayerByUsername(username).getImage();
    }

   public void runGame(ArrayList<String> usernames) {
        ArrayList<Player> players = new ArrayList<>();
        System.out.println("Players ready to play are:");
        for (String username : usernames) {
            System.out.println(username);
            players.add(Objects.requireNonNull(Player.getPlayerByUsername(username),
                    "Username passed to runGame doesn't exist."));
        }
        System.out.println("lets go play");
        // ViewHandler.getViewHandler().push(new StartGameView(players,event));
    }

    public ArrayList<AvailableGame> getAvailableGames() {
        return AvailableGame.getAvailableGames();
    }
    public String createAvailableGame(Map<String, Object> primitiveSettings) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(loggedIn);
        AvailableGame availableGame = new AvailableGame(primitiveSettings,players,game,event,generateId());
        availableGame.createGame();
        return availableGame.getAvailableGameId();
    }
}
