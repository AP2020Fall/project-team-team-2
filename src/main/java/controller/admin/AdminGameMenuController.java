package controller.admin;

import javafx.scene.image.Image;
import main.ClientInfo;
import model.Game;

public class AdminGameMenuController extends AdminGamesMenuController {
    private final Game game ;
    public AdminGameMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.game = clientInfo.getGame();
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
