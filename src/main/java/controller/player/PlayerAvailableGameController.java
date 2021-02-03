package controller.player;

import main.ClientInfo;
import model.AvailableGame;
import model.Player;
import view.player.PlayerAvailableGameView;

import java.util.ArrayList;

public class PlayerAvailableGameController {
    private AvailableGame availableGame;
    public PlayerAvailableGameController(ClientInfo clientInfo)
    {
        availableGame = AvailableGame.getAvailableGameById(clientInfo.getAvailableGameId());
        if(availableGame == null)
            System.err.println("Available Game passed to PlayerAvailableGameController is null");
    }
    public ArrayList<Player> getJoinedPlayers() {
        return availableGame.getJoinedPlayers();
    }


}
