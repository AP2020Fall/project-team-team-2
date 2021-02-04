package model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import controller.risk.MatchCardController;
import javafx.animation.AnimationTimer;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.AudioClip;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RiskGame {
    private Player turn;
    private String status;
    private ArrayList<Player> originalPlayers;
    private ProgressBar progressBar;
    private AnimationTimer timer;
    private AudioClip audioClip;
    private static java.util.Map<String, Object> primitiveSettings;
    private ArrayList<Player> players;
    private boolean gameIsPlaying = true;
    private boolean draftDone;
    private boolean attackDone;
    private boolean beginDraftDone = false;
    private boolean fogIsSet = false;
    private boolean allPlayersAddedSoldier;
    private Integer i;
    private Integer j;
    private int duration;
    private boolean fortifyDone;
    private long currentTimeStamp = System.currentTimeMillis() / 1000L;
    private Country sourceCountryWinner;
    private boolean attackWon = false;
    private Country attackDestination;
    private boolean turnDone;
    private int startSoldiers;
    private boolean gotCards = false;
    private String gameID;
    private boolean placementFinished = false;
    private List<List<Country>> gameCountries = new ArrayList<List<Country>>();
    private Player currentPlayer;
    private boolean notifSent = false;
    private MatchCardController matchCardController = new MatchCardController(currentPlayer);
    private Player winner;
    private boolean soldierPlacedAfterWin = true;
    private Event event;
    private static List<Player> allPlayers;
    private static Player playingUser;
    private final String riskGameId;
    /* Status Of player : Drafting - Attacking - Fortifing */
    private static String playerStatus;

    public RiskGame(AvailableGame availableGame,String riskGameId , int soldiers , Event event){

        this.primitiveSettings = availableGame.getPrimitiveSetting();
        this.event = event;
        this.players = (ArrayList<Player>) primitiveSettings.get("Players");
        this.riskGameId = riskGameId;

        for (Player player : this.players) {
            player.setCard();
        }
        this.fogIsSet = (boolean) primitiveSettings.get("Fog of War");
        this.startSoldiers = soldiers;
        this.duration = (int) primitiveSettings.get("Duration");
        for (Player player : players) {
            player.setRequestAndFriendsList();
        }
        /* Shaping Map*/
        this.shapeMap();

        currentPlayer = players.get(0);
        /* Show Turn*/
    }

    public void shapeMap() {
        Integer mapNumber = (Integer) primitiveSettings.get("Map Number");
        String mapFileAddress = "src/main/resources/maps/map_" + String.valueOf(mapNumber) + ".txt";
        Gson newGson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(mapFileAddress));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int europeNumber = 0;
        int africaNumber = 0;
        int asiaNumber = 0;
        int americaNumber = 0;
        int australiaNumber = 0;
        int[][] newPath = newGson.fromJson(reader, int[][].class);
        int gameCountryNumber = 0;
        for (int i = 0; i < newPath.length; i++) {
            gameCountries.add(new ArrayList<Country>());
            for (int j = 0; j < newPath[i].length; j++) {
                Countries countryDetails = Countries.values()[newPath[i][j] - 1];
                String countryName = countryDetails.getName();
                String countryContinent = countryDetails.getContinent();
                int setNumberContinentCountry = 0;
                switch (countryContinent) {
                    case "Africa":
                        africaNumber++;
                        setNumberContinentCountry = africaNumber;
                        break;
                    case "Europe":
                        europeNumber++;
                        setNumberContinentCountry = europeNumber;
                        break;
                    case "Asia":
                        asiaNumber++;
                        setNumberContinentCountry = asiaNumber;
                        break;
                    case "America":
                        americaNumber++;
                        setNumberContinentCountry = americaNumber;
                        break;
                    case "Australia":
                        australiaNumber++;
                        setNumberContinentCountry = australiaNumber;
                        break;
                }
                gameCountryNumber++;
                Country country = new Country(countryName, countryContinent, gameCountryNumber);
                country.setNumberOfContinentCountry(setNumberContinentCountry);
                gameCountries.get(i).add(country);
            }
        }
        setBlizzard();
    }

    public void setBlizzard() {
        if ((boolean) primitiveSettings.get("Blizzards")) {
            int rndRow = new Random().nextInt(gameCountries.size());
            int rndCol = new Random().nextInt(gameCountries.get(0).size());
            gameCountries.get(rndRow).get(rndCol).enableBlizzard();
        }
    }
    public void main(String[] args) {
    }

    public void createGame() {
    }

    public void endGame() {
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTurn(Player player) {
        this.turn = player;
    }

    public Player getTurn() {
        return turn;
    }

    public static void addPlayer(Player player) {
        RiskGame.allPlayers.add(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getDuration() {
        return duration;
    }

    public void setAttackDone(boolean attackDone) {
        this.attackDone = attackDone;
    }

    public Integer getI() {
        return i;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public Integer getJ() {
        return j;
    }

    public Player getWinner() {
        return winner;
    }

    public static Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }

    public Country getSourceCountryWinner() {
        return sourceCountryWinner;
    }

    public int getStartSoldiers() {
        return startSoldiers;
    }

    public long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public Country getAttackDestination() {
        return attackDestination;
    }

    public String getGameID() {
        return gameID;
    }

    public List<List<Country>> getGameCountries() {
        return gameCountries;
    }

    public MatchCardController getMatchCardController() {
        return matchCardController;
    }

    public Event getEvent() {
        return event;
    }

    public static List<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setFortifyDone(boolean fortifyDone) {
        this.fortifyDone = fortifyDone;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setPlacementFinished(boolean placementFinished) {
        this.placementFinished = placementFinished;
    }

    public void setAllPlayersAddedSoldier(boolean allPlayersAddedSoldier) {
        this.allPlayersAddedSoldier = allPlayersAddedSoldier;
    }
    public boolean getAllPlayersAddedSoldier(){
        return allPlayersAddedSoldier;
    }
    public void setBeginDraftDone(boolean beginDraftDone) {
        this.beginDraftDone = beginDraftDone;
    }

    public void setAttackDestination(Country attackDestination) {
        this.attackDestination = attackDestination;
    }

    public void setAttackWon(boolean attackWon) {
        this.attackWon = attackWon;
    }

    public void setCurrentTimeStamp(long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public void setDraftDone(boolean draftDone) {
        this.draftDone = draftDone;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setFogIsSet(boolean fogIsSet) {
        this.fogIsSet = fogIsSet;
    }
    public boolean getFogIsSet(){
        return fogIsSet;
    }
    
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGameIsPlaying(boolean gameIsPlaying) {
        this.gameIsPlaying = gameIsPlaying;
    }
    public boolean getGameIsPlaying(){
        return gameIsPlaying;
    }
    public static void setPrimitiveSettings(Map<String, Object> primitiveSettings) {
        RiskGame.primitiveSettings = primitiveSettings;
    }

    public void setGameCountries(List<List<Country>> gameCountries) {
        this.gameCountries = gameCountries;
    }

    public void setSourceCountryWinner(Country sourceCountryWinner) {
        this.sourceCountryWinner = sourceCountryWinner;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setGotCards(boolean gotCards) {
        this.gotCards = gotCards;
    }

    public void setStartSoldiers(int startSoldiers) {
        this.startSoldiers = startSoldiers;
    }

    public static void setAllPlayers(List<Player> allPlayers) {
        RiskGame.allPlayers = allPlayers;
    }

    public void setTurnDone(boolean turnDone) {
        this.turnDone = turnDone;
    }

    public boolean isFogIsSet() {
        return fogIsSet;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setMatchCardController(MatchCardController matchCardController) {
        this.matchCardController = matchCardController;
    }

    public void setNotifSent(boolean notifSent) {
        this.notifSent = notifSent;
    }

    public void setSoldierPlacedAfterWin(boolean soldierPlacedAfterWin) {
        this.soldierPlacedAfterWin = soldierPlacedAfterWin;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
    public static Player getPlayingUser() {
        return playingUser;
    }

    public static void setPlayerStatus(String playerStatus) {
        RiskGame.playerStatus = playerStatus;
    }

    public static void setPlayingUser(Player playingUser) {
        RiskGame.playingUser = playingUser;
    }

    public static String getPlayerStatus() {
        return playerStatus;
    }

    public boolean getDraftDone() {
        return draftDone;
    }

    public boolean getBeginDraftDone() {
        return beginDraftDone;
    }

    public boolean getAttackDone() {
        return attackDone;
    }

    public boolean getFortifyDone() {
        return fortifyDone;
    }

    public boolean getGotCards() {
        return gotCards;
    }

    public boolean getTurnDone() {
        return turnDone;
    }

    public boolean getSoldierPlacedAfterWin() {
        return soldierPlacedAfterWin;
    }

    public boolean getPlacementFinished() {
        return placementFinished;
    }

    public boolean getAttackWon() {
        return attackDone;
    }

    public boolean getNotifSent() {
        return notifSent;
    }
}
