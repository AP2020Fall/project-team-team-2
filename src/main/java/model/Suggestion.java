package model;

import java.util.ArrayList;

public class Suggestion {
    private static  ArrayList<Suggestion> suggestions = new ArrayList<>();
    private final String gameName;
    private final String id;
    private final Player player;
    public Suggestion(String gameName,String id,Player player)
    {
        this.gameName= gameName;
        this.id = id;
        this.player = player;
    }

    public static ArrayList<Suggestion> getSuggestions() {
        return suggestions;
    }

    public static void addSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    public Player getPlayer() {
        return player;
    }

    public String getGameName() {
        return gameName;
    }

    public String getId() {
        return id;
    }
    public static Suggestion getSuggestionById(String id)
    {
        for(Suggestion suggestion: suggestions)
            if(suggestion.id.equals(id))
                return suggestion;
        return null;
    }

    public static boolean searchSuggestionById(String id)
    {
        return getSuggestionById(id) != null;
    }

    @Override
    public String toString() {
        return "Game suggested: " + gameName;

    }
}
