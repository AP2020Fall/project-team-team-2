package view.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Client;
import main.ClientMasterController;
import model.Admin;
import view.AlertMaker;
import view.View;
import view.ViewHandler;
import view.admin.AdminMainMenuLayout;
import view.player.PlayerMainMenuLayout;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterMenu implements View {
    private final ClientMasterController controller;
    @FXML
    private StackPane stackRoot;
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

    public RegisterMenu() {
        controller = Client.getConnector().getController();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/login/registerMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    @FXML
    private void register() {
        if (!controller.usernameExist(username.getText())) {
            money.setVisible(controller.adminExists());
            animate(-1);
        } else {
            setCredentialsColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Credentials", "Username is already taken.");
        }
    }

    @FXML
    private void back() {
        ViewHandler.getViewHandler().pop();
    }

    @FXML
    private void backSubmit() {
        clear();
        animate(1);
    }

    @FXML
    private void submit() throws IOException {
        ArrayList<String> additionalInfo = getAdditionalInfo();
        if (additionalInfo != null) {
            WelcomeMenu.getAudioClip().stop();
            if (controller.createAccount(username.getText(), password.getText(), additionalInfo)) {
                ViewHandler.getViewHandler().push(new AdminMainMenuLayout());
            } else {
                ViewHandler.getViewHandler().push(new PlayerMainMenuLayout());
            }
        }
    }

    private ArrayList<String> getAdditionalInfo() {
        ArrayList<String> inputs = new ArrayList<>();
        if (firstName.getText().isEmpty()) {
            setColourRed(firstName);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid first name.");
            return null;
        }
        unsetColour(firstName);
        inputs.add(firstName.getText());
        if (lastName.getText().isEmpty()) {
            setColourRed(lastName);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid last name.");
            return null;
        }
        unsetColour(lastName);
        inputs.add(lastName.getText());
        if (!controller.checkPhoneNumber(phoneNumber.getText())) {
            setColourRed(phoneNumber);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid phone number.");
            return null;
        }
        unsetColour(phoneNumber);
        inputs.add(phoneNumber.getText());

        if (!controller.checkEmail(email.getText())) {
            setColourRed(email);
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid email.");
            return null;
        }
        unsetColour(email);
        inputs.add(email.getText());

        if (controller.adminExists()) {
            if (!controller.checkMoney(money.getText())) {
                setColourRed(money);
                AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                        "Okay", "Invalid Information", "Invalid amount for money.");
                return null;
            }
            unsetColour(money);
            inputs.add(money.getText());
        }
        return inputs;
    }


    private void animate(int forward) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), animationPane);
        tt.setByX(forward * 400f);
        tt.play();
    }

    private void clear() {
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();
        email.clear();
        money.clear();
    }

    private void setCredentialsColourRed() {
        setColourRed(username);
        password.setUnFocusColor(Paint.valueOf("ff0000"));
        password.setFocusColor(Paint.valueOf("ff0000"));
    }

    private void setColourRed(JFXTextField textField) {
        textField.setUnFocusColor(Paint.valueOf("ff0000"));
        textField.setFocusColor(Paint.valueOf("ff0000"));
    }

    private void unsetColour(JFXTextField textField) {
        textField.setUnFocusColor(Paint.valueOf("#4d4d4d"));
        textField.setFocusColor(Paint.valueOf("#4059a9"));
    }
}
