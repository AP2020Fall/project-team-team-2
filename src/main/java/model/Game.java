package model;

import controller.RiskGameController;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    private static final ArrayList<Game> games = new ArrayList<>();
    private String name;
    private final String gameId;
    private final ArrayList<PlayLog> playLogs;
    private String details;
    private final Scoreboard scoreboard;
    public Game(String name, String gameId,String details) {
        this.name = name;
        this.gameId = gameId;
        this.details = details;
        this.playLogs = new ArrayList<>();
        scoreboard = new Scoreboard();
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PlayLog> getPlayLogs() {
        return playLogs;
    }

    public String getDetails() {
        return details;
    }

    public Scoreboard getScoreboard()
    {
        return scoreboard;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setName(String gameName)
    {
        this.name = gameName;
    }

    public static Game getGameByGameName(String gameName)
    {
        for(Game game:games)
            if(game.name.equals(gameName))
                return game;
            return null;
    }

    public static void addGame(Game game)
    {
        games.add(game);
    }

    @Override
    public String toString() {
        return "Game: " + name + '\n'
                + details + '\n';
    }
}
