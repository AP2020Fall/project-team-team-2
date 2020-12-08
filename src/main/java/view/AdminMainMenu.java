package view;

import controller.AdminMainMenuController;
import model.*;

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
//        Player player = controller.showUserProfile(username);
//        if (player == null) {
//            System.out.println("user does not exists!");
//        } else {
//            System.out.println(player);
//        }
    }

    private void viewUsers() {
        for (String username : controller.showUsers())
            System.out.println(username);
    }

    private void help() {
        System.out.println("View account menu\n" +
                "Add event <game_name>, <start_date>, <end_date>, <score>\n" +
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
        controller.removeSuggestion(suggestionId);
    }

    private void viewSuggestions() {
        for (Suggestion suggestion : controller.showSuggestions()) {
            System.out.println(suggestion);
        }
    }

    private void addSuggestion(String username, String gameName) {
        controller.addSuggestion(username, gameName);
    }

    private void removeEvent(String eventId) {
        controller.removeEvent(eventId);
    }

    private void editEvent(String eventId, String field, String value) {
        // controller.editEvent(eventId, field, value);
    }

    private void viewEvents() {
        for (Event event : controller.showEvents()) {
            System.out.println(event);
        }
    }

    private void addEvent(String gameName, String startDate, String endDate, String score) {
        //check the name and the dates + score greater than zero
        if (!gameName.equals("Risk"))
            System.out.println("Invalid Game!");
        //else if()
        //controller.addEvent(gameName,startDate,endDate,score);
    }


}
