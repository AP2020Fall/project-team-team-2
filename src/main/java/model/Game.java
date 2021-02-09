package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.sun.org.apache.xpath.internal.objects.XString;
import com.google.gson.reflect.TypeToken;
import controller.ServerMasterController.SQLConnector;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.jws.Oneway;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.Map;

public class Game {
    private String name;
    private final String gameId;
    private final ArrayList<String> playLogs;
    private String details;
    private final Scoreboard scoreboard;
    private String avatar;


    public Game(String name, String gameId, String details, String gameImage) {
        this.name = name;
        this.gameId = gameId;
        this.details = details;
        this.playLogs = new ArrayList<>();
        setImage(gameImage);
        scoreboard = new Scoreboard();
    }

    public Game(Map<String, Object> game) {
        this.name = (String) game.get("name");
        this.gameId = (String) game.get("game_id");
        this.details = (String) game.get("details");
        this.playLogs = new Gson().fromJson((String) game.get("play_log_id"), new TypeToken<ArrayList<String>>() {
        }.getType());
        setImage((String) game.get("avatar_address"));
        this.scoreboard = new Gson().fromJson((String) game.get("scoreboard"), new TypeToken<Scoreboard>() {
        }.getType());
    }

    public String getName() {
        return name;
    }

    public String getGameId() {
        return gameId;
    }

    public Image getImage() {
        return new Image(avatar);
    }

    public String getImageURL() {
        return avatar;
    }

    public String getDetails() {
        return details;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setName(String gameName) {
        this.name = gameName;
        editField("name", name);
    }

    public void setDetails(String details) {
        this.details = details;
        editField("details", details);
    }

    public void setImage(String url) {
        this.avatar = saveImageToFile(new Image(url), this.gameId);
        editField("avatar_address", avatar);
    }

    public void editField(String field, Object value) {
        java.util.Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("game_id", this.gameId);
        Map<String, Object> newValueMap = new HashMap<>();
        newValueMap.put(field, value);
        SQLConnector.updateTable(conditionMap, newValueMap, "game");
    }

    public void addPlayLog(PlayLog playLog) {
        playLogs.add(playLog.getPlayLogId());
        scoreboard.updateScoreboard(playLog);
        editField("play_log_id", new Gson().toJson(playLogs));
        editField("scoreboard", new Gson().toJson(scoreboard));
    }

    public static ArrayList<Game> getGames() {
        ArrayList<Game> result = new ArrayList<>();
        List<Map<String, Object>> gameList = SQLConnector.getWholeTable("game");
        if (gameList == null || gameList.isEmpty()) {
            System.out.println("[MODEL]: no Game could be found");
            return result;
        }
        for (Map<String, Object> game : gameList) {
            result.add(new Game(game));
        }
        return result;
    }

    public static List<Map<String, Object>> SQLGameSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "game");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: Game with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount;
    }


    public static void addGame(Game game) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", game.getName());
        resultMap.put("game_id", game.getGameId());
        resultMap.put("details", game.getDetails());
        resultMap.put("avatar_address", game.getImageURL());
        resultMap.put("play_log_id", new Gson().toJson(game.playLogs));
        resultMap.put("scoreboard", new Gson().toJson(game.getScoreboard()));

        SQLConnector.insertInDatabase(resultMap, "game");
    }

    public static Game getGameByGameName(String gameName) {
        List<Map<String, Object>> game = SQLGameSearch("name", gameName);
        if (game == null || game.isEmpty()) {
            return null;
        }
        return new Game(game.get(0));
    }

    public static Game getGameById(String gameId) {
        List<Map<String, Object>> game = SQLGameSearch("game_id", gameId);
        if (game == null || game.isEmpty()) {
            return null;
        }
        return new Game(game.get(0));
    }


    public void delete() {

        Map<String, Object> game = new HashMap<>();
        game.put("game_id", gameId);
        if (SQLConnector.deleteFromTable(game, "game")) {
            File imageFile = new File("database\\games\\images\\" + gameId + ".jpg");
            try {
                if (imageFile.exists())
                    if (imageFile.delete()) {
                        System.out.println("[MODEL]: Game " + name + "'s image deleted successfully.");
                    } else {
                        System.out.println("[MODEL]: Game " + name + "'s image deleted unsuccessfully.");

                    }
            } catch (Exception ignored) {
            }
            playLogs.clear();
            Suggestion.deleteGameSuggestion(name);
            removeEvents();
        } else {
            System.out.println("[MODEL]: Game with game ID = " + gameId + " couldn't be deleted");
        }
    }

    private void removeEvents() {
        List<Map<String, Object>> eventsMustDelete =
                Event.SQLEventSearch("game_name", this.name);
        for (Map<String, Object> event : eventsMustDelete) {
            Event.delete(event.get("event_id"));
        }
    }

    public static String saveImageToFile(Image image, String gameId) {

        File folder = new File("database\\games\\images");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File outputFile = new File("database\\games\\images\\" + gameId + ".jpg");

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
            return outputFile.toURI().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return "Game: " + name + '\n'
                + details;
    }
}
