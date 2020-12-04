package view;

import controller.StartGameController;

import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameView {
    private StartGameController startGameController = new StartGameController();
    public static void main(String[] args) {
        StartGameView newStart = new StartGameView();
        newStart.startGameView();
        System.out.println("Hi");
    }

    public void startGameView() {
        /* write variables to get commands */
        Scanner inputCommand = Menu.getScanner();
        String inputLine = new String();
        boolean check = false;
        int mapNumber = 0;
        int turnDuration = 0;
        int playerNumbers = 0;

        /* Start new risk game */

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
            boolean foundCommand = false;
            /* get input command */
            inputLine = inputCommand.nextLine().trim();
            if (inputLine.equals("data")) {
                foundCommand = true;
                System.out.println(startGameController.getPrimitiveSettings());
                continue;
            }
            /* check out which command is published */
            Matcher chooseMapMatcher = chooseMapCommand.matcher(inputLine);
            check = chooseMapMatcher.matches();
            if (check == true) {
                String strNumber = chooseMapMatcher.group("mapNumber");
                this.chooseMapNumber(strNumber);
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher placementManualTypeMatcher = placementManualTypeCommand.matcher(inputLine);
            check = placementManualTypeMatcher.matches();
            if (check == true) {
                placementType("on");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher placementNonManualTypeMatcher = placementNonManualTypeCommand.matcher(inputLine);
            check = placementNonManualTypeMatcher.matches();
            if (check == true) {
                placementType("off");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher alliancementOnMatcher = alliancementOnCommand.matcher(inputLine);
            check = alliancementOnMatcher.matches();
            if (check == true) {
                allianceType("on");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher alliancementOffMatcher = alliancementOffCommand.matcher(inputLine);
            check = alliancementOffMatcher.matches();
            if (check == true) {
                allianceType("off");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher blizardsOnMatcher = blizardsOnCommand.matcher(inputLine);
            check = blizardsOnMatcher.matches();
            if (check == true) {
                blizzardsType("on");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher blizardsOffMatcher = blizardsOffCommand.matcher(inputLine);
            check = blizardsOffMatcher.matches();
            if (check == true) {
                blizzardsType("off");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher fogOnMatcher = fogOnCommand.matcher(inputLine);
            check = fogOnMatcher.matches();
            if (check == true) {
                fogsType("on");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher fogOffMatcher = fogOffCommand.matcher(inputLine);
            check = fogOffMatcher.matches();
            if (check == true) {
                fogsType("off");
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher durationTimeMatcher = durationTimeCommand.matcher(inputLine);
            check = durationTimeMatcher.matches();
            if (check == true) {
                String strNumber = durationTimeMatcher.group("turnDuration");
                changeDurationTime(strNumber);
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher setPlayersNumberMatcher = setPlayersNumberCommand.matcher(inputLine);
            check = setPlayersNumberMatcher.matches();
            if (check == true) {
                String strNumber = setPlayersNumberMatcher.group("playerNumbers");
                changePlayersNumber(strNumber);
                check = false;
                foundCommand = true;
                continue;
            }

            Matcher startGameMatcher = startGameCommand.matcher(inputLine);
            check = startGameMatcher.matches();
            if (check == true) {
                startGameController.startGame();
                check = false;
                foundCommand = true;
                break;
            }

            if (foundCommand == false) {
                System.out.println("Invalid Command!");
            }

        }

    }

    public void changePlayersNumber(String strNumber) {
        int playerNumber = Integer.parseInt(strNumber);
        String callback = startGameController.setPlayerNumber(playerNumber);
        System.out.println(callback);

    }

    public void chooseMapNumber(String strNumber) {
        int mapNumber = Integer.parseInt(strNumber);
        String callback = startGameController.setMapNumber(mapNumber);
        System.out.println(callback);
    }

    public void changeDurationTime(String strNumber) {
        int number = Integer.parseInt(strNumber);
        String callback = startGameController.setDurationTime(number);
        System.out.println(callback);
    }

    public void placementType(String type) {
        String callback = startGameController.setPlacementType(type);
        System.out.println(callback);
    }

    public void allianceType(String type) {
        String callback = startGameController.setAllianceType(type);
        System.out.println(callback);
    }

    public void blizzardsType(String type) {
        String callback = startGameController.setBlizzardsType(type);
        System.out.println(callback);
    }

    public void fogsType(String type) {
        String callback = startGameController.setFogType(type);
        System.out.println(callback);
    }


}
