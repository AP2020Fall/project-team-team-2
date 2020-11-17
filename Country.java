public class Country {
    private String name;
    private Player Owner;
    private int soldiers;
    private int xPos;
    private int yPos;
    public static void main(String[] args) {}
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setOwner(Player owner) {
        Owner = owner;
    }
    public Player getOwner() {
        return Owner;
    }
    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }
    public int getSoldiers() {
        return soldiers;
    }
    public void setXPos(int xPos) { this.xPos() = xPos; }
    public void getXPos() { return xPos; }
    public void setYPos(int yPos) { this.yPos() = yPos; }
    public void getYPos() { return yPos; }
}
