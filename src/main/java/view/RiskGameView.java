package view;

import controller.risk.RiskGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Country;
import model.Player;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class RiskGameView implements View, Initializable {
    private final RiskGameController riskGameController;
    private Stage gameWindow;
    private final String mapNum;
    private final SVGPath[][] allPaths = new SVGPath[5][5];
    private final Label[][] allLabels = new Label[5][5];
    private final List<Circle> playersCircles = new ArrayList<Circle>();
    private final List<Circle> playerColors = new ArrayList<Circle>();
    private final List<Label> playerLabels = new ArrayList<Label>();
    private final String[][] defaultClasses = new String[5][5];
    @FXML
    private Circle aClub;
    @FXML
    private Circle aHeart;
    @FXML
    private Circle aDiamond;
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
    private void matchHandler(MouseEvent e){
        String id = e.getPickResult().getIntersectedNode().getId();
    }
    @FXML
    private void cardsMenuHandler(MouseEvent e) throws IOException, URISyntaxException {
        Stage aboutStage = new Stage();
        FXMLLoader aboutRoot = new FXMLLoader(getClass().getResource("/game/cardsMenu.fxml"));
        aboutRoot.setController(this);
        aboutStage.setScene(new Scene(aboutRoot.load()));
        aboutStage.setResizable(false);
        aboutStage.initModality(Modality.WINDOW_MODAL);
        aboutStage.initOwner(gameWindow);
        insertImage(aHeart , "/images/A_heart.png");
        insertImage(aClub , "/images/A_club.png");
        insertImage(aDiamond , "/images/A_diamond.png");
        aboutStage.show();
    }
    @FXML
    private void countryClick(MouseEvent e) {
        int[] indices = getCountryIndices(e.getPickResult().getIntersectedNode().getId());
        int i = indices[0];
        int j = indices[1];
        int soliders = 0;
        try {
            soliders = Integer.parseInt(inputNumber.getText());
            if(riskGameController.getPlacementFinished()) {
                switch (showWhatToDo()) {
                    case "Draft":
                        if (!inputNumber.getText().isEmpty()) {
                            if (!riskGameController.getAttackWon()) {
                                changeNotifText(draft(i, j, soliders));
                            } else {
                                changeNotifText(draftAfterWin(i, j, soliders));
                            }
                        }
                        break;
                    case "Attack":
                        if (riskGameController.getI() == null || riskGameController.getJ() == null) {
                            if (riskGameController.checkCountryIsYours(i, j)) {
                                riskGameController.setI(i);
                                riskGameController.setJ(j);
                            } else {
                                changeNotifText("This Country Is Not Yours");
                            }
                        } else {
                            if (!inputNumber.getText().isEmpty()) {
                                changeNotifText(attack(riskGameController.getI(), riskGameController.getJ(),
                                        i, j, soliders));
                                try {
                                    makeRightHBox();
                                }catch (Exception error2){

                                }
                            }
                        }
                        break;
                    case "Fortify":
                        if (riskGameController.getI() == null || riskGameController.getJ() == null) {
                            if (riskGameController.checkCountryIsYours(i, j)) {
                                riskGameController.setI(i);
                                riskGameController.setJ(j);
                            } else {
                                changeNotifText("This Country Is Not Yours");
                            }
                        } else {
                            if (!inputNumber.getText().isEmpty()) {
                                changeNotifText(fortify(riskGameController.getI(), riskGameController.getJ(),
                                        i, j, soliders));
                            }
                        }
                        break;
                }
            }else{
                if(riskGameController.getAllPlayersAdded()){
                    riskGameController.beginDraft(i,j,soliders);
                }else{
                    riskGameController.beginDraft(i , j , 1);
                }
            }
        }
        catch (NumberFormatException ex) {
            changeNotifText("please insert a number");
        }
        colorizeCountry();
        setColorMode();
        putCountryName();
        updatePlayerLabels();

    }
    @FXML
    private void nextStatus(MouseEvent e){
        changeNotifText(next());
        setColorMode();
        colorizeCountry();
        updatePlayerLabels();
    }
    @FXML
    private void nextTurnHandler(MouseEvent e) {
        changeNotifText(nextTurn());
        colorizeCountry();
        setColorTurn();
        setColorMode();
        updatePlayerLabels();
    }
    @FXML
    private void deselectHandler(MouseEvent e){
        deselect();
        colorizeCountry();
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


    private String showWhatToDo() {
        return riskGameController.showWhatToDo();
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

    public String nextTurn() {
        String toPrint = riskGameController.changeTurn();
        return toPrint;
    }

    public void showTurn() {
        String toPrint = riskGameController.showTurn();
    }

    public void showOptions() {
        String toPrint = riskGameController.showMatchOptions();
    }

    public void matchCards(int typical) {
        String toPrint = riskGameController.matchCards(typical);
    }

    public String draftAfterWin(int i, int j, int soldiers) {
        String toPrint = riskGameController.draftAfterWin(i, j, soldiers);
        return toPrint;
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
        int row = countries.size();
        int columns = countries.get(0).size();
        int[][] toShowFog = new int[row][columns];
        toShowFog = riskGameController.getFogOfWarMap(riskGameController.getCurrentPlayer());
        for (int i = 0; i < countries.size(); i++) {
            for (int j = 0; j < countries.get(i).size(); j++) {
                allLabels[i][j].setText("");
                if(!riskGameController.getFogStatus() || !riskGameController.getPlacementFinished()) {
                    allLabels[i][j].setText(countries.get(i).get(j).getName() + "\n" + countries.get(i).get(j).getSoldiers());
                }else{
                    int numberCheck = toShowFog[i][j];
                    if(numberCheck == 1 || numberCheck == 2){
                        allLabels[i][j].setText(countries.get(i).get(j).getName() + "\n" + countries.get(i).get(j).getSoldiers());
                    }
                }
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
        int row = countries.size();
        int columns = countries.get(0).size();
        int[][] toShowFog = new int[row][columns];
        toShowFog = riskGameController.getFogOfWarMap(riskGameController.getCurrentPlayer());
        boolean fogStatus = riskGameController.getFogStatus();
        for (int i = 0; i < countries.size(); i++) {
            for (int j = 0; j < countries.get(i).size(); j++) {
                Player owner = countries.get(i).get(j).getOwner();
                if (owner != null) {
                    if(fogStatus) {
                        int statusNumber = toShowFog[i][j];
                        if(statusNumber == 1 || statusNumber == 2) {
                            String toClass = "country_player_" + owner.getPlayerNumber();
                            allPaths[i][j].getStyleClass().clear();
                            allPaths[i][j].getStyleClass().add(toClass);
                        }else{
                            String toClass = "country_fog";
                            allPaths[i][j].getStyleClass().clear();
                            allPaths[i][j].getStyleClass().add(toClass);
                        }
                    }else{
                        String toClass = "country_player_" + owner.getPlayerNumber();
                        allPaths[i][j].getStyleClass().clear();
                        allPaths[i][j].getStyleClass().add(toClass);
                    }
                } else {
                    allPaths[i][j].getStyleClass().clear();
                    allPaths[i][j].getStyleClass().add("country_no_player");
                }
            }
        }
        addColorToSelected();
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
    public void resetInput(){
        inputNumber.setText("");
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
        int bigCircleSize = 0;
        if(riskGameController.getPlayers().size() <=3){
            bigCircleSize = 100;
        }else{
            bigCircleSize = 60;
        }
        rightVBox.getChildren().clear();
        playerLabels.clear();
        playersCircles.clear();
        for (Player player : riskGameController.getPlayers()) {
            i++;
            Image image = new Image(String.valueOf(getClass().getResource(playerImageAddress + i + ".png").toURI()));
            Circle littleCircle = new Circle(10);
            littleCircle.getStyleClass().add("none_active");
            playersCircles.add(littleCircle);


            Label playerLabel = new Label(String.valueOf(player.getDraftSoldiers()));
            playerLabel.getStyleClass().add("player_label");
            playerLabels.add(playerLabel);

            Circle playerColorCircle = new Circle(10);
            playerColorCircle.getStyleClass().add("country_player_color_" + player.getPlayerNumber());
            VBox verticalBox = new VBox(littleCircle, playerColorCircle, playerLabel);
            verticalBox.setAlignment(Pos.CENTER);
            verticalBox.setSpacing(10);

            Circle bigCircle = new Circle(bigCircleSize);
            bigCircle.setFill(new ImagePattern(image));
            bigCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            HBox tempHBox = new HBox(verticalBox, bigCircle);
            tempHBox.setAlignment(Pos.CENTER);
            tempHBox.setSpacing(10);
            rightVBox.getChildren().add(tempHBox);
        }
        setColorTurn();
    }

    public void updatePlayerLabels(){
        int i = 0;
        for(Label label : playerLabels){
            label.setText(String.valueOf(riskGameController.getPlayers().get(i).getDraftSoldiers()));
            i++;
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
        gameWindow = window;
        window.setTitle("Game Started");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
        try {
            insertImage(draftCircleImage, "/images/draft.png");
            insertImage(attackCircleImage, "/images/attack.png");
            insertImage(fortifyCircleImage, "/images/fortify.png");
            insertImage(nextTurn, "/images/next.png");
            insertImage(nextStatus , "/images/next_status.png");
            insertImage(deselectIcon , "/images/deselect.png");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void addColorToSelected(){
        Integer i = riskGameController.getI();
        Integer j = riskGameController.getJ();
        if(i != null && j != null){
            allPaths[i-1][j-1].getStyleClass().clear();
            allPaths[i-1][j-1].getStyleClass().add("country_selected_source");
        }
    }

}
