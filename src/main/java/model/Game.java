package model;

import com.google.gson.GsonBuilder;
//import com.sun.org.apache.xpath.internal.objects.XString;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final ArrayList<Game> games = new ArrayList<>();
    private String name;
    private final String gameId;
    private final ArrayList<PlayLog> playLogs;
    private String details;
    private final Scoreboard scoreboard;
    private String avatar;

    public Image getImage() {
        File file = new File("database\\games\\images\\" + gameId + ".jpg");
        return new Image(file.toURI().toString());
        //return new Image(avatar);
    }

    public void setImage(Image image) {
        this.avatar = saveImageToFile(image,gameId);
    }

    public String getImageURL() {
        return avatar;
    }
    public Game(String name, String gameId, String details, Image gameImage) {
        this.name = name;
        this.gameId = gameId;
        this.details = details;
        this.playLogs = new ArrayList<>();
        avatar = saveImageToFile(gameImage,gameId);
        scoreboard = new Scoreboard();
    }

    public String getName() {
        return name;
    }

    public String getGameId() {
        return gameId;
    }

    public ArrayList<PlayLog> getPlayLogs() {
        return playLogs;
    }

    public void addPlayLog(PlayLog playLog) {
        playLogs.add(playLog);
        scoreboard.updateScoreboard(playLog);
    }

    public String getDetails() {
        return details;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setName(String gameName) {
        this.name = gameName;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public static ArrayList<Game> getGames() {
        return games;
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    public static Game getGameByGameName(String gameName) {
        for (Game game : games)
            if (game.name.equals(gameName))
                return game;
        return null;
    }

    public static Game getGameById(String gameId) {
        for (Game game : games)
            if (game.gameId.equals(gameId))
                return game;
        return null;
    }


    public void delete() {
        File file = new File("database" + "\\" + "games" + "\\game\\" + this.getGameId() + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }

        File imageFile = new File("database\\games\\images\\" + gameId + ".jpg");
        try {
            if (imageFile.exists())
                imageFile.delete();
        } catch (Exception ignored) {
        }
        playLogs.clear();
        for (Player player : Player.getAllPlayers()) {
            //Suggestion suggestion = player.getSuggestionByGameName(this.name);
            if (player.getSuggestionByGameName(this.name) != null) {
                player.getSuggestionByGameName(this.name).delete();
            }
            //player.removeSuggestion(this);
            player.removeGameLog(this);
            player.removeFavouriteGame(this);
        }
        //Suggestion.getSuggestions().removeIf(suggestion -> suggestion.getGameName().equals(this.name));
        removeEvents();
        Game.getGames().remove(this);
    }

    private void removeEvents() {
        ArrayList<Event> eventsMustDelete = new ArrayList<>();
        for (Event event : Event.getEvents()) {
            if (event.getGameName().equals(this.name)) {
                eventsMustDelete.add(event);
            }
        }
        for (Event event : eventsMustDelete) {
            event.delete();
        }
    }


    public static void open() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "games\\game");
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
        Game game = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Game.class);
        openFileToImage(game);
        return game;
    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) json.append(reader.nextLine());
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
        FileWriter file = new FileWriter("database" + "\\" + "games" + "\\game\\" + game.getGameId() + ".json");
        file.write(jsonAccount);
        file.close();
        System.out.println("saving ended " + game.getGameId());
        saveImageToFile(game.getImage(), game.getGameId());
        System.out.println("saving image ended " + game.getGameId());
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

    public static void openFileToImage(Game game) {
        File file = new File("database\\games\\images\\" + game.getGameId() + ".jpg");
        game.setImage(new Image(file.toURI().toString()));
    }

    @Override
    public String toString() {
        return "Game: " + name + '\n'
                + details;
    }


}
