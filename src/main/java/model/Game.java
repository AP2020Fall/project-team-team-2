package model;

import com.google.gson.GsonBuilder;
import controller.RiskGameController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    private static final ArrayList<Game> games = new ArrayList<>();
    private String name;
    private final String gameId;
    private final ArrayList<PlayLog> playLogs;
    private String details;
    private final Scoreboard scoreboard;

    public Game(String name, String gameId, String details) {
        this.name = name;
        this.gameId = gameId;
        this.details = details;
        this.playLogs = new ArrayList<>();
        scoreboard = new Scoreboard();
    }

    public static Game getGameById(String gameId) {
        for(Game game:games)
            if(game.gameId.equals(gameId))
                return game;
            return null;
    }

    public String getGameId() {
        return gameId;
    }

    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void open() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "games");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                games.add(openGame(file));
            }
        }
    }

    private static Game openGame(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Game.class);
    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return json;
    }

    public static void save() throws IOException {
        for (Game game : games) {
            save(game);
        }
    }

    private static void save(Game game) throws IOException {
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(game);
        FileWriter file = new FileWriter("database" + "\\" + "games" + "\\" + game.getGameId() + ".json");
        file.write(jsonAccount);
        file.close();
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

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setName(String gameName) {
        this.name = gameName;
    }

    public static Game getGameByGameName(String gameName) {
        for (Game game : games)
            if (game.name.equals(gameName))
                return game;
        return null;
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    @Override
    public String toString() {
        return "Game: " + name + '\n'
                + details ;
    }
}
