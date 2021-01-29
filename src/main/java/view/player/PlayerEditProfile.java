package view.player;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
//import com.sun.xml.internal.ws.api.model.SEIModel;
import controller.ClientMasterController.ClientMasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import main.Client;
import view.AlertMaker;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerEditProfile implements Tab, Initializable {
    private final ClientMasterController controller;
    @FXML
    private StackPane stackRoot;
    @FXML
    private ImageView playerImage;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextArea bio;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phoneNumber;

    public PlayerEditProfile() {
        controller = Client.getConnector().getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedPlayerInfo();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerEditProfile.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @FXML
    private void cancel() {
        TabHandler.getTabHandler().back();
    }

    @FXML
    private void save() {
        if (validateInput()) {
            controller.setPlayerUsername(username.getText());
            controller.setPlayerPassword(password.getText());
            controller.setPlayerBio(bio.getText());
            controller.setPlayerFirstName(firstName.getText());
            controller.setPlayerLastName(lastName.getText());
            controller.setPlayerPhoneNumber(phoneNumber.getText());
            controller.setPlayerEmail(email.getText());
            TabHandler.getTabHandler().back();
        }
    }

    @FXML
    private void addAvatar() {
        String givenImage = AlertMaker.getImageFromUser();
        if (givenImage != null) {
            controller.setPlayerImage(givenImage);
            playerImage.setImage(controller.getPlayerImage());
        }
    }

    private void initializedPlayerInfo() {
        username.setText(controller.getPlayerUsername());
        password.setText(controller.getPlayerPassword());
        firstName.setText(controller.getPlayerFirstName());
        lastName.setText(controller.getPlayerLastName());
        bio.setText(controller.getPlayerBio());
        email.setText(controller.getPlayerEmail());
        phoneNumber.setText(controller.getPlayerPhoneNumber());
        playerImage.setImage(controller.getPlayerImage());
    }

    private void setColourRed(JFXTextField textField) {
        textField.setFocusColor(Paint.valueOf("#ff0000"));
        textField.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void unsetColour(JFXTextField textField) {
        textField.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        textField.setFocusColor(Paint.valueOf("#4059a9"));
    }

    private void setColourRed(JFXPasswordField passwordField) {
        passwordField.setFocusColor(Paint.valueOf("#ff0000"));
        passwordField.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void unsetColour(JFXPasswordField passwordField) {
        passwordField.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        passwordField.setFocusColor(Paint.valueOf("#4059a9"));
    }

    private boolean validateInput() {
        if (username.getText().isEmpty()) {
            setColourRed(username);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No username is given.");
            return false;
        }
        if (!username.getText().equals(Client.getClientInfo().getLoggedInUsername()) &&
                controller.usernameExist(username.getText())) {
            setColourRed(username);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Username already taken.");
            return false;
        }
        unsetColour(username);
        if (password.getText().isEmpty()) {
            setColourRed(password);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No password is given.");
            return false;
        }
        unsetColour(password);
        if (firstName.getText().isEmpty()) {
            setColourRed(firstName);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No first name is given.");
            return false;
        }
        unsetColour(firstName);
        if (lastName.getText().isEmpty()) {
            setColourRed(lastName);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No last name is given.");
            return false;
        }
        unsetColour(lastName);
        if (!controller.checkEmail(email.getText())) {
            setColourRed(email);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Invalid email is given.");
            return false;
        }
        unsetColour(email);
        if (!controller.checkPhoneNumber(phoneNumber.getText())) {
            setColourRed(phoneNumber);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Invalid phone number is given.");
            return false;
        }
        unsetColour(phoneNumber);
        return true;
    }
}