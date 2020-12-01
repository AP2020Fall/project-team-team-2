package model;

import controller.RiskGameController;

public class Game {
    public Game(String name, String gameId) {
        this.name = name;
        this.gameId = gameId;
    }

    public void goToGame() {
        RiskGameController game = new RiskGameController();
    }

    private String name;
    private String gameId;
}
