package view.admin;

import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;
import view.player.PlayerEditProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccountMenu implements Tab, Initializable {
    @FXML
    private Label date = new Label();
    @FXML
    private Label username = new Label();
    @FXML
    private Label firstName = new Label();
    @FXML
    private Label lastName= new Label();
    @FXML
    private ImageView avatar;
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
    private void editInfo() {
        TabHandler.getTabHandler().push(new AdminEditProfile());
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        controller.logout();
        TabHandler.getTabHandler().logout();
        ViewHandler.getViewHandler().logout();
    }

    private void initializedInfo() {
        username.setText(controller.getUsername());
        firstName.setText(controller.getFirstName());
        lastName.setText(controller.getLastName());
        email.setText(controller.getEmail());
        phoneNumber.setText(controller.getPhoneNumber());
        System.out.println("6");
        date.setText(controller.getDate());
        System.out.println("5");
       // avatar.setImage(controller.getImage());
        System.out.println("4");
    }
}
