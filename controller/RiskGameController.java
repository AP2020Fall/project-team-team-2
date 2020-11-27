package controller;

import model.Player;
import model.Map;


import java.util.ArrayList;
import java.util.List;

public class RiskGameController {
    private Player currentPlayer;
    private ArrayList<Player> players;
    private List<List<Map>> gameMap;
    {

    }
    public static void main(String[] args) {}

    /* Me - Amirhossein Kiani */
    public void createGame(int n , int m){

    }

    /* Draft */
    public void placeSoldier(){

    }
    public void attack(){}
    /* Move Soldiers */
    public void move(){}
    /* Draf-Attck-forfeit */
    public void next(){}
    public void changeTurn(){}
    public void matchCards(){}

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
