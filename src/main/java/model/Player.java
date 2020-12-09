package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Player extends Account {
    public Player(String firstName, String lastName, String username, String accountId,
                  String password, String email, String phoneNumber, double money) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
        registerDay = LocalDate.now();
        this.money = money;
        this.score = 0;
        gameLogs = new ArrayList<>();
        friends = new ArrayList<>();
        friendRequests = new ArrayList<>();
        cards = new ArrayList<>();
        messages = new ArrayList<>();
        favouriteGames = new ArrayList<>();
    }

    public Player(String botName, String username) {
        super(botName, username);
    }

    //private static final ArrayList<Player> allPlayers = new ArrayList<>();
    private LocalDate registerDay;
    private double money;
    private int score;
    private int newSoldiers;
    private ArrayList<GameLog> gameLogs;
    private ArrayList<Player> friends;
    private ArrayList<FriendRequest> friendRequests;
    private ArrayList<Message> messages;
    private ArrayList<Card> cards;
    private ArrayList<Game> favouriteGames;
    // private final ArrayList<String> suggestions;
    private Suggestion suggestion;
    private int playerNumber;

    /*public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }*/

    public int getDayOfRegister() {
        return (int) ChronoUnit.DAYS.between( registerDay,LocalDate.now());
    }

    public double getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public int getNewSoldiers() { return newSoldiers; }

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

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Game> getFavouriteGames() {
        return favouriteGames;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addNewSoldiers(int soldiers) { newSoldiers = newSoldiers + soldiers; }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public boolean suggestionExists() {
        return suggestion != null;
    }

    public void removeSuggestion() {
        suggestion = null;
    }

    public Player getFriendByUsername(String username) {
        //if username is friend of player, return the Player object of username else null.
        for (Player friend : friends)
            if (friend.getUsername().equals(username))
                return friend;
        return null;
    }

    public FriendRequest getFriendRequestByUsername(String username) {
        //if username sent a FriendRequest to player, return the FriendRequest else null.
        for (FriendRequest friendRequest : friendRequests)
            if (friendRequest.getPlayer().getUsername().equals(username))
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

    public int getNumberOfWins() {
        int wins = 0;
        for (GameLog gameLog : gameLogs)
            wins += gameLog.getWins();
        return wins;
    }

    public GameLog getGameHistory(String gameName) {
        for (GameLog gameLog : gameLogs)
            if (gameLog.getGame().getName().equals(gameName))
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

    public GameLog getLastGamePlayed()
    {
        if(gameLogs.isEmpty())
            return null;
        gameLogs.sort((a, b) -> b.getLastPlay().compareTo(a.getLastPlay()));
        return gameLogs.get(0);
    }
    @Override
    public String toString() {
        return super.toString()
                + "money: " + getMoney() + "$\n"
                + "registered: " + getDayOfRegister() + " days ago\n";
    }
}