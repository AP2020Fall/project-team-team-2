package model;
import java.util.Arrays;
import java.util.List;
public class Map{
    private static List<List<Country>> countries;
    public static void main(String[] args) {
    }
    public static void addCountry(int row , int column , Country country) {
        Map.countries.get(row-1).set(column-1 , country);
    }
    public static List<List<Country>> getCountries() {
        return countries;
    }
    public static Country getCountry(int row,int column){
        return Map.countries.get(row-1).get(column-1);
    }
    public static void setMap(int n , int m){

    }
}