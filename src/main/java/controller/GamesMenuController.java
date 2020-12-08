package controller;

import java.util.ArrayList;

public class GamesMenuController {
    public ArrayList<String> listOfGames() {
        //returns ArrayList of Game names, currently only Risk
        ArrayList<String> result = new ArrayList<>();
        result.add("Risk");
        return result;
    }
    public boolean gameIsListed(String gameName)
    {
        //Checks if gameName is Risk
        return gameName.equals("Risk");
    }
}
