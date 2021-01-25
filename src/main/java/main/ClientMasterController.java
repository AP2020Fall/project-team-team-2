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

    public void endConnection() {
        Command command = new Command("endConnection","",new ArrayList<>());
        Client.getConnector().serverQuery(command.toJson());
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
        Command command = new Command("createAccount","controller.login.RegisterController",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean usernameAndPasswordMatch(String text, String text1) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        params.add(text1);
        Command command = new Command("usernameAndPasswordMatch","controller.Controller",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean login(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("login","controller.login.LoginController",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public boolean isUsernamePlayer(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("isUsernamePlayer","controller.Controller",params);
        String answer =  Client.getConnector().serverQuery(command.toJson());
        Boolean result = new Gson().fromJson(answer,Boolean.class);
        return result;
    }

    public void delete(String text) {
        ArrayList<Object> params = new ArrayList<>();
        params.add(text);
        Command command = new Command("delete","controller.login.LoginController",params);
        Client.getConnector().serverQuery(command.toJson());
    }
}
