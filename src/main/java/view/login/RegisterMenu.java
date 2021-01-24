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

public class RegisterMenu  implements View {
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
    private final ClientMasterController controller;

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

    private ArrayList<String> getAdditionalInfo() {
        ArrayList<String> inputs = new ArrayList<>();
        if (firstName.getText().isEmpty()) {
            setFirstNameColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid first name.");
            return null;
        }
        inputs.add(firstName.getText());
        if (lastName.getText().isEmpty()) {
            setLastNameColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid last name.");
            return null;
        }
        inputs.add(lastName.getText());
        if (!controller.checkPhoneNumber(phoneNumber.getText())) {
            setPhoneNumberColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid phone number.");
            return null;
        }
        inputs.add(phoneNumber.getText());

        if (!controller.checkEmail(email.getText())) {
            setEmailColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Information", "Invalid email.");
            return null;
        }
        inputs.add(email.getText());

        if (Admin.isAdminExist()) {
            if (!controller.checkMoney(money.getText())) {
                setMoneyColourRed();
                AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                        "Okay", "Invalid Information", "Invalid amount for money.");
                return null;
            }
            inputs.add(money.getText());
        }
        return inputs;
    }

    @FXML
    private void register() {
        if (!controller.usernameExist(username.getText())) {
            money.setVisible(Admin.isAdminExist());
            animate(-1);
        } else {
            setCredentialsColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0),
                    "Okay", "Invalid Credentials", "Username is already taken.");
        }
    }

    public void submit(ActionEvent actionEvent) throws IOException {
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

    @FXML
    private void back() {
        ViewHandler.getViewHandler().pop();
    }

    @FXML
    private void backSubmit() {
        clear();
        animate(1);
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
        username.setUnFocusColor(Paint.valueOf("ff0000"));
        username.setFocusColor(Paint.valueOf("ff0000"));
        password.setUnFocusColor(Paint.valueOf("ff0000"));
        password.setFocusColor(Paint.valueOf("ff0000"));

    }

    private void setEmailColourRed() {
        email.setUnFocusColor(Paint.valueOf("ff0000"));
        email.setFocusColor(Paint.valueOf("ff0000"));
    }

    private void setPhoneNumberColourRed() {
        phoneNumber.setUnFocusColor(Paint.valueOf("ff0000"));
        phoneNumber.setFocusColor(Paint.valueOf("ff0000"));
    }

    private void setFirstNameColourRed() {
        firstName.setUnFocusColor(Paint.valueOf("ff0000"));
        firstName.setFocusColor(Paint.valueOf("ff0000"));
    }

    private void setLastNameColourRed() {
        lastName.setUnFocusColor(Paint.valueOf("ff0000"));
        lastName.setFocusColor(Paint.valueOf("ff0000"));
    }

    private void setMoneyColourRed() {
        money.setUnFocusColor(Paint.valueOf("ff0000"));
        money.setFocusColor(Paint.valueOf("ff0000"));
    }
}
