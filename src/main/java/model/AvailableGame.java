package model;

import controller.risk.RiskGameController;
import main.Server;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AvailableGame {
    private static final ArrayList<AvailableGame> availableGames = new ArrayList<>();
    private final Map<String, Object> primitiveSetting;
    private transient final Map<Player, DataOutputStream> joinedPlayers;
    private final ArrayList<Player> readyPlayers;
    private final String availableGameId;

    private final Game game;
    private final Event event;

    public AvailableGame(Map<String, Object> primitiveSetting, Map<Player, DataOutputStream> joinedPlayers, Game game, Event event, String id) {
        this.primitiveSetting = primitiveSetting;
        this.joinedPlayers = joinedPlayers;
        this.readyPlayers = new ArrayList<>();
        this.game = game;
        this.event = event;
        availableGameId = id;
    }

    public static ArrayList<AvailableGame> getAvailableGames() {
        return availableGames;
    }

    public static AvailableGame getAvailableGameById(String availableGameId) {
        for (AvailableGame availableGame : availableGames)
            if (availableGame.getAvailableGameId().equals(availableGameId))
                return availableGame;
        return null;
    }

    public Map<String, Object> getPrimitiveSetting() {
        return primitiveSetting;
    }

    public ArrayList<Player> getJoinedPlayers() {
        ArrayList<Player> result = new ArrayList<>();
        for(Map.Entry<Player,DataOutputStream> entry: joinedPlayers.entrySet())
            result.add(entry.getKey());
        return result;
    }

    public Map<Player,DataOutputStream> getClientsSocket()
    {
        return joinedPlayers;
    }

    public Game getGame() {
        return game;
    }

    public Event getEvent() {
        return event;
    }

    public void createGame() {
        availableGames.add(this);
    }

    public String getAvailableGameId() {
        return availableGameId;
    }

    public Boolean playerJoin(DataOutputStream dataOutputStream, Player loggedIn) {
        if (Math.round((Double) primitiveSetting.get("PlayersNum")) > joinedPlayers.size()
                && !isPlayerInIt(joinedPlayers.keySet(), loggedIn.getUsername())) {
            joinedPlayers.put(loggedIn, dataOutputStream);
            for(Map.Entry<Player,DataOutputStream> client: joinedPlayers.entrySet()) {
                if(!client.getKey().getUsername().equals(loggedIn.getUsername())) {
                    Server.refresh(client.getValue());
                    Server.notify(client.getValue(), "Player joined", loggedIn.getUsername() + " joined.");
                }
            }
            return true;
        }
        return false;
    }

    public void playerQuit(Player player) {
        //joinedPlayers.removeIf(p -> p.getUsername().equals(player.getUsername()));

        joinedPlayers.entrySet().removeIf(joined -> joined.getKey().getUsername().equals(player.getUsername()));
        readyPlayers.removeIf(p -> p.getUsername().equals(player.getUsername()));
        for(Map.Entry<Player,DataOutputStream> client: joinedPlayers.entrySet()) {
            if(!client.getKey().getUsername().equals(player.getUsername())) {
                Server.refresh(client.getValue());
                Server.notify(client.getValue(), "Player joined", player.getUsername() + " joined.");
            }}

        if (joinedPlayers.isEmpty())
            availableGames.remove(this);
    }

    public void playerReady(Player player) {
        if (isPlayerInIt(joinedPlayers.keySet(), player.getUsername()) && !isPlayerInIt(readyPlayers, player.getUsername())) {
            readyPlayers.add(player);
            for(Map.Entry<Player,DataOutputStream> client: joinedPlayers.entrySet()) {
                if(!client.getKey().getUsername().equals(player.getUsername())) {
                    Server.refresh(client.getValue());
                }
            }
        }

    }

    public boolean allReady() {
        return Math.round((Double) primitiveSetting.get("PlayersNum")) == readyPlayers.size();
    }

    public Boolean isPlayerReady(String username) {
        return isPlayerInIt(readyPlayers, username);
    }

    public ArrayList<Player> getReadyPlayers() {
        return readyPlayers;
    }

    public Boolean isPlayerInIt(ArrayList<Player> list, String player) {
        for (Player player1 : list)
            if (player1.getUsername().equals(player))
                return true;
        return false;
    }

    public Boolean isPlayerInIt(Set<Player> playerSet, String username) {
        for (Player player : playerSet)
            if (player.getUsername().equals(username))
                return true;
        return false;
    }

    public String startGame() {
        //todo do something if needed
        new RiskGame(this, 20);
        return availableGameId;
    }
}
