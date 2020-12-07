package controller;

import model.Player;
import model.Suggestion;

public class PlayerMainMenuController {

    private Player player;

    public PlayerMainMenuController(Player player) {
        this.player = player;
    }

    public void showPoints() {
    }

    public void showFavoriteGames() {
    }

    public void showPlatoBotsMessages() {
    }

    public void showLastPlayed() {
    }

    public Suggestion showAdminsSuggestions() {
        return player.getSuggestion();
    }

    public String showSuggestedGame() {
        return player.getSuggestion().getGameName();
    }

    public void addFriend(String username) {
    }


}
