package controller.risk;

import model.Player;

public class MatchCardController {

    private Player player;
    private int addedSoldiers;

    public MatchCardController(Player player){
        this.player = player;
        addedSoldiers = 0;
    }

    public void incPlayerSoldier(Player player, int soldierNumber) {
        player.addDraftSoldier(soldierNumber);
    }
}
