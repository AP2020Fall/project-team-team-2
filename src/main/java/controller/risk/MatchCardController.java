package controller.risk;

import model.Gamer;

public class MatchCardController {

    public MatchCardController(){

    }

    public void incPlayerSoldier(Gamer player, int soldierNumber) {
        player.addDraftSoldier(soldierNumber);
    }
}
