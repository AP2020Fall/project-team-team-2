package model.Entry;

import javafx.scene.image.ImageView;
import model.Game;
import model.GameLog;
import model.GameLogStates;

import java.time.LocalDateTime;

public class GameLogEntry {
    private  String gameName;
    private  String enemies;
    private  GameLogStates result;
    private  LocalDateTime timeFinished;
    private ImageView avatar;

    public GameLogEntry(GameLog gameLog)
    {
        enemies = gameLog.getEnemyUsernames().toString();
        result = gameLog.getResult();
        timeFinished = gameLog.getTimeFinished();
        gameName = gameLog.getGameName();
        avatar = new ImageView(Game.getGameByGameName(gameLog.getGameName()).getImage());
        avatar.setFitHeight(48);
        avatar.setFitWidth(48);
    }

    public String getGameName() {
        return gameName;
    }

    public String getEnemies() {
        return enemies;
    }

    public GameLogStates getResult() {
        return result;
    }

    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
