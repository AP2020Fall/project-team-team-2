package view.admin;

import controller.ClientMasterController.ClientMasterController;
import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.Client;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccountMenu implements Tab, Initializable {
    private final ClientMasterController controller;
    @FXML
    private Label date = new Label();
    @FXML
    private Label username = new Label();
    @FXML
    private Label firstName = new Label();
    @FXML
    private Label lastName = new Label();
    @FXML
    private ImageView avatar;
    @FXML
    private Label email = new Label();
    @FXML
    private Label phoneNumber = new Label();


    public AdminAccountMenu() {
        controller = Client.getConnector().getController();
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
        AdminMainMenuLayout.getAudioClip().stop();
        //controller.logout();
        TabHandler.getTabHandler().logout();
        ViewHandler.getViewHandler().logout();
    }

    private void initializedInfo() {
        username.setText(controller.getAdminUsername());
        firstName.setText(controller.getAdminFirstName());
        lastName.setText(controller.getAdminLastName());
        email.setText(controller.getAdminEmail());
        phoneNumber.setText(controller.getAdminPhoneNumber());
        date.setText(controller.getAdminDate());
        avatar.setImage(controller.getAdminImage());
    }
}
