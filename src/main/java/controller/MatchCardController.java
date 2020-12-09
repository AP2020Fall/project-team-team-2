package controller;

import model.Player;

public class MatchCardController {

    private Player player;
    private int addedSoldiers;

    public void incPlayerSoldier(Player player, int soldierNumber) {
        player.addNewSoldiers(soldierNumber);
    }

}
