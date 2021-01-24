package main;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Command {
    private String command;
    private ArrayList<Object> params;

    public Command(String command, ArrayList<Object> params) {
        this.command = command;
        this.params = (ArrayList<Object>) params.clone();
    }
    public String toJson()
    {
        return new Gson().toJson(this);
    }
    public static Command fromJson(String json)
    {
        return new Gson().fromJson(json, Command.class);
    }

    public String getCommand() {
        return command;
    }

    public ArrayList<Object> getParams() {
        return params;
    }
}
