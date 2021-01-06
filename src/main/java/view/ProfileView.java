package view;

import controller.ProfileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileView implements View, Initializable {
    private final ProfileViewController controller;
    @FXML private TextField searchUsername;
    @FXML private  Label score;
    @FXML private Label friendRequestPending;
    @FXML private Label username;
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label email;
    @FXML private Label phoneNumber;
    @FXML private Label daysPassed;
    @FXML private Button addButton;
    @FXML private Button removeButton;

    public ProfileView(Player player) {
        controller = new ProfileViewController(player);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(controller.showPoints());

        username.setText(controller.getUsername());
        firstName.setText(controller.getFirstName());
        lastName.setText(controller.getLastName());
        email.setText(controller.getEmail());
        phoneNumber.setText(controller.getPhoneNumber());
        daysPassed.setText(controller.getDaysPassed());

        friendRequestPending.setVisible(false);
        addButton.setVisible(!controller.areFriends());
        removeButton.setVisible(controller.areFriends());
        if(controller.HasFriendRequestBeenSent())
        {
            addButton.setVisible(false);
            removeButton.setVisible(false);
            friendRequestPending.setVisible(true);
        }
        if(controller.areTheSame())
        {
            addButton.setVisible(false);
            removeButton.setVisible(false);
            friendRequestPending.setVisible(false);
        }
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/profileView.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    @FXML
    private void platoMsg() throws IOException {
        ViewHandler.getViewHandler().push(new PlatoMessageView());
    }

    @FXML
    private void back() throws IOException {
        ViewHandler.getViewHandler().mainMenuBack();
    }

    @FXML
    private void viewFriendsMenu() throws IOException {
        ViewHandler.getViewHandler().push(new FriendsMenu());
    }

    @FXML
    private void viewGamesMenu() throws IOException {
        ViewHandler.getViewHandler().push(new GamesMenu());
    }

    @FXML
    private void viewAccountMenu() throws IOException {
        ViewHandler.getViewHandler().push(new AccountMenu());
    }

    @FXML
    private void viewMainMenu() throws IOException {
        ViewHandler.getViewHandler().push(new PlayerMainMenu());
    }

    @FXML
    private void search() throws IOException {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else if (!searchUsername.getText().equals(controller.getUsername())){
            //not the same person
            ViewHandler.getViewHandler().push(new
                    ProfileView(controller.searchPlayer(searchUsername.getText())));
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
