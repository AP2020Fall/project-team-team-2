package model.Entry;

import model.AvailableGame;

public class AvailableGameEntry {
    private final String gameName;
    public AvailableGameEntry(AvailableGame availableGame)
    {
        gameName = availableGame.getGame().getName();
    }

    public String getGameName() {
        return gameName;
    }
}
