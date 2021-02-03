package model;

import com.google.gson.GsonBuilder;
import controller.ServerMasterController.SQLConnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map;

public class Suggestion {
    private final String gameName;
    private final String suggestionId;
    private final String playerName;

    public Suggestion(Game game, String suggestionId, Player player) {
        this.gameName = game.getName();
        this.suggestionId = suggestionId;
        this.playerName = player.getUsername();
    }

    public Suggestion(Map<String, Object> suggestion) {
        Map<String, Object> resultMap = new HashMap<>();
        this.gameName = (String) suggestion.get("game_name");
        this.suggestionId = (String) suggestion.get("suggestion_id");
        this.playerName = (String) suggestion.get("player_name");
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    /*public void editField(String field, Object value) {
        java.util.Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("suggestion_id", this.suggestionId);
        java.util.Map<String, Object> newValueMap = new HashMap<>();
        newValueMap.put(field, value);
        SQLConnector.updateTable(conditionMap, newValueMap, "suggestions");
    }*/

    public static void addSuggestion(Suggestion suggestion) {
        java.util.Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("game_name", suggestion.gameName);
        resultMap.put("suggestion_id",suggestion.suggestionId);
        resultMap.put("player_name", suggestion.playerName);
        SQLConnector.insertInDatabase(resultMap,"suggestions");
    }

    public static List<java.util.Map<String, Object>> SQLSuggestionSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "suggestions");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: Suggestion with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount;
    }



    public static ArrayList<Suggestion> getSuggestions() {
        ArrayList<Suggestion> result = new ArrayList<>();
        List<Map<String, Object>> suggestionList = SQLConnector.getWholeTable("suggestions");
        if (suggestionList == null || suggestionList.isEmpty())
        {
            System.out.println("[MODEL]: no Suggestion could be found");
            return result;
        }
        for (Map<String,Object> suggestion:suggestionList)
        {
            result.add(new Suggestion(suggestion));
        }
        return result;
    }

    /*public static void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }
    */

    public static Suggestion getSuggestionById(String suggestionId) {
        List<Map<String,Object>> suggestion = SQLSuggestionSearch("suggestion_id",suggestionId);
        if(suggestion == null || suggestion.isEmpty())
        {
            return null;
        }
        return new Suggestion(suggestion.get(0));
    }

    public void delete() {
        /*File file = new File("database" + "\\" + "suggestions" + "\\" + this.suggestionId + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }*/
        Player player = Player.getPlayerByUsername( this.getPlayerName());
        if(player != null)
        {
           player.removeSuggestion(this);
        }

    }

    public static void deletePlayerSuggestion(String username) {
        Map<String, Object> suggestion = new HashMap<>();
        suggestion.put("player_name", username);
        if (!SQLConnector.deleteFromTable(suggestion, "suggestions")) {
            System.out.println("[MODEL]: Suggestion with player name = " + username + " couldn't be deleted");
        }
    }

    public static void deleteGameSuggestion(String gameName) {
        //todo delete from player as well
        Map<String, Object> suggestion = new HashMap<>();
        suggestion.put("game_name", gameName);
        if (!SQLConnector.deleteFromTable(suggestion, "suggestions")) {
            System.out.println("[MODEL]: Suggestion with player name = " + gameName + " couldn't be deleted");
        }
    }
/*
    public static void open() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "suggestions");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                suggestions.add(openSuggestion(file));
            }
        }
    }

    private static Suggestion openSuggestion(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Suggestion.class);

    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) json.append(reader.nextLine());
        reader.close();
        return json;
    }

    public static void save() throws IOException {
        for (Suggestion suggestion : suggestions) {
            save(suggestion);
        }
    }

    private static void save(Suggestion suggestion) throws IOException {
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create()
                .toJson(suggestion);
        FileWriter file = new FileWriter("database" + "\\" + "suggestions" + "\\" + suggestion.getSuggestionId()
                + ".json");
        file.write(jsonAccount);
        file.close();
    }
*/
    @Override
    public String toString() {
        //throw NullPointerException if there is any error
        return "Game suggested: " +gameName;
    }
}
