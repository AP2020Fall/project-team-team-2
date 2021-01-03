package view;

import controller.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class RegisterMenu  implements View {

    @FXML private Label errorMsg = new Label();
    @FXML private PasswordField password = new PasswordField();
    @FXML private TextField username = new TextField();
    @FXML private TextField firstName = new TextField();
    @FXML private TextField lastName = new TextField();
    @FXML private TextField phoneNumber = new TextField() ;
    @FXML private TextField email = new TextField();
    @FXML private TextField money = new TextField();

    private RegisterController controller = new RegisterController();

    public RegisterMenu() {
        /*super(account);
        System.out.println("Register Menu");
        controller = new RegisterController();
        registerMenu();*/
    }

    @Override
    public void show(Stage window) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/plato/registerMenu.fxml"));
        window.setTitle("Plato");
        window.setScene(new Scene(root));
        window.setResizable(false);
    }
/*
    private void registerMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if ((matcher = getMatcher(input, "^register (\\S+), (\\S+)$")).find()) {
                register(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }*/

   /* private void help() {
        System.out.println("register <username, password>\n" +
                "back");
    }*/

    private ArrayList<String> getAdditionalInfo() {
        ArrayList<String> inputs = new ArrayList<>();
        //System.out.println("firstName: ");
        inputs.add(firstName.getText());
        //System.out.println("lastName: ");
        inputs.add(lastName.getText());
        if (controller.checkEmail(email.getText()))
        {
            inputs.add(email.getText());
        }else
        {
            errorMsg.setText("invalid email");
            return null;
        }
        /*do {
            System.out.println("email: ");
            input = scanner.nextLine();
            if (!checkEmail(input)) {
                System.out.println("invalid email");
            }
        } while (!checkEmail(input));
                inputs.add(input);*/
        if(controller.checkPhoneNumber(phoneNumber.getText()))
        {
            inputs.add(phoneNumber.getText());
        }
        else
        {
            errorMsg.setText("invalid phone number");
            return null;
        }
       /* do {
            System.out.println("phoneNumber:(09121234567)");
            input = scanner.nextLine();
            if (!checkPhoneNumber(input)) {
                System.out.println("invalid phone number");
            }
        } while (!checkPhoneNumber(input));
        inputs.add(input);*/
        if (Admin.isAdminExist()) {
            //System.out.println("money:");
            inputs.add(money.getText());
        }
        return inputs;
    }
    @FXML
    private void register()
    {
        if (!controller.isUsernameExist(username.getText())) {
            //ArrayList<String> additionalInfo = getAdditionalInfo();
            //System.out.println(username + " registered successfully");
            //todo get the info and wait for submit button with an animation
            money.setVisible(Admin.isAdminExist()); //todo must be add before the animation
        } else {
            System.out.println("username exists!");
        }
    }

    public void submit(ActionEvent actionEvent) {
        ArrayList<String> additionalInfo = getAdditionalInfo();
        if(additionalInfo != null) {
            errorMsg.setText(username.getText() + " registered successfully");
            controller.createAccount(username.getText(), password.getText(), additionalInfo);
        }
    }

    public void back() throws IOException {
        ViewHandler.getViewHandler().pop();
    }

    /*private void register(String username, String password) {
        if (!controller.isUsernameExist(username)) {
            ArrayList<String> additionalInfo = getAdditionalInfo();
            System.out.println(username + " registered successfully");
            controller.createAccount(username, password, additionalInfo);
        } else {
            System.out.println("username exists!");
        }
    }*/
}
