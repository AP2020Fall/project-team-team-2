package model;

import model.Entry.AvailableGameEntry;

import java.util.ArrayList;
import java.util.Map;

public class AvailableGame {
    private static ArrayList<AvailableGame> availableGames = new ArrayList<>();
    private final Map<String,Object> primitiveSetting;
    private final ArrayList<Player> joinedPlayers;
    private final ArrayList<Player> readyPlayers;
    private final String availableGameId;
    private final Game game;
    private final Event event;

    public AvailableGame(Map<String, Object> primitiveSetting, ArrayList<Player> joinedPlayers, Game game, Event event,String id) {
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
        for(AvailableGame availableGame :availableGames)
            if(availableGame.getAvailableGameId().equals(availableGameId))
                return availableGame;
            return null;
    }

    public Map<String, Object> getPrimitiveSetting() {
        return primitiveSetting;
    }

    public ArrayList<Player> getJoinedPlayers() {
        return joinedPlayers;
    }

    public Game getGame() {
        return game;
    }

    public Event getEvent() {
        return event;
    }

    public void createGame()
    {
        availableGames.add(this);
    }

    public String getAvailableGameId() {
        return availableGameId;
    }

    public Boolean playerJoin(Player loggedIn) {
        if( Math.round((Double) primitiveSetting.get("PlayersNum")) > joinedPlayers.size()) {
            joinedPlayers.add(loggedIn);
            return true;
        }
        return false;
    }

    public void playerQuit(Player player) {
        joinedPlayers.remove(player);
        readyPlayers.remove(player);
        if(joinedPlayers.isEmpty())
            availableGames.remove(this);
    }

    public void playerReady(Player player) {
        if(isPlayerInIt(joinedPlayers,player) && !isPlayerInIt(readyPlayers,player))
            readyPlayers.add(player);

    }
    public boolean allReady() {
        return  Math.round((Double) primitiveSetting.get("PlayersNum")) == readyPlayers.size();
    }

    public ArrayList<Player> getReadyPlayers() {
        return readyPlayers;
    }
    public Boolean isPlayerInIt(ArrayList<Player> list, Player player)
    {
        for(Player player1 : list)
            if(player.getUsername().equals(player1.getUsername()))
                return true;
            return false;
    }
}
