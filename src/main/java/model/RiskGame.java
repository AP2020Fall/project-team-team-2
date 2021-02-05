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
    private static final ArrayList<RiskGame> riskGames = new ArrayList<>();
    private String status;


    private ProgressBar progressBar;
    private AnimationTimer timer;
    private AudioClip audioClip;
    private java.util.Map<String, Object> primitiveSettings;
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

    private boolean notifSent = false;
    private MatchCardController matchCardController;// = new MatchCardController(currentPlayer);

    private Gamer winner;
    private ArrayList<Gamer> originalPlayers;
    private Gamer turn;
    private Gamer currentPlayer;
    private  ArrayList<Gamer> allPlayers;
    private  String playerStatus;
    private  Gamer playingUser;
    private ArrayList<Gamer> players;

    private boolean soldierPlacedAfterWin = true;
    private Event event;
    private final String riskGameId;
    /* Status Of player : Drafting - Attacking - Fortifing */

    public RiskGame(AvailableGame availableGame, int soldiers ){

        this.primitiveSettings = availableGame.getPrimitiveSetting();
        System.out.println("--------" + this.primitiveSettings);
        this.event = availableGame.getEvent();

        PlayingGame playingGame = new PlayingGame(availableGame);

        this.players = playingGame.getPlayers();
        this.riskGameId = availableGame.getAvailableGameId();

        for (Gamer gamer : this.players) {
            gamer.setCard();
        }
        this.fogIsSet = (boolean) primitiveSettings.get("Fog of War");
        this.startSoldiers = soldiers;
        this.duration = (int) Math.round((Double) primitiveSettings.get("Duration"));

        for (Gamer gamer : players) {
            gamer.setRequestAndFriendsList();
        }
        /* Shaping Map*/
        this.shapeMap();
        currentPlayer = players.get(0);
        /* Show Turn*/
        riskGames.add(this);
    }

    public static RiskGame getRiskGameById(String availableGameId) {
        for (RiskGame riskGame:riskGames)
            if(riskGame.riskGameId.equals(availableGameId))
                return riskGame;
            return null;
    }

    public void shapeMap() {
        int mapNumber = (int) Math.round((Double) primitiveSettings.get("Map Number"));
        String mapFileAddress = "src/main/resources/maps/map_" + mapNumber + ".txt";
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
            gameCountries.add(new ArrayList<>());
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

    public void setTurn(Gamer gamer) {
        this.turn = gamer;
    }

    public Gamer getTurn() {
        return turn;
    }

    public  void addPlayer(Gamer gamer) {
        allPlayers.add(gamer);
    }

    public Gamer getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Gamer> getPlayers() {
        return players;
    }

    public Gamer getWinner() {
        return winner;
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

    public Map<String, Object> getPrimitiveSettings() {
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

    public ArrayList<Gamer> getAllPlayers() {
        return allPlayers;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setFortifyDone(boolean fortifyDone) {
        this.fortifyDone = fortifyDone;
    }

    public void setPlayers(ArrayList<Gamer> players) {
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
    
    public void setCurrentPlayer(Gamer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGameIsPlaying(boolean gameIsPlaying) {
        this.gameIsPlaying = gameIsPlaying;
    }
    public boolean getGameIsPlaying(){
        return gameIsPlaying;
    }
    public  void setPrimitiveSettings(Map<String, Object> primitiveSettings) {
       this.primitiveSettings = primitiveSettings;
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

    public void setAllPlayers(ArrayList<Gamer> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public void setTurnDone(boolean turnDone) {
        this.turnDone = turnDone;
    }

    public boolean isFogIsSet() {
        return fogIsSet;
    }

   /* public void setEvent(Event event) {
        this.event = event;
    }*/

    public void setMatchCardController(MatchCardController matchCardController) {
        this.matchCardController = matchCardController;
    }

    public void setNotifSent(boolean notifSent) {
        this.notifSent = notifSent;
    }

    public void setSoldierPlacedAfterWin(boolean soldierPlacedAfterWin) {
        this.soldierPlacedAfterWin = soldierPlacedAfterWin;
    }

    public void setWinner(Gamer winner) {
        this.winner = winner;
    }
    public  Gamer getPlayingUser() {
        return playingUser;
    }

    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    public void setPlayingUser(Gamer playingUser) {
        this.playingUser = playingUser;
    }

    public  String getPlayerStatus() {
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
