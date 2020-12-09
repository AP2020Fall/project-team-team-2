package controller;

import model.Game;
import model.Player;
import view.GameMenu;

import java.util.ArrayList;
import java.util.Objects;

public class GamesMenuController extends Controller{
    public ArrayList<String> listOfGames() {
        //returns ArrayList of Game names
        ArrayList<String> result = new ArrayList<>();
        for(Game game: Game.getGames())
            result.add(game.getName());
        return result;
    }

    public boolean gameIsListed(String gameName) {
        //Checks if gameName exist
        return Game.getGameByGameName(gameName) != null;
    }

    public void run(String gameName, Player player)
    {
        new GameMenu(player, Objects.requireNonNull(Game.getGameByGameName(gameName),
                "Game passed to GamesMenuController.run doesn't exist."));
    }
}
