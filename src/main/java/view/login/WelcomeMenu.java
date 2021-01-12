package view.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.*;
import view.View;
import view.ViewHandler;

import java.io.File;
import java.io.IOException;


public class WelcomeMenu implements View {
    public WelcomeMenu() {
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/login/welcomeMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
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
        ViewHandler.getViewHandler().exit();
    }
}
