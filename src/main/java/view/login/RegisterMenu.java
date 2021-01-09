package view.login;

import controller.login.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import view.View;
import view.ViewHandler;
import view.admin.AdminMainMenuLayout;
import view.player.PlayerMainMenuLayout;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterMenu  implements View {

    @FXML
    private Label errorMsg = new Label();
    @FXML
    private PasswordField password = new PasswordField();
    @FXML
    private TextField username = new TextField();
    @FXML
    private TextField firstName = new TextField();
    @FXML
    private TextField lastName = new TextField();
    @FXML
    private TextField phoneNumber = new TextField();
    @FXML
    private TextField email = new TextField();
    @FXML
    private TextField money = new TextField();

    private RegisterController controller = new RegisterController();

    public RegisterMenu() {
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

    public void back() {
        ViewHandler.getViewHandler().pop();
    }
}