package view;

import controller.AccountMenuController;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import view.login.WelcomeMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountMenu implements View, Initializable {
    AccountMenuController controller;

    public AccountMenu() {
        //super(account);
        controller = new AccountMenuController();
        //accountMenu();
    }

    private void viewPersonalInfo() {
        System.out.println(controller.showPersonalInfo());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void show(Stage stage) throws IOException {

    }
    /*private void accountMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^View personal info$").find()) {
                viewPersonalInfo();
            } else if ((matcher = getMatcher(input, "^Change password (\\S+), (\\S+)$")).find()) {
                changePassword(matcher.group(1), matcher.group(2));
            } else if ((matcher = getMatcher(input, "^Edit (\\S+), (\\S+)$")).find()) {
                editField(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "^View plato statistics$").find()) {
                viewPlatoStatistics();
            } else if (getMatcher(input, "^Games history$").find()) {
                gameHistory();
            } else if ((matcher = getMatcher(input, "^Game statistics (\\S+)$")).find()) {
                gameStatistics(matcher.group(1));
            } else if (getMatcher(input, "^Logout$").find()) {
                logout();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }*/

    private void logout() {
        new WelcomeMenu();
    }

   /* private void gameStatistics(String gameName) {
        if (account instanceof Player) {
            if (!gameName.equals("RiskGame")) {
                System.out.println("Game dose not exists!");
            } else {
                System.out.println(controller.showGameStatistics(gameName));
            }
        } else {
            System.out.println("you are not a player!");
        }
    }

    private void gameHistory() {
        if (account instanceof Player) {
            for(String gameHistory: controller.showGamesHistory())
                System.out.println(gameHistory);
        } else {
            System.out.println("you are not a player!");
        }
    }

    private void viewPlatoStatistics() {
        if (account instanceof Player) {
            ArrayList<Integer> results = controller.viewPlatoStatistics();
            System.out.println("number of friends: " + results.get(0) +
                    "\nnumber of wins: " + results.get(1) +
                    "\nscore: " + results.get(2) +
                    "\nnumber of day passed: " + results.get(2));
        } else {
            System.out.println("you are not a player!");
        }
    }

    private void editField(String field, String newValue) {
        if (!isFieldCanChange(field)) {
            System.out.println("invalid field or this field is not changeable");
            return;
        }
        if (field.equals("email") && !controller.checkEmail(newValue)) {
            System.out.println("invalid email");
            return;
        }
        if (field.equals("phoneNumber") && !controller.checkPhoneNumber(newValue)) {
            System.out.println("invalid phone number");
            return;
        }
        if (field.equals("username") && controller.isUsernameExist(newValue)) {
            System.out.println("this username exists");
            return;
        }
        controller.editField(field, newValue);
        System.out.println("edited successfully!");

    }

    private boolean isFieldCanChange(String field) {
        if (field.equals("firstName") || field.equals("lastName") || field.equals("email") || field.equals("phoneNumber") || field.equals("username")) {
            return true;
        } else {
            return false;
        }
    }

    private void help() {
        System.out.println("View personal info\n" +
                "Change password <current_pass>, <new_pass>\n" +
                "Edit <field>, <new_value>\n" +
                "View plato statistics\n" +
                "Games history\n" +
                "Game statistics <game_name>\n" +
                "Logout\n" +
                "back");
    }

    private void changePassword(String currentPassword, String newPassword) {
        if (!account.getPassword().equals(currentPassword)) {
            System.out.println("password is not correct");
        } else {
            controller.changePassword(newPassword);
            System.out.println("password changed successfully");
        }
    }
*/

}
