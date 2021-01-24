package main;

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
        Command command = new Command("checkPhoneNumber",params);
        Client.getConnector().serverQuery(command.toJson());
        return true;
    }

    public boolean checkEmail(String text) {
        return true;
    }

    public boolean checkMoney(String text) {
        return true;
    }

    public boolean isUsernameExist(String text) {
        return false;
    }

    public boolean createAccount(String text, String text1, ArrayList<String> additionalInfo) {
        return true;
    }

    public void endConnection() {
        Command command = new Command("endConnection",new ArrayList<>());
        Client.getConnector().serverQuery(command.toJson());
    }
}
