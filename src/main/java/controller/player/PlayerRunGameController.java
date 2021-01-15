package controller.player;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import model.Event;
import model.Game;
import model.Player;
import view.StartGameView;
import view.TabHandler;
import view.ViewHandler;
import view.player.PlayerProfileView;
import view.player.PlayerRunGameView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class PlayerRunGameController extends PlayerMainMenuLayoutController{
    private Game game;
    private Event event;

    public PlayerRunGameController(Game game, Event event) {
        this.game = game;
        this.event = event;
    }

    public ObservableList<MenuItem> getSearchQuery(JFXTextField textField, String searchQuery,
                                                   PlayerRunGameView controller) {
        ObservableList<MenuItem> result = FXCollections.observableArrayList();
        ArrayList<Player> top5Players = usernameFuzzySearchTop5(searchQuery);
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
            result.add(new Menu("No similar user found."));
        }
        return result;
    }

    public Image getGameImage() {
        return game.getImage();
    }

    public String getEventMode() {
        if(event != null)
            return event.getComment();
        return "Casual";
    }

    public Image getUsernameImage(String username) {
        return Player.getPlayerByUsername(username).getImage();
    }

    public String getUsername() {
        return loggedIn.getUsername();
    }
    public void runGame(ArrayList<String> usernames) {
        ArrayList<Player> players = new ArrayList<>();
        for(String username: usernames)
        {
            players.add(Objects.requireNonNull(Player.getPlayerByUsername(username),
                    "Username passed to runGame doesn't exist."));
        }
        ViewHandler.getViewHandler().push(new StartGameView(players,event));
        System.out.println("Game Finished");
    }
}
