package controller.player;

import controller.Controller;
import main.ClientInfo;
import model.Player;

import java.util.Objects;

public class PlayerMainMenuLayoutController extends Controller {
    protected Player loggedIn;

    public PlayerMainMenuLayoutController(ClientInfo clientInfo) {
        super(clientInfo);
        this.loggedIn =  Objects.requireNonNull((Player) clientInfo.getLoggedIn(),
                "LoggedIn player passed to PlayerMainMenuController is null.");
    }
}
