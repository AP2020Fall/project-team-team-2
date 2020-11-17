public class Game {
    private static Player turn;
    public static void main(String[] args) {}
    public void createGame(){}
    public void endGame(){}
    public static void setTurn(Player turn) {
        Game.turn = turn;
    }
    public static Player getTurn() {
        return turn;
    }
    
}