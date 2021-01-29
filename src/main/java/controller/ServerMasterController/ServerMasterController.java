package controller.ServerMasterController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.Command;
import model.Account;
import org.javatuples.Pair;

public class ServerMasterController {
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
