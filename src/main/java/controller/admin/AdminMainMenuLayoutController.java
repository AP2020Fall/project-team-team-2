package controller.admin;

import controller.Controller;
import main.ClientInfo;
import model.Admin;

public class AdminMainMenuLayoutController extends Controller {
    protected static Admin loggedIn;

    public AdminMainMenuLayoutController(ClientInfo clientInfo) {
        super(clientInfo);
    }

    public void login(Admin admin)
    {
        loggedIn = admin;
    }

    public void logout()
    {
        loggedIn = null;
    }
}
