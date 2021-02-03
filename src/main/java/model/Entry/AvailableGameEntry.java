package model.Entry;

import model.AvailableGame;

public class AvailableGameEntry {
    private final String gameName;
    private final String availableGameId;
    public AvailableGameEntry(AvailableGame availableGame)
    {
        gameName = availableGame.getGame().getName();
        availableGameId = availableGame.getAvailableGameId();
    }

    public String getGameName() {
        return gameName;
    }

    public String getAvailableGameId() {
        return availableGameId;
    }
}
