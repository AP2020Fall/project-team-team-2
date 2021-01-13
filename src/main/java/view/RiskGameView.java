package view;

import controller.risk.RiskGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Country;
import model.Player;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RiskGameView implements View, Initializable {
    private final RiskGameController riskGameController;
    private final String mapNum;
    private final SVGPath[][] allPaths = new SVGPath[5][5];
    private final Label[][] allLabels = new Label[5][5];
    private final List<Circle> playersCircles = new ArrayList<Circle>();
    private final String[][] defaultClasses = new String[5][5];
    @FXML
    private Rectangle nextTurn;
    @FXML
    private Circle nextStatus;
    @FXML
    private Circle deselectIcon;
    @FXML
    private TextField inputNumber;
    @FXML
    private Circle draftCircleImage;
    @FXML
    private Circle draftModeShape;
    @FXML
    private Circle attackCircleImage;
    @FXML
    private Circle attackModeShape;
    @FXML
    private Circle fortifyCircleImage;
    @FXML
    private Circle fortifyModeShape;
    @FXML
    private VBox rightVBox;
    private double rightVBoxWidth = 300;
    private double RightVBoxHeight = 600;
    @FXML
    private Label gameNotifs;
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
    private Label label_1_1;
    @FXML
    private Label label_1_2;
    @FXML
    private Label label_1_3;
    @FXML
    private Label label_1_4;
    @FXML
    private Label label_1_5;
    @FXML
    private Label label_2_1;
    @FXML
    private Label label_2_2;
    @FXML
    private Label label_2_3;
    @FXML
    private Label label_2_4;
    @FXML
    private Label label_2_5;
    @FXML
    private Label label_3_1;
    @FXML
    private Label label_3_2;
    @FXML
    private Label label_3_3;
    @FXML
    private Label label_3_4;
    @FXML
    private Label label_3_5;
    @FXML
    private Label label_4_1;
    @FXML
    private Label label_4_2;
    @FXML
    private Label label_4_3;
    @FXML
    private Label label_4_4;
    @FXML
    private Label label_4_5;
    @FXML
    private Label label_5_1;
    @FXML
    private Label label_5_2;
    @FXML
    private Label label_5_3;
    @FXML
    private Label label_5_4;
    @FXML
    private Label label_5_5;

    @FXML
    private void countryClick(MouseEvent e) {
        int[] indices = getCountryIndices(e.getPickResult().getIntersectedNode().getId());
        int i = indices[0];
        int j = indices[1];
        int soliders = 0;
        try {
            soliders = Integer.parseInt(inputNumber.getText());
            switch (showWhatToDo()) {
                case "Draft":
                    if (!inputNumber.getText().isEmpty()) {
                        changeNotifText(draft(i, j, soliders));
                    }
                    break;
                case "Attack":
                    if (riskGameController.getI() == null || riskGameController.getJ() == null) {
                        riskGameController.setI(i);
                        riskGameController.setJ(j);
                    } else {
                        if (!inputNumber.getText().isEmpty()) {
                            changeNotifText(attack(riskGameController.getI(), riskGameController.getJ(),
                                    i, j, soliders));
                        }
                    }
                    break;
                case "Fortify":
                    if (riskGameController.getI() == null || riskGameController.getJ() == null) {
                        riskGameController.setI(i);
                        riskGameController.setJ(j);
                    } else {
                        if (!inputNumber.getText().isEmpty()) {
                            changeNotifText(fortify(riskGameController.getI(), riskGameController.getJ(),
                                    i, j, soliders));
                        }
                    }
                    break;

            }
        }
        catch (NumberFormatException ex) {
            changeNotifText("please insert a number");
        }
        colorizeCountry();
        setColorMode();

    }
    @FXML
    private void nextStatus(MouseEvent e){
        changeNotifText(next());
        setColorMode();
    }
    @FXML
    private void nextTurnHandler(MouseEvent e) {
        nextTurn();
        colorizeCountry();
        setColorTurn();
        setColorMode();
    }
    @FXML
    private void deselectHandler(MouseEvent e){
        deselect();
    }

    private void deselect() {
        riskGameController.deselect();
    }

    public int[] getCountryIndices(String countryClicked) {
        String[] details = countryClicked.split("_");
        int[] toReturn = new int[2];
        toReturn[0] = Integer.parseInt(details[1]);
        toReturn[1] = Integer.parseInt(details[2]);
        return toReturn;
    }

    public void changeNotifText(String notifText) {
        gameNotifs.setText(notifText);
    }

    public RiskGameView(Map<String, Object> primitiveSettings, int soldiers, int mapNum) {
        this.riskGameController = new RiskGameController(primitiveSettings, soldiers);
        this.mapNum = String.valueOf(mapNum);
        if (!(boolean) riskGameController.getPrimitiveSettings().get("Placement")) {
            autoPlace();
        }
    }

    public void toggleColor(SVGPath path) {
        String styleClass = String.valueOf(path.getStyleClass());
        switch (styleClass) {
            case "country":
                path.getStyleClass().clear();
                path.getStyleClass().add("country_clicked");
                break;
            case "country_clicked":
                path.getStyleClass().clear();
                path.getStyleClass().add("country");
                break;
        }

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


    private String showWhatToDo() {
        return riskGameController.showWhatToDo();
    }

    public void showMap() {
        String toPrint = this.riskGameController.showMap();
        System.out.println(toPrint);
    }

    public String placeSoldier(int i, int j, int soldiers) {
        String toPrint = this.riskGameController.placeSoldier(i, j, soldiers);
        return toPrint;    }

    public String draft(int i, int j, int soldiers) {
        String toPrint = this.riskGameController.draft(i, j, soldiers);
        return toPrint;
    }

    public String attack(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        String toPrint = riskGameController.attack(sourceI, sourceJ, destI, destJ, soldiers);
        return toPrint;
    }

    public String fortify(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        String toPrint = this.riskGameController.fortify(sourceI, sourceJ, destI, destJ, soldiers);
        return toPrint;
    }

    public String next() {
        String toPrint = riskGameController.next();
        return toPrint;
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

    public void draftAfterWin(int i, int j, int soldiers) {
        String toPrint = riskGameController.draftAfterWin(i, j, soldiers);
        System.out.println(toPrint);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rightVBox.setSpacing(5);
        try {
            makeRightHBox();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setColorTurn();
        setColorMode();
        allPaths[0][0] = country_1_1;
        allPaths[0][1] = country_1_2;
        allPaths[0][2] = country_1_3;
        allPaths[0][3] = country_1_4;
        allPaths[0][4] = country_1_5;
        allPaths[1][0] = country_2_1;
        allPaths[1][1] = country_2_2;
        allPaths[1][2] = country_2_3;
        allPaths[1][3] = country_2_4;
        allPaths[1][4] = country_2_5;
        allPaths[2][0] = country_3_1;
        allPaths[2][1] = country_3_2;
        allPaths[2][2] = country_3_3;
        allPaths[2][3] = country_3_4;
        allPaths[2][4] = country_3_5;
        allPaths[3][0] = country_4_1;
        allPaths[3][1] = country_4_2;
        allPaths[3][2] = country_4_3;
        allPaths[3][3] = country_4_4;
        allPaths[3][4] = country_4_5;
        allPaths[4][0] = country_5_1;
        allPaths[4][1] = country_5_2;
        allPaths[4][2] = country_5_3;
        allPaths[4][3] = country_5_4;
        allPaths[4][4] = country_5_5;
        allLabels[0][0] = label_1_1;
        allLabels[0][1] = label_1_2;
        allLabels[0][2] = label_1_3;
        allLabels[0][3] = label_1_4;
        allLabels[0][4] = label_1_5;
        allLabels[1][0] = label_2_1;
        allLabels[1][1] = label_2_2;
        allLabels[1][2] = label_2_3;
        allLabels[1][3] = label_2_4;
        allLabels[1][4] = label_2_5;
        allLabels[2][0] = label_3_1;
        allLabels[2][1] = label_3_2;
        allLabels[2][2] = label_3_3;
        allLabels[2][3] = label_3_4;
        allLabels[2][4] = label_3_5;
        allLabels[3][0] = label_4_1;
        allLabels[3][1] = label_4_2;
        allLabels[3][2] = label_4_3;
        allLabels[3][3] = label_4_4;
        allLabels[3][4] = label_4_5;
        allLabels[4][0] = label_5_1;
        allLabels[4][1] = label_5_2;
        allLabels[4][2] = label_5_3;
        allLabels[4][3] = label_5_4;
        allLabels[4][4] = label_5_5;
        putCountryName();
        colorizeCountry();
        labelSetMouserTransparent();


    }

    public void putCountryName() {
        List<List<Country>> countries = this.riskGameController.getGameCountries();
        for (int i = 0; i < countries.size(); i++) {
            for (int j = 0; j < countries.get(i).size(); j++) {
                allLabels[i][j].setText(countries.get(i).get(j).getName());
            }
        }
    }

    public void labelSetMouserTransparent() {
        for (Label[] labels : allLabels) {
            for (Label label : labels) {
                if (label != null) {
                    label.setMouseTransparent(true);
                }
            }
        }
    }
    public void colorizeCountry() {
        List<List<Country>> countries = this.riskGameController.getGameCountries();
        for (int i = 0; i < countries.size(); i++) {
            for (int j = 0; j < countries.get(i).size(); j++) {
                Player owner = countries.get(i).get(j).getOwner();
                if (owner != null) {
                    String toClass = "country_player_" + owner.getPlayerNumber();
                    allPaths[i][j].getStyleClass().clear();
                    allPaths[i][j].getStyleClass().add(toClass);
                } else {
                    allPaths[i][j].getStyleClass().clear();
                    allPaths[i][j].getStyleClass().add("country_no_player");
                }
            }
        }
    }

    public void setColorMode() {
        setClassForShape(draftModeShape, "none_active");
        setClassForShape(attackModeShape, "none_active");
        setClassForShape(fortifyModeShape, "none_active");
        switch (showWhatToDo()) {
            case "Draft":
                draftModeShape.getStyleClass().clear();
                draftModeShape.getStyleClass().add("status_on");
                break;
            case "Attack":
                attackModeShape.getStyleClass().clear();
                attackModeShape.getStyleClass().add("status_on");
                break;
            case "Fortify":
                fortifyModeShape.getStyleClass().clear();
                fortifyModeShape.getStyleClass().add("status_on");
                break;
        }
    }

    public void setColorTurn() {
        setDefaultClasses(playersCircles, "none_active");
        int turnIndex = riskGameController.getCurrentPlayerIndex();
        playersCircles.get(turnIndex).getStyleClass().clear();
        playersCircles.get(turnIndex).getStyleClass().add("status_on");
    }

    public void setClassForShape(Shape shape, String className) {
        shape.getStyleClass().clear();
        shape.getStyleClass().add(className);
    }

    public void setDefaultClasses(List<Circle> shapes, String defaltClassName) {
        for (Shape shape : shapes) {
            setClassForShape(shape, defaltClassName);
        }
    }

    public void makeRightHBox() throws URISyntaxException {
        int i = 0;
        String playerImageAddress = "/images/player_";
        for (Player player : riskGameController.getPlayers()) {
            i++;
            Image image = new Image(String.valueOf(getClass().getResource(playerImageAddress + i + ".png").toURI()));
            Circle littleCircle = new Circle(10);
            littleCircle.getStyleClass().add("none_active");
            playersCircles.add(littleCircle);
            Circle bigCircle = new Circle(100);
            bigCircle.setFill(new ImagePattern(image));
            bigCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            HBox tempHBox = new HBox(littleCircle, bigCircle);
            tempHBox.setAlignment(Pos.CENTER);
            tempHBox.setSpacing(10);
            rightVBox.getChildren().add(tempHBox);
        }
    }

    public void insertImage(Shape shape, String imageAddress) throws URISyntaxException {
        Image image = new Image(String.valueOf(getClass().getResource(imageAddress).toURI()));
        shape.setFill(new ImagePattern(image));
        shape.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }

    @Override
    public void show(Stage window) throws IOException {

        String fileAddress = "/game/maps/map_" + mapNum + ".fxml";
        FXMLLoader root = new FXMLLoader(getClass().getResource(fileAddress));
        root.setController(this);
        window.setTitle("Game Started");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
        try {
            insertImage(draftCircleImage, "/images/draft.png");
            insertImage(attackCircleImage, "/images/attack.png");
            insertImage(fortifyCircleImage, "/images/fortify.png");
            insertImage(nextTurn, "/images/next.png");
            insertImage(nextStatus , "/images/next_status.png");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
