package view;
import controller.StartGameController;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameView {
    private Scanner scanner = View.getScanner();

    public static void main(String[] args) {

    }
    public void startGameView(){
        /* write variables to get commands */
        Scanner inputCommand = View.getScanner();
        String inputLine = new String();
        boolean check = false;
        int mapNumber = 0;
        int turnDuration = 0;
        int playerNumbers = 0;

        /* Different patterns of valid commands */
        Pattern startGameCommand = Pattern.compile("(^)start risk game($)");
        Pattern chooseMapCommand = Pattern.compile("(^)choose map\\s+(?<mapNumber>\\d+)($)");
        Pattern placementManualTypeCommand = Pattern.compile("(^)manual placement on($)");
        Pattern placementNonManualTypeCommand = Pattern.compile("(^)manual placement off($)");
        Pattern alliancementOnCommand = Pattern.compile("(^)alliance on($)");
        Pattern alliancementOffCommand = Pattern.compile("(^)alliance off($)");
        Pattern blizardsOnCommand = Pattern.compile("(^)blizzards on($)");
        Pattern blizardsOffCommand = Pattern.compile("(^)blizzards off($)");
        Pattern fogOnCommand = Pattern.compile("(^)fog of war on($)");
        Pattern fogOffCommand = Pattern.compile("(^)fog of war off($)");
        Pattern durationTimeCommand = Pattern.compile("(^)turn duration is\\s+(?<turnDuration>\\d+)\\s+seconds($)");
        Pattern setPlayersNumberCommand = Pattern.compile("(^)number of players are\\s+(?<playerNumbers>\\d+)($)");


        while (inputCommand.hasNextLine()) {
            /* get input command */
            inputLine = inputCommand.nextLine().trim();
            if(inputLine.equals("data")){
                System.out.println(StartGameController.getPrimitiveSettings());
            }
            /* check out which command is published */
            Matcher startGameMatcher = startGameCommand.matcher(inputLine);
            check = startGameMatcher.matches();
            if (check == true) {
                System.out.println("Game Started");
                check = false;
                break;
            }

            Matcher chooseMapMatcher = chooseMapCommand.matcher(inputLine);
            check = chooseMapMatcher.matches();
            if (check == true) {
                String strNumber = chooseMapMatcher.group("mapNumber");
                chooseMapNumber(strNumber);
                System.out.println("Map Choosen");
                check = false;
            }

            Matcher placementManualTypeMatcher = placementManualTypeCommand.matcher(inputLine);
            check = placementManualTypeMatcher.matches();
            if (check == true) {
                placementType("on");
                check = false;
            }

            Matcher placementNonManualTypeMatcher = placementNonManualTypeCommand.matcher(inputLine);
            check = placementNonManualTypeMatcher.matches();
            if (check == true) {
                placementType("off");
                check = false;
            }

            Matcher alliancementOnMatcher = alliancementOnCommand.matcher(inputLine);
            check = alliancementOnMatcher.matches();
            if (check == true) {
                alliencementType("on");
                check = false;
            }

            Matcher alliancementOffMatcher = alliancementOffCommand.matcher(inputLine);
            check = alliancementOffMatcher.matches();
            if (check == true) {
                alliencementType("off");
                check = false;
            }

            Matcher blizardsOnMatcher = blizardsOnCommand.matcher(inputLine);
            check = blizardsOnMatcher.matches();
            if (check == true) {
                blizzardsType("on");
                check = false;
            }

            Matcher blizardsOffMatcher = blizardsOffCommand.matcher(inputLine);
            check = blizardsOffMatcher.matches();
            if (check == true) {
                blizzardsType("off");
                check = false;
            }

            Matcher fogOnMatcher = fogOnCommand.matcher(inputLine);
            check = fogOnMatcher.matches();
            if (check == true) {
                fogsType("on");
                check = false;
            }

            Matcher fogOffMatcher = fogOffCommand.matcher(inputLine);
            check = fogOffMatcher.matches();
            if (check == true) {
                fogsType("off");
                check = false;
            }

            Matcher durationTimeMatcher = durationTimeCommand.matcher(inputLine);
            check = durationTimeMatcher.matches();
            if (check == true) {
                String strNumber = durationTimeMatcher.group("turnDuration");
                changeDurationTime(strNumber);
                check = false;
            }

            Matcher setPlayersNumberMatcher = setPlayersNumberCommand.matcher(inputLine);
            check = setPlayersNumberMatcher.matches();
            if (check == true) {
                String strNumber = setPlayersNumberMatcher.group("playerNumbers");
                changePlayersNumber(strNumber);
                check = false;
            }
        }
    }
    public static void changePlayersNumber(String strNumber){
        int number = Integer.parseInt(strNumber);
        StartGameController.setPrimitiveSettings("PlayersNum" , number);
        System.out.println("Player Number Changes To : " + strNumber);
    }
    public static void chooseMapNumber(String strNumber){
        int number = Integer.parseInt(strNumber);
        StartGameController.setPrimitiveSettings("Map Number" , number);
        System.out.println("Map Number Changes To : " + strNumber);
    }
    public static void changeDurationTime(String strNumber){
        int number = Integer.parseInt(strNumber);
        StartGameController.setPrimitiveSettings("Duration" , number);
        System.out.println("Duration Number Changes To : " + strNumber);
    }
    public static void placementType(String type){
        switch (type){
            case "on":
                StartGameController.setPrimitiveSettings("Placement" , true);
                break;
            case "off":
                StartGameController.setPrimitiveSettings("Placement" , false);
                break;
        }
        System.out.println("Placement Type changed To : " + type.toUpperCase());
    }
    public static void alliencementType(String type){
        switch (type){
            case "on":
                StartGameController.setPrimitiveSettings("Alliance" , true);
                break;
            case "off":
                StartGameController.setPrimitiveSettings("Alliance" , false);
                break;
        }
        System.out.println("Alliance Type changed To : " + type.toUpperCase());
    }
    public static void blizzardsType(String type){
        switch (type){
            case "on":
                StartGameController.setPrimitiveSettings("Blizzards" , true);
                break;
            case "off":
                StartGameController.setPrimitiveSettings("Blizzards" , false);
                break;
        }
        System.out.println("Blizzards Type changed To : " + type.toUpperCase());
    }
    public static void fogsType(String type){
        switch (type){
            case "on":
                StartGameController.setPrimitiveSettings("Fog Of War" , true);
                break;
            case "off":
                StartGameController.setPrimitiveSettings("Fog Of War" , false);
                break;
        }
        System.out.println("Fog Of War Type changed To : " + type.toUpperCase());
    }
}
