package controller;

import model.Message;
import model.Player;
import model.Suggestion;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerMainMenuController {

    private Player player;

    public PlayerMainMenuController(Player player) {
        this.player = Objects.requireNonNull(player,
                "Player passed to PlayerMainMenuController is null.");
    }

    public int showPoints() {
        //returns the score of player
        return player.getScore();
    }

    public void showFavoriteGames() {
    }

    public ArrayList<String> showPlatoBotsMessages() {
        //returns the list of messages send to player
        ArrayList<String> result = new ArrayList<>();
        for(Message message: player.getMessages())
            result.add(message.toString());
        return result;
    }

    public void showLastPlayed() {
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
