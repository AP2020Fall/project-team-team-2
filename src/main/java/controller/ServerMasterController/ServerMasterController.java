package controller.ServerMasterController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.risk.MatchCardController;
import controller.risk.RiskGameController;
import controller.risk.StartGameController;
import main.Command;
import model.Account;
import model.RiskGame;
import org.javatuples.Pair;

public class ServerMasterController {

    MatchCardController matchCardController;
    RiskGameController riskGameController;
    StartGameController startGameController;

    public ServerMasterController(){

        riskGameController = new RiskGameController();
        startGameController = new StartGameController();
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
