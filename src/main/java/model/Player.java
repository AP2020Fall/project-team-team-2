package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.ServerMasterController.SQLConnector;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.*;

public class Player extends Account {
    private double money;
    private ArrayList<String> gameLogSummaries;
    private ArrayList<String> friends;
    private ArrayList<String> receivedFriendRequests;
    private ArrayList<String> sentFriendRequests;
    private ArrayList<String> messages;
    private ArrayList<String> favouriteGames;
    private ArrayList<String> suggestions;

    public Player(String firstName, String lastName, String username, String accountId,
                  String password, String email, String phoneNumber, double money) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber, false);
        this.money = money;
        gameLogSummaries = new ArrayList<>();
        friends = new ArrayList<>();
        receivedFriendRequests = new ArrayList<>();
        sentFriendRequests = new ArrayList<>();
        messages = new ArrayList<>();
        favouriteGames = new ArrayList<>();
        suggestions = new ArrayList<>();
    }

    public Player(String botName, String username) {
        super(botName, username);
    }

    public Player(Map<String, Object> player) {
        super(player);
        this.money = Double.parseDouble((String) player.get("money"));
        gameLogSummaries = new Gson().fromJson((String) player.get("game_log_summaries")
                , new TypeToken<ArrayList<String>>() {}.getType());
        friends =  new Gson().fromJson((String) player.get("friends")
                , new TypeToken<ArrayList<String>>() {}.getType());
        receivedFriendRequests =  new Gson().fromJson((String) player.get("received_friend_request")
                , new TypeToken<ArrayList<String>>() {}.getType());
        sentFriendRequests =  new Gson().fromJson((String) player.get("sent_friend_request")
                , new TypeToken<ArrayList<String>>() {}.getType());
        messages =  new Gson().fromJson((String) player.get("player_messages")
                , new TypeToken<ArrayList<String>>() {}.getType());
        favouriteGames =  new Gson().fromJson((String) player.get("favourite_game")
                , new TypeToken<ArrayList<String>>() {}.getType());
        suggestions =  new Gson().fromJson((String) player.get("suggestions")
                , new TypeToken<ArrayList<String>>() {}.getType());
    }

    public static void add(Player player) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("first_name", player.getFirstName());
        resultMap.put("last_name", player.getLastName());
        resultMap.put("username", player.getUsername());
        resultMap.put("hash_password", player.getPassword());
        resultMap.put("email_address", player.getEmail());
        resultMap.put("phone_number", player.getPhoneNumber());
        resultMap.put("register_date", player.getRegisterDay().toString());
        resultMap.put("avatar_address", player.getImageURL());
        resultMap.put("player_id", player.getAccountId());
        resultMap.put("bio", player.getBio());
        resultMap.put("is_admin", player.isAdmin() ? 1 : 0);
        resultMap.put("status", player.getStatus());
        resultMap.put("money", String.valueOf(player.getMoney()));
        resultMap.put("game_log_summaries", new Gson().toJson(player.getSQLGameLogSummaries()));
        resultMap.put("friends", new Gson().toJson(player.getSQLFriends()));
        resultMap.put("received_friend_request", new Gson().toJson(player.getSQLReceivedFriendRequests()));
        resultMap.put("sent_friend_request", new Gson().toJson(player.getSQLSentFriendRequests()));
        resultMap.put("player_messages", new Gson().toJson(player.getSQLMessages()));
        resultMap.put("favourite_game", new Gson().toJson(player.getSQLFavouriteGames()));
        resultMap.put("suggestions", new Gson().toJson(player.getSQLSuggestions()));
        SQLConnector.insertInDatabase(resultMap, "players");

    }

    public ArrayList<String> getSQLGameLogSummaries() {
        return gameLogSummaries;
    }

    public ArrayList<String> getSQLFriends() {
        return friends;
    }

    public ArrayList<String> getSQLReceivedFriendRequests() {
        return receivedFriendRequests;
    }

    public ArrayList<String> getSQLSentFriendRequests() {
        return sentFriendRequests;
    }

    public ArrayList<String> getSQLMessages() {
        return messages;
    }

    public ArrayList<String> getSQLFavouriteGames() {
        return favouriteGames;
    }

    public ArrayList<String> getSQLSuggestions() {
        return suggestions;
    }

    public double getMoney() {
        return money;
    }

    public int getScore() {
        int result = 0;
        for (GameLogSummary gameLogSummary : this.getGameLogSummaries())
            result += gameLogSummary.getScore();
        return result;
    }

    public void setMoney(double money) {
        this.money = money;
        editField("money",String.valueOf(this.money));
    }



    public ArrayList<GameLogSummary> getGameLogSummaries() {
        ArrayList<GameLogSummary> result = new ArrayList<>();
        for (String gameLogSummaryId : gameLogSummaries)
            result.add(GameLogSummary.getGameLogSummaryById(gameLogSummaryId));
        return result;
    }


    public GameLogSummary getGameLogLastGamePlayed() {
        if (this.getGameLogSummaries().isEmpty()) {
            return null;
        }
        this.getGameLogSummaries().sort((a, b) -> b.getLastPlay().compareTo(a.getLastPlay()));
        return this.getGameLogSummaries().get(0);
    }

    public GameLogSummary getGameHistory(String gameName) {
        for (GameLogSummary gameLogSummary : this.getGameLogSummaries())
            if (gameLogSummary.getGameName().equals(gameName))
                return gameLogSummary;
        return null;
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

    public ArrayList<FriendRequest> getReceivedFriendRequests() {
        //throws NullPointerException if there is any error
        ArrayList<FriendRequest> result = new ArrayList<>();
        for (String friendRequests : receivedFriendRequests) {
            FriendRequest friendRequest = FriendRequest.getFriendRequestById(friendRequests);
            if (friendRequest != null)
                result.add(friendRequest);
        }
        return result;
    }

    public ArrayList<FriendRequest> getSentFriendRequests() {
        //throws NullPointerException if there is any error
        ArrayList<FriendRequest> result = new ArrayList<>();
        for (String friendRequests : sentFriendRequests) {
            FriendRequest friendRequest = FriendRequest.getFriendRequestById(friendRequests);
            if (friendRequest != null)
                result.add(friendRequest);
        }
        return result;
    }

    public FriendRequest getFriendRequestByUsername(String username) {
        //if username sent a FriendRequest to player, return the FriendRequest else null.
        //throws NullPointerException if there is an error
        for (String friendRequestId : receivedFriendRequests) {
            FriendRequest friendRequest = FriendRequest.getFriendRequestById(friendRequestId);
            if (friendRequest!= null && friendRequest.getPlayer().getUsername().equals(username))
                return friendRequest;
        }
        return null;
    }

    public ArrayList<Suggestion> getSuggestions() {
        //throws NullPointerException if there is any error
        ArrayList<Suggestion> result = new ArrayList<>();
        for (String suggestion : suggestions) {
            Suggestion suggestion1 = Suggestion.getSuggestionById(suggestion);
            if(suggestion1 != null)
            result.add(suggestion1);
        }
        return result;
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

    public ArrayList<Message> getMessages() {
        ArrayList<Message> result = new ArrayList<>();
        for (String messageId : messages) {
            result.add(Message.getMessageById(messageId));
        }
        Collections.sort(result);
        return result;
    }

    public ArrayList<Game> getFavouriteGames() {
        ArrayList<Game> result = new ArrayList<>();
        for (String gameId : favouriteGames) {
            result.add(Objects.requireNonNull(Game.getGameById(gameId)));
        }
        return result;
    }

    public Boolean isGameFavourite(String gameId) {
        return favouriteGames.contains(gameId);
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
        for (GameLogSummary gameLogSummary : this.getGameLogSummaries())
            wins += gameLogSummary.getWins();
        return wins;
    }

    private static ArrayList<Player> getEnemies(ArrayList<Gamer> players, Player winner) {
        ArrayList<Player> result = new ArrayList<>();
        for (Gamer player : players)
            if (!player.getUsername().equals(winner.getUsername()))
                result.add(Player.getPlayerByUsername(player.getUsername()));
        return result;
    }

    public static void addGameLog(ArrayList<Gamer> players, Game game, GameStates gameState, Gamer gWinner,
                                  int win, int draw, int lose) {
        System.out.println("MODEL"+gameState);
        System.out.println(players);
        Player winner = Player.getPlayerByUsername(gWinner.getUsername());
        if (winner != null) {
            GameLogSummary gameLog = winner.getGameHistory(game.getName());
            if (gameLog == null) {
                gameLog = new GameLogSummary(game.getName(), Controller.generateId(0));
                gameLog.addGameLogSummary();
                winner.addGameLogSummary(gameLog);
            }
            gameLog.updateForWin(win, LocalDateTime.now(), new GameLog(winner, getEnemies(players, winner),
                    game.getName(), GameLogStates.WON, LocalDateTime.now(),Controller.generateId(0)));


            for (Gamer gamer : players) {
                Player player = Player.getPlayerByUsername(gamer.getUsername());
                if ( player != null && !player.getAccountId().equals(winner.getAccountId())) {
                    gameLog = player.getGameHistory(game.getName());
                    if (gameLog == null) {
                        gameLog = new GameLogSummary(game.getName(), Controller.generateId(0));
                        player.addGameLogSummary(gameLog);
                        gameLog.addGameLogSummary();
                    }
                    gameLog.updateForLoss(lose, LocalDateTime.now(), new GameLog(player, getEnemies(players, player),
                            game.getName(), GameLogStates.LOST, LocalDateTime.now(),Controller.generateId(0)));
                }
            }
        } else {
            for (Gamer gamer : players) {
                Player player = Player.getPlayerByUsername(gamer.getUsername());
                if(player != null) {
                    GameLogSummary gameLog = player.getGameHistory(game.getName());
                    if (gameLog == null) {
                        gameLog = new GameLogSummary(game.getName(), Controller.generateId(0));
                        gameLog.addGameLogSummary();
                        player.addGameLogSummary(gameLog);
                    }
                    gameLog.updateForDraw(draw, LocalDateTime.now(), new GameLog(player, getEnemies(players, player),
                            game.getName(), GameLogStates.DRAWN, LocalDateTime.now(), Controller.generateId(0)));
                }
            }
        }
        PlayLog playLog = new PlayLog(game.getName(), players, winner, LocalDateTime.now(),Controller.generateId(0));
        playLog.addPlayLog();
        game.addPlayLog(playLog);
    }

    private void addGameLogSummary(GameLogSummary gameLog) {
        gameLogSummaries.add(gameLog.getGameLogSummaryId());
        editField("game_log_summaries", new Gson().toJson(gameLogSummaries));
    }

    /*public void removeGameLog(Game game) {
        //todo make it better if it doesnt work
        ArrayList<GameLogSummary> checker = this.getGameLogSummaries();
        for (int i = 0; i < checker.size(); i++) {
            if (checker.get(i).getGameName().equals(game.getName())) {
                gameLogSummaries.remove(i);
            }
        }
        editField("game_log_summaries", new Gson().toJson(gameLogSummaries));
    }*/


    public void addFriend(Player friend) {
        friends.add(friend.getUsername());
        editField("friends",new Gson().toJson(friends));
    }

    public void removeFriend(Player friend) {
        friends.remove(friend.getUsername());
        editField("friends",new Gson().toJson(friends));
    }


    public void addReceivedFriendRequest(FriendRequest friendRequest) {
        receivedFriendRequests.add(friendRequest.getFriendRequestId());
        editField("received_friend_request",new Gson().toJson(receivedFriendRequests));
    }

    public void addSentFriendRequest(FriendRequest friendRequest) {
        sentFriendRequests.add(friendRequest.getFriendRequestId());
        editField("sent_friend_request",new Gson().toJson(sentFriendRequests));
    }


    public void removeReceivedFriendRequest(FriendRequest friendRequest) {
        receivedFriendRequests.remove(friendRequest.getFriendRequestId());
        editField("received_friend_request",new Gson().toJson(receivedFriendRequests));
    }

    public void removeSentFriendRequest(FriendRequest friendRequest) {
        sentFriendRequests.remove(friendRequest.getFriendRequestId());
        editField("sent_friend_request",new Gson().toJson(sentFriendRequests));
    }


    public void addSuggestion(Suggestion suggestion) {
        this.suggestions.add(suggestion.getSuggestionId());
        editField("suggestions",new Gson().toJson(suggestion));
    }

    public void removeSuggestion(Suggestion suggestion) {
        suggestions.remove(suggestion.getSuggestionId());
        editField("suggestions",new Gson().toJson(suggestion));
    }


    public void addFavouriteGame(Game game) {
        favouriteGames.add(game.getGameId());
        editField("favourite_game", new Gson().toJson(favouriteGames));
    }

    public void removeFavouriteGame(Game game) {
        favouriteGames.remove(game.getGameId());
        editField("favourite_game", new Gson().toJson(favouriteGames));
    }

    public void addMessage(String messageId) {
        messages.add(messageId);
        editField("player_messages",new Gson().toJson(messages));
    }


    public void delete() {
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
        Suggestion.deletePlayerSuggestion(getUsername());

        suggestions.clear();

        Map<String, Object> event = new HashMap<>();
        event.put("player_id", getAccountId());
        if (SQLConnector.deleteFromTable(event, "events")) {
            File imageFile = new File("database\\account\\images\\" + getAccountId() + ".jpg");
            try {
                if (imageFile.exists())
                    imageFile.delete();
            } catch (Exception ignored) {
                System.out.println("[MODEL]: player image not found!");
            }
        }
        else{
            System.out.println("[MODEL]: Account with event ID = " + getAccountId() + " couldn't be deleted");
        }

    }

    @Override
    public String toString() {
        return super.toString()
                + "money: " + getMoney() + "$\n";
        //   + "registered: " + getDayOfRegister() + " days ago\n";
    }
}