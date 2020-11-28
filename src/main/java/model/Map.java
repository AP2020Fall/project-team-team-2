package model;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import com.google.gson.*;
import java.util.Arrays;
import java.util.List;
public class Map{
    private static List<List<Country>> countries;
    public static void main(String[] args) {
        List<Country> map_1 = Countries.getRandomCountries(5,5);
        int[][] map_11 = new int[3][3];
        map_11[0][0] = 1;
        map_11[1][0] = 2;
        map_11[2][0] = 3;
        map_11[0][1] = 9;
        map_11[1][1] = 10;
        map_11[2][1] = 13;
        map_11[2][1] = 14;
        map_11[0][2] = 26;
        map_11[1][2] = 27;
        map_11[2][2] = 28;
        GSON newGson = new GSON();
        System.out.println(newGson.toJSON(map_11));
    }
    public static void addCountry(int row , int column , Country country) {
        countries.get(row-1).remove(column-1);
    }
    public static List<List<Country>> getCountries() {
        return countries;
    }
    public static Country getCountry(int row,int column){
        return countries.get(row-1).get(column-1);
    }

}
enum Maps{

}