package model;


import java.util.*;

public class Country {
    private String name;
    private Player Owner;
    private String continent;
    private int numberOfContinentCountry = 0;
    private int soldiers = 0;

    public static void main(String[] args) {
    }

    public Country(String name, String continent) {
        this.name = name;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public void setOwner(Player owner) {
        Owner = owner;
    }

    public Player getOwner() {
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

    public int getNumberOfContinentCountry() {
        return numberOfContinentCountry;
    }

    public void setNumberOfContinentCountry(int numberOfContinentCountry) {
        this.numberOfContinentCountry = numberOfContinentCountry;
    }

    @Override
    public String toString() {
        String firstPartOfName = name.substring(0, 2).toUpperCase();
        int ownerNumber = 0;
        if (Owner != null) {
            ownerNumber = Owner.getPlayerNumber();
        }
        return firstPartOfName + "." + numberOfContinentCountry + "." + ownerNumber + "." + soldiers;
    }
}


