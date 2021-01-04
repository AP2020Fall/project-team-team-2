package view;

import controller.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import model.Account;
import model.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class PlayerMainMenu implements View, Initializable {
    public TreeTableView games;
    public TreeTableView eventList;
    PlayerMainMenuController controller =new PlayerMainMenuController(null);

    public PlayerMainMenu() {
        //super(account);
       // controller = new ((Player) account);
       // System.out.println("Player MainMenu");
       // playerMainMenu();
    }

    @Override
    public void show(Stage stage) throws IOException {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    @FXML
    private void viewFriendsMenu() {
    }
    @FXML
    private void viewGamesMenu() {

    }
    @FXML
    private void viewAccountMenu() {

    }


//  private void addFriend(String username) {
//        controller.addFriend(username);
//    }

    private void chooseSuggestedGame(String gameName) {
        if(!controller.isGameSuggested(gameName))
            System.out.println("game is not suggested!");
        else
            controller.chooseSuggestedGame(gameName);
    }

    private void viewAdminSuggestion() {
        //todo initialize a row to TreeTableView games
        if(controller.showAdminsSuggestions().isEmpty())
            System.out.println("no suggestions!");
        else {
            for (String suggestion : controller.showAdminsSuggestions())
                System.out.println(suggestion);
        }
    }

    private void viewLastPlayed() {
        //todo initialize a row to TreeTableView games
        if(!controller.hasPlayerPlayed())
            System.out.println("player has not played a game!");
        else
        System.out.println( controller.showLastPlayed());
    }

    private void viewPlatoBotMessages() {
        //todo add mail icon to the menu bar
        if(controller.showPlatoBotsMessages().isEmpty())
            System.out.println("no message!");
        else {
            for (String message : controller.showPlatoBotsMessages()) {
                System.out.println(message);
            }
        }
    }

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

    private void showPoints() {
        int points = controller.showPoints();
        System.out.println("points: " + points);
    }

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



}
