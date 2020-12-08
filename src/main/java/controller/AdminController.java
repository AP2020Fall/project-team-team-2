package controller;

import com.google.gson.internal.$Gson$Preconditions;
import model.Admin;

public class AdminController extends Controller {
    private Admin admin;

    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public void login() {
    }

    public void logout() {
    }

    public void editInformation() {
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }
}
