package model.Entry;

import javafx.scene.image.ImageView;
import model.Game;
import model.GameLogSummary;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GameLogSummaryEntry {
    private final int frequency;
    private final int wins;
    private final int score;
    private final LocalDate lastPlay;
    private final String gameName;
    private final ImageView avatar;

    public GameLogSummaryEntry(GameLogSummary gameLog)
    {
        frequency = gameLog.getFrequency();
        wins = gameLog.getWins();
        score = gameLog.getScore();
        lastPlay = gameLog.getLastPlay().toLocalDate();
        gameName = gameLog.getGameName();
        avatar = new ImageView(Game.getGameByGameName(gameLog.getGameName()).getImage());
        avatar.setFitHeight(48);
        avatar.setFitWidth(48);
    }
    public int getFrequency() {
        return frequency;
    }

    public int getWins() {
        return wins;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getLastPlay() {
        return lastPlay;
    }

    public String getGameName() {
        return gameName;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
