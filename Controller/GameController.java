package Controller;
import Model.Player;
public class GameController {
    private static Player playingUser;
    /* Status Of player : Drafting - Attacking - Fortifing */
    private static String playerStatus;
    public static void main(String[] args) {}
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
