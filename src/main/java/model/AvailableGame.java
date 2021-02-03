package model;

import model.Entry.AvailableGameEntry;

import java.util.ArrayList;
import java.util.Map;

public class AvailableGame {
    private static ArrayList<AvailableGame> availableGames = new ArrayList<>();
    private final Map<String,Object> primitiveSetting;
    private final ArrayList<Player> joinedPlayers;
    private final String availableGameId;
    private final Game game;
    private final Event event;

    public AvailableGame(Map<String, Object> primitiveSetting, ArrayList<Player> joinedPlayers, Game game, Event event,String id) {
        this.primitiveSetting = primitiveSetting;
        this.joinedPlayers = joinedPlayers;
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
        joinedPlayers.add(loggedIn);
        return true;
    }
}
