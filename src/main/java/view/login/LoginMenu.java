package view.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Account;
import view.AlertMaker;
import view.View;
import view.ViewHandler;
import view.admin.AdminMainMenuLayout;
import view.player.PlayerMainMenuLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenu implements View, Initializable {
    @FXML
    private StackPane root;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    LoginController controller;

    public LoginMenu() {
        controller = new LoginController();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/login/loginMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    private void playClickSound() {
        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/click.mp3").toString());
        audioClip.play();
    }

    @FXML
    private void loginAccount() {
        playClickSound();
        if (!controller.isUsernameExist(username.getText())) {
            setColourRed();
            AlertMaker.showMaterialDialog(root, root.getChildren().get(0), "Okay"
                    , "Invalid Credentials", "Username does not exist.");
        } else {

            if (!controller.isUsernameAndPasswordMatch(username.getText(), password.getText())) {
                setColourRed();
                AlertMaker.showMaterialDialog(root, root.getChildren().get(0), "Okay"
                        , "Invalid Credentials", "Username and password are not match.");
            } else {
                if (controller.login(username.getText())) {
                    ViewHandler.getViewHandler().push(new AdminMainMenuLayout());
                } else {
                    ViewHandler.getViewHandler().push(new PlayerMainMenuLayout());
                }
            }
        }
    }

    @FXML
    private void deleteAccount() {
        playClickSound();
        if (!controller.isUsernameExist(username.getText())) {
            setColourRed();
            AlertMaker.showMaterialDialog(root, root.getChildren().get(0), "Okay"
                    , "Invalid Credentials", "Username does not exist.");
        } else if (!controller.isUsernamePlayer(username.getText())) {
            setColourRed();
            AlertMaker.showMaterialDialog(root, root.getChildren().get(0), "Okay"
                    , "Invalid Credentials", "Admin account can't be deleted.");
        } else {
            if (!controller.isUsernameAndPasswordMatch(username.getText(), password.getText())) {
                setColourRed();
                AlertMaker.showMaterialDialog(root, root.getChildren().get(0), "Okay"
                        , "Invalid Credentials", "Username and password are not match.");
            } else {
                controller.delete(username.getText());
                AlertMaker.showMaterialDialog(root, root.getChildren().get(0), "Okay"
                        , "Invalid Credentials", username.getText() +
                                " deleted successfully!");
            }
        }
    }

    @FXML
    private void rememberMe() {
        Account.setRememberMeUsername(this.username.getText());
        Account.setRememberMePassword(this.password.getText());
    }

    @FXML
    private void back() {
        playClickSound();
        ViewHandler.getViewHandler().pop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Account.getRememberMeUsername() != null && Account.getRememberMePassword() != null) {
            username.setText(Account.getRememberMeUsername());
            password.setText(Account.getRememberMePassword());
        }
    }

    private void setColourRed() {
        username.setUnFocusColor(Paint.valueOf("ff0000"));
        username.setFocusColor(Paint.valueOf("ff0000"));
        password.setUnFocusColor(Paint.valueOf("ff0000"));
        password.setFocusColor(Paint.valueOf("ff0000"));

    }
}