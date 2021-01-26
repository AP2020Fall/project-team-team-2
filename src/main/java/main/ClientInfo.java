package main;

import model.Account;
import model.Event;
import model.Game;
import model.Player;

public class ClientInfo {
    private Account loggedIn;
    private Game game;
    private Event event;
    private Player player;

    public ClientInfo() {
    }

    public Account getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Account loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
