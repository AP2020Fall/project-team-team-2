package view.player;

import controller.FriendsMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import model.Entry.FriendEntry;
import model.Entry.FriendRequestEntry;
import view.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerFriendsMenu implements Tab, Initializable {
    @FXML
    private TreeTableView<FriendEntry> friendsList;
    @FXML
    private TableView<FriendRequestEntry> friendRequestList;
    private final FriendsMenuController controller;


    public PlayerFriendsMenu() {
        controller = new FriendsMenuController();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerFriendsMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTreeFriendsList();
        initializeTableFriendRequestList();
    }

    private void initializeTreeFriendsList() {
        TreeTableColumn<FriendEntry, String> friendNames = new TreeTableColumn<>("Name");
        friendNames.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<FriendEntry, Hyperlink> friendView = new TreeTableColumn<>("View");
        friendView.setCellValueFactory(new TreeItemPropertyValueFactory<>("view"));
        TreeTableColumn<FriendEntry, Hyperlink> friendRemove = new TreeTableColumn<>("Remove");
        friendRemove.setCellValueFactory(new TreeItemPropertyValueFactory<>("remove"));
        TreeItem<FriendEntry> friendRoot = new TreeItem<>();

        friendRoot.getChildren().addAll(controller.getFriends());
        friendsList.setRoot(friendRoot);
        friendsList.setShowRoot(false);
        friendsList.getColumns().addAll(friendNames, friendView, friendRemove);
    }

    private void initializeTableFriendRequestList() {
        TableColumn<FriendRequestEntry, String> friendRequestNames = new TableColumn<>("Name");
        friendRequestNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<FriendRequestEntry, Hyperlink> friendRequestAccept =
                new TableColumn<>("Accept");
        friendRequestAccept.setCellValueFactory(new PropertyValueFactory<>("accept"));
        TableColumn<FriendRequestEntry, Hyperlink> friendRequestDecline =
                new TableColumn<>("Decline");
        friendRequestDecline.setCellValueFactory(new PropertyValueFactory<>("decline"));

        friendRequestList.getColumns().addAll(friendRequestNames, friendRequestAccept, friendRequestDecline);
        friendRequestList.getItems().addAll(controller.getFriendRequests());
    }


}
