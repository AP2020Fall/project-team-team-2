package view.player;

import controller.ClientMasterController.ClientMasterController;
import controller.player.PlayerMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.Client;
import view.AlertMaker;
import view.TabHandler;
import view.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerMainMenuLayout implements View, Initializable {
    private static AudioClip audioClip = new AudioClip(PlayerMainMenuLayout.class.getResource("/sounds/playerSound.mp3").toString());
    private final ClientMasterController controller;
    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane stackRoot;
    @FXML
    private TextField searchUsername;
    @FXML
    private Label moneyMenuBar = new Label();
    @FXML
    private Label scoreMenuBar = new Label();
    @FXML
    private ContextMenu searchContextMenu;

    public PlayerMainMenuLayout() {
        controller = Client.getConnector().getController();
    }

    public static AudioClip getAudioClip() {
        return audioClip;
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/player/playerMainMenuLayout.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        if (!audioClip.isPlaying())
            playSoundtrack();
        Scene scene = new Scene(root.load());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
    }

    private void playSoundtrack() {
        audioClip.setCycleCount(audioClip.INDEFINITE);
        audioClip.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMenuBar();
        TabHandler.getTabHandler().setBorderPane(borderPane);
        TabHandler.getTabHandler().setStackPane(stackRoot);
        viewMainMenu();
    }

    @FXML
    private void search() {
        if (!controller.usernameExist(searchUsername.getText())) {
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid username", "Username does not exist!");
        } else {
            TabHandler.getTabHandler().push(
                    new PlayerProfileView(searchUsername.getText()));
        }
    }

    @FXML
    void updateContextMenu(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) return;
        String searchQuery = searchUsername.getText();
        searchContextMenu.getItems().clear();
        searchContextMenu.getItems().addAll(controller.getSearchQuery(searchQuery));
        searchContextMenu.show(searchUsername, Side.BOTTOM, 0, 0);

    }

    @FXML
    private void platoMessage() throws IOException {
        new PlatoMessageView().openWindow();
    }

    @FXML
    private void back() {
        TabHandler.getTabHandler().back();
    }

    @FXML
    private void ahead() {
        TabHandler.getTabHandler().ahead();
    }

    @FXML
    private void viewMainMenu() {
        if (TabHandler.getTabHandler().current() instanceof PlayerMainMenu) {
            TabHandler.getTabHandler().refresh();
        } else
            TabHandler.getTabHandler().push(new PlayerMainMenu());
    }

    @FXML
    private void viewFriendsMenu() {
        if (TabHandler.getTabHandler().current() instanceof PlayerFriendsMenu) {

            TabHandler.getTabHandler().refresh();
        } else
            TabHandler.getTabHandler().push(new PlayerFriendsMenu());
    }

    @FXML
    private void viewGamesMenu() {
        if (TabHandler.getTabHandler().current() instanceof PlayerGamesMenu)
            TabHandler.getTabHandler().refresh();
        else
            TabHandler.getTabHandler().push(new PlayerGamesMenu());
    }

    @FXML
    private void viewAccountMenu() {
        if (TabHandler.getTabHandler().current() instanceof PlayerAccountMenu)
            TabHandler.getTabHandler().refresh();
        else
            TabHandler.getTabHandler().push(new PlayerAccountMenu());
    }

    public void initializeMenuBar() {
        scoreMenuBar.setText(controller.getPlayerPoints());
        moneyMenuBar.setText(controller.getPlayerMoney());
    }
}
