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
import java.util.Collections;
import java.util.List;
public class Map {
    private static List<List<Country>> countries;

    public static void main(String[] args) throws FileNotFoundException {
        String[] names = new String[10];
        names[0] = "Desini";
        names[1] = "Limbus";
        names[2] = "Quine";
        names[3] = "Majora";
        names[4] = "Hotkey";
        names[5] = "Purine";
        names[6] = "Huger";
        names[7] = "Tunny";
        names[8] = "Wittle";
        names[9] = "Bigram";

        Gson newGson = new Gson();
        String filename = "src/main/resources/robots/names.txt";
        Path path = Paths.get(filename);
        try {
            Files.write(path , Collections.singleton(newGson.toJson(names)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Files.writeString(path, newGson.toJson(map_11), StandardCharsets.UTF_8);
//        } catch (IOException ex) {
//            // Handle exception
//        }
        JsonReader reader = new JsonReader(new FileReader(filename));
        String[] newPath = newGson.fromJson(reader, String[].class);
        String[] newString = new String[newPath.length];
        System.out.println(newPath.length);
        for (int i = 0; i < newString.length; i++) {
            System.out.println(newPath[i]);
        }
        System.out.println("Hiiii");
    }

    public static void addCountry(int row, int column, Country country) {
        countries.get(row - 1).remove(column - 1);
    }

    public static boolean checkMapExists(int mapNumber) {
        String mapAddressName = "src/main/resources/maps/map_" + mapNumber + ".txt";
        File file = new File(mapAddressName);
        boolean existence = false;
        if (file.exists()) {
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