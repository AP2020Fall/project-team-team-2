package controller.ServerMasterController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.risk.MatchCardController;
import controller.risk.RiskGameController;
import controller.risk.StartGameController;
import javafx.scene.control.ProgressBar;
import main.Client;
import main.Command;
import model.*;
import org.javatuples.Pair;
import view.risk.RiskGameView;

import java.util.ArrayList;
import java.util.Map;

public class ServerMasterController {

    MatchCardController matchCardController;
    RiskGameController riskGameController;
    StartGameController startGameController;

    public ServerMasterController(){

    }

    public void createMatchCardController(Player player) {
        matchCardController = new MatchCardController(player);
    }

    public void createRiskGameController(Map<String, Object> primitiveSettings, int soldiers, Event event) {
        riskGameController = new RiskGameController(primitiveSettings, soldiers, event);
    }

    public void createStartGameController(ArrayList<Player> players, Event event) {
        startGameController = new StartGameController(players, event);
    }

    public Pair<String, String> takeAction(String input) {
        Command command = Command.fromJson(input);
        if (command.getCommand().equals("endConnection")) {
            return new Pair<>("Connection is terminated.", new Gson().toJson(command.getClientInfo()));
        }
        if (command.getDeclaringClass() == null)
            return new Pair<>("", new Gson().toJson(command.getClientInfo()));
        if (command.getMethod() == null)
            return new Pair<>("", new Gson().toJson(command.getClientInfo()));
        return new Pair<>(new Gson().toJson(command.invokeMethod()),
                new Gson().toJson(command.getClientInfo()));
    }

    public String test(String input) {
        if (input.equalsIgnoreCase("test")) {
            return "Test successfully completed.";
        } else
            return "Test was unsuccessful.";
    }




