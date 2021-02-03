package controller.player;

import main.ClientInfo;
import model.AvailableGame;
import model.Player;
import view.player.PlayerAvailableGameView;

import java.util.ArrayList;

public class PlayerAvailableGameController {
    private final AvailableGame availableGame;
    private final Player player;
    public PlayerAvailableGameController(ClientInfo clientInfo) {
        player = Player.getPlayerByUsername(clientInfo.getLoggedInUsername());
        if(player == null)
            System.err.println("Player passed to PlayerAvailableGameController is null");
        availableGame = AvailableGame.getAvailableGameById(clientInfo.getAvailableGameId());
        if (availableGame == null)
            System.err.println("Available Game passed to PlayerAvailableGameController is null");
    }

    public ArrayList<Player> getJoinedPlayers() {
        return availableGame.getJoinedPlayers();
    }

    public void playerReady() {
         availableGame.playerReady(player);
    }

    public void playerQuit() {
        availableGame.playerQuit(player);
    }
    public Boolean allPlayerReady()
    {
        return availableGame.allReady();
    }

    public ArrayList<Player> getReadyPlayers()
    {
        return availableGame.getReadyPlayers();
    }
}
