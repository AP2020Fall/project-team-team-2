package controller;

import java.util.HashMap;
import java.util.Map;

public class StartGameController {
    private static Map<String , Object> primitiveSettings = new HashMap<String , Object>();
    static {
        primitiveSettings.put("Map Number"  , 0);
        primitiveSettings.put("Placement"   , false);
        primitiveSettings.put("Alliance"    , false);
        primitiveSettings.put("Blizzards"   , false);
        primitiveSettings.put("Fog Of War"  , false);
        primitiveSettings.put("Duration"    , 0);
        primitiveSettings.put("PlayersNum"  , 0);
    }
    public void startGame(){
        
    }
    public static Map<String, Object> getPrimitiveSettings() {
        return primitiveSettings;
    }
    public static void setPrimitiveSettings(String index , Object value) {
        StartGameController.primitiveSettings.put(index , value);
    }
}
