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
import java.util.Random;

public class RiskGameController {
    private static java.util.Map<String, Object> primitiveSettings;
    private ArrayList<Player> players;
    private boolean draftDone;
    private boolean attackDone;
    private boolean fortifyDone;
    private boolean turnDone;
    private int startSoldiers;
    private String gameID;
    private boolean placementFinished = false;
    private List<List<Country>> gameCountries = new ArrayList<List<Country>>();
    private Player currentPlayer;
    private MatchCardController matchCardController = new MatchCardController(currentPlayer);

    public RiskGameController(java.util.Map<String, Object> primitiveSettings, String gameID, int soldiers) {
        this.primitiveSettings = primitiveSettings;
        this.players = (ArrayList<Player>) primitiveSettings.get("Players");
        this.gameID = gameID;
        this.startSoldiers = soldiers;
        setStartSoldiers();
        /* Shaping Map*/
        this.shapeMap();
        /* Make Robot Players*/
//        this.makeRobotPlayers();
        /* Set Player One */
        currentPlayer = players.get(0);
        /* Show Turn*/
        showTurn();


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
    }

    public void makeRobotPlayers() {
        String mainBotName = "Robot";
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

    public int getRemainSoldiers() {
        return currentPlayer.getNewSoldiers();
    }

    public void setStartSoldiers() {
        int number = 1;
        for (Player player : players) {
            player.addDraftSoldier(startSoldiers);
            player.setPlayerNumber(number);
            number++;
        }
    }

    public String draft(String countryDetails, int soldiers) {

        String toPrint = "";
        if (soldiers > currentPlayer.getNewSoldiers() || soldiers < 1) {
            toPrint = "Soldiers are not enough or invalid";
        } else {
            placeSoldier(countryDetails, soldiers);
            currentPlayer.addNewSoldiers(-soldiers);
        }

        return toPrint;
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
        boolean errorFound = false;
        if (source.getOwner() != null && source.getOwner().equals(currentPlayer)) {
            sourceCountryValid = true;
        }
        if (destination.getOwner() != null && !destination.getOwner().equals(currentPlayer)) {
            destinationCountryValid = true;
        }
        if (!sourceCountryValid && !errorFound) {
            toPrint = "Source country is not valid";
            errorFound = true;
        }
        if (sourceCountryValid && !destinationCountryValid && !errorFound) {
            toPrint = "Destination country is not valid";
            errorFound = true;
        }
        if (sourceCountryValid && destinationCountryValid && (soldiers > source.getSoldiers() || soldiers < 0) && !errorFound) {
            toPrint = "Soldiers are not enough or not valid";
            errorFound = true;
        }
        if(!draftDone && !errorFound){
            toPrint = "Draft didn't completed yet";
            errorFound = true;
        }
        if(errorFound){
            /*Do Nothing*/
        }
        else {
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
                if (source.getSoldiers() == 0 || destination.getSoldiers() == 0) {
                    inWar = false;
                    setAttackDone(true);
                }
            } while (inWar);


        }
        return toPrint;
    }

