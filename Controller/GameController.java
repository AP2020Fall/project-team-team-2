package Controller;
import Model.Player;

import java.util.Scanner;

public class GameController {
    private static Player playingUser;
    /* Status Of player : Drafting - Attacking - Fortifing */
    private static String playerStatus;
    public static void main(String[] args) {

        Scanner inputCommand = new Scanner();
        String inputLine = new String();
        boolean check = false;

        inputLine = inputCommand.nextline().trim();

        Pattern startGameCommand = Pattern.compile("(^)start//s+risk//s+game");
/*        Pattern chooseMapCommand = Pattern.compile("(^)choose map ");
        Pattern placementTypeCommand = Pattern.compile("(^)start risk game");
        Pattern alliancementCommand = Pattern.compile("(^)start risk game");
        Pattern blizardsCommand = Pattern.compile("(^)start risk game");
        Pattern durationTimeCommand = Pattern.compile("(^)start risk game");
        Pattern setPlayersNumberCommand = Pattern.compile("(^)start risk game");
*/
        Matcher startGameMatcher = startGameCommand.matcher(inputLine);
        check = startGameMatcher.matches();
        if(check == true){
            System.out.println("OK!");
        }

    }
    public void startGame(){}
    public void chooseMap(){}
    public void placementType(){}
    public void alliancement(){}
    public void blizards(){}
    public void fog(){}
    public void durationTime(){}
    public void setPlayersNumber(){}
    /* Draft */
    public void placeSoldier(){}
    public void attack(){}
    /* Move Soldiers */
    public void move(){}
    /* Draf-Attck-forfeit */
    public void next(){}
    public void changeTurn(){}
    public void matchCards(){}
    
    public static void setPlayerStatus(String playerStatus) {
        GameController.playerStatus = playerStatus;
    }
    public static String getPlayerStatus() {
        return playerStatus;
    }
    public static void setPlayingUser(Player playingUser) {
        GameController.playingUser = playingUser;
    }
    public static Player getPlayingUser() {
        return playingUser;
    }
}
