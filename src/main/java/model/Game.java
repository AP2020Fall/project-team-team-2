package model;

import controller.RiskGameController;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    private final String name;
    private final String gameId;
    private final ArrayList<PlayLog> playLogs;
    private String details;
    public Game(String name, String gameId,String details) {
        this.name = name;
        this.gameId = gameId;
        this.details = details;
        this.playLogs = new ArrayList<>();
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<PlayLog> getPlayLogs() {
        return playLogs;
    }

    public String getName() {
        return name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void goToGame() {
//        RiskGameController game = new RiskGameController();
    }
}
