package controller.admin;

import javafx.scene.image.Image;
import main.ClientInfo;
import model.Event;
import model.Game;

public class AdminGameMenuController extends AdminGamesMenuController {
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

    public void edit(String gameName, String gameDetail, Image image) {
        game.setName(gameName);
        game.setDetails(gameDetail);
        game.setImage(image);
    }
}