    //######################## MatchCardController Commands ########################\\
/*
    public void incPlayerSoldier(Player player, int soldierNumber) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        params.add(soldierNumber);
        Command command = new Command("incPlayerSoldier", "controller.risk.MatchCardController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }


    //######################## RiskGameController Commands ########################\\

    public void shapeMap() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("shapeMap", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void makeRobotPlayers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("makeRobotPlayers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Boolean getGameIsPlaying() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getGameIsPlaying", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public Boolean getFogStatus() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getFogStatus", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public int getRemainSoldiers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getRemainSoldiers", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int.class);
    }

    public void setStartSoldiers() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("setStartSoldiers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String draft(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("draft", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String beginDraft(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("beginDraft", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void checkAllPlayersAdded() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkAllPlayersAdded", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Boolean getAllPlayersAdded() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getAllPlayersAdded", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public String attack(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(sourceI);
        params.add(sourceJ);
        params.add(destI);
        params.add(destJ);
        params.add(soldiers);
        Command command = new Command("attack", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Boolean getSoldierPlaced() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getSoldierPlaced", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public void addDestinationCardsToSource() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addDestinationCardsToSource", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String fortify(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(sourceI);
        params.add(sourceJ);
        params.add(destI);
        params.add(destJ);
        params.add(soldiers);
        Command command = new Command("fortify", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String next() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("next", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void mainChangeTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("mainChangeTurn", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String changeTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("changeTurn", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void checkPlacementFinished() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkPlacementFinished", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Boolean getPlacementFinished() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getPlacementFinished", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Boolean.class);
    }

    public String matchCards(int type) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(type);
        Command command = new Command("matchCards", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String showMap() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showMap", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(currentPlayer);
        Command command = new Command("setCurrentPlayer", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlayers(ArrayList<Player> players) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(players);
        Command command = new Command("setPlayers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Player getCurrentPlayer() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCurrentPlayer", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Player.class);
    }

    public String placeSoldier(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("placeSoldier", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String leaveTheGame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("leaveTheGame", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void makeCountryEmpty(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("makeCountryEmpty", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean getDraftDone() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getDraftDone", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public void setDraftDone(boolean status) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        Command command = new Command("setDraftDone", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public boolean getTurnDone() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getTurnDone", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public void setAttackDone(boolean status) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        Command command = new Command("setAttackDone", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setFortifyDone(boolean status) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(status);
        Command command = new Command("setFortifyDone", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void matchCardAddSoldiers(int soldiersNumber) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(soldiersNumber);
        Command command = new Command("matchCardAddSoldiers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Country getCountryByDetails(int i, int j) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        Command command = new Command("getCountryByDetails", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Country.class);
    }

    public String showTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showTurn", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public Player getTurn() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getTurn", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Player.class);
    }

    public String getStatus() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getStatus", "controller.admin.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void autoPlace() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("autoPlace", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlacementFinished(boolean placementFinished) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(placementFinished);
        Command command = new Command("setPlacementFinished", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public int[][] getCountryByDetails(Player currentPlayer) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(currentPlayer);
        Command command = new Command("getCountryByDetails", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int[][].class);
    }


    public void changeNumberElement(int i, int j, int[][] inputArray, int number) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(inputArray);
        params.add(number);
        Command command = new Command("changeNumberElement", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setBlizzard() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("setBlizzard", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String nextPart() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("nextPart", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public boolean checkWinner() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkWinner", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public boolean checkAdditionalPlayers(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("checkAdditionalPlayers", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public boolean getAttackWon() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getAttackWon", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public int getCurrentPlayerIndex() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCurrentPlayerIndex", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int.class);
    }

    public String draftAfterWin(int i, int j, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        params.add(soldiers);
        Command command = new Command("draftAfterWin", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public boolean checkCountryIsYours(int i, int j) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(i);
        params.add(j);
        Command command = new Command("checkCountryIsYours", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public String addCard() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("addCard", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String showMatchOptions() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showMatchOptions", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String showWhatToDo() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("showWhatToDo", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public boolean getCheckRequests() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("getCheckRequests", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public String addRequest(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("addRequest", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void addFriend(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("addFriend", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void rejectRequest(Player player) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(player);
        Command command = new Command("rejectRequest", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public Player getPlayerByPlayerNumber(int number) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(number);
        Command command = new Command("getPlayerByPlayerNumber", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, Player.class);
    }

    public boolean checkCountryIsAlliance(Country destination) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(destination);
        Command command = new Command("checkCountryIsAlliance", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public boolean checkTime() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("checkTime", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, boolean.class);
    }

    public void updateCurrentTime() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("updateCurrentTime", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void getProgressBar(ProgressBar progressBar) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(progressBar);
        Command command = new Command("getProgressBar", "controller.risk.RiskGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }



    //######################## StartGameController Commands ########################\\

    public RiskGameView startGame() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("startGame", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, RiskGameView.class);
    }

    public String setMapNumber(int mapNumber) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(mapNumber);
        Command command = new Command("setMapNumber", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String setPlayerNumber(int playerNumber) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(playerNumber);
        Command command = new Command("setPlayerNumber", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public String setDurationTime(int number) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(number);
        Command command = new Command("setDurationTime", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setFogType(boolean type) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(type);
        Command command = new Command("setFogType", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public int generateSoldiersNumber(){
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("generateSoldiersNumber", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, int.class);
    }

    public void setAllianceType(boolean type) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(type);
        Command command = new Command("setAllianceType", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setBlizzardsType(boolean type) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(type);
        Command command = new Command("setBlizzardsType", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPlacementType(boolean type) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(type);
        Command command = new Command("setPlacementType", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public void setPrimitiveSettings(String index, Object value) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(index);
        params.add(value);
        Command command = new Command("setPrimitiveSettings", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }

    public String generateGameId() {
        ArrayList<Object> params = new ArrayList<>();
        Command command = new Command("generateGameId", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        String answer = Client.getConnector().serverQuery(command.toJson());
        return new Gson().fromJson(answer, String.class);
    }

    public void setMapSoldiers(Country country, int soldiers) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(country);
        params.add(soldiers);
        Command command = new Command("setMapSoldiers", "controller.risk.StartGameController"
                , params, Client.getClientInfo());
        Client.getConnector().serverQuery(command.toJson());
    }


*/

}
