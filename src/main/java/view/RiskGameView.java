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
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RiskGameView {
    private RiskGameController riskGameController;

    public RiskGameView(Map<String, Object> primitiveSettings, String gameID,int soldiers) {
        this.riskGameController = new RiskGameController(primitiveSettings, gameID , soldiers);
    }


    public static void main(String[] args) {
        String check = "hi";
    }

    public void riskGameView() {
        /* write variables to get commands */
        Scanner inputCommand = Menu.getScanner();
        String inputLine = new String();
        boolean check = false;
        boolean matchCardEnable = false;


        /* Different patterns of valid match cards commands */
        Pattern matchCardsCommand = Pattern.compile("(^)match cards($)");
        Pattern type1MatchCommand = Pattern.compile("(^)1-type1,type1,type1 score:4($)");
        Pattern type2MatchCommand = Pattern.compile("(^)2-type2,type2,type2 score:6($)");
        Pattern type3MatchCommand = Pattern.compile("(^)3-type3,type3,type3 score:8($)");
        Pattern differentTypeMatchCommand = Pattern.compile("(^)4-type1,type2,type3 score:10($)");


        /* Pattern to place soldier in manual placement*/
        Pattern placeSoldierManual = Pattern.compile("(^)place soldier in (?<countryDetails>\\w+\\.\\d+)($)");
        /* Pattern to draft soldier */
        Pattern placeSoldier = Pattern
                .compile("(^)place (?<soldierNumber>\\d+) soldiers in (?<countryDetails>\\w+\\.\\d+)($)");
        /* Pattern to Attac*/
        Pattern attackPattern = Pattern
                .compile("(^)attack from (?<sourceCountry>\\w+\\.\\d+) with (?<soldierNumber>\\d+) soldiers to (?<destinationCountry>\\w+\\.\\d+)($)");

        /* Pattern to fortify*/
        Pattern fortifyPattern = Pattern
                .compile("(^)move (?<soldierNumber>\\d+) soldiers from (?<sourceCountry>\\w+\\.\\d+) to (?<destinationCountry>\\w+\\.\\d+)($)");
        /* get input command */

        while (inputCommand.hasNextLine()) {
            boolean placementStatus = riskGameController.getPlacementFinished();
            /* get input command */
            inputLine = inputCommand.nextLine().trim();

            /* Check manual placement*/
            Matcher manualPlacementMatcher = placeSoldierManual.matcher(inputLine);
            check = manualPlacementMatcher.matches();
            if(check == true && !placementStatus){
                String countryDetails = manualPlacementMatcher.group("countryDetails");
                placeSoldier(countryDetails,1);
            }
            /* Check match cards */
            Matcher matchCardsMatcher = matchCardsCommand.matcher(inputLine);
            check = matchCardsMatcher.matches();
            if (check == true && placementStatus) {
                check = false;
                matchCardEnable = true;
                continue;
            }

            while(matchCardEnable){

                inputLine = inputCommand.nextLine().trim();

                Matcher type1MatchMatcher = type1MatchCommand.matcher(inputLine);
                check = type1MatchMatcher.matches();
                if (check == true && placementStatus) {
                    riskGameController.matchCardAddSoldiers(4);
                    check = false;
                    continue;
                }

                Matcher type2MatchMatcher = type2MatchCommand.matcher(inputLine);
                check = type2MatchMatcher.matches();
                if (check == true && placementStatus) {
                    riskGameController.matchCardAddSoldiers(6);
                    check = false;
                    continue;
                }

                Matcher type3MatchMatcher = type3MatchCommand.matcher(inputLine);
                check = type3MatchMatcher.matches();
                if (check == true && placementStatus) {
                    riskGameController.matchCardAddSoldiers(8);
                    check = false;
                    continue;
                }

                Matcher diffrentTypeMatchMatcher = differentTypeMatchCommand.matcher(inputLine);
                check = diffrentTypeMatchMatcher.matches();
                if (check == true && placementStatus) {
                    riskGameController.matchCardAddSoldiers(10);
                    check = false;
                    continue;
                }

                if (check == false) {
                    System.out.println("Invalid Command!");
                }
            }

            if(inputLine.equals("next")){

            }
            if (inputLine.equals("turn over")) {
                nextTurn();
            }
            if (check == false) {
                System.out.println("Invalid Command!");
            }
        }
    }
    public void showMap(){
        String toPrint = this.riskGameController.showMap();
        System.out.println(toPrint);
    }
//    public void manualPlaceSoldier() {
//        String toPrint = this.riskGameController.manualPlaceSoldier();
//        System.out.println(toPrint);
//    }
    public void placeSoldier(String countryDetals , int soldiers){
        String toPrint = this.riskGameController.placeSoldier(countryDetals , soldiers);
        System.out.println(toPrint);
    }
    public void attack(String sourceCountry , String destinationCountry , int soldiers){
        String toPrint = riskGameController.attack(sourceCountry ,destinationCountry , soldiers);
        System.out.println(toPrint);
    }
    public void fortify(String sourceCountry , String destinationCountry , int soldiers){

    }
    public void next(){

    }
    public void nextTurn(){
        String toPrint = riskGameController.changeTurn();
        System.out.println(toPrint);
    }
}
