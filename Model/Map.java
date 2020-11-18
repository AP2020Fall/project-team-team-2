package Model;
import java.util.List;
public class Map{
    private List<List<Country>> countries;
    public static void main(String[] args) {}
    public void setCountries(List<List<Country>> countries) {
        this.countries = countries;
    }
    public List<List<Country>> getCountries() {
        return countries;
    }
}