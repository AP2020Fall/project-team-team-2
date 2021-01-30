package view.admin;

import controller.ClientMasterController.ClientMasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Client;
import model.Entry.PlayerEntry;
import view.Tab;
import view.TabHandler;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPlayerListMenu implements Tab, Initializable {
    @FXML
    private TableView<PlayerEntry> playerList;
    private ClientMasterController controller;

    public AdminPlayerListMenu() {
        controller =Client.getConnector().getController();
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

        playerList.setPlaceholder(new Label("No player has registered."));
        playerList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        playerList.getColumns().addAll(playerAvatar, playerName);
        playerList.getItems().addAll(controller.getPlayers());
    }

    @FXML
    void playerTableSelected(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (playerList.getSelectionModel().getSelectedItems().size() != 0) {
                    PlayerEntry playerEntry = playerList.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new AdminProfileView(playerEntry.getName()));
                }
            }

        }
    }
}
