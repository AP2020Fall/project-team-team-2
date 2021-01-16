package view.player;

import com.jfoenix.controls.JFXTextField;
import controller.player.PlayerRunGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Event;
import model.Game;
import view.AlertMaker;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerRunGameView implements Tab, Initializable {
    @FXML
    private JFXTextField player2Name;
    @FXML
    private JFXTextField player3Name;
    @FXML
    private JFXTextField player4Name;
    @FXML
    private JFXTextField player5Name;
    @FXML
    private ImageView gameAvatar;
    @FXML
    private ImageView player2Avatar;
    @FXML
    private ImageView player3Avatar;
    @FXML
    private ImageView player4Avatar;
    @FXML
    private ImageView player5Avatar;
    @FXML
    private Label eventMode;
    private final ContextMenu searchContextMenu;
    private final PlayerRunGameController controller;
    private final ArrayList<String> usernames;
    public PlayerRunGameView(Game game, Event event)
    {
        controller = new PlayerRunGameController(game,event);
        usernames = new ArrayList<>();
        usernames.add(controller.getUsername());
        searchContextMenu = new ContextMenu();
    }
    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerRunGameView.fxml"));
        loader.setController(this);
        return loader.load();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
            initializeInfo();
    }
    @FXML
    void playerSearch(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) return;
        JFXTextField textField = (JFXTextField) event.getSource();
        String searchQuery = textField.getText();
        searchContextMenu.getItems().clear();
        searchContextMenu.getItems().addAll(controller.getSearchQuery(textField, searchQuery,this));
        searchContextMenu.show(textField, Side.RIGHT,0,0);
    }

    @FXML
    void start() {
        controller.runGame(usernames);
    }
    @FXML
    void cancel() {
            TabHandler.getTabHandler().back();
    }

    private void initializeInfo() {
        gameAvatar.setImage(controller.getGameImage());
        eventMode.setText(controller.getEventMode());
    }
    @FXML
    void update(ActionEvent event) {
        JFXTextField textField = (JFXTextField) event.getSource();
        update(textField);
    }
    public void update(JFXTextField textField)
    {
        if (!controller.isUsernameExist(textField.getText())) {
            AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                    TabHandler.getTabHandler().getStackRoot().getChildren().get(0), "Okay",
                    "Invalid username", "Username does not exist!");
        } else if (usernames.contains(textField.getText())) {
            AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                    TabHandler.getTabHandler().getStackRoot().getChildren().get(0), "Okay",
                    "Invalid username", "Username is already added!");
        } else {
            updateImageMenuContext(textField);
            textField.setDisable(true);
        }
    }
    private void updateImage(ImageView imageView,String username)
    {
        imageView.setImage(controller.getUsernameImage(username));
        usernames.add(username);
    }

    private void updateImageMenuContext(JFXTextField textField) {
        if (textField.equals(player2Name)) {
            updateImage(player2Avatar, textField.getText());
        } else if (textField.equals(player3Name)) {
            updateImage(player3Avatar, textField.getText());
        } else if (textField.equals(player4Name)) {
            updateImage(player4Avatar, textField.getText());
        } else if (textField.equals(player5Name)) {
            updateImage(player5Avatar, textField.getText());

        }
    }
}
