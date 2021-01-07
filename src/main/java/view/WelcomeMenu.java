package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;


public class WelcomeMenu implements View {
    public WelcomeMenu() {
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/welcomeMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    private void saveFiles() {
        try {
            Account.save();
            Event.save();
            Suggestion.save();
            Game.save();
            FriendRequest.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @FXML
    private void openLoginMenu() {
        ViewHandler.getViewHandler().push(new LoginMenu());
    }

    @FXML
    private void openRegisterMenu() {
        ViewHandler.getViewHandler().push(new RegisterMenu());
    }

    @FXML
    private void exit() {
        saveFiles();
        ViewHandler.getViewHandler().exit();
        System.exit(1);
    }
}
