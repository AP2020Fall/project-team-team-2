package model;

import java.util.ArrayList;

public class Player extends Account {

    public Player(String firstName, String lastName, String accountName, int accountId, String password, String email, String phoneNumber, int dayOfRegister, double money, int score) {
        super(firstName, lastName, accountName, accountId, password, email, phoneNumber);
        this.dayOfRegister = dayOfRegister;
        this.money = money;
        this.score = score;
    }

    private static ArrayList<Player> allPlayers = new ArrayList<>();
    private int dayOfRegister;
    private double money;
    private int score;
    private ArrayList<GameLog> gameLogs;
    private ArrayList<Account> friends;
    private ArrayList<FriendRequest> friendRequests;

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public int getDayOfRegister() {
        return dayOfRegister;
    }

    public double getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<GameLog> getGameLogs() {
        return gameLogs;
    }

    public ArrayList<Account> getFriends() {
        return friends;
    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setDayOfRegister(int dayOfRegister) {
        this.dayOfRegister = dayOfRegister;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static Player getPlayerById(int id) {
        return null;
    }
}