    public String fortify(String sourceCountry, String destinyCountry, int soldiers) {
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
        } else if (sourceCountryValid && !destinationCountryValid) {
            toPrint = "Destination country is not valid";
        } else if (sourceCountryValid && destinationCountryValid && (soldiers > (source.getSoldiers() - 1) || soldiers < 1)) {
            toPrint = "Soldiers are not enough or not valid";
        } else {
            turnDone = true;
            source.addSoldiers(-soldiers);
            destination.addSoldiers(soldiers);
            toPrint = "Move " + soldiers + " soldiers from " + sourceCountryName + " to " + destinationCountryName;
            setFortifyDone(true);
        }
        return toPrint;
    }

    /* Move Soldiers */
    public void move() {
    }

    /* Draf-Attck-forfeit */
    public void next() {
        if(draftDone){

        }else if(attackDone){

        }else if(fortifyDone){

        }
    }

    public void mainChangeTurn() {
        int currentTurnIndex = this.players.indexOf(this.currentPlayer);
        if (currentTurnIndex != this.players.size() - 1) {
            this.currentPlayer = this.players.get(currentTurnIndex + 1);
        } else {
            this.currentPlayer = this.players.get(0);
        }
    }

    public String changeTurn() {
        String toPrint = "";
        if (!getPlacementFinished()) {
            if (draftDone) {
                mainChangeTurn();
                toPrint = "Next Turn done successfully, It's " + currentPlayer.getUsername() + " turn";
                setDraftDone(false);
                setAttackDone(false);
                setFortifyDone(false);
            } else {
                toPrint = "You didn't place any soldier, please first try to place a soldier in remain countries.";
            }
        } else {
            if (draftDone) {
                /*Todo: attack doesn't need to be checked(?)*/
                if (attackDone) {
                    if (fortifyDone) {
                        turnDone = false;
                        mainChangeTurn();
                        toPrint = "Next Turn done successfully, It's " + currentPlayer.getUsername() + " turn";
                        setDraftDone(false);
                        setAttackDone(false);
                        setFortifyDone(false);
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
        if(soldiers > getCurrentPlayer().getDraftSoldiers()){
            toPrint = "You do not have enough soldiers";
            return toPrint;
        }
        int countryContinentNumber = Integer.parseInt(countryDetails2[1]);
        Country toCheckCountry = this.getCountryByDetails(countryContinent, countryContinentNumber);
        if (!this.getDraftDone()) {
            if (toCheckCountry.getName() == null) {
                toPrint = "Chosen country is invalid. Please try again";
            } else {
                if ( toCheckCountry.getOwner() == null || toCheckCountry.getOwner().equals(currentPlayer)) {
                    toCheckCountry.setOwner(currentPlayer);
                    toCheckCountry.addSoldiers(soldiers);
                    toPrint = currentPlayer.getPlayerNumber() + " add " + soldiers + " soldier to "
                            + toCheckCountry.getName();
                    this.setDraftDone(true);
                } else {
                    toPrint = "Please choose a country that is yours or no one has been chosen it yet";
                }
            }
        } else {
            toPrint = "You have been done your draft turn.";
        }
        if(getPlacementFinished() == false){
            boolean allDone = false;
            for (Player player : players) {
                if (player.getDraftSoldiers() != 3) {
                    allDone = false;
                    break;
                } else {
                    allDone = true;
                }
            }
            if (allDone == true) {
                setPlacementFinished(true);
            }
        }
        return toPrint;
    }

    public boolean getDraftDone() {
        return draftDone;
    }

    public void setDraftDone(boolean status) {
        draftDone = status;
    }

    public boolean getTurnDone() { return turnDone; }

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

    public void matchCardAddSoldiers(int soldiersNumber) {
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

    public String showTurn() {
        String toPrint = "It's " + currentPlayer.getUsername() + " Player";
        return toPrint;
    }

    public String getStatus() {
        String toPrint = "";
        if (!draftDone) {
            toPrint = "Please draft your soldiers in one of available countries";
        } else if (!attackDone) {
            toPrint = "Please attack to one of the valid countries";
        } else if (!fortifyDone) {
            toPrint = "if you want to move your soldiers, you can try now!";
        }
        return toPrint;
    }

    public void autoPlace() {
        do {
            boolean allDone = false;
            for (Player player : players) {
                if (player.getDraftSoldiers() != 3) {
                    allDone = false;
                    break;
                } else {
                    allDone = true;
                }
            }
            if (allDone == true) {
                break;
            }

            if (currentPlayer.getDraftSoldiers() == 3) {
                continue;
            }
            int rows = gameCountries.size() - 1;
            int columns = gameCountries.get(0).size() - 1;
            int randomRow = (int) (Math.random() * (rows - 0 + 1) + 0);
            int randomColumn = (int) (Math.random() * (columns + 1));
            Country getRandomCountry = gameCountries.get(randomRow).get(randomColumn);
            if (getRandomCountry.getOwner() == null || getRandomCountry.getOwner().equals(currentPlayer)) {
                getRandomCountry.setOwner(currentPlayer);
                getRandomCountry.addSoldiers(1);
                currentPlayer.addDraftSoldier(-1);
                mainChangeTurn();
            }
        } while (true);
        setPlacementFinished(true);
    }

    public void setPlacementFinished(boolean placementFinished) {
        this.placementFinished = placementFinished;
    }
}
