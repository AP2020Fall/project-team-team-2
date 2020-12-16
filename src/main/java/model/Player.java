package model;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class Player extends Account {
    private LocalDate registerDay;
    private double money;
    private int score;
    private int loses;
    private int wins;
    private int draws;
    private ArrayList<GameLog> gameLogs;
    private ArrayList<String> friends;
    private ArrayList<String> receivedFriendRequests;
    private ArrayList<String> sentFriendRequests;
    private ArrayList<Message> messages;
    private ArrayList<String> favouriteGames;
    private ArrayList<String> suggestions;

    private ArrayList<Card> cards;
    private int newSoldiers;
    private int playerNumber;
    private int draftSoldiers = 0;

    public Player(String firstName, String lastName, String username, String accountId,
                  String password, String email, String phoneNumber, double money) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
        registerDay = LocalDate.now();
        this.money = money;
        this.score = 0;
        //this.loses = this.wins = this.draws = 0;
        gameLogs = new ArrayList<>();
        friends = new ArrayList<>();
        receivedFriendRequests = new ArrayList<>();
        sentFriendRequests = new ArrayList<>();
        cards = new ArrayList<>();
        messages = new ArrayList<>();
        favouriteGames = new ArrayList<>();
        suggestions = new ArrayList<>();
    }

    public Player(String botName, String username) {
        super(botName, username);
    }

    public int getDayOfRegister() {
        return (int) ChronoUnit.DAYS.between(registerDay, LocalDate.now());
    }

    public double getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public ArrayList<GameLog> getGameLogs() {
        return gameLogs;
    }

    public GameLog getLastGamePlayed() {
        if (gameLogs.isEmpty())
            return null;
        gameLogs.sort((a, b) -> b.getLastPlay().compareTo(a.getLastPlay()));
        return gameLogs.get(0);
    }

    public GameLog getGameHistory(String gameName) {
        for (GameLog gameLog : gameLogs)
            if (gameLog.getGameName().equals(gameName))
                return gameLog;
        return null;
    }

    public void addGameLog(GameLog gameLog)
    {
        gameLogs.add(gameLog);
    }

    public void removeGameLog(Game game)
    {
        gameLogs.removeIf(o->o.getGameName().equals(game.getName()));
    }


    public ArrayList<Player> getFriends() {
        ArrayList<Player> result = new ArrayList<>();
        for (String friendId : friends)
            result.add(Player.getPlayerByUsername(friendId));
        return result;
    }

    public Player getFriendByUsername(String username) {
        //if username is friend of player, return the Player object of username else null.
        for (String friend : friends)
            if (friend.equals(username))
                return Player.getPlayerByUsername(friend);
        return null;
    }

    public void addFriend(Player friend) {
        friends.add(friend.getUsername());
    }

    public void removeFriend(Player friend) {
        friends.remove(friend.getUsername());
    }



    public ArrayList<FriendRequest> getReceivedFriendRequests() {
        //throws NullPointerException if there is any error
        ArrayList<FriendRequest> result = new ArrayList<>();
        for (String friendRequests : receivedFriendRequests)
            result.add(Objects.requireNonNull( FriendRequest.getFriendRequestById(friendRequests)));
        return result;
    }

    public ArrayList<FriendRequest> getSentFriendRequests() {
        //throws NullPointerException if there is any error
        ArrayList<FriendRequest> result = new ArrayList<>();
        for (String friendRequests : sentFriendRequests)
            result.add(Objects.requireNonNull( FriendRequest.getFriendRequestById(friendRequests)));
        return result;
    }

    public void addReceivedFriendRequest(FriendRequest friendRequest) {
        receivedFriendRequests.add(friendRequest.getFriendRequestId());
    }

    public void addSentFriendRequest(FriendRequest friendRequest) {
        sentFriendRequests.add(friendRequest.getFriendRequestId());
    }

    public FriendRequest getFriendRequestByUsername(String username) {
        //if username sent a FriendRequest to player, return the FriendRequest else null.
        //throws NullPointerException if there is an error
        for (String friendRequestId : receivedFriendRequests) {
            FriendRequest friendRequest = Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequestId));
            if (friendRequest.getPlayer().getUsername().equals(username))
                return friendRequest;
        }
        return null;
    }

    public void removeReceivedFriendRequest(FriendRequest friendRequest) {
        receivedFriendRequests.remove(friendRequest.getFriendRequestId());
    }

    public void removeSentFriendRequest(FriendRequest friendRequest) {
        sentFriendRequests.remove(friendRequest.getFriendRequestId());
    }


    public ArrayList<Suggestion> getSuggestions() {
        //throws NullPointerException if there is any error
        ArrayList<Suggestion> result= new ArrayList<>();
        for(String suggestion: suggestions)
        {
            result.add(Objects.requireNonNull(Suggestion.getSuggestionById(suggestion)));
        }
        return result;
    }

    public void addSuggestion(Suggestion suggestion) {
        this.suggestions.add(suggestion.getSuggestionId());
    }

    public Suggestion getSuggestionByGameName(String gameName) {
        for (String suggestionId : suggestions) {
            Suggestion suggestion = Objects.requireNonNull(Suggestion.getSuggestionById(suggestionId));
            if (suggestion.getGameName().equals(gameName))
                return suggestion;
        }
        return null;
    }

    public boolean suggestionExists(String gameName) {
        return getSuggestionByGameName(gameName) != null;
    }

    public void removeSuggestion(Suggestion suggestion) {
        suggestions.remove(suggestion.getSuggestionId());
    }

    public void removeSuggestion(Game game)
    {
        Iterator<String> iterator = suggestions.iterator();
        while(iterator.hasNext())
        {
            Suggestion suggestion = Objects.requireNonNull(Suggestion.getSuggestionById(iterator.next()));
            if(suggestion.getGameName().equals(game.getName()))
                iterator.remove();
        }
    }



    public ArrayList<Message> getMessages() {
        return messages;
    }



    public ArrayList<Game> getFavouriteGames() {
        ArrayList<Game> result = new ArrayList<>();
        for (String gameId : favouriteGames)
        {
            result.add(Objects.requireNonNull(Game.getGameById(gameId)));
        }
        return result;
    }

    public void addFavouriteGame(Game game)
    {
        favouriteGames.add(game.getGameId());
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



    public int getNumberOfWins() {
        int wins = 0;
        for (GameLog gameLog : gameLogs)
            wins += gameLog.getWins();
        return wins;
    }


    public void delete() {
        File file = new File("database" + "\\" + "accounts" + "\\" + "players" + "\\" +
                this.getUsername() + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }
        gameLogs.clear();
        for(String username: friends)
        {
            Player friend = Objects.requireNonNull(Player.getPlayerByUsername(username));
            friend.removeFriend(this);
        }
        friends.clear();

        for(String friendRequestId: receivedFriendRequests)
        {
            FriendRequest friendRequest = Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequestId));
            friendRequest.getPlayer().removeSentFriendRequest(friendRequest);
            friendRequest.delete();
        }
        receivedFriendRequests.clear();

        for(String friendRequestId: sentFriendRequests)
        {
            FriendRequest friendRequest = Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequestId));
            friendRequest.getFriend().removeReceivedFriendRequest(friendRequest);
            friendRequest.delete();
        }
        sentFriendRequests.clear();

        messages.clear();

        favouriteGames.clear();
        Iterator<String> iterator = suggestions.iterator();
        while(iterator.hasNext()) {
            String res = iterator.next();
            Suggestion suggestion = Objects.requireNonNull(Suggestion.getSuggestionById(res));
            Suggestion.getSuggestions().remove(suggestion);
            iterator.remove();
        }
        suggestions.clear();
        Account.getAllAccounts().remove(this);

    }

    @Override
    public String toString() {
        return super.toString()
                + "money: " + getMoney() + "$\n"
                + "registered: " + getDayOfRegister() + " days ago\n";
    }





    public int getDraftSoldiers() {
        return draftSoldiers;
    }

    public void addDraftSoldier(int draftSoldiers) {
        this.draftSoldiers += draftSoldiers;
    }

    public int getNewSoldiers() {
        return newSoldiers;
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

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }


    public void setDraws() {
        this.draws++;
    }

    public void setWins() {
        this.wins++;
    }

    public void setLoses() {
        this.loses++;
    }
}