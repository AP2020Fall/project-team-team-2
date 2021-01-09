package view.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.login.RegisterController;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Admin;
import view.View;
import view.ViewHandler;
import view.admin.AdminMainMenuLayout;
import view.player.PlayerMainMenuLayout;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterMenu  implements View {
    @FXML
    private Label errorMsg;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField phoneNumber;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField money;
    @FXML
    private Pane animationPane;
    private final RegisterController controller;

    public RegisterMenu() {
        controller = new RegisterController();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/login/registerMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    private ArrayList<String> getAdditionalInfo() {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(firstName.getText());
        inputs.add(lastName.getText());
        if (controller.checkEmail(email.getText())) {
            inputs.add(email.getText());
        } else {
            errorMsg.setText("invalid email");
            return null;
        }
        if (controller.checkPhoneNumber(phoneNumber.getText())) {
            inputs.add(phoneNumber.getText());
        } else {
            errorMsg.setText("invalid phone number");
            return null;
        }

        if (Admin.isAdminExist()) {
            inputs.add(money.getText());
        }
        return inputs;
    }

    @FXML
    private void register() {
        if (!controller.isUsernameExist(username.getText())) {
            //todo get the info and wait for submit button with an animation
            animate(-1);
            money.setVisible(Admin.isAdminExist()); //todo must be add before the animation
        } else {
            errorMsg.setText("username exists!");
        }
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        ArrayList<String> additionalInfo = getAdditionalInfo();
        if (additionalInfo != null) {
            errorMsg.setText(username.getText() + " registered successfully");
            if (controller.createAccount(username.getText(), password.getText(), additionalInfo)) {
                ViewHandler.getViewHandler().push(new AdminMainMenuLayout());
            } else {
                ViewHandler.getViewHandler().push(new PlayerMainMenuLayout());
            }
        }
    }

    @FXML private void back() {
        ViewHandler.getViewHandler().pop();
    }

    @FXML private void backSubmit() {
        clear();
        animate(1);
    }
    private void animate(int forward)
    {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), animationPane);
        tt.setByX(forward*400f);
        tt.play();
    }
    private void clear()
    {
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();
        email.clear();
        money.clear();
    }

}
