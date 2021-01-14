package model;

import controller.Controller;
import controller.player.PlayerMainMenuController;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Player extends Account {
    private double money;
    private int score;
    private int loses;
    private int wins;
    private int draws;
    private ArrayList<GameLogSummary> gameLogSummaries;
    private ArrayList<String> friends;
    private ArrayList<String> receivedFriendRequests;
    private ArrayList<String> sentFriendRequests;
    private ArrayList<Message> messages;
    private ArrayList<String> favouriteGames;
    private ArrayList<String> suggestions;
    private List<Player> alliance = new ArrayList<Player>();


    private ArrayList<Card> cards = new ArrayList<Card>();
    private int newSoldiers;
    private int playerNumber;
    private int draftSoldiers = 0;

    public Player(String firstName, String lastName, String username, String accountId,
                  String password, String email, String phoneNumber, double money) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
        this.money = money;
        this.score = 0;
        gameLogSummaries = new ArrayList<>();
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

    public ArrayList<GameLogSummary> getGameLogSummaries() {
        return gameLogSummaries;
    }


    public GameLogSummary getGameLogLastGamePlayed() {
        if (gameLogSummaries.isEmpty()) {
            return null;
        }
        gameLogSummaries.sort((a, b) -> b.getLastPlay().compareTo(a.getLastPlay()));
        return gameLogSummaries.get(0);
    }

    public GameLogSummary getGameHistory(String gameName) {
        for (GameLogSummary gameLogSummary : gameLogSummaries)
            if (gameLogSummary.getGameName().equals(gameName))
                return gameLogSummary;
        return null;
    }

    public static void addGameLog(ArrayList<Player> players, Game game, GameStates gameState, Player winner,
                                  int win,int draw,int lose) {
        if (winner != null) {
            GameLogSummary gameLog = winner.getGameHistory("Risk");
            if (gameLog == null) {
                gameLog = new GameLogSummary(game.getName(), Controller.generateId(0));
                winner.addGameLogSummary(gameLog);
            }
            gameLog.updateForWin(win, LocalDateTime.now(), new GameLog(winner, getEnemies(players, winner),
                    game.getName(), GameLogStates.WON, LocalDateTime.now()));


            for (Player player : players) {
                if (!player.getAccountId().equals(winner.getAccountId())) {
                    gameLog = player.getGameHistory(game.getName());
                    if (gameLog == null) {
                        gameLog = new GameLogSummary(game.getName(), Controller.generateId(0));
                        player.addGameLogSummary(gameLog);
                    }
                    gameLog.updateForLoss(lose, LocalDateTime.now(), new GameLog(player, getEnemies(players, player),
                            game.getName(), GameLogStates.LOST, LocalDateTime.now()));
                }
            }
        } else {
            for (Player player : players) {
                GameLogSummary gameLog = player.getGameHistory(game.getName());
                if (gameLog == null) {
                    gameLog = new GameLogSummary(game.getName(), Controller.generateId(0));
                    player.addGameLogSummary(gameLog);
                }
                gameLog.updateForDraw(draw, LocalDateTime.now(), new GameLog(player, getEnemies(players, player),
                        game.getName(), GameLogStates.DRAWN, LocalDateTime.now()));
            }
        }
        PlayLog playLog = new PlayLog(game.getName(), players, winner, LocalDateTime.now());
        game.addPlayLog(playLog);
    }

    private static ArrayList<Player> getEnemies(ArrayList<Player> players, Player winner) {
        ArrayList<Player> result = new ArrayList<>();
        for(Player player: players)
            if(!player.getAccountId().equals(winner.getAccountId()))
                result.add(player);
            return result;
    }

    private void addGameLogSummary(GameLogSummary gameLog) {
        gameLogSummaries.add(gameLog);
    }

    public void removeGameLog(Game game) {
        gameLogSummaries.removeIf(o -> o.getGameName().equals(game.getName()));
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
            result.add(Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequests)));
        return result;
    }

    public ArrayList<FriendRequest> getSentFriendRequests() {
        //throws NullPointerException if there is any error
        ArrayList<FriendRequest> result = new ArrayList<>();
        for (String friendRequests : sentFriendRequests)
            result.add(Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequests)));
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
        ArrayList<Suggestion> result = new ArrayList<>();
        for (String suggestion : suggestions) {
            result.add(Objects.requireNonNull(Suggestion.getSuggestionById(suggestion)));
        }
        return result;
    }

    public void addSuggestion(Suggestion suggestion) {
        this.suggestions.add(suggestion.getSuggestionId());
    }

    public Suggestion getSuggestionByGameName(String gameName) {
        for (String suggestionId : suggestions) {
            Suggestion suggestion = Suggestion.getSuggestionById(suggestionId);
            if (suggestion != null && suggestion.getGameName().equals(gameName))
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

    public void removeSuggestion(Game game) {
        Iterator<String> iterator = suggestions.iterator();
        while (iterator.hasNext()) {
            Suggestion suggestion = Objects.requireNonNull(Suggestion.getSuggestionById(iterator.next()));
            if (suggestion.getGameName().equals(game.getName()))
                iterator.remove();
        }
    }


    public ArrayList<Message> getMessages() {
        return messages;
    }


    public ArrayList<Game> getFavouriteGames() {
        ArrayList<Game> result = new ArrayList<>();
        for (String gameId : favouriteGames) {
            result.add(Objects.requireNonNull(Game.getGameById(gameId)));
        }
        return result;
    }

    public void addFavouriteGame(Game game) {
        favouriteGames.add(game.getGameId());
    }

    public void removeFavouriteGame(Game game) {
        favouriteGames.remove(game.getGameId());
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

    public List<Player> getAlliance() {
        return alliance;
    }
    public int getNumberOfWins() {
        int wins = 0;
        for (GameLogSummary gameLogSummary : gameLogSummaries)
            wins += gameLogSummary.getWins();
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

        File imageFile = new File("database" + "\\" + "accounts" + "\\" + "images" + "\\" +
                this.getAccountId() + ".jpg");
        try {
            if (imageFile.exists())
                imageFile.delete();
        } catch (Exception ignored) {
            System.out.println("image not found!");
        }

        gameLogSummaries.clear();
        for (String username : friends) {
            Player friend = Objects.requireNonNull(Player.getPlayerByUsername(username));
            friend.removeFriend(this);
        }
        friends.clear();

        for (String friendRequestId : receivedFriendRequests) {
            FriendRequest friendRequest = Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequestId));
            friendRequest.getPlayer().removeSentFriendRequest(friendRequest);
            friendRequest.delete();
        }
        receivedFriendRequests.clear();

        for (String friendRequestId : sentFriendRequests) {
            FriendRequest friendRequest = Objects.requireNonNull(FriendRequest.getFriendRequestById(friendRequestId));
            friendRequest.getFriend().removeReceivedFriendRequest(friendRequest);
            friendRequest.delete();
        }
        sentFriendRequests.clear();

        messages.clear();

        favouriteGames.clear();
        Iterator<String> iterator = suggestions.iterator();
        while (iterator.hasNext()) {
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
    public int[] getCardsNumber(){
        int[] numbers = new int[3];
        int number1 = 0;
        int number2 = 0;
        int number3 = 0;
        for(Card card : cards){
            if(card.equals(Card.CARD_1)){
                number1++;
            }
            if(card.equals(Card.CARD_2)){
                number2++;
            }
            if(card.equals(Card.CARD_3)){
                number3++;
            }
        }
        numbers[0] = number1;
        numbers[1] = number2;
        numbers[2] = number3;
        return numbers;
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