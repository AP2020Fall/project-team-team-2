package model;
import java.util.List;
public class RiskGame {
    private Player turn;
    private String status;
    private static List<Player> allPlayers;
    private static Player playingUser;
    /* Status Of player : Drafting - Attacking - Fortifing */
    private static String playerStatus;
    public void main(String[] args) {}
    public void createGame(){}
    public void endGame(){}
    

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setTurn(Player player) {
        this.turn = player;
    }
    public Player getTurn() {
        return turn;
    }
    public static void addPlayer(Player player) {
        RiskGame.allPlayers.add(player);
    }
    
    public static Player getPlayingUser() {
        return playingUser;
    }
    public static void setPlayerStatus(String playerStatus) {
        RiskGame.playerStatus = playerStatus;
    }
    public static void setPlayingUser(Player playingUser) {
        RiskGame.playingUser = playingUser;
    }
    public static String getPlayerStatus() {
        return playerStatus;
    }
}
