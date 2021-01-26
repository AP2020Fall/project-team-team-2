package view.player;

import controller.player.PlayerFriendsMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Client;
import model.Entry.EventEntry;
import model.Entry.FriendEntry;
import model.Entry.FriendRequestEntry;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerFriendsMenu implements Tab, Initializable {
    @FXML
    private TreeTableView<FriendEntry> friendsList;
    @FXML
    private TableView<FriendRequestEntry> friendRequestList;
    private final PlayerFriendsMenuController controller;


    public PlayerFriendsMenu() {
        controller = new PlayerFriendsMenuController(Client.getClientInfo());
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
        TreeTableColumn<FriendEntry, ImageView>  friendAvatarColumn = new TreeTableColumn<>("Avatar");
        friendAvatarColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("avatar"));
        TreeTableColumn<FriendEntry, String> friendNames = new TreeTableColumn<>("Name");
        friendNames.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<FriendEntry, Hyperlink> friendRemove = new TreeTableColumn<>("Remove");
        friendRemove.setCellValueFactory(new TreeItemPropertyValueFactory<>("remove"));
        TreeItem<FriendEntry> friendRoot = new TreeItem<>();

        friendRoot.getChildren().addAll(controller.getFriends());
        friendsList.setRoot(friendRoot);
        friendsList.setShowRoot(false);
        friendsList.setPlaceholder(new Label("No friends."));
        friendsList.getColumns().addAll(friendAvatarColumn,friendNames, friendRemove);
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
        friendRequestList.setPlaceholder(new Label("No friend requests."));
        friendRequestList.getColumns().addAll(friendRequestNames, friendRequestAccept, friendRequestDecline);
        friendRequestList.getItems().addAll(controller.getFriendRequests());
    }
    @FXML
    void friendRequestTableSelected(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                if(friendRequestList.getSelectionModel().getSelectedItems().size() != 0)
                {
                    FriendRequestEntry friendEntry = friendRequestList.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new PlayerProfileView(controller.getFriend(friendEntry)));
                }
            }

        }
    }

    @FXML
    void friendTableSelected(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                if(friendsList.getSelectionModel().getSelectedItems().size() != 0)
                {
                    FriendEntry friendEntry = friendsList.getSelectionModel().getSelectedItems().get(0).getValue();
                    TabHandler.getTabHandler().push(new PlayerProfileView(controller.getFriend(friendEntry)));
                }
            }

        }
    }




}
