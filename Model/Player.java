package Model;

public class Player {
    private String username;
    private String password;
    private String name;
    private int score;
    private int coin;
    private int soldiers;
    public static void main(String[] args) {}
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setCoin(int coin) {
        this.coin = coin;
    }
    public int getCoin() {
        return coin;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPassword() {
        return password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getName() {
        return name;
    }
    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }
    public int getSoldiers() {
        return soldiers;
    }

}