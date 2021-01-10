package view.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.View;
import view.ViewHandler;
import view.admin.AdminMainMenuLayout;
import view.player.PlayerMainMenuLayout;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenu implements View, Initializable {
    @FXML
    private Label errorMsg = new Label();
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    LoginController controller;
    private static String rememberUsername;
    private static String rememberPassword;

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

    @FXML
    private void loginAccount() {
        if (!controller.isUsernameExist(username.getText())) {
            errorMsg.setText("username does not exist");
        } else {

            if (!controller.isUsernameAndPasswordMatch(username.getText(), password.getText())) {
                errorMsg.setText("username and password are not match");
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
        if (!controller.isUsernameExist(username.getText())) {
            errorMsg.setText("username does not exist");
        } else if (!controller.isUsernamePlayer(username.getText())) {
            errorMsg.setText("Admin account can't be deleted");
        } else {
            if (!controller.isUsernameAndPasswordMatch(username.getText(), password.getText())) {
                errorMsg.setText("username and password are not match");
            } else {
                controller.delete(username.getText());
                errorMsg.setText(username.getText() + " deleted successfully!");
            }
        }
    }

    @FXML
    private void rememberMe() {
        rememberUsername = this.username.getText();
        rememberPassword = this.password.getText();
    }

    @FXML
    private void back() {
        ViewHandler.getViewHandler().pop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (rememberUsername != null && rememberPassword != null) {
            username.setText(rememberUsername);
            password.setText(rememberPassword);
        }
    }
}