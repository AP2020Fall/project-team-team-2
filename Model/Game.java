package Model;

public class Game {
    private Player turn;
    private String status;
    public void main(String[] args) {}
    public void createGame(){}
    public void endGame(){}
    

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setTurn(Player turn) {
        this.turn = turn;
    }
    public Player getTurn() {
        return turn;
    }
    
}
