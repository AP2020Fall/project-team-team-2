package controller;

import model.Game;

import java.util.Objects;

public class GameMenuController {
    private Game game;

    public GameMenuController(Game game) {
        this.game = Objects.requireNonNull(game,"Game passed to GameMenuController is null.");
    }

    public void showScoreBoard() {
    }

    public void showDetails() {
    }

    public void showLog() {
    }

    public void showWinsCount() {
    }

    public void showPlayedCount() {
    }

    public void addToFavorites() {
    }

    public void runGame() {
    }

    public void showPoints() {
    }
}
