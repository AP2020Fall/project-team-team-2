package controller.player;

import controller.Controller;
import model.Player;

public class PlayerMainMenuLayoutController extends Controller {
    protected static Player loggedIn;
    public void login(Player player)
    {
        loggedIn = player;
    }
    public void logout()
    {
        //todo
    }
}
