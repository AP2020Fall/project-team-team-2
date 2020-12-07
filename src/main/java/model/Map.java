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

public class Map {
    private static List<List<Country>> countries;

    public static void main(String[] args) throws FileNotFoundException {
//        int[][] map_11 = new int[4][4];
//        map_11[0][0] = 40;
//        map_11[0][1] = 43;
//        map_11[0][2] = 9;
//        map_11[0][3] = 21;
//        map_11[1][0] = 41;
//        map_11[1][1] = 42;
//        map_11[1][2] = 10;
//        map_11[1][3] = 22;
//        map_11[2][0] = 1;
//        map_11[2][1] = 4;
//        map_11[2][2] = 11;
//        map_11[2][3] = 33;
//        map_11[3][0] = 2;
//        map_11[3][1] = 3;
//        map_11[3][2] = 35;
//        map_11[3][3] = 33;
//
//
//        Gson newGson = new Gson();
//        String filename = "src/main/resources/maps/map_7.txt";
//        Path path = Paths.get(filename);
//
////        try {
////         //   Files.writeString(path, newGson.toJson(map_11), StandardCharsets.UTF_8);
////        } catch (IOException ex) {
////            // Handle exception
////        }
//        JsonReader reader = new JsonReader(new FileReader(filename));
//        int[][] newPath = newGson.fromJson(reader, int[][].class);
//        String[][] newString = new String[newPath.length][newPath[0].length];
//        System.out.println(newPath[0].length);
//        for (int i = 0; i < newPath.length; i++) {
//            for (int j = 0; j < newPath[i].length; j++) {
//                newString[i][j] = Countries.values()[newPath[i][j] - 1].getContinent();
//            }
//        }
        System.out.println("Hiiii");
    }

    public static void addCountry(int row, int column, Country country) {
        countries.get(row - 1).remove(column - 1);
    }
    public static boolean checkMapExists(int mapNumber){
        String mapAddressName = "src/main/resources/maps/map_" + mapNumber + ".txt";
        File file = new File(mapAddressName);
        boolean existence = false;
        if(file.exists()){
            existence = true;
        }
        return existence;
    }
    public static List<List<Country>> getCountries() {
        return countries;
    }

    public static Country getCountry(int row, int column) {
        return countries.get(row - 1).get(column - 1);
    }

}

enum Maps {

}