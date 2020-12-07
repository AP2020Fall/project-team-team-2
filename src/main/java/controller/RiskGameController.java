package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Countries;
import model.Player;
import model.Map;
import model.Country;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RiskGameController {
    private static java.util.Map<String, Object> primitiveSettings;
    private ArrayList<Player> players;
    private List<List<Map>> gameMap;
    private boolean draftDone;
    private boolean attackDone;
    private boolean fortifyDone;
    private List<Player> allPlayers;
    private String gameID;
    private boolean placementFinished = false;
    private List<List<Country>> gameCountries = new ArrayList<List<Country>>();
    private Player currentPlayer = players.get(0);
    public RiskGameController( java.util.Map<String, Object> primitiveSettings , String gameID){
        this.primitiveSettings = primitiveSettings;
        this.gameID = gameID;
//        this.players = players;
        /* Shaping Map*/
        this.shapeMap();
        /* Make Robot Players*/
        this.makeRobotPlayers();
    }
    public void shapeMap(){
        String mapNumber =(String) primitiveSettings.get("Map Number");
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
        for (int i = 0; i < newPath.length; i++) {
            for (int j = 0; j < newPath[i].length; j++) {
                Countries countryDetails = Countries.values()[newPath[i][j] - 1];
                String countryName = countryDetails.getName();
                String countryContinent = countryDetails.getContinent();
                int setNumberContinentCountry = 0;
                switch (countryContinent){
                    case "Africa":africaNumber++;setNumberContinentCountry = africaNumber;break;
                    case "Europe":europeNumber++;setNumberContinentCountry = europeNumber;break;
                    case "Asia":asiaNumber++;setNumberContinentCountry = asiaNumber;break;
                    case "America":americaNumber++;setNumberContinentCountry = americaNumber;break;
                    case "Australia":australiaNumber++;setNumberContinentCountry = australiaNumber;break;
                }
                Country country = new Country(countryName , countryContinent);
                country.setNumberOfContinentCountry(setNumberContinentCountry);
                gameCountries.get(i).set(j , country);
            }
        }
    }
    public void makeRobotPlayers(){
        String mainBotName = "Robot";
        /*
         * here we will add the Main Player To allPlayers
         *
         */
        for(int i = 1 ; i < (int)primitiveSettings.get("PlayersNum") ; i++){
            Player newRobotPlayer = new Player(mainBotName + " " + i , mainBotName + " " + i);
            allPlayers.add(newRobotPlayer);
        }
    }
    public static void main(String[] args) {
    }

    /* Me - Amirhossein Kiani */
    public void createGame(int n, int m) {

    }
    /* Draft */
    public void placeSoldier(Country country, int soldierNumber) {
        country.addSoldiers(soldierNumber);
        this.draftDone = true;
    }

    public void attack(Country sourceCountry , Country destinyCountry) {
    }

    /* Move Soldiers */
    public void move() {
    }

    /* Draf-Attck-forfeit */
    public void next() {
    }

    public void changeTurn() {
        int currentTurnIndex = this.players.indexOf(this.currentPlayer);
        if(currentTurnIndex != this.players.size() - 1){
            this.currentPlayer = this.players.get(currentTurnIndex+1);
        }else{
            this.currentPlayer = this.players.get(0);
        }
    }

    public static java.util.Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }

    public boolean getPlacementFinished(){
        return this.placementFinished;
    }
    public void matchCards() {
    }
    public void showMap(){
        for(List<Country> listCountries : gameCountries){
            String lineString = "";
            for(Country country : listCountries){
                if(!country.equals(listCountries.get(listCountries.size()-1))) {
                    lineString = lineString + country.toString() + " | ";
                }else{
                    lineString = lineString + country.toString();
                }
            }
            System.out.println(lineString);
        }
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
