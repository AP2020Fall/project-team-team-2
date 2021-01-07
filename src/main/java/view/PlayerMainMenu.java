package view;

import controller.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import model.Entry.*;
import model.Event;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class PlayerMainMenu implements View, Initializable {


    @FXML private TreeTableView<GameEntry> gamesList;
    @FXML private TreeTableView<EventEntry> eventList;
    @FXML private TreeTableView<FriendEntry> friendsList;
    @FXML private TableView<FriendRequestEntry> friendRequestList;
    @FXML private TableView<GameEntry> gamesListGamesMenu;
    @FXML private TableView<GameLogEntry> gameHistoryList;
    @FXML private Label bio = new Label();
    @FXML private Label money = new Label() ;
    @FXML private Label date =  new Label();
    @FXML private Label wins = new Label();
    @FXML private Label friends= new Label();
    @FXML private TextField searchUsername;
    @FXML private Label score = new Label();
    @FXML private Label moneyMenuBar= new Label();
    @FXML private Label scoreMenuBar= new Label();
    PlayerMainMenuController controller = new PlayerMainMenuController();

    public PlayerMainMenu() {
        //super(account);
        // controller = new ((Player) account);
        // System.out.println("Player MainMenu");
        // playerMainMenu();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/playerMainMenu.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMenuBar();

        initializeTreeGamesList();
        initializeTreeEventList();

        initializeTreeFriendsList();
        initializeTableFriendRequestList();

        initializeTableGamesList();

        initializedInfo();
        initializeTableGameHistoryList();
    }



    /*@FXML
    private void viewFriendsMenu() throws IOException {
       ViewHandler.getViewHandler().push(new FriendsMenu());
    }

    @FXML
    private void viewGamesMenu() throws IOException {
        ViewHandler.getViewHandler().push(new GamesMenu());
    }

    @FXML
    private void viewAccountMenu() throws IOException {
        ViewHandler.getViewHandler().push(new PlayerAccountMenu());
    }
    @FXML
    private void viewMainMenu() throws IOException {
        //ViewHandler.getViewHandler().push(new PlayerMainMenu());
    }

    @FXML
    private void back() throws IOException {
        ViewHandler.getViewHandler().mainMenuBack();
    }*/

    public void search() throws IOException {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            ViewHandler.getViewHandler().push(new
                    ProfileView(controller.searchPlayer(searchUsername.getText())));
        }
    }

    public void logout() throws IOException {
        controller.logout();
        ViewHandler.getViewHandler().logout();
    }

    public void editInfo(ActionEvent actionEvent) {
    }

    public void platoMessage(ActionEvent actionEvent) throws IOException {
        ViewHandler.getViewHandler().push(new PlatoMessageView());

    }

    public void initializeMenuBar() {
        scoreMenuBar.setText(controller.showPoints());
        moneyMenuBar.setText(controller.getMoney());
    }

    private void initializeTreeGamesList() {
        TreeItem<GameEntry> favourite = new TreeItem<>(new GameEntry("Favourite Games"));
        favourite.setExpanded(true);
        for (GameEntry gameEntry : controller.favoriteGames()) {

            favourite.getChildren().add(new TreeItem<>(gameEntry));
        }

        TreeItem<GameEntry> recently = new TreeItem<>(new GameEntry("Recently Played"));
        recently.setExpanded(true);
        if (controller.hasPlayerPlayed())
            recently.getChildren().add(new TreeItem<>(controller.lastGamePlayed()));

        TreeItem<GameEntry> suggested = new TreeItem<>(new GameEntry("Suggested"));
        suggested.setExpanded(true);

        for (GameEntry gameEntry : controller.adminsSuggestions()) {
            suggested.getChildren().add(new TreeItem<>(gameEntry));
        }

        TreeTableColumn<GameEntry, String> gameName = new TreeTableColumn<>("Name");
        gameName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeTableColumn<GameEntry, Hyperlink> gameOpen = new TreeTableColumn<>("Open game");
        gameOpen.setCellValueFactory(new TreeItemPropertyValueFactory<>("link"));
        TreeItem<GameEntry> gameRoot = new TreeItem<>();
        gameRoot.getChildren().addAll(favourite, recently, suggested);
        gamesList.setRoot(gameRoot);
        gamesList.setShowRoot(false);
        gamesList.getColumns().addAll(gameName, gameOpen);
    }

    private void initializeTreeEventList() {
        TreeTableColumn<EventEntry, String> eventName = new TreeTableColumn<>("Name");
        eventName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        TreeItem<EventEntry> eventRoot = new TreeItem<>();
        for (EventEntry eventEntry : controller.getEvents())
            eventRoot.getChildren().add(new TreeItem<>(eventEntry));
        eventList.setRoot(eventRoot);
        eventList.setShowRoot(false);
        eventList.getColumns().add(eventName);
    }

    private void initializedInfo() {
        score.setText(controller.showPoints());
        bio.setText(controller.getBio());
        money.setText(controller.getMoney());
        date.setText(controller.getDate());
        wins.setText(controller.getWins());
        friends.setText(controller.getFriendCount());
    }

    private void initializeTableGameHistoryList() {
        TableColumn<GameLogEntry, String> gameNameColumn = new TableColumn<>("Game");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        TableColumn<GameLogEntry, Integer> frequencyColumn = new TableColumn<>("Frequency");
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        TableColumn<GameLogEntry, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<GameLogEntry, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn<GameLogEntry, LocalDateTime> lastPlayColumn = new TableColumn<>("Last Play");
        lastPlayColumn.setCellValueFactory(new PropertyValueFactory<>("lastPlay"));
        gameHistoryList.getColumns().addAll(gameNameColumn, frequencyColumn, winsColumn, scoreColumn, lastPlayColumn);
        gameHistoryList.getItems().addAll(controller.getGameHistory());
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
    /*private void playerMainMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^Show Points$").find()) {
                showPoints();
            } else if (getMatcher(input, "^View favorite games$").find()) {
                viewFavoriteGames();
            } else if (getMatcher(input, "^View platobot's messages$").find()) {
                viewPlatoBotMessages();
            } else if (getMatcher(input, "^View last played$").find()) {
                viewLastPlayed();
            } else if (getMatcher(input, "^View admin's suggestions$").find()) {
                viewAdminSuggestion();
            } else if ((matcher = getMatcher(input, "^Choose suggested game (\\S+)")).find()) {
                chooseSuggestedGame(matcher.group(1));
//            } else if ((matcher = getMatcher(input, "^Add friend (\\S+)$")).find()) {
//                addFriend(matcher.group(1));
            } else if (getMatcher(input, "^View account menu$").find()) {
                viewAccountMenu(account);
            } else if (getMatcher(input, "^View friends menu").find()) {
                viewFriendsMenu(account);
            } else if (getMatcher(input, "View Games menu").find()) {
                ViewGamesMenu(account);
            } else {
                System.out.println("invalid command!");
            }
        }
    }*/

//  private void addFriend(String username) {
//        controller.addFriend(username);
//    }

  /*  private void chooseSuggestedGame(String gameName) {
        if (!controller.isGameSuggested(gameName))
            System.out.println("game is not suggested!");
        else
            controller.chooseSuggestedGame(gameName);
    }*/

    /*
        private void viewAdminSuggestion() {
            //todo initialize a row to TreeTableView games
            if(controller.showAdminsSuggestions().isEmpty())
                System.out.println("no suggestions!");
            else {
                for (String suggestion : controller.showAdminsSuggestions())
                    System.out.println(suggestion);
            }
        }
    *//*
    private void viewLastPlayed() {
        //todo initialize a row to TreeTableView games
        if(!controller.hasPlayerPlayed())
            System.out.println("player has not played a game!");
        else
        System.out.println( controller.showLastPlayed());
    }
*/
  /*  private void viewPlatoBotMessages() {
        //todo add mail icon to the menu bar
        if (controller.showPlatoBotsMessages().isEmpty())
            System.out.println("no message!");
        else {
            for (String message : controller.showPlatoBotsMessages()) {
                System.out.println(message);
            }
        }
    }
*/
    /*
        private void viewFavoriteGames() {
            //todo initialize a row to TreeTableView games
            if(controller.showFavoriteGames().isEmpty())
                System.out.println("no favorite game!");
            else {
                for (String favoriteGame : controller.showFavoriteGames()) {
                    System.out.println(favoriteGame);
                }
            }
        }
    */
  /*  private void showPoints() {
        int points = controller.showPoints();
        System.out.println("points: " + points);
    }*/


/*
    private void help() {
        System.out.println("Show Points\n" +
                "View favorite games\n" +
                "View platobot's messages\n" +
                "View last played\n" +
                "View admin's suggestions\n" +
                "Choose suggested game\n" +
                "View account menu\n" +
                "View friends menu\n" +
                "View games menu\n");
    }
*/

