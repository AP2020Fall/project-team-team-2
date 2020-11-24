package Controller;
import Model.Player;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class GameController {
    private static Player playingUser;
    /* Status Of player : Drafting - Attacking - Fortifing */
    private static String playerStatus;
    public static void main(String[] args) {


        Scanner inputCommand = new Scanner(System.in);
        String inputLine = new String();
        boolean check = false;

        int mapNumber = 0;
        int turnDuration = 0;
        int playerNumbers = 0;


        Pattern startGameCommand = Pattern.compile("(^)start risk game");
        Pattern chooseMapCommand = Pattern.compile("(^)choose map\\s+(?<mapNumber>\\d+)");
        Pattern placementManualTypeCommand = Pattern.compile("(^)manual placement on");
        Pattern placementNonManualTypeCommand = Pattern.compile("(^)manual placement off");
        Pattern alliancementOnCommand = Pattern.compile("(^)alliance on");
        Pattern alliancementOffCommand = Pattern.compile("(^)alliance off");
        Pattern blizardsOnCommand = Pattern.compile("(^)blizzards on");
        Pattern blizardsOffCommand = Pattern.compile("(^)blizzards off");
        Pattern fogOnCommand = Pattern.compile("(^)fog of war on");
        Pattern fogOffCommand = Pattern.compile("(^)fog of war off");
        Pattern durationTimeCommand = Pattern.compile("(^)turn duration is\\s+(?<turnDuration>\\d+)\\s+seconds");
        Pattern setPlayersNumberCommand = Pattern.compile("(^)number of players are\\s+(?<playerNumbers>\\d+)");


        while(true) {
            inputLine = inputCommand.nextLine().trim();

            Matcher startGameMatcher = startGameCommand.matcher(inputLine);
            check = startGameMatcher.matches();
            if(check == true){
                System.out.println("Command1 pass!");
                check = false;
            }

            Matcher chooseMapMatcher = chooseMapCommand.matcher(inputLine);
            check = chooseMapMatcher.matches();
            if(check == true){
                System.out.println("Command2 pass!");
                check = false;
            }

            Matcher placementManualTypeMatcher = placementManualTypeCommand.matcher(inputLine);
            check = placementManualTypeMatcher.matches();
            if(check == true){
                System.out.println("Command3 pass! " + "placement Manually");
                check = false;
            }

            Matcher placementNonManualTypeMatcher = placementNonManualTypeCommand.matcher(inputLine);
            check = placementNonManualTypeMatcher.matches();
            if(check == true){
                System.out.println("Command3 pass! " + "placement NonManually");
                check = false;
            }

            Matcher alliancementOnMatcher = alliancementOnCommand.matcher(inputLine);
            check = alliancementOnMatcher.matches();
            if(check == true){
                System.out.println("Command4 pass! " + "alliancement On");
                check = false;
            }

            Matcher alliancementOffMatcher = alliancementOffCommand.matcher(inputLine);
            check = alliancementOffMatcher.matches();
            if(check == true){
                System.out.println("Command4 pass! " + "alliancement Off");
                check = false;
            }

            Matcher blizardsOnMatcher = blizardsOnCommand.matcher(inputLine);
            check = blizardsOnMatcher.matches();
            if(check == true){
                System.out.println("Command5 pass! " + "blizards On");
                check = false;
            }

            Matcher blizardsOffMatcher = blizardsOffCommand.matcher(inputLine);
            check = blizardsOffMatcher.matches();
            if(check == true){
                System.out.println("Command5 pass! " + "blizards Off");
                check = false;
            }

            Matcher fogOnMatcher = fogOnCommand.matcher(inputLine);
            check = fogOnMatcher.matches();
            if(check == true){
                System.out.println("Command6 pass! " + "fog of war On");
                check = false;
            }

            Matcher fogOffMatcher = fogOffCommand.matcher(inputLine);
            check = fogOffMatcher.matches();
            if(check == true){
                System.out.println("Command6 pass! " + "fog of war Off");
                check = false;
            }

            Matcher durationTimeMatcher = durationTimeCommand.matcher(inputLine);
            check = durationTimeMatcher.matches();
            if(check == true){
                System.out.println("Command7 pass!");
                check = false;
            }

            Matcher setPlayersNumberMatcher = setPlayersNumberCommand.matcher(inputLine);
            check = setPlayersNumberMatcher.matches();
            if(check == true){
                System.out.println("Command8 pass!");
                check = false;
            }
        }

    }
    public void startGame(){}
    public void chooseMap(){}
    public void placementType(){}
    public void alliancement(){}
    public void blizards(){}
    public void fog(){}
    public void durationTime(){}
    public void setPlayersNumber(){}
    /* Draft */
    public void placeSoldier(){}
    public void attack(){}
    /* Move Soldiers */
    public void move(){}
    /* Draf-Attck-forfeit */
    public void next(){}
    public void changeTurn(){}
    public void matchCards(){}
    
    public static void setPlayerStatus(String playerStatus) {
        GameController.playerStatus = playerStatus;
    }
    public static String getPlayerStatus() {
        return playerStatus;
    }
    public static void setPlayingUser(Player playingUser) {
        GameController.playingUser = playingUser;
    }
    public static Player getPlayingUser() {
        return playingUser;
    }
}
