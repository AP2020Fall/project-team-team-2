package view.admin;

import controller.admin.AdminMainMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Entry.FriendRequestEntry;
import model.Entry.GameEntry;
import model.Entry.PlayerEntry;
import view.Tab;
import view.TabHandler;
import view.player.PlayerProfileView;

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

        playerList.setPlaceholder(new Label("No player has registered."));
        playerList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        playerList.getColumns().addAll(playerAvatar,playerName, playerView);
        playerList.getItems().addAll(controller.getPlayers());
    }
    @FXML
    void friendRequestTableSelected(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                if(playerList.getSelectionModel().getSelectedItems().size() != 0)
                {
                    PlayerEntry playerEntry = playerList.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new AdminProfileView(controller.getPlayer(playerEntry)));
                }
            }

        }
    }
}
