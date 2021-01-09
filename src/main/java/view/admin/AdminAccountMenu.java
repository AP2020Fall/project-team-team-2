package view.admin;

import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccountMenu implements Tab, Initializable {
    private AdminMainMenuController controller;

    public AdminAccountMenu() {
        controller = new AdminMainMenuController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/admin/adminAccountMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @FXML
    private void editInfo(ActionEvent actionEvent) {
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        controller.logout();
        TabHandler.getTabHandler().logout();
        ViewHandler.getViewHandler().logout();
    }
}
