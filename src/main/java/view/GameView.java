package view;
import model.Player;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameView {
    private Scanner scanner = View.getScanner();
    private Player currentPlayer;
    public static void main(String[] args) {

    }
    public void currentGame(){
        Scanner inputCommand = View.getScanner();
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
