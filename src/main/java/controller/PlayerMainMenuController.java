package controller;

import model.Game;
import model.Message;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerMainMenuController {

    private final Player player;

    public PlayerMainMenuController(Player player) {
        this.player = Objects.requireNonNull(player,
                "Player passed to PlayerMainMenuController is null.");
    }

    public int showPoints() {
        //returns the score of player.
        return player.getScore();
    }

    public ArrayList<String> showFavoriteGames() {
        //returns the names of player's favourite games.
        ArrayList<String> result = new ArrayList<>();
        for(Game game : player.getFavouriteGames())
            result.add(game.getName());
        return result;

    }

    public ArrayList<String> showPlatoBotsMessages() {
        //returns the list of messages send to player.
        ArrayList<String> result = new ArrayList<>();
        for (Message message : player.getMessages())
            result.add(message.toString());
        return result;
    }

    public String showLastPlayed() {
        //returns the last game played by player.
        //throws NullPointerException if gameLogs is empty.
        return Objects.requireNonNull(player.getLastGamePlayed(),
                "Player hasn't played any games.").getGame().getName();
    }

    public String showAdminsSuggestions() {
        return player.getSuggestion().toString();
    }

    public String showSuggestedGame() {
        return player.getSuggestion().getGameName();
    }

    public void addFriend(String username) {
    }


}
