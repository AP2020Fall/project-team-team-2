package view;

import controller.ProfileViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileView implements View, Initializable {
    private final ProfileViewController controller;
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

    public ProfileView(Player player) {
        controller = new ProfileViewController(player);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText(controller.getUsername());
        firstName.setText(controller.getFirstName());
        lastName.setText(controller.getLastName());
        email.setText(controller.getEmail());
        phoneNumber.setText(controller.getPhoneNumber());
        daysPassed.setText(controller.getDaysPassed());
        addButton.setVisible(!controller.areFriends());
        removeButton.setVisible(controller.areFriends());
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
    private void platoMsg(ActionEvent actionEvent) {
    }

    @FXML
    private void back(ActionEvent actionEvent) {

    }

    @FXML
    private void viewFriendsMenu() throws IOException {
        ViewHandler.getViewHandler().push(new FriendsMenu());
    }

    @FXML
    private void viewGamesMenu() {
    }

    @FXML
    private void viewAccountMenu() {

    }

    @FXML
    private void viewMainMenu(ActionEvent actionEvent) {
    }

    @FXML
    private void search(ActionEvent actionEvent) {
    }

    @FXML
    private void addFriend() {
    }

    @FXML
    private void removeFriend() {
    }

}
