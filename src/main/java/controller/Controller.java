package controller;

import com.google.gson.Gson;
import main.ClientInfo;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import model.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static ArrayList<String> ids = new ArrayList<>();
    //protected static Account loggedIn;

    public Controller(ClientInfo clientInfo)
    {

    }
    protected static String generateId() {
        String id = "";
        while (true) {
            id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
            if (!idExists(id)) {
                ids.add(id);
                return id;
            }
        }
    }

    public static String generateId(int different) {
        String id = "";
        while (true) {
            id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
            if (!idExists(id)) {
                ids.add(id);
                return id;
            }
        }
    }

    public Boolean usernameExist(String username) {
        return Account.getAccountByUsername(username) != null;
    }

    public Boolean checkEmail(String email) {
        return email.matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$");
    }

    public Boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("09\\d{9}");
    }

    public Boolean checkNumber(String number) {
        return number.matches("^\\d+");
    }


    public Boolean usernameAndPasswordMatch(String username, String password) {
        Account account = Account.getAccountByUsername(username);
        if (account != null) {
            return account.getPassword().equals(password);
        }
        return false;
    }

    private static Boolean idExists(String id) {
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

    public Boolean isValidDate(String date) {
        String[] splitDate = date.split("\\/");
        int year = Integer.parseInt(splitDate[0]);
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }
        return true;
    }

    public Boolean checkRelativeDate(String startString, String endString) {
        LocalDate start = new Gson().fromJson(startString,LocalDate.class);
        LocalDate end = new Gson().fromJson(endString,LocalDate.class);
        return start.compareTo(end) < 0;
    }

    public Boolean checkStartDate(String dateString) {
        LocalDate date = new Gson().fromJson(dateString,LocalDate.class);
        return LocalDate.now().compareTo(date) <= 0;
    }

    public Boolean checkEndDate(String dateString) {
        LocalDate date = new Gson().fromJson(dateString,LocalDate.class);
        return LocalDate.now().compareTo(date) < 0;
    }

    public boolean isEventIdExists(String eventId) {
        return Event.getEventById(eventId) != null;
    }

    public Boolean isSuggestionIdExists(String suggestionId) {
        for (Suggestion suggestion : Suggestion.getSuggestions()) {
            if (suggestion.getSuggestionId().equals(suggestionId)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isUsernamePlayer(String username) {
        Account account = Account.getAccountByUsername(username);
        if(account != null)
            return account instanceof Player;
        return false;
    }

    public Admin getAdmin()
    {
        return Account.getAdmin();
    }

    public Boolean adminExists()
    {
        return Admin.isAdminExist();
    }
    public Boolean doesGameExist(String gameName) {
        return Game.getGameByGameName(gameName) != null;
    }


    public Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public Player searchPlayer(String username) {
        return Player.getPlayerByUsername(username);
    }


    public HashMap<Player, Integer> usernameFuzzySearch(String username) {
        List<Map.Entry<Player, Integer>> list = new LinkedList<>();
        for (Player player : Account.getAllPlayers()) {
            int fuzzyVal = FuzzySearch.ratio(username, player.getUsername());
            if (fuzzyVal > 69)
                list.add(new AbstractMap.SimpleEntry<Player, Integer>(player, fuzzyVal));
        }
        list.sort(new Comparator<Map.Entry<Player, Integer>>() {
            public int compare(Map.Entry<Player, Integer> o1,
                               Map.Entry<Player, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<Player, Integer> temp = new LinkedHashMap<Player, Integer>();
        for (Map.Entry<Player, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;

    }

    public ArrayList<Player> usernameFuzzySearchTop10(String username) {
        HashMap<Player, Integer> temp = usernameFuzzySearch(username);
        ArrayList<Player> result = new ArrayList<>();
        int counter = 10;
        for (Map.Entry<Player, Integer> player : temp.entrySet()) {
            result.add(player.getKey());
            counter--;
            if (counter == 0) break;
        }
        return result;
    }

    public ArrayList<Player> usernameFuzzySearchTop5(String username) {
        HashMap<Player, Integer> temp = usernameFuzzySearch(username);
        ArrayList<Player> result = new ArrayList<>();
        int counter = 5;
        for (Map.Entry<Player, Integer> player : temp.entrySet()) {
            result.add(player.getKey());
            counter--;
            if (counter == 0) break;
        }
        return result;
    }
}
