package model;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Suggestion {
    private static final ArrayList<Suggestion> suggestions = new ArrayList<>();
    private final String gameId;
    private final String suggestionId;
    private final String playerId;

    public Suggestion(Game game, String suggestionId, Player player) {
        this.gameId = game.getGameId();
        this.suggestionId = suggestionId;
        this.playerId = player.getAccountId();
    }

    public static ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }

    public static void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    public Player getPlayer() {
        //throw NullPointerException if there is any error
        return Objects.requireNonNull( Player.getPlayerById(playerId));
    }

    public Game getGame() {
        //throw NullPointerException if there is any error
        return Objects.requireNonNull(Game.getGameById(gameId));
    }

    public String getGameName() {
        //throw NullPointerException if there is any error
        return Objects.requireNonNull( Game.getGameById(gameId)).getName();
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public static Suggestion getSuggestionById(String suggestionId) {
        for (Suggestion suggestion : suggestions)
            if (suggestion.suggestionId.equals(suggestionId))
                return suggestion;
        return null;
    }

    public void delete() {
        File file = new File("database" + "\\" + "suggestions" + "\\" + this.suggestionId + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }
        this.getPlayer().removeSuggestion(this);
        Suggestion.getSuggestions().remove(this);
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

    @Override
    public String toString() {
        //throw NullPointerException if there is any error
        return "Game suggested: " +Objects.requireNonNull( Game.getGameById(gameId)).getName();
    }
}
