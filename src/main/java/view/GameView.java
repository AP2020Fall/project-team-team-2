package view;
import model.Player;
import java.util.Scanner;

public class GameView {

    private Player currentPlayer;
    public static void main(String[] args) {

    }
    public void currentGame(){
       // Scanner inputCommand = Menu.getScanner();
    }
    /* Me - Amirhossein Kian */
    public void printCreateGame(){}
    public void printPlaceSoldier(){}
    public void printAttack(){}
    public void printMove(){}
    public void printNext(){}
    public void printChangeTurn(){}
    public void printMatchCards(){}
    public void printEndGame(){}
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
