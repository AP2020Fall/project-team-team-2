package model;
import java.io.*;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
public class Map{
    private static List<List<Country>> countries;
    public static void main(String[] args) throws FileNotFoundException {
        int[][] map_11 = new int[8][5];
        map_11[0][0] = 1;
        map_11[0][1] = 2;
        map_11[0][2] = 3;
        map_11[1][0] = 4;
        map_11[1][1] = 5;

        map_11[0][3] = 9;
        map_11[0][4] = 10;
        map_11[1][2] = 11;
        map_11[1][3] = 12;
        map_11[1][4] = 13;

        map_11[2][0] = 40;
        map_11[2][1] = 41;
        map_11[2][2] = 42;
        map_11[2][3] = 43;
        map_11[2][4] = 44;
        map_11[3][0] = 45;

        map_11[3][1] = 33;
        map_11[3][2] = 34;
        map_11[3][3] = 35;
        map_11[3][4] = 36;
        map_11[4][0] = 37;
        map_11[4][1] = 38;
        map_11[4][2] = 39;

        map_11[4][3] = 21;
        map_11[4][4] = 22;
        map_11[5][0] = 23;
        map_11[5][1] = 24;
        map_11[5][2] = 25;
        map_11[5][3] = 26;
        map_11[5][4] = 27;
        map_11[6][0] = 28;
        map_11[6][1] = 29;
        map_11[6][2] = 30;
        map_11[6][3] = 31;
        map_11[6][4] = 32;

        map_11[7][0] = 6;
        map_11[7][1] = 7;

        map_11[7][2] = 14;
        map_11[7][3] = 15;
        map_11[7][4] = 16;







        Gson newGson = new Gson();
        String filename = "src/main/java/files/maps/map_5.txt";
        Path path = Paths.get(filename);

        try {
            Files.writeString(path, newGson.toJson(map_11), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            // Handle exception
        }
        JsonReader reader = new JsonReader(new FileReader(filename));
        int[][] newPath = newGson.fromJson(reader,int[][].class);
        String[][] newString = new String[newPath.length][newPath[0].length];
        System.out.println(newPath[0].length);
        for(int i = 0 ; i < newPath.length ; i++){
            for(int j = 0 ; j< newPath[i].length ; j++){
                newString[i][j] = Countries.values()[newPath[i][j]-1].getContinent();
            }
        }
        System.out.println(Arrays.deepToString(newString));
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