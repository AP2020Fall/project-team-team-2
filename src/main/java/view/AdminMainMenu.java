package view;

import controller.AdminMainMenuController;
import model.*;

import java.time.LocalDate;
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
            } else if ((matcher = getMatcher(input, "Send message (\\S+)")).find()) {
                sendMessage(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Add suggestion (\\S+), (\\S+)")).find()) {
                addSuggestion(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "View suggestions").find()) {
                viewSuggestions();
            } else if ((matcher = getMatcher(input, "Remove suggestion (\\d+)")).find()) {
                removeSuggestion(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Add game (\\S+), (\\S+)")).find()) {
                addGame(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "View games$").find()) {
                viewGames();
            } else if ((matcher = getMatcher(input, "Edit game name (\\S+), (\\S+)")).find()) {
                editGameName(matcher.group(1), matcher.group(2));
            } else if (((matcher = getMatcher(input, "Edit game detail (\\S+), (\\S+)")).find())) {
                editGameDetail(matcher.group(1), matcher.group(2));
            } else if (((matcher = getMatcher(input, "Remove game (\\S+)")).find())) {
                removeGame(matcher.group(1));
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

    private void help() {
        System.out.println("View account menu\n" +
                "Add event <game_name>, <start_date>, <end_date>, <score>\n" +
                "View events\n" +
                "Edit event <event_id>, <field>, <new_value>\n" +
                "Remove event <event_id>\n" +
                "Send message <message>\n" +
                "Add suggestion <username>, <game_name>\n" +
                "View suggestions\n" +
                "Remove suggestion <suggestion_id>\n" +
                "Add game <game_name>, <game_detail>\n" +
                "Edit game name <game_name>, <new_game_name>\n" +
                "Edit game detail <game_name>, <new_game_detail>\n" +
                "Remove game <game_name>\n" +
                "View users\n" +
                "View user profile <username>\n"
        );
    }

    private void addEvent(String gameName, String startDate, String endDate, String score) {
        if (!controller.checkStartDate(startDate) || !controller.checkEndDate(endDate)) {
            System.out.println("invalid date!");
        } else if (Integer.parseInt(score) <= 0) {
            System.out.println("score should be positive!");
        } else if (!controller.doesGameExist(gameName)) {
            System.out.println("invalid game!");
        } else {
            controller.addEvent(gameName, startDate, endDate, Integer.parseInt(score));
        }
    }

    private void viewEvents() {
        for (Event event : controller.showEvents()) {
            System.out.println(event);
        }
    }

    private void editEvent(String eventId, String field, String value) {
        if (!controller.isEventIdExists(eventId)) {
            System.out.println("invalid event id!");
        } else {
            if (field.equals("startDate") && controller.checkStartDate(value)) {
                controller.editEventStart(eventId, value);
                System.out.println("changed successfully!");
            } else if (field.equals("endDate") && controller.checkEndDate(value)) {
                controller.editEventEnd(eventId, value);
                System.out.println("changed successfully!");
            } else if (field.equals("score") && !value.equals("0")) {
                controller.editEventScore(eventId, Integer.parseInt(value));
                System.out.println("changed successfully!");
            } else {
                System.out.println("you cannot change this field!");
            }
        }
    }

    private void removeEvent(String eventId) {
        if (!controller.isEventIdExists(eventId)) {
            System.out.println("invalid event id!");
        } else {
            controller.removeEvent(eventId);
        }
    }

    private void sendMessage(String message) {
        controller.sendMessage(message);
    }

    private void addSuggestion(String username, String gameName) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (!controller.doesGameExist(gameName)) {
            System.out.println("game does not exist!");
        } else {
            controller.addSuggestion(username, gameName);
        }
    }

    private void viewSuggestions() {
        for (Suggestion suggestion : controller.showSuggestions()) {
            System.out.println(suggestion);
        }
    }

    private void removeSuggestion(String suggestionId) {
        if (!controller.isSuggestionIdExists(suggestionId)) {
            System.out.println("invalid suggestion id!");
        } else {
            controller.removeSuggestion(suggestionId);
        }
    }

    private void addGame(String gameName, String gameDetail) {
        if (!controller.doesGameExist(gameName))
            System.out.println("game exists!");
        else
            controller.addGame(gameName, gameDetail);
    }

    private void viewGames() {
        for (String game: controller.viewGames())
            System.out.println(game);
    }

    private void editGameName(String gameName, String newGameName) {
        if (!controller.doesGameExist(gameName))
            System.out.println("game does not exist!");
        else
            controller.editGameName(gameName, newGameName);

    }

    private void editGameDetail(String gameName, String newGameDetail) {
        if (!controller.doesGameExist(gameName))
            System.out.println("game does not exist!");
        else
            controller.editGameDetail(gameName, newGameDetail);
    }

    private void removeGame(String gameName) {
        if (!controller.doesGameExist(gameName))
            System.out.println("game does not exist!");
        else
            controller.removeGame(gameName);
    }

    private void viewUserProfile(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exists!");
        } else {
            String output = controller.showUserProfile(username);
            System.out.println(output);
        }
    }

    private void viewUsers() {
        for (String username : controller.showUsers())
            System.out.println(username);
    }
}