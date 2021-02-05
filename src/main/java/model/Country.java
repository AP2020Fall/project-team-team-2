package model;


import java.util.*;

public class Country {
    private String name ="";
    private Gamer Owner;
    private String continent;
    private int gameCountryNumber;
    private int numberOfContinentCountry = 0;
    private int soldiers = 0;
    private boolean blizzard;

    public Country(){}
    public Country(String name, String continent , int gameCountryNumber) {
        this.name = name;
        this.continent = continent;
        this.gameCountryNumber = gameCountryNumber;
        this.blizzard = false;
    }

    public String getName() {
        return name;
    }

    public void setOwner(Gamer owner) {
        Owner = owner;
    }

    public Gamer getOwner() {
        return Owner;
    }
    public void addSoldiers(int soldiers) {
        this.soldiers += soldiers;
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public String getContinent() {
        return continent;
    }

    public void enableBlizzard(){ blizzard = true; }

    public void disableBlizzard(){ blizzard = false; }
    public boolean getBlizzard(){
        return blizzard;
    }
    public int getNumberOfContinentCountry() {
        return numberOfContinentCountry;
    }

    public void setNumberOfContinentCountry(int numberOfContinentCountry) {
        this.numberOfContinentCountry = numberOfContinentCountry;
    }
    public void emptyCountry(){
        Owner = null;
        soldiers = 0;
    }
    public int getGameCountryNumber() {
        return gameCountryNumber;
    }

    @Override
    public String toString() {
        String firstPartOfName = continent.substring(0, 2).toUpperCase();
        int ownerNumber = 0;
        if (Owner != null) {
            ownerNumber = Owner.getPlayerNumber();
        }
        return firstPartOfName + "." + numberOfContinentCountry + "." + ownerNumber + "." + soldiers;
    }
}


