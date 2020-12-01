package controller;

import model.Player;

public class MatchCardController {

    private Player player;
    private int tempScore;

    public void incPlayerScore(Player player, int score) {
        tempScore = player.getScore();
        tempScore = tempScore + score;
        player.setScore(tempScore);
    }

}
