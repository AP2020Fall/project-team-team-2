package model;
import java.util.List;
public class Gamer {
    private String username;
    private int score;
    private int coin;
    private List<Card> cards;
    public static void main(String[] args) {}
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void addCoin(int coin) {
        this.coin += coin;
    }
    public int getCoin() {
        return coin;
    }
    public void addScore(int score) {
        this.score += score;
    }
    public int getScore() {
        return score;
    }
    public List<Card> getCards() {
        return cards;
    }
    public void addCard(Card card){
        this.cards.add(card);
    }

}
