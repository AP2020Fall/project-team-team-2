package model;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Suggestion {
    private static ArrayList<Suggestion> suggestions = new ArrayList<>();
    private final String gameId;
    private final String id;
    private final String playerId;

    public Suggestion(Game game, String id, Player player) {
        this.gameId = game.getGameId();
        this.id = id;
        this.playerId = player.getAccountId();
    }

    public static ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }

    public static void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

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
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return json;
    }

    public static void save() throws IOException {
        for (Suggestion suggestion : suggestions) {
            save(suggestion);
        }
    }

    private static void save(Suggestion suggestion) throws IOException {
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(suggestion);
        FileWriter file = new FileWriter("database" + "\\" + "suggestions" + "\\" + suggestion.getId() + ".json");
        file.write(jsonAccount);
        file.close();
    }

    public Player getPlayer() {
        return Player.getPlayerById(playerId);
    }

    public Game getGame() {
        return Game.getGameById(gameId);
    }

    public String getGameName() {
        return Game.getGameById(gameId).getName();
    }

    public String getId() {
        return id;
    }

    public static Suggestion getSuggestionById(String id) {
        for (Suggestion suggestion : suggestions)
            if (suggestion.id.equals(id))
                return suggestion;
        return null;
    }

    public static boolean searchSuggestionById(String id) {
        return getSuggestionById(id) != null;
    }

    @Override
    public String toString() {
        return "Game suggested: " + Game.getGameById(gameId).getName();

    }
}
