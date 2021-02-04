package model;

import main.Server;

import java.util.ArrayList;
import java.util.Map;

public class AvailableGame {
    private static final ArrayList<AvailableGame> availableGames = new ArrayList<>();
    private final Map<String,Object> primitiveSetting;
    private final Map<Player,Server.ClientHandler> joinedPlayers;
    private final ArrayList<Player> readyPlayers;
    private final String availableGameId;

    private final Game game;
    private final Event event;

    public AvailableGame(Map<String, Object> primitiveSetting, Map<Player,Server.ClientHandler> joinedPlayers, Game game, Event event,String id) {
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
        return (ArrayList<Player>) joinedPlayers.keySet();
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

    public Boolean playerJoin(Server.ClientHandler clientHandler,Player loggedIn) {
        if( Math.round((Double) primitiveSetting.get("PlayersNum")) > joinedPlayers.size()) {
            joinedPlayers.put( loggedIn,clientHandler);
            return true;
        }
        return false;
    }

    public void playerQuit(Player player) {
        joinedPlayers.removeIf(p -> p.getUsername().equals(player.getUsername()));
        readyPlayers.removeIf(p -> p.getUsername().equals(player.getUsername()));
        if(joinedPlayers.isEmpty())
            availableGames.remove(this);
    }

    public void playerReady(Player player) {
        if(isPlayerInIt(joinedPlayers,player.getUsername()) && !isPlayerInIt(readyPlayers,player.getUsername()))
            readyPlayers.add(player);

    }
    public boolean allReady() {
        return  Math.round((Double) primitiveSetting.get("PlayersNum")) == readyPlayers.size();
    }

    public Boolean isPlayerReady(String username)
    {
        return isPlayerInIt(readyPlayers,username);
    }

    public ArrayList<Player> getReadyPlayers() {
        return readyPlayers;
    }
    public Boolean isPlayerInIt(ArrayList<Player> list, String player)
    {
        for(Player player1 : list)
            if(player1.getUsername().equals(player))
                return true;
            return false;
    }

    public String startGame() {
        //todo do something if needed
        return availableGameId;
    }
}
