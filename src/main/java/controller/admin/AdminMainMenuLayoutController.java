package controller.admin;

import controller.risk.Controller;
import model.Admin;

public class AdminMainMenuLayoutController extends Controller {
    protected static Admin loggedIn;

    public void login(Admin admin)
    {
        loggedIn = admin;
    }

    public void logout()
    {
        //todo
    }
}
