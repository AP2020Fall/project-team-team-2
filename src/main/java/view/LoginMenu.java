package view;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.player.PlayerMainMenuLayout;

import java.io.IOException;

public class LoginMenu implements View {
    @FXML
    private Label errorMsg = new Label();
    @FXML
    private TextField username = new TextField();
    @FXML
    private PasswordField password = new PasswordField();
    LoginController controller;

    public LoginMenu() {
        controller = new LoginController();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/loginMenu.fxml"));
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
                    //ViewHandler.getViewHandler().push(new AdminMainMenu());
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
    private void back() {
        ViewHandler.getViewHandler().pop();
    }
}