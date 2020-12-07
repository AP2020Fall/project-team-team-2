package view;
import com.google.gson.stream.JsonReader;
import controller.RiskGameController;
import model.Countries;
import model.Country;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import model.Player;
import controller.RiskGameController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RiskGameView {
    private RiskGameController riskGameController;

    public RiskGameView(Map<String, Object> primitiveSettings , String gameID){
        this.riskGameController = new RiskGameController(primitiveSettings , gameID);
    }


    public static void main(String[] args) {
        String check = "hi";
    }
    public void riskGameView(){
        /* write variables to get commands */
        Scanner inputCommand = Menu.getScanner();
        String inputLine = new String();
        boolean check = false;


        /* Different patterns of valid match cards commands */
        Pattern matchCardsCommand = Pattern.compile("(^)match cards($)");
        Pattern type1MatchCommand = Pattern.compile("(^)1-type1,type1,type1 score:4($)");
        Pattern type2MatchCommand = Pattern.compile("(^)2-type2,type2,type2 score:6($)");
        Pattern type3MatchCommand = Pattern.compile("(^)3-type3,type3,type3 score:8($)");
        Pattern differentTypeMatchCommand = Pattern.compile("(^)4-type1,type2,type3 score:10($)");




        /* get input command */

        while (inputCommand.hasNextLine()) {
            /* get input command */
            inputLine = inputCommand.nextLine().trim();

            /* Check match cards */
            if(riskGameController.getPlacementFinished()){
                if((boolean)riskGameController.getPrimitiveSettings().get("Placement")){

                }else{

                }
                continue;
            }
            /* check out which command is published */
            Matcher matchCardsMatcher = matchCardsCommand.matcher(inputLine);
            check = matchCardsMatcher.matches();
            if (check == true) {
                System.out.println("Command1!");
                check = false;
                continue;
            }

            Matcher type1MatchMatcher = type1MatchCommand.matcher(inputLine);
            check = type1MatchMatcher.matches();
            if (check == true) {
                System.out.println("Command2!");
                check = false;
                continue;
            }

            Matcher type2MatchMatcher = type2MatchCommand.matcher(inputLine);
            check = type2MatchMatcher.matches();
            if (check == true) {
                System.out.println("Command3!");
                check = false;
                continue;
            }

            Matcher type3MatchMatcher = type3MatchCommand.matcher(inputLine);
            check = type3MatchMatcher.matches();
            if (check == true) {
                System.out.println("Command4!");
                check = false;
                continue;
            }

            Matcher diffrentTypeMatchMatcher = differentTypeMatchCommand.matcher(inputLine);
            check = diffrentTypeMatchMatcher.matches();
            if (check == true) {
                System.out.println("Command5!");
                check = false;
                continue;
            }
            if(inputLine.equals("next turn")){

            }
            if (check == false) {
                System.out.println("Invalid Command!");
            }
        }
    }

    public void manualPlaceSoldier(){

    }

}
