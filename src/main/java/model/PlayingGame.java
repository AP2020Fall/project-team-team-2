package model;

import org.javatuples.Pair;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayingGame {
    private final ArrayList<Gamer> players;
    private final Map<String, DataOutputStream> clients;
    private final String playingGameId;

    public PlayingGame(AvailableGame availableGame)
    {
        players = new ArrayList<>();
        for(Player player: availableGame.getJoinedPlayers())
        {
            players.add(new Gamer(player.getUsername(),player.getImageURL()));
        }
        clients = new HashMap<>();
        for (Map.Entry<Player,DataOutputStream> client: availableGame.getClientsSocket().entrySet())
        {
            clients.put(client.getKey().getUsername(),client.getValue());
        }

        playingGameId = availableGame.getAvailableGameId();
    }

    public DataOutputStream getPlayerSocket(String username)
    {
        return clients.get(username);
    }

    public ArrayList<Gamer> getPlayers() {
        return players;
    }

    public String getPlayingGameId() {
        return playingGameId;
    }
}
