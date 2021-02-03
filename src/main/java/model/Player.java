package model;

import com.google.gson.Gson;
import controller.Controller;
import controller.ServerMasterController.SQLConnector;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map;

public class Player extends Account {
    private double money;
    private ArrayList<String> gameLogSummaries;
    private ArrayList<String> friends;
    private ArrayList<String> receivedFriendRequests;
    private ArrayList<String> sentFriendRequests;
    private ArrayList<String> messages;
    private ArrayList<String> favouriteGames;
    private ArrayList<String> suggestions;



    private transient ArrayList<Card> cards = new ArrayList<>();
    private transient int newSoldiers;
    private transient int playerNumber;
    private transient int draftSoldiers = 0;
    private transient ArrayList<Player> requests;
    private transient ArrayList<Player> gameFriends;

    public Player(String firstName, String lastName, String username, String accountId,
                  String password, String email, String phoneNumber, double money) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber,false);
        this.money = money;
        //this.score = 0;
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

    public static void add(Player player) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("first_name", player.getFirstName());
        resultMap.put("last_name", player.getLastName());
        resultMap.put("username", player.getUsername());
        resultMap.put("hash_password", player.getPassword());
        resultMap.put("email_address", player.getEmail());
        resultMap.put("phone_number", player.getPhoneNumber());
        resultMap.put("register_date", player.getRegisterDay().format(DateTimeFormatter.ISO_DATE_TIME));
        resultMap.put("avatar_address", player.getImageURL());
        resultMap.put("player_id", player.getAccountId());
        resultMap.put("bio", player.getBio());
        resultMap.put("is_admin", player.isAdmin() ? 1 : 0);
        resultMap.put("money",String.valueOf(player.getMoney()));
        resultMap.put("game_log_summaries",new Gson().toJson(player.getSQLGameLogSummaries()));
        resultMap.put("friends",new Gson().toJson(player.getSQLFriends()));
        resultMap.put("received_friend_request",new Gson().toJson(player.getSQLReceivedFriendRequests()));
        resultMap.put("sent_friend_request",new Gson().toJson(player.getSQLSentFriendRequests()));
        resultMap.put("player_messages",new Gson().toJson(player.getSQLMessages()));
        resultMap.put("favourite_games",new Gson().toJson(player.getSQLFavouriteGames()));
        resultMap.put("suggestions",new Gson().toJson(player.getSQLSuggestions()));

        SQLConnector.insertInDatabase(resultMap, "players");

    }

    public ArrayList<String > getSQLGameLogSummaries()
    {
        return gameLogSummaries;
    }

    public ArrayList<String > getSQLFriends()
    {
        return friends;
    }
    public ArrayList<String > getSQLReceivedFriendRequests()
    {
        return receivedFriendRequests;
    }
    public ArrayList<String > getSQLSentFriendRequests()
    {
        return sentFriendRequests;
    }
    public ArrayList<String > getSQLMessages()
    {
        return messages;
    }
    public ArrayList<String > getSQLFavouriteGames()
    {
        return favouriteGames;
    }
    public ArrayList<String > getSQLSuggestions()
    {
        return suggestions;
    }




    public double getMoney() {
        return money;
    }

    public int getScore() {
        int result = 0;
       for(GameLogSummary gameLogSummary: this.getGameLogSummaries())
           result += gameLogSummary.getScore();
       return result;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    /* public void setScore(int score) {
        this.score += score;
    }*/

    public ArrayList<GameLogSummary> getGameLogSummaries() {
        ArrayList<GameLogSummary> result = new ArrayList<>();
        for (String gameLogSummaryId: gameLogSummaries)
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
        gameLogSummaries.add(gameLog.getGameLogSummaryId());
        editField("game_log_summaries",new Gson().toJson(gameLogSummaries));
    }

    public void removeGameLog(Game game) {
        //todo make it better if it doesnt work
        ArrayList<GameLogSummary> checker = this.getGameLogSummaries();
        for (int i = 0 ; i < checker.size(); i++)
        {
            if(checker.get(i).getGameName().equals(game.getName()))
                gameLogSummaries.remove(i);
        }
        editField("game_log_summaries",new Gson().toJson(gameLogSummaries));
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
        ArrayList<Message> result = new ArrayList<>();
        for(String messageId:messages)
        {
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

    public int getNumberOfWins() {
        int wins = 0;
        for (GameLogSummary gameLogSummary : this.getGameLogSummaries())
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
        //Account.getAllAccounts().remove(this);

    }

    @Override
    public String toString() {
        return super.toString()
                + "money: " + getMoney() + "$\n";
             //   + "registered: " + getDayOfRegister() + " days ago\n";
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

    public ArrayList<Player> getGameFriends() {
        return gameFriends;
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
/*
    public void setDraws() {
        this.draws++;
    }

    public void setWins() {
        this.wins++;
    }

    public void setLoses() {
        this.loses++;
    }
*/
    public void addGameRequest(Player player){
        requests.add(player);
    }
    public void addGameFriend(Player player){
        gameFriends.add(player);
    }
    public void rejectRequest(Player player){
        requests.remove(player);
    }

    public ArrayList<Player> getRequests() {
        return requests;
    }

    public boolean checkPlayerHasRequest(){
        if(requests.size() > 0){
            return true;
        }else{
            return false;
        }
    }
    public void setRequestAndFriendsList(){
        this.requests = new ArrayList<Player>();
        this.gameFriends = new ArrayList<Player>();
    }
    public void resetRequestAndFriends(){
        this.requests.clear();
        this.gameFriends.clear();
    }
    public void setCard(){
        this.cards = new ArrayList<Card>();
    }

    public void addMessage(String messageId) {
    }
}