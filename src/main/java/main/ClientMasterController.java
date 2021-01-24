package main;

import com.google.gson.Gson;
import controller.Controller;

import java.util.ArrayList;

public class ClientMasterController {
    private static ClientMasterController controller;
    private ClientMasterController()
    {}
    public static ClientMasterController getController()
    {
        if(controller == null)
            return controller = new ClientMasterController();
        else
            return controller;
    }
    public boolean checkPhoneNumber(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkPhoneNumber","controller.Controller",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean checkEmail(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkEmail","controller.Controller",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean checkMoney(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("checkNumber","controller.Controller",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean usernameExist(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("usernameExist","controller.Controller",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean createAccount(String text, String text1, ArrayList<String> additionalInfo) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        params.add(additionalInfo);
        Command command = new Command("usernameExist","controller.login.RegisterController",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public void endConnection() {
        Command command = new Command("endConnection","",new ArrayList<>());
        Client.getConnector().serverQuery(command.toJson());
    }
}
