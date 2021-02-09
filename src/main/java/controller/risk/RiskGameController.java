package controller.risk;

import com.google.gson.Gson;
import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Client;
import main.ClientInfo;
import model.*;
import org.controlsfx.control.Notifications;
import view.risk.RiskGameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RiskGameController extends Controller {


    //ArrayList<Gamer> originalPlayers;
    private final RiskGame riskGameModel;

    public ArrayList<Gamer> getPlayers() {
        return riskGameModel.getPlayers();
    }

    public RiskGameController(ClientInfo clientInfo) {
        super(Client.getClientInfo());
        this.riskGameModel =RiskGame.getRiskGameById(clientInfo.getAvailableGameId());
        if(riskGameModel == null)
            System.err.println("Risk Game passed to RiskGameController is null");
        else {
            //originalPlayers = new ArrayList<>(riskGameModel.getPlayers());
        }
    }


    /*public void makeRobotPlayers() {
        String mainBotName = "Robot";
        for (int i = 1; i < (int) primitiveSettings.get("PlayersNum"); i++) {
            Player newRobotPlayer = new Player(mainBotName + " " + i, mainBotName + " " + i);
            players.add(newRobotPlayer);
        }
    }*/

    public boolean getGameIsPlaying() {
        return riskGameModel.getGameIsPlaying();
    }

    public boolean getFogStatus() {
        return riskGameModel.getFogIsSet();
    }

    public int getRemainSoldiers() {
        return riskGameModel.getCurrentPlayer().getNewSoldiers();
    }

    public void setStartSoldiers() {
        int number = 1;
        for (Gamer player : riskGameModel.getPlayers()) {
            player.addDraftSoldier(riskGameModel.getStartSoldiers());
            player.setPlayerNumber(number);
            number++;
        }
    }

    public String draft(Double i, Double j, Double soldiers) {
        int i_1 = (int) Math.round(i);
        int j_1 = (int) Math.round(j);
        int soldiers_1 = (int) Math.round(soldiers);
        String toPrint = "";

        if (!riskGameModel.getDraftDone()) {
            Country destination = getCountryByDetails(i_1, j_1);
            if (soldiers > riskGameModel.getCurrentPlayer().getDraftSoldiers() || soldiers_1 < 1) {
                toPrint = "Soldiers are not enough or invalid";
            } else if (destination.getName().equals("") || destination.getBlizzard()) {
                toPrint = "Destination country is not valid";
            } else {
                if (!destination.getOwner().equals(getCurrentPlayer())) {
                    toPrint = "This country is not yours";
                } else {
                    placeSoldier(i_1, j_1, soldiers_1);
                    riskGameModel.getCurrentPlayer().addDraftSoldier(-1 * soldiers_1);
                    toPrint = "" + soldiers + " soldiers added to " + destination.getName() + " successfully";
                    riskGameModel.setDraftDone(true);
                }
            }
        } else {
            toPrint = "Draft has been done";
        }
        return toPrint;
    }

    public String beginDraft(String iS, String jS, String soldiersS) {
        int i = new Gson().fromJson(iS,Integer.class);
        int j = new Gson().fromJson(jS,Integer.class);
        int soldiers = new Gson().fromJson(soldiersS,Integer.class);

        String toPrint = "";
        if (!riskGameModel.getBeginDraftDone()) {
            Country destination = getCountryByDetails(i, j);
            if (soldiers > riskGameModel.getCurrentPlayer().getDraftSoldiers() || soldiers < 1) {
                toPrint = "Soldiers are not enough or invalid";
            } else if (destination.getName().equals("") || destination.getBlizzard()) {
                toPrint = "Destination country is not valid";
            } else {
                if (destination.getOwner() != null) {
                    if (!destination.getOwner().equals(getCurrentPlayer())) {
                        toPrint = "This country is not yours";
                    } else {
                        placeSoldier(i, j, soldiers);
                        riskGameModel.getCurrentPlayer().addDraftSoldier(-1 * soldiers);
                        toPrint = "" + soldiers + " soldiers added to " + destination.getName() + " successfully";
                        riskGameModel.setBeginDraftDone(true);
                    }
                } else {
                    placeSoldier(i, j, soldiers);
                    riskGameModel.getCurrentPlayer().addDraftSoldier(-1 * soldiers);
                    toPrint = "" + soldiers + " soldiers added to " + destination.getName() + " successfully";
                    riskGameModel.setBeginDraftDone(true);
                }
            }
        } else {
            toPrint = "Draft has been done";
        }
        checkAllPlayersAdded();
        return toPrint;
    }

    public void checkAllPlayersAdded() {
        boolean toCheck = true;
        outerLoop:
        for (List<Country> countries : riskGameModel.getGameCountries()) {
            for (Country country : countries) {
                if (country.getSoldiers() == 0) {
                    toCheck = false;
                    break outerLoop;
                }
            }
        }
        riskGameModel.setAllPlayersAddedSoldier(toCheck);
    }

    public boolean getAllPlayersAdded() {
        return riskGameModel.getAllPlayersAddedSoldier();
    }

    public String attack(Double sourceI_1, Double sourceJ_1, Double destI_1, Double destJ_1, Double soldiers) {
        int sourceI = (int) Math.round(sourceI_1);
        int sourceJ = (int) Math.round(sourceJ_1);
        int destI = (int) Math.round(destI_1);
        int destJ = (int) Math.round(destJ_1);
        int soldiers_1 = (int) Math.round(soldiers);
        String toPrint = "";
        boolean isFriend = false;

        if (!riskGameModel.getAttackDone()) {

            boolean sourceCountryValid = false;
            boolean destinationCountryValid = false;
            Country source = getCountryByDetails(sourceI, sourceJ);
            Country destination = getCountryByDetails(destI, destJ);
            isFriend = checkCountryIsAlliance(destination);
            boolean errorFound = false;
            if (!source.getName().equals("")) {
                if (source.getOwner().equals(riskGameModel.getCurrentPlayer())) {
                    sourceCountryValid = true;
                }
            }
            if (!destination.getName().equals("")) {
                if (destination.getOwner() != null) {
                    if (!destination.getOwner().equals(riskGameModel.getCurrentPlayer())) {
                        destinationCountryValid = true;
                    }
                } else {
                    destination.setOwner(riskGameModel.getCurrentPlayer());
                    riskGameModel.setAttackWon(true);
                    riskGameModel.setAttackDestination(destination);
                    toPrint += "\nYou now should add one to " + (source.getSoldiers() - 1) + " soldiers to "
                            + destination.getName();
                    riskGameModel.setSourceCountryWinner(source);
                }
            }
            if (!sourceCountryValid) {
                toPrint = "Source country is not valid";
                errorFound = true;
            }
            if (sourceCountryValid && (!destinationCountryValid || destination.getBlizzard())) {
                toPrint = "Destination country is not valid";
                errorFound = true;
            }
            if (sourceCountryValid && destinationCountryValid && (soldiers > source.getSoldiers() || soldiers < 0
                    || source.getSoldiers() <= 1) && !errorFound) {
                toPrint = "Soldiers are not enough or not valid";
                errorFound = true;
            }
            if (!riskGameModel.getDraftDone() && !errorFound) {
                toPrint = "Draft didn't completed yet";
                errorFound = true;
            }
            if (isFriend && !errorFound) {
                toPrint = "This Country is Alliance";
                errorFound = true;
            }
            if (errorFound) {

            }
            else if (attackNeighbourhoodCheck(source, destination)) {
                boolean inWar = true;
                /*ToDo*/
//                audioClip.play();
                String result = "";
                do {
                    int randomNumberSource = (int) (Math.random() * (6 - 0 + 1)) + 0;
                    int randomNumberDestination = (int) (Math.random() * (6 - 0 + 1)) + 0;
                    toPrint = "Source Dice : " + randomNumberSource + " - Destination Dice : " + randomNumberDestination;
                    if (randomNumberSource > randomNumberDestination) {
                        destination.addSoldiers(-1);
                        toPrint = toPrint + " Destination Country Lost 1 soldier! , Destination Soldiers "
                                + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                    } else if (randomNumberDestination > randomNumberSource) {
                        source.addSoldiers(-1);
                        soldiers--;
                        toPrint = toPrint + " Source Country Lost 1 soldier! , Destination Soldiers "
                                + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                    } else {
                        source.addSoldiers(-1);
                        soldiers--;
                        toPrint = toPrint + " Source Country Lost 1 soldier! , Destination Soldiers "
                                + destination.getSoldiers() + " - Source Soldiers " + source.getSoldiers();
                    }
                    if (soldiers == 0 || destination.getSoldiers() == 0 || source.getSoldiers() == 1) {
                        inWar = false;
                        if (source.getSoldiers() == 1 || soldiers == 0) {
                            toPrint = "attack failed";
                            result = "Failed";
                        } else {
                            toPrint = "attack was successful";
                            result = "Successful";
                            if (!riskGameModel.getGotCards()) {
                                toPrint += "\nYou Got new Card!\n : Card " + addCard() + " \nhas been added to you";
                                riskGameModel.setGotCards(true);
                            }
                            if (source.getSoldiers() == 2) {
                                source.addSoldiers(-1);
                                destination.addSoldiers(+1);
                                Gamer tempPlayer = destination.getOwner();
                                destination.setOwner(riskGameModel.getCurrentPlayer());
                                boolean playerDone = checkAdditionalPlayers(tempPlayer);
                                if (playerDone) {
                                    addDestinationCardsToSource(source.getOwner(), tempPlayer);
                                }

                            } else {
                                Gamer tempPlayer = destination.getOwner();
                                destination.setOwner(riskGameModel.getCurrentPlayer());
                                boolean playerDone = checkAdditionalPlayers(tempPlayer);
                                if (playerDone) {
                                    addDestinationCardsToSource(source.getOwner(), tempPlayer);
                                }
                                riskGameModel.setSoldierPlacedAfterWin(false);
                                riskGameModel.setAttackWon(true);

                                riskGameModel.setAttackDestination(destination);
                                toPrint += "\nYou now should add one to \n" + (source.getSoldiers() - 1) + " soldiers to \n"
                                        + destination.getName();
                                riskGameModel.setSourceCountryWinner(source);
                                riskGameModel.setDraftDone(false);
                            }
                        }
                        attackAnimation(result);
                        riskGameModel.setI(null);
                        riskGameModel.setJ(null);
                        deselect();
                    }
                } while (inWar);
            } else {
                toPrint = "there is not any path between source and destination country";
            }
        } else {
            toPrint = "Attack has been done";
        }
        riskGameModel.updateUI();
        return toPrint;
    }

  /*  public boolean getSoldierPlaced() {
        return riskGameModel.getsoldier();
    }*/

    private void addDestinationCardsToSource(Gamer sourcePlayer, Gamer destinationPlayer) {
        for (Card card : destinationPlayer.getCards()) {
            sourcePlayer.addCard(card);

        }
    }



    public String fortify(int sourceI_1, int sourceJ_1, int destI_1, int destJ_1, int soldiers_1) {
        int sourceI = (int) Math.round(sourceI_1);

        int sourceJ = (int) Math.round(sourceJ_1);
        int destI = (int) Math.round(destI_1);
        int destJ = (int) Math.round(destJ_1);
        int soldiers = (int) Math.round(soldiers_1);

        String toPrint = "";
        if (!riskGameModel.getFortifyDone()) {
            boolean sourceCountryValid = false;
            boolean destinationCountryValid = false;
            Country source = getCountryByDetails(sourceI, sourceJ);
            Country destination = getCountryByDetails(destI, destJ);
            if (source.getOwner() != null && source.getOwner().equals(riskGameModel.getCurrentPlayer())) {
                sourceCountryValid = true;
            }
            if (destination.getOwner() != null && destination.getOwner().equals(riskGameModel.getCurrentPlayer())) {
                destinationCountryValid = true;
            }
            if (!sourceCountryValid) {
                toPrint = "Source country is not valid";
            } else if (sourceCountryValid && (!destinationCountryValid || destination.getBlizzard())) {
                toPrint = "Destination country is not valid";
            } else if (sourceCountryValid && destinationCountryValid && (soldiers > (source.getSoldiers() - 1) || soldiers < 1)) {
                toPrint = "Soldiers are not enough or not valid";
            } else if (fortifyNeighbourhoodCheck(source, destination)) {
                riskGameModel.setTurnDone(true);
                source.addSoldiers(-1 * soldiers);
                destination.addSoldiers(soldiers);
                toPrint = "Move " + soldiers + " soldiers from " + source.getName() + " to " + destination.getName();
                setFortifyDone(true);
                riskGameModel.setI(null);
                riskGameModel.setJ(null);
            } else {
                toPrint = "there is not any path between source and destination country";
            }
        } else {
            toPrint = "Fortify has been done";
        }
        riskGameModel.updateUI();
        return toPrint;
    }

    /* Draf-Attck-forfeit */
    public String next() {
        return riskGameModel.next();
    }

    public void mainChangeTurn() {
        updateCurrentTime();
        riskGameModel.mainChangeTurn();
    }

    public String changeTurn() {
        return riskGameModel.changeTurn();
    }

    public void checkPlacementFinished() {
        boolean toCheck = true;
        for (Gamer player : getPlayers()) {
            if (player.getDraftSoldiers() != 0) {
                toCheck = false;
                break;
            }
        }
        if (toCheck) {
            riskGameModel.setPlacementFinished(true);
        }
    }

    public java.util.Map<String, Object> getPrimitiveSettings() {
        return riskGameModel.getPrimitiveSettings();
    }

    public boolean getPlacementFinished() {
        return riskGameModel.getPlacementFinished();
    }

    public String matchCards(int type) {
        String toPrint = "";
        switch (type) {
            case 1:
                toPrint = Card.matchCard(riskGameModel.getCurrentPlayer().getCards(), 1, riskGameModel.getCurrentPlayer());
                break;
            case 2:
                toPrint = Card.matchCard(riskGameModel.getCurrentPlayer().getCards(), 2, riskGameModel.getCurrentPlayer());
                break;
            case 3:
                toPrint = Card.matchCard(riskGameModel.getCurrentPlayer().getCards(), 3, riskGameModel.getCurrentPlayer());
                break;
            case 4:
                toPrint = Card.matchCard(riskGameModel.getCurrentPlayer().getCards(), 4, riskGameModel.getCurrentPlayer());
                break;
        }
        return toPrint;
    }

    public String showMap() {
        StringBuilder lineString = new StringBuilder();
        for (List<Country> listCountries : riskGameModel.getGameCountries()) {
            for (Country country : listCountries) {
                if (!country.equals(listCountries.get(listCountries.size() - 1))) {
                    lineString.append(country.toString()).append(" | ");
                } else {
                    lineString.append(country.toString()).append("\n");
                }
            }
        }
        return lineString.toString().trim();
    }

    public void setCurrentPlayer(Gamer player) {
        this.riskGameModel.setCurrentPlayer(player);
    }


    public Gamer getCurrentPlayer() {
        return riskGameModel.getCurrentPlayer();
    }

    public String placeSoldier(int i, int j, int soldiers) {
        String toPrint = "";
        if (soldiers > getCurrentPlayer().getDraftSoldiers()) {
            toPrint = "You do not have enough soldiers";
            return toPrint;
        }
        Country toCheckCountry = this.getCountryByDetails(i, j);
        if (!this.getDraftDone()) {
            if (toCheckCountry.getName().equals("") || toCheckCountry.getBlizzard()) {
                toPrint = "Chosen country is invalid. Please try again";
            } else {
                if (toCheckCountry.getOwner() == null || toCheckCountry.getOwner().equals(riskGameModel.getCurrentPlayer())) {
                    toCheckCountry.setOwner(riskGameModel.getCurrentPlayer());
                    toCheckCountry.addSoldiers(soldiers);
                    toPrint = riskGameModel.getCurrentPlayer().getPlayerNumber() + " add " + soldiers + " soldier to "
                            + toCheckCountry.getName();
                } else {
                    toPrint = "Please choose a country that is yours or no one has been chosen it yet";
                }
            }
        } else {
            toPrint = "You have been done your draft turn.";
        }
        if (getPlacementFinished() == false) {
            boolean allDone = false;
            for (Gamer player : riskGameModel.getPlayers()) {
                if (player.getDraftSoldiers() != 3) {
                    allDone = false;
                    break;
                } else {
                    allDone = true;
                }
            }
            if (allDone == true) {
                setPlacementFinished(true);
                toPrint += "\nGame has been started";
                mainChangeTurn();
                toPrint += "\nit's player " + riskGameModel.getCurrentPlayer().getUsername() + " turn to draft";
                riskGameModel.setDraftDone(false);
            }
        }
        riskGameModel.updateUI();
        return toPrint;
    }

    public String leaveTheGame() {
        return riskGameModel.leaveTheGame();
    }



    public boolean getDraftDone() {
        return riskGameModel.getDraftDone();
    }

    public void setDraftDone(boolean status) {
        riskGameModel.setDraftDone(status);
    }

    public boolean getTurnDone() {
        return riskGameModel.getTurnDone();
    }

    public void setAttackDone(boolean status) {
        riskGameModel.setAttackDone(status);
    }

    public void setFortifyDone(boolean status) {
        this.riskGameModel.setFortifyDone(status);
    }

    public void matchCardAddSoldiers(int soldiersNumber) {
        riskGameModel.getMatchCardController().incPlayerSoldier(riskGameModel.getCurrentPlayer(), soldiersNumber);
    }

    public Country getCountryByDetails(int i, int j) {
        return riskGameModel.getGameCountries().get(i - 1).get(j - 1);
    }

    public String showTurn() {
        String toPrint = "It's " + riskGameModel.getCurrentPlayer().getUsername() + " Player";
        return toPrint;
    }

    public Gamer getTurn() {
        return riskGameModel.getCurrentPlayer();
    }

    public String getStatus() {
        String toPrint = "";
        if (!riskGameModel.getDraftDone()) {
            toPrint = "Please draft your soldiers in one of available countries";
        } else if (!riskGameModel.getAttackDone()) {
            toPrint = "Please attack to one of the valid countries";
        } else if (!riskGameModel.getFortifyDone()) {
            toPrint = "if you want to move your soldiers, you can try now!";
        }
        return toPrint;
    }
    public void autoPlace() {
        do {
            boolean allDone = false;
            for (Gamer player : riskGameModel.getPlayers()) {
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

            if (riskGameModel.getCurrentPlayer().getDraftSoldiers() == 3) {
                continue;
            }
            int rows = riskGameModel.getGameCountries().size() - 1;
            int columns = riskGameModel.getGameCountries().get(0).size() - 1;
            int randomRow = (int) (Math.random() * (rows - 0 + 1) + 0);
            int randomColumn = (int) (Math.random() * (columns + 1));
            Country getRandomCountry = riskGameModel.getGameCountries().get(randomRow).get(randomColumn);
            if ((getRandomCountry.getOwner() == null || getRandomCountry.getOwner().equals(riskGameModel.getCurrentPlayer())) && !getRandomCountry.getBlizzard()) {
                getRandomCountry.setOwner(riskGameModel.getCurrentPlayer());
                getRandomCountry.addSoldiers(1);
                riskGameModel.getCurrentPlayer().addDraftSoldier(-1);
                mainChangeTurn();
            }
        } while (true);
        setPlacementFinished(true);
    }

    public void setPlacementFinished(boolean placementFinished) {
        riskGameModel.setPlacementFinished(placementFinished);
    }

    public int[][] getFogOfWarMap(String JSONPlayer) {
        Gamer currentPlayer = new Gson().fromJson(JSONPlayer,Gamer.class);
        int row = riskGameModel.getGameCountries().size();
        int column = riskGameModel.getGameCountries().get(0).size();
        int[][] countryNumbers = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                countryNumbers[i][j] = 0;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (riskGameModel.getGameCountries().get(i).get(j).getOwner() != null) {
                    if (riskGameModel.getGameCountries().get(i).get(j).getOwner().getUsername().equals(currentPlayer.getUsername())
                            || riskGameModel.getGameCountries().get(i).get(j).getOwner().getGameFriends().contains(currentPlayer)
                    ) {
                        countryNumbers[i][j] = 1;
                        changeNumberElement(i - 1, j - 1, countryNumbers, 2);
                        changeNumberElement(i - 1, j, countryNumbers, 2);
                        changeNumberElement(i - 1, j + 1, countryNumbers, 2);
                        changeNumberElement(i, j - 1, countryNumbers, 2);
                        changeNumberElement(i, j + 1, countryNumbers, 2);
                        changeNumberElement(i + 1, j - 1, countryNumbers, 2);
                        changeNumberElement(i + 1, j, countryNumbers, 2);
                        changeNumberElement(i + 1, j + 1, countryNumbers, 2);
                    }
                }
            }
        }

        return countryNumbers;
    }

    public void changeNumberElement(int i, int j, int[][] inputArray, int number) {
        try {
            if (inputArray[i][j] != 1) {
                inputArray[i][j] = number;
            }
        } catch (Exception ignored) {
        }
    }

    /*
        int row = riskGameModel.getGameCountries().size();
        int column = riskGameModel.getGameCountries().get(0).size();
        int[][] countryNumbers = new int[row][column];

        countryNumbers = setFogOfWarMap(riskGameModel.getCurrentPlayer());
    */

    public boolean isPath(int[][] CountryNumbers, int n, int m) {
        // Defining visited array to keep
        // track of already visited indexes
        boolean[][] visited
                = new boolean[n][m];

        // Flag to indicate whether the
        // path exists or not
        boolean flag = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // if matrix[i][j] is source
                // and it is not visited
                if (CountryNumbers[i][j] == 1 && !visited[i][j])
                    // Starting from i, j and
                    // then finding the path
                    if (isPath(CountryNumbers, i, j, visited)) {
                        // if path exists
                        flag = true;
                        break;
                    }
            }
        }
        return flag;
    }

    // Method for checking boundaries
    public boolean isSafe(int i, int j, int[][] CountryNumbers) {

        return i >= 0 && i < CountryNumbers.length && j >= 0 && j < CountryNumbers[0].length;
    }

    // Returns true if there is a
    // path from a source (a
    // cell with value 1) to a
    // destination (a cell with
    // value 2)
    public boolean isPath(int[][] CountryNumbers, int i, int j, boolean[][] visited) {

        // Checking the boundries, walls and
        // whether the cell is unvisited
        if (isSafe(i, j, CountryNumbers) && CountryNumbers[i][j] != 0 && !visited[i][j]) {
            // Make the cell visited
            visited[i][j] = true;

            // if the cell is the required
            // destination then return true
            if (CountryNumbers[i][j] == 2)
                return true;

            // traverse up
            boolean up = isPath(CountryNumbers, i - 1, j, visited);

            // if path is found in up
            // direction return true
            if (up)
                return true;

            // traverse up
            boolean upRight = isPath(CountryNumbers, i - 1, j + 1, visited);

            // if path is found in up
            // direction return true
            if (upRight)
                return true;

            // traverse up
            boolean upLeft = isPath(CountryNumbers, i - 1, j - 1, visited);

            // if path is found in up
            // direction return true
            if (upLeft)
                return true;

            // traverse left
            boolean left = isPath(CountryNumbers, i, j - 1, visited);

            // if path is found in left
            // direction return true
            if (left)
                return true;

            // traverse right
            boolean right = isPath(CountryNumbers, i, j + 1, visited);

            // if path is found in right
            // direction return true
            if (right)
                return true;

            // traverse down
            boolean down = isPath(CountryNumbers, i + 1, j, visited);

            // if path is found in down
            // direction return true
            if (down)
                return true;

            // traverse down
            boolean downRight = isPath(CountryNumbers, i + 1, j + 1, visited);

            // if path is found in down
            // direction return true
            if (downRight)
                return true;

            // traverse down
            boolean downLeft = isPath(CountryNumbers, i + 1, j - 1, visited);

            // if path is found in down
            // direction return true
            if (downLeft)
                return true;

        }
        // no path has been found
        return false;
    }

    public int[][] attackMakeCountryNumbers(Country sourceCountry, Country destinationCountry) {
        int row = riskGameModel.getGameCountries().size();
        int column = riskGameModel.getGameCountries().get(0).size();
        int[][] countryNumbers = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (riskGameModel.getGameCountries().get(i).get(j).getName().equals(sourceCountry.getName())) {
                    countryNumbers[i][j] = 1;
                } else if (riskGameModel.getGameCountries().get(i).get(j).getName().equals(destinationCountry.getName())) {
                    countryNumbers[i][j] = 2;
                } else {
                    countryNumbers[i][j] = 0;
                }
            }
        }

        return countryNumbers;
    }

    public int[][] fortifyMakeCountryNumbers(Country sourceCountry, Country destinationCountry) {
        int row = riskGameModel.getGameCountries().size();
        int column = riskGameModel.getGameCountries().get(0).size();
        int[][] countryNumbers = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (!riskGameModel.getGameCountries().get(i).get(j).getBlizzard()) {
                    if (riskGameModel.getGameCountries().get(i).get(j).getOwner() != null) {
                        if (riskGameModel.getGameCountries().get(i).get(j).getName().equals(sourceCountry.getName())) {
                            countryNumbers[i][j] = 1;
                        } else if (riskGameModel.getGameCountries().get(i).get(j).getName().equals(destinationCountry.getName())) {
                            countryNumbers[i][j] = 2;
                        } else if (riskGameModel.getGameCountries().get(i).get(j).getOwner().getUsername().equals(sourceCountry.getOwner().getUsername())) {
                            countryNumbers[i][j] = 3;
                        } else {
                            countryNumbers[i][j] = 0;
                        }
                    }
                } else {
                    countryNumbers[i][j] = 0;
                }
            }
        }

        return countryNumbers;
    }

    public boolean attackNeighbourhoodCheck(Country sourceCountry, Country destinationCountry) {
        int row = riskGameModel.getGameCountries().size();
        int column = riskGameModel.getGameCountries().get(0).size();
        int[][] countryNumbers = attackMakeCountryNumbers(sourceCountry, destinationCountry);

        return isPath(countryNumbers, row, column);
    }

    public boolean fortifyNeighbourhoodCheck(Country sourceCountry, Country destinationCountry) {
        int row = riskGameModel.getGameCountries().size();
        int column = riskGameModel.getGameCountries().get(0).size();
        int[][] countryNumbers = fortifyMakeCountryNumbers(sourceCountry, destinationCountry);

        return isPath(countryNumbers, row, column);
    }

    public String nextPart() {
        String toPrint = "";
        if (getPlacementFinished()) {
            if (riskGameModel.getDraftDone() && !riskGameModel.getAttackDone() && !riskGameModel.getFortifyDone()) {
                toPrint = "Draft done, Start Attacking";
                if (riskGameModel.getAttackDone() && !riskGameModel.getFortifyDone()) {
                    toPrint = "Attack done, Start fortifying";
                    if (riskGameModel.getFortifyDone()) {
                        toPrint = "Fortify done, try turn over";
                    } else {
                        toPrint = "Please first try to fortify , then use `turn over`";
                    }
                } else {
                    toPrint = "Please first Attack to a country then try next";
                }
            } else {

            }
        } else {
            if (riskGameModel.getDraftDone()) {
                toPrint = "wanna change your turn? you should use 'turn over'";
            } else {
                toPrint = "You should draft your ";
            }
        }
        return toPrint;
    }

    public boolean checkWinner() {
        return riskGameModel.checkWinner();
    }

    public boolean checkAdditionalPlayers(Gamer player) {
        boolean toCheck = true;
        outerLoop:
        for (List<Country> countries : riskGameModel.getGameCountries()) {
            for (Country country : countries) {
                if (country.getOwner() != null) {
                    if (country.getOwner().equals(player)) {
                        toCheck = false;
                        break outerLoop;
                    }
                }
            }
        }
        if (toCheck) {
            riskGameModel.getPlayers().remove(player);
        }
        return toCheck;
    }

    public boolean getAttackWon() {

        return riskGameModel.getAttackWon();
    }

    public int getCurrentPlayerIndex() {
        return riskGameModel.getCurrentPlayer().getPlayerNumber();
    }

    public String draftAfterWin(Double i_1, Double j_1, Double soldiers_1) {
        int i = (int) Math.round(i_1);
        int j = (int) Math.round(j_1);
        int soldiers = (int) Math.round(soldiers_1);
        String toPrint = "";
        Country destination = getCountryByDetails(i, j);

        if (soldiers > riskGameModel.getSourceCountryWinner().getSoldiers() - 1 || soldiers < 1) {

            toPrint = "Soldiers are not enough or invalid, Please try between one and "
                    + (riskGameModel.getSourceCountryWinner().getSoldiers() - 1);
        } else if (destination.getName().equals("")) {
            toPrint = "Destination country is not valid";
        } else {
            if (!destination.getOwner().equals(getCurrentPlayer())) {
                toPrint = "This country is not yours";
            } else {
                if (destination.equals(riskGameModel.getAttackDestination())) {
                    destination.addSoldiers(soldiers);
                    riskGameModel.getSourceCountryWinner().addSoldiers(-1 * soldiers);
                    toPrint = "" + soldiers + " soldiers added to " + destination.getName() + " successfully";
                    riskGameModel.setAttackWon(false);
                    riskGameModel.setAttackDestination(null);
                    riskGameModel.setSourceCountryWinner(null);
                    riskGameModel.setDraftDone(true);
                    riskGameModel.setSoldierPlacedAfterWin(true);
                } else {
                    toPrint = "Please try the previous destination country, not others";
                }
            }
        }
        return toPrint;
    }

    public boolean checkCountryIsYours(Double i_1, Double j_1) {
        int i = (int) Math.round(i_1);
        int j = (int) Math.round(j_1);
        if (riskGameModel.getCurrentPlayer().equals(riskGameModel.getGameCountries().get(i - 1).get(j - 1).getOwner())) {
            return true;
        } else {
            return false;
        }
    }

    public String addCard() {
        int rnd = new Random().nextInt(Card.values().length);
        Card toGetCard = Card.values()[rnd];
        riskGameModel.getCurrentPlayer().addCard(toGetCard);
        return toGetCard.name();
    }

    public String showMatchOptions() {
        String toPrint = "1-type1,type1,type1 score:4" + "\n" +
                "2-type2,type2,type2 score:6" + "\n" +
                "3-type3,type3,type3 score:8" + "\n" +
                "4-type1,type2,type3 score:10" + "\n";
        return toPrint;
    }

    public String showWhatToDo() {
        String toPrint = "";
        if (!riskGameModel.getDraftDone()) {
            toPrint += "Draft";
        } else if (!riskGameModel.getAttackDone()) {
            toPrint += "Attack";
        } else if (!riskGameModel.getFortifyDone()) {
            toPrint += "Fortify";
        }
        return toPrint;
    }

    public Integer getI() {
        return riskGameModel.getI();
    }

    public void setI(Double i_1) {
        int i = (int) Math.round(i_1);
        riskGameModel.setI(i);
    }

    public Integer getJ() {
        return riskGameModel.getJ();
    }

    public void setJ(Double j_1) {
        int j = (int) Math.round(j_1);
        riskGameModel.setJ(j);
    }

    public void deselect() {
        riskGameModel.setI(null);
        riskGameModel.setJ(null);
    }

    public boolean getCheckRequests() {
        return riskGameModel.getCurrentPlayer().checkPlayerHasRequest() && (!riskGameModel.getDraftDone() || !riskGameModel.getBeginDraftDone());
    }

    public String addRequest(Gamer player) {
        if (!player.getRequests().contains(riskGameModel.getCurrentPlayer())) {
            player.addGameRequest(riskGameModel.getCurrentPlayer());
            return "Request sent successfully";
        } else {
            return "You have been requested to this player";
        }
    }

    public void addFriend(Gamer player) {
        player.addGameFriend(player);
    }

    public void rejectRequest(Gamer player) {
        player.rejectRequest(player);
    }

    public Gamer getPlayerByPlayerNumber(int number) {
        for (Gamer player : riskGameModel.getPlayers()) {
            if (player.getPlayerNumber() == number) {
                return player;
            }
        }
        return null;
    }

    public void notifSent() {
        riskGameModel.setNotifSent(true);
    }

    public void resetNotif() {
        riskGameModel.setNotifSent(false);
    }

    public boolean getNotifSent() {
        return riskGameModel.getNotifSent();
    }

    public boolean checkCountryIsAlliance(Country destination) {
        if (destination.getOwner() != null) {
            if (riskGameModel.getCurrentPlayer().getGameFriends().contains(destination.getOwner())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkTime() {
        if (System.currentTimeMillis() / 1000L - riskGameModel.getCurrentTimeStamp() > riskGameModel.getDuration()) {
            mainChangeTurn();
            riskGameModel.setCurrentTimeStamp(System.currentTimeMillis() / 1000L);
            return false;
        } else {
            return true;
        }
    }

    public void updateCurrentTime() {
        riskGameModel.setCurrentTimeStamp(System.currentTimeMillis() / 1000L);

    }
    public void getProgressBar(ProgressBar progressBar) {
        /*Todo*/
//        this.progressBar = progressBar;
//        timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                double progressed = Double.valueOf(System.currentTimeMillis() / 1000L - currentTimeStamp) / Double.valueOf(duration);
//                progressBar.setProgress(progressed);
//                if (progressed >= 1) {
//                    mainChangeTurn();
//                    progressBar.setProgress(0);
//                }
//            }
//        };
//        timer.start();
    }

    public void attackAnimation(String result) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Notifications notify = null;
                Image img = new Image(String.valueOf(getClass().getResource("/images/attack.png")));

                notify = Notifications.create().title("Attack!")
                        .graphic(new ImageView(img))
                        .text(result)
                        .hideAfter(javafx.util.Duration.seconds(2))
                        .position(Pos.TOP_CENTER);
                notify.darkStyle();
                notify.showInformation();
                notify.graphic(new ImageView(img));
            }
        });
    }

    public List<List<Country>> getGameCountries()
    {
        return riskGameModel.getGameCountries();
    }

    public long getCurrentTimestamp(){
        return riskGameModel.getCurrentTimeStamp();
    }
    public void updateRiskGameModel(){
        riskGameModel.updateUI();
    }

}
