package controller.ServerMasterController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.risk.MatchCardController;
import controller.risk.RiskGameController;
import controller.risk.StartGameController;
import main.Command;
import model.Account;
import model.Event;
import model.Player;
import model.RiskGame;
import org.javatuples.Pair;

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







}
