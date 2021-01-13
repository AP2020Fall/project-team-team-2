package controller.admin;

import javafx.scene.image.Image;
import model.Game;

public class AdminGameMenuController extends AdminGamesMenuController {
    private final Game game ;
    public AdminGameMenuController (Game game)
    {
        this.game = game;
    }

    public Image getImage() {
        return game.getImage();
    }
    public String getGameName()
    {return game.getName();}
    public String getGameDetail()
    {
        return game.getDetails();
    }

    public void edit(String gameName, String gameDetail, Image image) {
        game.setName(gameName);
        game.setDetails(gameDetail);
        game.setImage(image);
    }
}
