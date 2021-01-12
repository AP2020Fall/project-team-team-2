package view.admin;

import controller.admin.AdminMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Entry.GameEntry;
import model.Entry.PlayerEntry;
import view.Tab;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPlayerListMenu implements Tab, Initializable {
    @FXML
    private TableView<PlayerEntry> playerList;
    private AdminMainMenuController controller;
    public AdminPlayerListMenu()
    {
        controller = new AdminMainMenuController();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableAccountList();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/admin/adminPlayerListMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }
    private void initializeTableAccountList() {
        TableColumn<PlayerEntry, ImageView> playerAvatar = new TableColumn<>("Avatar");
        playerAvatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        TableColumn<PlayerEntry, String> playerName = new TableColumn<>("Name");
        playerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<PlayerEntry, Hyperlink> playerView = new TableColumn<>("View");
        playerView.setCellValueFactory(new PropertyValueFactory<>("view"));

        playerList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        playerList.getColumns().addAll(playerAvatar,playerName, playerView);
        playerList.getItems().addAll(controller.getPlayers());
    }
}
