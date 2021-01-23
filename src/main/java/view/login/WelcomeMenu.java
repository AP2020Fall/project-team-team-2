package view.login;

import client.ClientController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.*;
import sun.util.cldr.CLDRLocaleDataMetaInfo;
import view.View;
import view.ViewHandler;

import java.io.*;
import java.net.Socket;

public class WelcomeMenu extends ClientController implements View {
    private static AudioClip audioClip = new AudioClip(WelcomeMenu.class.getResource("/sounds/soundtrack.mp3").toString());

    public WelcomeMenu() {
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/login/welcomeMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        if (!audioClip.isPlaying())
            audioClip.play();
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    public static AudioClip getAudioClip() {
        return audioClip;
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
