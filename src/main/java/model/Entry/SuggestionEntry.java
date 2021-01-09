package model.Entry;

import model.Suggestion;

public class SuggestionEntry {
    private String gameName;
    private String suggestionId;
    private String playerName;
    public SuggestionEntry(Suggestion suggestion)
    {
        gameName= suggestion.getGameName();
        suggestionId = suggestion.getSuggestionId();
        playerName = suggestion.getPlayer().getUsername();
    }

    public String getGameName() {
        return gameName;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public String getPlayerName() {
        return playerName;
    }
}
