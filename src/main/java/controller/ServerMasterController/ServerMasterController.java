package controller.ServerMasterController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.Command;
import main.Token;
import model.Account;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.nio.channels.Pipe;

public class ServerMasterController {
    private static Token token;
    public static Token getCurrentToken() {
        return token;
    }

    public Triplet<String, String,String> takeAction(String input) {
        Command command = Command.fromJson(input);
         token = Token.decrypt(command.getAuthToken());
        if(!token.validate(command.getClientInfo()))
        {
            return new Triplet<>("Token is invalid",new Gson().toJson(command.getClientInfo()),
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
