package view;

import controller.risk.RiskGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RiskGameView implements View, Initializable {
    private final RiskGameController riskGameController;
    private final String mapNum;

    @FXML
    private SVGPath country_1_1;
    @FXML
    private SVGPath country_1_2;
    @FXML
    private SVGPath country_1_3;
    @FXML
    private SVGPath country_1_4;
    @FXML
    private SVGPath country_1_5;
    @FXML
    private SVGPath country_2_1;
    @FXML
    private SVGPath country_2_2;
    @FXML
    private SVGPath country_2_3;
    @FXML
    private SVGPath country_2_4;
    @FXML
    private SVGPath country_2_5;
    @FXML
    private SVGPath country_3_1;
    @FXML
    private SVGPath country_3_2;
    @FXML
    private SVGPath country_3_3;
    @FXML
    private SVGPath country_3_4;
    @FXML
    private SVGPath country_3_5;
    @FXML
    private SVGPath country_4_1;
    @FXML
    private SVGPath country_4_2;
    @FXML
    private SVGPath country_4_3;
    @FXML
    private SVGPath country_4_4;
    @FXML
    private SVGPath country_4_5;
    @FXML
    private SVGPath country_5_1;
    @FXML
    private SVGPath country_5_2;
    @FXML
    private SVGPath country_5_3;
    @FXML
    private SVGPath country_5_4;
    @FXML
    private SVGPath country_5_5;
    @FXML
    private void countryClick(MouseEvent e)  {
        int[] indices = getCountryIndices(e.getPickResult().getIntersectedNode().getId());
        System.out.println("" + indices[0] + " " + indices[1]);
    }
    public int[] getCountryIndices(String countryClicked){
        String[] details = countryClicked.split("_");
        int[] toReturn = new int[2];
        toReturn[0] = Integer.parseInt(details[1]);
        toReturn[1] = Integer.parseInt(details[2]);
        return toReturn;
    }
    public RiskGameView(Map<String, Object> primitiveSettings, int soldiers, int mapNum) {
        this.riskGameController = new RiskGameController(primitiveSettings, soldiers);
        this.mapNum = String.valueOf(mapNum);
        if (!(boolean) riskGameController.getPrimitiveSettings().get("Placement")) {
            autoPlace();
        }
    }


    public static void main(String[] args) {
        String check = "hi";
    }

    public void riskGameView() {
        /* write variables to get commands *//*
        Scanner inputCommand = Menu.getScanner();
        String inputLine = new String();
        boolean check = false;
        boolean matchCardEnable = false;
        boolean draftMode = false;
        boolean fortifyMode = false;
        *//* Different patterns of valid match cards commands *//*
        Pattern matchCardsCommand = Pattern.compile("(^)match cards($)");
        *//*Typical 1*//*
        Pattern type1MatchCommand = Pattern.compile("(^)1-type1,type1,type1 score:4($)");
        *//*Typical 2*//*
        Pattern type2MatchCommand = Pattern.compile("(^)2-type2,type2,type2 score:6($)");
        *//*Typical 3*//*
        Pattern type3MatchCommand = Pattern.compile("(^)3-type3,type3,type3 score:8($)");
        *//*Typical 4*//*
        Pattern differentTypeMatchCommand = Pattern.compile("(^)4-type1,type2,type3 score:10($)");


        *//* Pattern to place soldier in manual placement*//*
        Pattern placeSoldierManual = Pattern.compile("(^)place soldier in (?<countryDetails>\\w+\\.\\d+)($)");
        *//* Pattern to draft soldier *//*
        Pattern placeSoldier = Pattern
                .compile("(^)place (?<soldierNumber>\\d+) soldiers in (?<countryDetails>\\w+\\.\\d+)($)");
        *//* Pattern to Attac*//*
        Pattern attackPattern = Pattern
                .compile("(^)attack from (?<sourceCountry>\\w+\\.\\d+) with (?<soldierNumber>\\d+) soldiers to (?<destinationCountry>\\w+\\.\\d+)($)");

        *//* Pattern to fortify*//*
        Pattern fortifyPattern = Pattern
                .compile("(^)move (?<soldierNumber>\\d+) soldiers from (?<sourceCountry>\\w+\\.\\d+) to (?<destinationCountry>\\w+\\.\\d+)($)");
        *//* Sow Map Pattern *//*
        Pattern showMapPattern = Pattern.compile("(^)show map($)");
        *//* get input command *//*
        while (inputCommand.hasNextLine() && riskGameController.getGameIsPlaying()) {
            boolean placementStatus = riskGameController.getPlacementFinished();
            *//* get input command *//*
         *//* Command Found*//*
            boolean commandFound = false;

            inputLine = inputCommand.nextLine().trim();
            *//* Check manual placement*//*
            Matcher manualPlacementMatcher = placeSoldierManual.matcher(inputLine);
            check = manualPlacementMatcher.matches();
            if (check == true && !placementStatus) {
                String countryDetails = manualPlacementMatcher.group("countryDetails");
                placeSoldier(countryDetails, 1);
                commandFound = true;
            }
            *//* Show Map Match*//*
            Matcher showMapMatcher = showMapPattern.matcher(inputLine);
            check = showMapMatcher.matches();
            if (check == true) {
                this.showMap();
                check = false;
                commandFound = true;
            }
            *//* Check draft mode*//*
            Matcher placeSoldierMatcher = placeSoldier.matcher(inputLine);
            check = placeSoldierMatcher.matches();
            if (riskGameController.getAttackWon() && placementStatus) {
                if (check) {
                    String countryDetails = placeSoldierMatcher.group("countryDetails");
                    int soldierNumber = Integer.parseInt(placeSoldierMatcher.group("soldierNumber"));
                    draftAfterWin(countryDetails, soldierNumber);
                    commandFound = true;
                    check = false;
                } else if(!commandFound) {
                    System.out.println("Invalid command!");
                    continue;
                }
            }
            if (check == true && placementStatus) {
                String countryDetails = placeSoldierMatcher.group("countryDetails");
                int soldierNumber = Integer.parseInt(placeSoldierMatcher.group("soldierNumber"));
                draft(countryDetails, soldierNumber);
                commandFound = true;
            }
            *//* Check attack mode *//*
            Matcher attackMatcher = attackPattern.matcher(inputLine);
            check = attackMatcher.matches();
            if (check == true && placementStatus) {
                String sourceCountry = attackMatcher.group("sourceCountry");
                String destinationCountry = attackMatcher.group("destinationCountry");
                int soldierNumber = Integer.parseInt(attackMatcher.group("soldierNumber"));
                attack(sourceCountry, destinationCountry, soldierNumber);
                commandFound = true;
            }
            *//* Check fortify mode*//*
            Matcher fortifyMatcher = fortifyPattern.matcher(inputLine);
            check = fortifyMatcher.matches();
            if (check == true && placementStatus) {
                String sourceCountry = fortifyMatcher.group("sourceCountry");
                String destinationCountry = fortifyMatcher.group("destinationCountry");
                int soldierNumber = Integer.parseInt(fortifyMatcher.group("soldierNumber"));
                fortify(sourceCountry, destinationCountry, soldierNumber);
                commandFound = true;
            }


            *//* Check match cards *//*
            Matcher matchCardsMatcher = matchCardsCommand.matcher(inputLine);
            check = matchCardsMatcher.matches();
            if (check == true && placementStatus) {
                check = false;
                showOptions();
                commandFound = true;
            }
            Matcher type1MatchMatcher = type1MatchCommand.matcher(inputLine);
            check = type1MatchMatcher.matches();
            if (check == true && placementStatus) {
                riskGameController.matchCards(1);
                check = false;
                commandFound = true;
            }

            Matcher type2MatchMatcher = type2MatchCommand.matcher(inputLine);
            check = type2MatchMatcher.matches();
            if (check == true && placementStatus) {
                riskGameController.matchCards(2);
                check = false;
                commandFound = true;
            }

            Matcher type3MatchMatcher = type3MatchCommand.matcher(inputLine);
            check = type3MatchMatcher.matches();
            if (check == true && placementStatus) {
                riskGameController.matchCards(3);
                check = false;
                commandFound = true;
                commandFound = true;
            }

            Matcher diffrentTypeMatchMatcher = differentTypeMatchCommand.matcher(inputLine);
            check = diffrentTypeMatchMatcher.matches();
            if (check == true && placementStatus) {
                riskGameController.matchCards(4);
                check = false;
                commandFound = true;
            }

            if (inputLine.equals("next")) {
                next();
                commandFound =true;
            }
            if (inputLine.equals("turn over")) {
                nextTurn();
                commandFound = true;
            }
            if(inputLine.equals("show what to do")){
                showWhatToDo();
                commandFound = true;
            }
            if (commandFound == false) {
                System.out.println("Invalid Command!");
            }
        }*/
    }



    private void showWhatToDo() {
        String toPrint = riskGameController.showWhatToDo();
        System.out.println(toPrint);
    }

    public void showMap() {
        String toPrint = this.riskGameController.showMap();
        System.out.println(toPrint);
    }

    public void placeSoldier(String countryDetals, int soldiers) {
        String toPrint = this.riskGameController.placeSoldier(countryDetals, soldiers);
        System.out.println(toPrint);
    }

    public void draft(String countryDetals, int soldiers) {
        String toPrint = this.riskGameController.draft(countryDetals, soldiers);
        System.out.println(toPrint);
    }

    public void attack(String sourceCountry, String destinationCountry, int soldiers) {
        String toPrint = riskGameController.attack(sourceCountry, destinationCountry, soldiers);
        System.out.println(toPrint);
    }

    public void fortify(String sourceCountry, String destinationCountry, int soldiers) {
        String toPrint = this.riskGameController.fortify(sourceCountry, destinationCountry, soldiers);
        System.out.println(toPrint);
    }

    public void next() {
        String toPrint = riskGameController.next();
        System.out.println(toPrint);
    }

    public void autoPlace() {
        riskGameController.autoPlace();
    }

    public void nextTurn() {
        String toPrint = riskGameController.changeTurn();
        System.out.println(toPrint);
    }

    public void showTurn() {
        String toPrint = riskGameController.showTurn();
        System.out.println(toPrint);
    }

    public void showOptions() {
        String toPrint = riskGameController.showMatchOptions();
        System.out.println(toPrint);
    }

    public void matchCards(int typical) {
        String toPrint = riskGameController.matchCards(typical);
        System.out.println(toPrint);
    }

    public void draftAfterWin(String countryDetails, int soldiers) {
        String toPrint = riskGameController.draftAfterWin(countryDetails, soldiers);
        System.out.println(toPrint);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void show(Stage window) throws IOException {
        String fileAddress = "/game/maps/map_" + mapNum + ".fxml";
        FXMLLoader root = new FXMLLoader(getClass().getResource(fileAddress));
        root.setController(this);
        window.setTitle("Game Started");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }
}
