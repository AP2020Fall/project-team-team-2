package view.player;

import controller.PlayerMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entry.GameEntry;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerGamesMenu implements Tab, Initializable {
    @FXML
    private TableView<GameEntry> gamesListGamesMenu;
    private PlayerMainMenuController controller;

    public PlayerGamesMenu() {
        controller = new PlayerMainMenuController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableGamesList();
    }


    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/playerGamesMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }
    private void initializeTableGamesList() {
        TableColumn<GameEntry, String> gameNameColumn = new TableColumn<>("Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<GameEntry, Hyperlink> gameOpenColumn =
                new TableColumn<>("Open game");
        gameOpenColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        gamesListGamesMenu.getColumns().addAll(gameNameColumn, gameOpenColumn);
        gamesListGamesMenu.getItems().addAll(controller.getGames());
    }

}
