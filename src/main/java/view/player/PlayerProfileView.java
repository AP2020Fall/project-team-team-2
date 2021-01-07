package view.player;

import controller.ProfileViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;
import view.Tab;
import view.View;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerProfileView implements Tab, Initializable {
    private final ProfileViewController controller;
    @FXML
    private TextField searchUsername;
    @FXML
    private Label score;
    @FXML
    private Label friendRequestPending;
    @FXML
    private Label username;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label daysPassed;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;

    public PlayerProfileView(Player player) {
        controller = new ProfileViewController(player);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //score.setText(controller.showPoints());

        username.setText(controller.getUsername());
        firstName.setText(controller.getFirstName());
        lastName.setText(controller.getLastName());
        email.setText(controller.getEmail());
        phoneNumber.setText(controller.getPhoneNumber());
        daysPassed.setText(controller.getDaysPassed());

        friendRequestPending.setVisible(false);
        addButton.setVisible(!controller.areFriends());
        removeButton.setVisible(controller.areFriends());
        if (controller.HasFriendRequestBeenSent()) {
            addButton.setVisible(false);
            removeButton.setVisible(false);
            friendRequestPending.setVisible(true);
        }
        if (controller.areTheSame()) {
            addButton.setVisible(false);
            removeButton.setVisible(false);
            friendRequestPending.setVisible(false);
        }
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/playerProfileView.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @FXML
    private void platoMsg() {
        ViewHandler.getViewHandler().push(new PlatoMessageView());
    }

    @FXML
    private void back() {
        ViewHandler.getViewHandler().mainMenuBack();
    }

    @FXML
    private void search() {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else if (!searchUsername.getText().equals(controller.getUsername())) {
            //not the same person
            /*ViewHandler.getViewHandler().push(new
                    PlayerProfileView(controller.searchPlayer(searchUsername.getText())));*/
        }
    }

    @FXML
    private void addFriend() {
        controller.addFriend();
        addButton.setVisible(false);
        removeButton.setVisible(false);
        friendRequestPending.setVisible(true);
    }

    @FXML
    private void removeFriend() {
        controller.removeFriend();
        addButton.setVisible(true);
        removeButton.setVisible(false);
        friendRequestPending.setVisible(false);
    }


}
