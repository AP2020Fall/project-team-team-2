package view;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;

//public class LoginMenu extends Menu implements View {
public class LoginMenu implements View {
    @FXML private  Label errorMsg = new Label();
    @FXML private TextField username = new TextField();
    @FXML private  PasswordField password = new PasswordField();
    LoginController controller;

   /* public LoginMenu(Account account) {
        super(account);
        System.out.println("Login Menu:");
        controller = new LoginController();
 //       loginMenu();
    }*/

    public LoginMenu() {
        //System.out.println("Login Menu:");
        controller = new LoginController();
        //       loginMenu();
    }
    @Override
    public void show(Stage window) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/plato/loginMenu.fxml"));
        window.setTitle("Plato");
        window.setScene(new Scene(root));
        window.setResizable(false);
    }

 /*   private void loginMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if ((matcher = getMatcher(input, "^login (\\S+)$")).find()) {
                loginAccount(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^delete (\\S+)$")).find()) {
                deleteAccount(matcher.group(1));
            } else if (getMatcher(input, "back").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }
*/
    @FXML
    private void loginAccount() {
        if (!controller.isUsernameExist(username.getText())) {
            //System.out.println("username does not exist");
            errorMsg.setText("username does not exist");
        } else {
           /* System.out.println("password: ");
            String password = scanner.nextLine();*/
            if (!controller.isUsernameAndPasswordMatch(username.getText(), password.getText())) {
               // System.out.println("username and password are not match");
                errorMsg.setText("username and password are not match");
            } else {
                controller.login(username.getText());
            }
        }
    }
    @FXML
    private void deleteAccount() {
        if (!controller.isUsernameExist(username.getText())) {
            //System.out.println("username does not exist");
            errorMsg.setText("username does not exist");
        } else if (!controller.isUsernamePlayer(username.getText())) {
            //System.out.println("Admin account can't be deleted");
            errorMsg.setText("Admin account can't be deleted");
        } else {
            /*System.out.println("password: ");
            String password = scanner.nextLine();*/
            if (!controller.isUsernameAndPasswordMatch(username.getText(), password.getText())) {
                //System.out.println("username and password are not match");
                errorMsg.setText("username and password are not match");
            } else {
                controller.delete(username.getText());
                //System.out.println(username.getText() + " deleted successfully!");
                errorMsg.setText(username.getText() + " deleted successfully!");
            }
        }
    }


    /*private void help() {
        System.out.println("login <username>\n" +
                "delete <username>\n" +
                "back");
    }*/

    @FXML
    private void back() throws IOException {
        ViewHandler.getViewHandler().pop();
    }
}