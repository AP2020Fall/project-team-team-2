package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Account;
import model.Event;
import model.Game;
import model.Suggestion;

import java.io.IOException;

//public class WelcomeMenu extends Menu implements View{
    //public WelcomeMenu(Account account) {
    //    super(account);
       // System.out.println("Welcome Menu");
       // welcomeMenu();
public class WelcomeMenu implements View{
    public WelcomeMenu() {
    }

    @Override
    public void show(Stage window) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/plato/welcomeMenu.fxml"));
        window.setTitle("Plato");
        window.setScene(new Scene(root));
        window.setResizable(false);
    }

   /* private void welcomeMenu() {
        while (true) {
            String input = scanner.nextLine();

            if (getMatcher(input, "^register$").find()) {
                openRegisterMenu();
            } else if (getMatcher(input, "^login$").find()) {
                openLoginMenu();
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^exit$").find()) {
                saveFiles();
                System.exit(1);
            } else {
                System.out.println("invalid command!");
            }
        }
    }*/

    private void saveFiles() {
        try {
            Account.save();
            Event.save();
            Suggestion.save();
            Game.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void help() {
        System.out.println("register\n" +
                "login\n" +
                "help\n" +
                "exit");
    }
    @FXML
    private void openLoginMenu() {
        new LoginMenu(null);
    }

    @FXML
    private void openRegisterMenu() {
        new RegisterMenu(null);
    }

    @FXML
    private void exit()
    {
        saveFiles();
        System.exit(1);
    }
}
