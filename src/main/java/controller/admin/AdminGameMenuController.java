package controller.admin;

import controller.Controller;
import javafx.scene.image.Image;
import main.ClientInfo;
import model.Event;
import model.Game;

public class AdminGameMenuController extends Controller {
    private final Game game;

    public AdminGameMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.game = Game.getGameByGameName(clientInfo.getGameName());
        if (game == null)
            System.err.println("Game passed to AdminGameMenuController is null");
    }

    public String getImage() {
        return game.getImageURL();
    }//done

    public String getGameName() {
        return game.getName();
    }//done

    public String getGameDetail() {
        return game.getDetails();
    }//done

    public void edit(String gameName, String gameDetail, String url) {
        game.setName(gameName);
        game.setDetails(gameDetail);
        game.setImage(url);
    }

    public void addGame(String gameName, String gameDetail, String gameImage) {
        //creates a game and adds it to the list of games
        Game game = new Game(gameName, generateId(), gameDetail,gameImage);
        Game.addGame(game);
    }
}
