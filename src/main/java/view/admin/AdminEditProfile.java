package view.admin;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.player.PlayerMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import view.AlertMaker;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditProfile implements Tab, Initializable {
    @FXML
    private StackPane stackRoot;
    @FXML
    private ImageView playerImage;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phoneNumber;
    private final PlayerMainMenuController controller;

    public AdminEditProfile() {
        controller = new PlayerMainMenuController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedPlayerInfo();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/admin/adminEditProfile.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @FXML
    private void cancel() {
        TabHandler.getTabHandler().back();
    }

    @FXML
    private void save() {
        if(validateInput())
        {
            controller.setUsername(username.getText());
            controller.setPassword(password.getText());
            controller.setFirstName(firstName.getText());
            controller.setLastName(lastName.getText());
            controller.setPhoneNumber(phoneNumber.getText());
            controller.setEmail(email.getText());
            TabHandler.getTabHandler().back();
        }
    }
    @FXML
    private void addAvatar(MouseEvent event) {
        //todo
        Image givenImage = AlertMaker.getImageFromUser();
        if(givenImage != null)
        {
            playerImage.setImage(givenImage);
            controller.setPlayerImage(givenImage);
        }
    }

    private void initializedPlayerInfo() {
        //todo set image
        username.setText(controller.getUsername());
        password.setText(controller.getPassword());
        firstName.setText(controller.getFirstName());
        lastName.setText(controller.getLastName());
        email.setText(controller.getEmail());
        phoneNumber.setText(controller.getPhoneNumber());
        playerImage.setImage(controller.getPlayerImage());
    }

    private void setUsernameColourRed() {
        username.setFocusColor(Paint.valueOf("#ff0000"));
        username.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void setPasswordColourRed() {
        password.setFocusColor(Paint.valueOf("#ff0000"));
        password.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void setFirstNameColourRed() {
        firstName.setFocusColor(Paint.valueOf("#ff0000"));
        firstName.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void setLastNameColourRed() {
        lastName.setFocusColor(Paint.valueOf("#ff0000"));
        lastName.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void setEmailColourRed() {
        email.setFocusColor(Paint.valueOf("#ff0000"));
        email.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void setPhoneNumberColourRed() {
        phoneNumber.setFocusColor(Paint.valueOf("#ff0000"));
        phoneNumber.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private boolean validateInput() {
        if(username.getText().isEmpty())
        {
            setPasswordColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No username is given.");
            return false;
        }
        if (!username.getText().equals(controller.getUsername()) &&
                controller.isUsernameExist(username.getText()))  {
            setUsernameColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Username already taken.");
            return false;
        }
        if (password.getText().isEmpty()) {
            setPasswordColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No password is given.");
            return false;
        }
        if (firstName.getText().isEmpty()) {
            setFirstNameColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No first name is given.");
            return false;
        }
        if (lastName.getText().isEmpty()) {
            setLastNameColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "No last name is given.");
            return false;
        }
        if (!controller.checkEmail(email.getText())) {
            setEmailColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Invalid email is given.");
            return false;
        }
        if (!controller.checkPhoneNumber(phoneNumber.getText())) {
            setPhoneNumberColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Invalid phone number is given.");
            return false;
        }
        return true;
    }
}
