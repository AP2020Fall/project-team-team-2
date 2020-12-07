package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Account {
    public Player(String firstName, String lastName, String username, String accountId,
                  String password, String email, String phoneNumber, int dayOfRegister, double money, int score) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
        this.dayOfRegister = dayOfRegister;
        this.money = money;
        this.score = score;
        gameLogs = new ArrayList<>();
        friends = new ArrayList<>();
        friendRequests = new ArrayList<>();
        cards = new ArrayList<>();
    }

    //private static final ArrayList<Player> allPlayers = new ArrayList<>();
    private int dayOfRegister;
    private double money;
    private int score;
    private ArrayList<GameLog> gameLogs;
    private ArrayList<Player> friends;
    private ArrayList<FriendRequest> friendRequests;
    private ArrayList<Card> cards;
    // private final ArrayList<String> suggestions;
    private Suggestion suggestion;
    private int playerNumber;

    /*public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }*/

    public int getDayOfRegister() {
        return dayOfRegister;
    }

    public double getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<GameLog> getGameLogs() {
        return gameLogs;
    }

    public ArrayList<Player> getFriends() {
        return friends;
    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setDayOfRegister(int dayOfRegister) {
        this.dayOfRegister = dayOfRegister;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public boolean suggestionExists() {
        return suggestion != null;
    }

    public void removeSuggestion() {
        suggestion = null;
    }

    public Player getFriendByUsername(String username)
    {
        //if username is friend of player, return the Player object of username else null.
        for(Player friend: friends)
            if(friend.getUsername().equals(username))
                return friend;
            return null;
    }

    public FriendRequest getFriendRequestByUsername(String username)
    {
        //if username sent a FriendRequest to player, return the FriendRequest else null.
        for (FriendRequest friendRequest: friendRequests)
            if(friendRequest.getPlayer().getUsername().equals(username))
                return friendRequest;
            return null;
    }
    public static Player getPlayerById(String id) {
        //if a player with id exists returns player else null.
        Account account = Account.getAccountById(id);
        if (account instanceof Player)
            return (Player) account;
        return null;
    }

    public static Player getPlayerByUsername(String username) {
        Account account = Account.getAccountByUsername(username);
        if (account instanceof Player)
            return (Player) account;
        return null;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        Iterator iterate = this.cards.iterator();
        while (iterate.hasNext()) {
            if (iterate.next().equals(card)) {
                iterate.remove();
                break;
            }
        }
    }
    public int getNumberOfWins()
    {
        int wins = 0;
        for(GameLog gameLog: gameLogs)
            wins += gameLog.getWins();
        return wins;
    }

    public GameLog getGameHistory(String gameName)
    {
        for(GameLog gameLog : gameLogs)
            if(gameLog.getGame().getName().equals(gameName))
                return gameLog;
        return null;
    }
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}