package Model;
import java.util.List;
public class Country {
    private String name;
    private Player Owner;
    private int soldiers;
    
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
}
