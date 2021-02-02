package controller.ServerMasterController;

import com.google.gson.Gson;
import main.Command;
import main.Token;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class ServerMasterController {
    private static Token token;

    public static Token getCurrentToken() {
        return token;
    }

    public static void main(String[] args) {
        /*
        * The Socket we got from client
        * */
        String player_id = "12345";
        Map<String,Object> tempMap = new HashMap<>();
        tempMap.put("player_id" , player_id);
    }

    public static void logout() {
        token = new Token();
    }

    public Triplet<String, String, String> takeAction(String input) {
        Command command = Command.fromJson(input);
        token = Token.validate(command.getAuthToken(),command.getClientInfo());
        if (token.isTokenFalse()) {

            return new Triplet<>("Token is invalid", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        }
        if (command.getCommand().equals("endConnection")) {
            return new Triplet<>("Connection is terminated.", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        }
        if (command.getDeclaringClass() == null)
            return new Triplet<>("", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        if (command.getMethod() == null)
            return new Triplet<>("", new Gson().toJson(command.getClientInfo()),
                    token.encrypt());
        return new Triplet<>(new Gson().toJson(command.invokeMethod()), new Gson().toJson(command.getClientInfo()),
                token.encrypt());
    }

    public String test(String input) {
        if (input.equalsIgnoreCase("test")) {
            return "Test successfully completed.";
        } else
            return "Test was unsuccessful.";
    }

}
