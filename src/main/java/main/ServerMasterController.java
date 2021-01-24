package main;

import com.google.gson.Gson;

import java.lang.reflect.Method;

public class ServerMasterController {
    public String takeAction(String input) {
       Command command = Command.fromJson(input);
       if(command.getCommand().equals("endConnection"))
        {
            return "Connection is terminated.";
        }
       if(command.getDeclaringClass() == null)
           return "";
       if(command.getMethod() == null)
           return "";
        return new Gson().toJson(command.invokeMethod());
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
