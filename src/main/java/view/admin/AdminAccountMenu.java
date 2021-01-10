package view.admin;

import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccountMenu implements Tab, Initializable {
    @FXML
    private Label bio = new Label();
    @FXML
    private Label date = new Label();
    @FXML
    private Label username = new Label();
    @FXML
    private Label firstname = new Label();
    @FXML
    private Label lastname = new Label();
    @FXML
    private Label email = new Label();
    @FXML
    private Label phoneNumber = new Label();

    private AdminMainMenuController controller;

    public AdminAccountMenu() {
        controller = new AdminMainMenuController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedInfo();
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

    private void initializedInfo() {
        username.setText(controller.getAdmin().getUsername());
        firstname.setText(controller.getAdmin().getFirstName());
        lastname.setText(controller.getAdmin().getLastName());
        email.setText(controller.getAdmin().getEmail());
        phoneNumber.setText(controller.getAdmin().getPhoneNumber());
        bio.setText(controller.getAdmin().getBio());
    }
}
