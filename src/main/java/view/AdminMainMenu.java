package view;

import controller.AdminMainMenuController;
import controller.Controller;
import model.Account;
import model.Admin;
import model.Player;

import java.util.regex.Matcher;

public class AdminMainMenu extends Menu {
    private final AdminMainMenuController controller;
    public AdminMainMenu(Account account) {
        super(account);
        System.out.println("Admin MainMenu");
        controller = new AdminMainMenuController((Admin) account);
        adminMainMenu();
    }

    private void adminMainMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if ((matcher = getMatcher(input, "^Add event (\\S+), (\\d{4}\\/\\d{2}\\/\\d{2}), (\\d{4}\\/\\d{2}\\/\\d{2}), (\\d+)$")).find()) {
                addEvent(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
            } else if (getMatcher(input, "^View events$").find()) {
                viewEvents();
            } else if ((matcher = getMatcher(input, "Edit event (\\d+), (\\S+), (\\S+)")).find()) {
                editEvent(matcher.group(1), matcher.group(2), matcher.group(3));
            } else if ((matcher = getMatcher(input, "Remove event (\\d+)")).find()) {
                removeEvent(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Add suggestion (\\S+), (\\S+)")).find()) {
                addSuggestion(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "View suggestions").find()) {
                viewSuggestions();
            } else if ((matcher = getMatcher(input, "Remove suggestion (\\d+)")).find()) {
                removeSuggestion(matcher.group(1));
            } else if (getMatcher(input, "View users").find()) {
                viewUsers();
            } else if ((matcher = getMatcher(input, "View user profile (\\S+)")).find()) {
                viewUserProfile(matcher.group(1));
            } else if (getMatcher(input, "View account menu").find()) {
                viewAccountMenu(account);
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void viewUserProfile(String username) {
        Player player = controller.showUserProfile(username);
        //printing the info
    }

    private void viewUsers() {
        for(String username: controller.showUsers())
            System.out.println(username);
    }

    private void help() {
        System.out.println("Add event <game_name>, <start_date>, <end_date>, <score>\n" +
                "View events\n" +
                "Edit event <event_id>, <field>, <new_value>\n" +
                "Remove event <event_id>\n" +
                "Add suggestion <username>, <game_name>\n" +
                "View suggestions\n" +
                "Remove suggestion <suggestion_id>\n" +
                "View users\n" +
                "View user profile <username>\n"
        );
    }

    private void removeSuggestion(String suggestionId) {
    }

    private void viewSuggestions() {
    }

    private void addSuggestion(String username, String gameName) {
    }

    private void removeEvent(String eventId) {
    }

    private void editEvent(String eventId, String field, String value) {
    }

    private void viewEvents() {
    }

    private void addEvent(String gameName, String startDate, String endDate, String score) {
        //check the name and the dates + score greater than zero
        if(!gameName.equals("Risk"))
            System.out.println("Invalid Game!");
        //else if()
        //controller.addEvent(gameName,startDate,endDate,score);
    }


}
