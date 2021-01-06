package view;

import controller.FriendsMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import model.Account;
import model.Entry.FriendEntry;
import model.Entry.FriendRequestEntry;
import model.FriendRequest;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class FriendsMenu implements View, Initializable {

    @FXML private  TextField searchUsername;
    @FXML private  Label score;
    @FXML private  TreeTableView<FriendEntry> friends;
    @FXML private  TreeTableView<FriendRequestEntry> friendRequest;
    private final FriendsMenuController controller;

    public FriendsMenu() {
        controller = new FriendsMenuController();
       // friendsMenu();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/friendsMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        score.setText(controller.showPoints());

        TreeTableColumn<FriendEntry,String> friendNames = new TreeTableColumn<>("Name");
        friendNames.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<FriendEntry, Hyperlink> friendView = new TreeTableColumn<>("View");
        friendView.setCellValueFactory(new TreeItemPropertyValueFactory<>("view"));
        TreeTableColumn<FriendEntry,Hyperlink> friendRemove = new TreeTableColumn<>("Remove");
        friendRemove.setCellValueFactory(new TreeItemPropertyValueFactory<>("remove"));
        TreeItem<FriendEntry> friendRoot = new TreeItem<>();
        friendRoot.getChildren().addAll(controller.getFriends());
        friends = new TreeTableView<>(friendRoot);
        friends.setShowRoot(false);
        friends.getColumns().addAll(friendNames,friendView,friendRemove);

        TreeTableColumn<FriendRequestEntry,String> friendRequestNames = new TreeTableColumn<>("Name");
        friendNames.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<FriendRequestEntry, Hyperlink> friendRequestAccept=
                new TreeTableColumn<>("Accept");
        friendView.setCellValueFactory(new TreeItemPropertyValueFactory<>("accept"));
        TreeTableColumn<FriendRequestEntry,Hyperlink> friendRequestDecline =
                new TreeTableColumn<>("Decline");
        friendRemove.setCellValueFactory(new TreeItemPropertyValueFactory<>("decline"));
        TreeItem<FriendRequestEntry> friendRequestRoot = new TreeItem<>();
        friendRequestRoot.getChildren().addAll(controller.getFriendRequest());
        friendRequest = new TreeTableView<>(friendRequestRoot);
        friendRequest.setShowRoot(false);
        friendRequest.getColumns().addAll(friendRequestNames,friendRequestAccept,friendRequestDecline);
    }

    @FXML
    private void platoMsg() throws IOException {
        ViewHandler.getViewHandler().push(new PlatoMessageView());
    }

    @FXML
    private void back() throws IOException {
        ViewHandler.getViewHandler().mainMenuBack();
    }
    @FXML
    private void viewFriendsMenu() throws IOException {
        //ViewHandler.getViewHandler().push(new FriendsMenu());
    }

    @FXML
    private void viewGamesMenu() throws IOException {
        ViewHandler.getViewHandler().push(new GamesMenu());
    }

    @FXML
    private void viewAccountMenu() throws IOException {
        ViewHandler.getViewHandler().push(new AccountMenu());
    }
    public void viewMainMenu() throws IOException {
        ViewHandler.getViewHandler().push(new PlayerMainMenu());
    }

    public void search() throws IOException {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            ViewHandler.getViewHandler().push(new
                    ProfileView(controller.searchPlayer(searchUsername.getText())));
        }
    }

/*
    private void friendsMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "help").find()) {
                help();
            } else if (getMatcher(input, "View account menu").find()) {
                viewAccountMenu(account);
            } else if (getMatcher(input, "Show friends").find()) {
                showFriends();
            } else if ((matcher = getMatcher(input, "Remove (\\S+)")).find()) {
                remove(matcher.group(1));
            } else if ((matcher = getMatcher(input, "View user profile (\\S+)")).find()) {
                viewUserProfile(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Add (\\S+)")).find()) {
                add(matcher.group(1));
            } else if (getMatcher(input, "Show friend requests").find()) {
                showFriendsRequests();
            } else if ((matcher = getMatcher(input, "Accept (\\S+)")).find()) {
                accept(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Decline (\\S+)")).find()) {
                decline(matcher.group(1));
            } else if (getMatcher(input, "back").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }
*/
    /*
    private void decline(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (controller.friendExists(username)) {
            System.out.println("this user has already been your friend");
        } else {
            controller.declineFriendRequest(username);
        }
    }

    private void accept(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (!controller.hasSentFriendRequest(username)) {
            System.out.println("this user hasn not send a friend request");
        } else {
            controller.acceptFriendRequest(username);
        }
    }

    private void showFriendsRequests() {
        if (controller.showFriendRequests().isEmpty())
            System.out.println("No friend requests.");
        else
        for (String friend : controller.showFriendRequests()) {
            System.out.println(friend);
        }
    }

    private void add(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (controller.friendExists(username)) {
            System.out.println("this user has already been your friend");
        }else if(account.getUsername().equals(username))
            System.out.println("can't add yourself.");
        else {
            controller.addFriend(username);
        }
    }

    private void viewUserProfile(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else {
            controller.showUserProfile(username);
        }
    }

    private void remove(String username) {
        if (!controller.friendExists(username)) {
            System.out.println(username + " is not your friend!");
        } else {
            controller.removeFriend(username);
            System.out.println("friend removed successfully!");
        }
    }

    private void showFriends() {
        if(controller.showFriends().isEmpty())
            System.out.println("No friends.");
        else
        for (String friend : controller.showFriends()) {
            System.out.println(friend);
        }
    }

    private void help() {
        System.out.println("View account menu\n" +
                "Show friends\n" +
                "Remove <username>\n" +
                "View user profile <username>\n" +
                "Add <username>\n" +
                "Show friend requests\n" +
                "Accept <username>\n" +
                "Decline <username>\n" +
                "help\n" +
                "back");
    }

*/
}
