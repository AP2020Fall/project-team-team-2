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
    private boolean draftDone;
    private boolean attackDone;
    private boolean fortifyDone;
    private int startSoldiers;
    private String gameID;
    private boolean placementFinished = false;
    private List<List<Country>> gameCountries = new ArrayList<List<Country>>();
    private Player currentPlayer = players.get(0);
    private MatchCardController matchCardController = new MatchCardController(currentPlayer);

    public RiskGameController(java.util.Map<String, Object> primitiveSettings, String gameID, int soldiers) {
        this.primitiveSettings = primitiveSettings;
        this.players = (ArrayList<Player>) primitiveSettings.get("Players");
        this.gameID = gameID;
        this.startSoldiers = soldiers;
        /* Shaping Map*/
        this.shapeMap();
        /* Make Robot Players*/
        this.makeRobotPlayers();

    }

    public void shapeMap() {
        String mapNumber = (String) primitiveSettings.get("Map Number");
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
                gameCountries.get(i).set(j, country);
            }
        }
    }

    public void makeRobotPlayers() {
        String mainBotName = "Robot";
        /*
         * here we will add the Main Player To allPlayers
         *
         */
        for (int i = 1; i < (int) primitiveSettings.get("PlayersNum"); i++) {
            Player newRobotPlayer = new Player(mainBotName + " " + i, mainBotName + " " + i);
            players.add(newRobotPlayer);
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

    public void draft(){

    }

    public String attack(String sourceCountry, String destinyCountry, int soldiers) {
        String toPrint = "";
        String[] sourceDetails = sourceCountry.split("\\.");
        String[] destinationDetails = destinyCountry.split("\\.");
        String sourceCountryName = sourceDetails[0];
        int sourceNumber = Integer.parseInt(sourceDetails[1]);
        String destinationCountryName = destinationDetails[0];
        int destinationNumber = Integer.parseInt(destinationDetails[1]);
        boolean sourceCountryValid = false;
        boolean destinationCountryValid = false;
        Country source = getCountryByDetails(sourceCountryName, sourceNumber);
        Country destination = getCountryByDetails(destinationCountryName, destinationNumber);
        if (source.getOwner() != null && source.getOwner().equals(currentPlayer)) {
            sourceCountryValid = true;
        }
            if (destination.getOwner() != null && !destination.getOwner().equals(currentPlayer)) {
                destinationCountryValid = true;
            }
        if (!sourceCountryValid) {
            toPrint = "Source country is not valid";
        }
        if (sourceCountryValid && !destinationCountryValid) {
            toPrint = "Destination country is not valid";
        }
        if (sourceCountryValid && destinationCountryValid && (soldiers > source.getSoldiers() || soldiers < 0)) {
            toPrint = "Soldiers are not enough or not valid";
        } else {
            boolean inWar = true;
            do {
                int randomNumberSource = (int) Math.random() * (6 - 0 + 1) + 0;
                int randomNumberDestination = (int) Math.random() * (6 - 0 + 1) + 0;
                toPrint = "Source Dice : " + randomNumberSource + " - Destination Dice : " + randomNumberDestination;
                if (randomNumberSource > randomNumberDestination) {
                    destination.addSoldiers(-1);
                    toPrint = toPrint + "Destination Country Lost 1 soldier! , Destination Soldiers "
                            + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                } else if (randomNumberDestination > randomNumberSource) {
                    source.addSoldiers(-1);
                    toPrint = toPrint + "Destination Country Lost 1 soldier! , Destination Soldiers "
                            + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                } else {
                    source.addSoldiers(-1);
                    toPrint = toPrint + "Destination Country Lost 1 soldier! , Destination Soldiers "
                            + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                }
                if(source.getSoldiers() == 0 || destination.getSoldiers() == 0){
                    inWar = false;
                }
            } while (inWar);


        }
        return toPrint;
    }

    public String forfeit(String sourceCountry, String destinyCountry, int soldiers) {
        String toPrint = "";
        String[] sourceDetails = sourceCountry.split("\\.");
        String[] destinationDetails = destinyCountry.split("\\.");
        String sourceCountryName = sourceDetails[0];
        int sourceNumber = Integer.parseInt(sourceDetails[1]);
        String destinationCountryName = destinationDetails[0];
        int destinationNumber = Integer.parseInt(destinationDetails[1]);
        boolean sourceCountryValid = false;
        boolean destinationCountryValid = false;
        Country source = getCountryByDetails(sourceCountryName, sourceNumber);
        Country destination = getCountryByDetails(destinationCountryName, destinationNumber);
        if (source.getOwner() != null && source.getOwner().equals(currentPlayer)) {
            sourceCountryValid = true;
        }
        if (destination.getOwner() != null && destination.getOwner().equals(currentPlayer)) {
            destinationCountryValid = true;
        }
        if (!sourceCountryValid) {
            toPrint = "Source country is not valid";
        }
        else if (sourceCountryValid && !destinationCountryValid) {
            toPrint = "Destination country is not valid";
        }
        else if (sourceCountryValid && destinationCountryValid && (soldiers > source.getSoldiers() || soldiers < 0)) {
            toPrint = "Soldiers are not enough or not valid";
        }
        else {

        }
        return toPrint;
    }

    /* Move Soldiers */
    public void move() {
    }

    /* Draf-Attck-forfeit */
    public void next() {
    }

    public String changeTurn() {
        String toPrint = "";
        if (!getPlacementFinished()) {
            if (draftDone) {
                int currentTurnIndex = this.players.indexOf(this.currentPlayer);
                if (currentTurnIndex != this.players.size() - 1) {
                    this.currentPlayer = this.players.get(currentTurnIndex + 1);
                } else {
                    this.currentPlayer = this.players.get(0);
                }
                toPrint = "Next Turn done successfully, It's " + currentPlayer.getUsername() + " turn";
            } else {
                toPrint = "You didn't place any soldier, please first try to place a soldier in remain countries.";
            }
        } else {
            if (draftDone) {
                /*Todo: attack doesn't need to be checked(?)*/
                if (attackDone) {
                    if (fortifyDone) {
                        int currentTurnIndex = this.players.indexOf(this.currentPlayer);
                        if (currentTurnIndex != this.players.size() - 1) {
                            this.currentPlayer = this.players.get(currentTurnIndex + 1);
                        } else {
                            this.currentPlayer = this.players.get(0);
                        }
                        toPrint = "Next Turn done successfully, It's " + currentPlayer.getUsername() + " turn";
                    } else {
                        toPrint = "You didn't fortify yet.";
                    }
                } else {
                    toPrint = "You didn't attack yet.";
                }
            } else {
                toPrint = "You didn't place any soldier, please first try to place a soldier in your countries.";
            }
        }
        return toPrint;
    }

    public static java.util.Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }

    public boolean getPlacementFinished() {
        return this.placementFinished;
    }

    public void matchCards() {
    }

    public String showMap() {
        String lineString = "";
        for (List<Country> listCountries : gameCountries) {
            for (Country country : listCountries) {
                if (!country.equals(listCountries.get(listCountries.size() - 1))) {
                    lineString = lineString + country.toString() + " | ";
                } else {
                    lineString = lineString + country.toString() + "\n";
                }
            }
        }
        return lineString.trim();
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String placeSoldier(String countryDetails, int soldiers) {
        String toPrint = "";
        String[] countryDetails2 = countryDetails.split("\\.");
        String countryContinent = countryDetails2[0];
        int countryContinentNumber = Integer.parseInt(countryDetails2[1]);
        Country toCheckCountry = this.getCountryByDetails(countryContinent, countryContinentNumber);
        if (!this.getDraftDone()) {
            if (toCheckCountry.getName() == null) {
                toPrint = "Chosen country is invalid. Plese try again";
            } else {
                if (toCheckCountry.getOwner().equals(currentPlayer) || toCheckCountry.getOwner() == null) {
                    toCheckCountry.setOwner(currentPlayer);
                    toCheckCountry.addSoldiers(soldiers);
                    toPrint = currentPlayer.getPlayerNumber() + " add one soldier to "
                            + toCheckCountry.getName();
                    this.setDraftDone(true);
                } else {
                    toPrint = "Please choose a country that is yours or no one has been chosen it yet";
                }
            }
        } else {
            toPrint = "You have been done your draft turn.";
        }
        return toPrint;
    }

    public boolean getDraftDone() {
        return draftDone;
    }

    public void setDraftDone(boolean status) {
        draftDone = status;
    }

    public boolean getAttackDone() {
        return attackDone;
    }

    public void setAttackDone(boolean status) {
        attackDone = status;
    }

    public boolean getFortifyDone() {
        return fortifyDone;
    }

    public void setFortifyDone(boolean status) {
        this.fortifyDone = status;
    }

    public void matchCardAddSoldiers(int soldiersNumber){
        matchCardController.incPlayerSoldier(currentPlayer, soldiersNumber);
    }

    public Country getCountryByDetails(String shortName, int countryContinentNumber) {
        Country toReturnCountry = new Country();
        for (List<Country> countries : this.gameCountries) {
            for (Country country : countries) {
                if (country.getNumberOfContinentCountry() == countryContinentNumber && country.getContinent()
                        .substring(0, 2).toUpperCase().equals(shortName)) {

                    toReturnCountry = country;
                }
            }
        }
        return toReturnCountry;
    }
}
