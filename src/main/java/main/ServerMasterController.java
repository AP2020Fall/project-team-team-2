package main;

public class ServerMasterController {
    public String takeAction(String input) {
       Command command = Command.fromJson(input);
       if(command.getCommand().equals("endConnection"))
        {
            return "Connection is terminated.";
        }
       String result = "Actions must be implemented";
       //todo do action
        return result;
    }
    public String test(String input)
    {
        if(input.equalsIgnoreCase("test"))
        {
            return "Test successfully completed.";
        }
        else
            return "Test was unsuccessful.";
    }

}
