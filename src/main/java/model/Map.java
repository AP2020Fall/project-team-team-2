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