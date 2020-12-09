package controller;

import model.Account;
import model.Event;
import model.Player;
import model.Suggestion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Controller {
    private static ArrayList<String> ids = new ArrayList<>();

    protected static String generateId() {
        String id = "";
        while (true) {
            id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
            if (!doesIdExist(id)) {
                ids.add(id);
                return id;
            }
        }
    }

    public boolean isUsernameExist(String username) {
        for (Account account : Account.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameAndPasswordMatch(String username, String password) {
        for (Account account : Account.getAllAccounts()) {
            if (account.getUsername().equals(username)) {
                return account.getPassword().equals(password);
            }
        }
        return false;
    }

    private static boolean doesIdExist(String id) {
        for (String existId : ids) {
            if (existId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public LocalDate createLocalDate(String input) {
        String[] splitInput = input.split("\\/");
        int year = Integer.parseInt(splitInput[0]);
        int month = Integer.parseInt(splitInput[1]);
        int day = Integer.parseInt(splitInput[2]);
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate;
    }

    public boolean isValidDate(String date) {
        String[] splitDate = date.split("\\/");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        return true;
    }

    public boolean checkStartDate(String date) {
        if (isValidDate(date)) {
            LocalDate localDate = createLocalDate(date);
            if (LocalDate.now().compareTo(localDate) < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEndDate(String date) {
        if (isValidDate(date)) {
            LocalDate localDate = createLocalDate(date);
            if (LocalDate.now().compareTo(localDate) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isEventIdExists(String eventId) {
        for (Event event : Event.getEvents()) {
            if (event.getEventId().equals(eventId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuggestionIdExists(String suggestionId) {
        for (Suggestion suggestion : Suggestion.getSuggestions()) {
            if (suggestion.getId().equals(suggestionId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernamePlayer(String username)
    {
        for (Account account : Account.getAllAccounts()) {
            if (account.getUsername().equals(username) && account instanceof Player) {
                return true;
            }
        }
        return false;
    }

}
