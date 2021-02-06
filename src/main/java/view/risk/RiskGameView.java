package view.risk;

import com.jfoenix.controls.JFXHamburger;
import controller.ClientMasterController.ClientMasterController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
import main.Client;
import model.Country;
import model.Gamer;
import model.Player;
import org.controlsfx.control.Notifications;
import view.View;
import view.ViewHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
//import javax.xml.bind.SchemaOutputResolver;

public class RiskGameView implements View, Initializable {
    public long currentTimeStamp = System.currentTimeMillis() / 1000L;
    private final ClientMasterController controller;
    private final String mapNum;
    private final SVGPath[][] allPaths = new SVGPath[5][5];
    private final Label[][] allLabels = new Label[5][5];
    private final Map<Integer, Circle> playersCircles = new HashMap<>();
    private final List<Label> playerLabels = new ArrayList<>();
    private Stage gameWindow;
    private int duration;
    private Stage aboutStage;
    private AnimationTimer timer;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private HBox requestsHBox;
    @FXML
    private JFXHamburger cardMenu;
    @FXML
    private Rectangle loseManual;
    @FXML
    private Circle aClub;
    @FXML
    private Circle aHeart;
    @FXML
    private Circle aDiamond;
    @FXML
    private Label card1Label;
    @FXML
    private Label card2Label;
    @FXML
    private Label card3Label;
    @FXML
    private Button match1;
    @FXML
    private Button match2;
    @FXML
    private Button match3;
    @FXML
    private Button matchAll;
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

    /* public RiskGameView(Map<String, Object> primitiveSettings, int soldiers, Event event) {
        // this.riskGameController = new RiskGameController(primitiveSettings, soldiers, event);
         this.mapNum = String.valueOf((int) primitiveSettings.get("Map Number"));
         this.duration = (int) primitiveSettings.get("Duration");

     }*/
    public RiskGameView(String availableGameId) {
        //
        controller = Client.getConnector().getController();
        //
        Client.getClientInfo().setAvailableGameId(availableGameId);
        //

        //
        this.mapNum = String.valueOf((int) Math.round((Double) controller.getPrimitiveSettings().get("Map Number")));
        //
        this.duration = (int) Math.round((Double) controller.getPrimitiveSettings().get("Duration"));
        if (!(Boolean) controller.getPrimitiveSettings().get("Placement")) {
            //todo auto place must run only once
            //autoPlace();
        }

    }

    @Override
    public void show(Stage window) throws IOException {

        String fileAddress = "/game/maps/map_" + mapNum + ".fxml";


        FXMLLoader root = new FXMLLoader(getClass().getResource(fileAddress));

        root.setController(this);
        gameWindow = window;
        window.setTitle("Risk Game");

        window.setScene(new Scene(root.load()));

        window.setResizable(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            insertImage(draftCircleImage, "/images/draft.png");
            insertImage(attackCircleImage, "/images/attack.png");
            insertImage(fortifyCircleImage, "/images/fortify.png");
            insertImage(nextTurn, "/images/next.png");
            insertImage(nextStatus, "/images/next_status.png");
            insertImage(deselectIcon, "/images/deselect.png");
            insertImage(loseManual, "/images/exit.png");
        } catch (URISyntaxException e) {

            e.printStackTrace();
        }

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

        sendProgressBar();

        progress();


    }

    @FXML
    private void loseManually(MouseEvent e) throws URISyntaxException {
        changeNotifText(controller.leaveTheGame());
        makeRightHBox();
        colorizeCountry();
        putCountryName();
        updatePlayerLabels();
        if (!controller.getGameIsPlaying()) {
            ViewHandler.getViewHandler().exitGame();
        }
    }

    @FXML
    private void match1Handler(MouseEvent e) {
        matchCards(1);
        setMyCardsLabels();
        updatePlayerLabels();
    }

    @FXML
    private void match2Handler(MouseEvent e) {
        matchCards(2);
        setMyCardsLabels();
        updatePlayerLabels();
    }

    @FXML
    private void match3Handler(MouseEvent e) {
        matchCards(3);
        setMyCardsLabels();
        updatePlayerLabels();
    }

    @FXML
    private void matchAllHandler(MouseEvent e) {
        matchCards(4);
        setMyCardsLabels();
        updatePlayerLabels();
    }

    @FXML
    private void cardsMenuHandler(MouseEvent e) throws IOException, URISyntaxException {
        if (checkCurrentPlayer()) {
            Stage aboutStage = new Stage();
            this.aboutStage = aboutStage;
            FXMLLoader aboutRoot = new FXMLLoader(getClass().getResource("/game/cardsMenu.fxml"));
            aboutRoot.setController(this);
            aboutStage.setScene(new Scene(aboutRoot.load()));
            aboutStage.setResizable(false);
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.initOwner(gameWindow);
            insertImage(aHeart, "/images/A_heart.png");
            insertImage(aClub, "/images/A_clubs.png");
            insertImage(aDiamond, "/images/A_diamond.png");
            setMyCardsLabels();
            aboutStage.show();
        } else {
            changeNotifText("It's not your turn");
        }
    }

    @FXML
    private void backToAbout(MouseEvent e) throws IOException, URISyntaxException {
        FXMLLoader requestRoot = new FXMLLoader(getClass().getResource("/game/cardsMenu.fxml"));
        requestRoot.setController(this);
        aboutStage.setScene(new Scene(requestRoot.load()));
        insertImage(aHeart, "/images/A_heart.png");
        insertImage(aClub, "/images/A_clubs.png");
        insertImage(aDiamond, "/images/A_diamond.png");
        setMyCardsLabels();
        aboutStage.show();
    }

    @FXML
    private void requestsMenu(MouseEvent e) throws IOException {
        FXMLLoader requestRoot = new FXMLLoader(getClass().getResource("/game/requests.fxml"));
        requestRoot.setController(this);
        aboutStage.setScene(new Scene(requestRoot.load()));
        updateRequestsBox();
        aboutStage.show();
    }

    @FXML
    private void countryClick(MouseEvent e) {
        if (checkCurrentPlayer()) {
            int[] indices = getCountryIndices(e.getPickResult().getIntersectedNode().getId());
            int i = indices[0];
            int j = indices[1];
            int soliders = 0;
            try {
                soliders = Integer.parseInt(inputNumber.getText());

                if (controller.getPlacementFinished()) {
                    switch (showWhatToDo()) {
                        case "Draft":

                            if (!inputNumber.getText().isEmpty()) {

                                if (!controller.getAttackWon()) {

                                    changeNotifText(draft(i, j, soliders));
                                } else {

                                    changeNotifText(draftAfterWin(i, j, soliders));
                                }
                            }
                            break;
                        case "Attack":


                            if (controller.getI() == null || controller.getJ() == null) {
                                if (controller.checkCountryIsYours(i, j)) {
                                    controller.setI(i);
                                    controller.setJ(j);
                                } else {
                                    changeNotifText("This Country Is Not Yours");
                                }
                            } else {

                                if (!inputNumber.getText().isEmpty()) {
                                    changeNotifText(attack(controller.getI(), controller.getJ(),
                                            i, j, soliders));
                                    try {
                                        makeRightHBox();
                                    } catch (Exception error2) {

                                    }
                                }
                            }
                            break;
                        case "Fortify":


                            if (controller.getI() == null || controller.getJ() == null) {
                                if (controller.checkCountryIsYours(i, j)) {
                                    controller.setI(i);
                                    controller.setJ(j);
                                } else {
                                    changeNotifText("This Country Is Not Yours");
                                }
                            } else {
                                if (!inputNumber.getText().isEmpty()) {
                                    changeNotifText(fortify(controller.getI(), controller.getJ(),
                                            i, j, soliders));
                                }
                            }
                            break;
                    }
                } else {


                    if (controller.getAllPlayersAdded()) {
                        controller.beginDraft(i, j, soliders);


                    } else {
                        controller.beginDraft(i, j, 1);


                    }
                }
            } catch (NumberFormatException ex) {
                changeNotifText("please insert a number");
            }
        } else {
            changeNotifText("It's not your turn");
        }
        setColorTurn();
        colorizeCountry();
        setColorMode();
        putCountryName();
        updatePlayerLabels();
    }

    @FXML
    private void nextStatus(MouseEvent e) {
        changeNotifText(next());
        setColorMode();
        colorizeCountry();
        updatePlayerLabels();
        putCountryName();
    }

    @FXML
    private void nextTurnHandler(MouseEvent e) {
        changeNotifText(nextTurn());
        colorizeCountry();
        setColorTurn();
        setColorMode();
        updatePlayerLabels();
        putCountryName();
    }

    @FXML
    private void deselectHandler(MouseEvent e) {
        deselect();
        colorizeCountry();
    }

    private void deselect() {
        controller.deselect();
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
        return controller.showWhatToDo();
    }

    public String placeSoldier(int i, int j, int soldiers) {
        String toPrint = this.controller.placeSoldier(i, j, soldiers);
        return toPrint;
    }

    public String draft(int i, int j, int soldiers) {
        String toPrint = this.controller.draft(i, j, soldiers);
        return toPrint;
    }

    public String attack(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        String toPrint = controller.attack(sourceI, sourceJ, destI, destJ, soldiers);
        return toPrint;
    }

    public String fortify(int sourceI, int sourceJ, int destI, int destJ, int soldiers) {
        String toPrint = this.controller.fortify(sourceI, sourceJ, destI, destJ, soldiers);
        return toPrint;
    }

    public String next() {
        String toPrint = "";
        if (checkCurrentPlayer()) {
            toPrint = controller.next();
        } else {
            toPrint = "Get the fuck out of here";
        }

        return toPrint;
    }

    public boolean checkCurrentPlayer() {
        return Client.getClientInfo().getLoggedInUsername().equals(controller.getCurrentPlayer().getUsername());
    }

    public void autoPlace() {
        controller.autoPlace();
    }

    public String nextTurn() {
        String toPrint = controller.changeTurn();
        sendNotif();
        return toPrint;
    }

    public void updateCurrentTime() {
        currentTimeStamp = controller.getCurrentTimestamp();
    }

    public void showTurn() {
        String toPrint = controller.showTurn();
    }

    public void showOptions() {
        String toPrint = controller.showMatchOptions();
    }

    public String matchCards(int typical) {
        String toPrint = controller.matchCards(typical);
        return toPrint;
    }

    public String draftAfterWin(int i, int j, int soldiers) {
        String toPrint = controller.draftAfterWin(i, j, soldiers);
        return toPrint;
    }


    public void putCountryName() {
        List<List<Country>> countries = controller.getGameCountries();
        int row = countries.size();
        int columns = countries.get(0).size();
        int[][] toShowFog = new int[row][columns];

        toShowFog = controller.getFogOfWarMap(controller.getCurrentPlayer());
        for (int i = 0; i < countries.size(); i++) {
            for (int j = 0; j < countries.get(i).size(); j++) {
                allLabels[i][j].setText("");
                if (!controller.getFogStatus() || !controller.getPlacementFinished()) {
                    allLabels[i][j].setText(countries.get(i).get(j).getName() + "\n" + countries.get(i).get(j).getSoldiers());
                } else {
                    int numberCheck = toShowFog[i][j];
                    if (numberCheck == 1 || numberCheck == 2) {
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
        List<List<Country>> countries = this.controller.getGameCountries();
        int row = countries.size();
        int columns = countries.get(0).size();
        int[][] toShowFog = new int[row][columns];
        toShowFog = controller.getFogOfWarMap(controller.getCurrentPlayer());
        boolean fogStatus = controller.getFogStatus();
        for (int i = 0; i < countries.size(); i++) {
            for (int j = 0; j < countries.get(i).size(); j++) {

                Gamer owner = countries.get(i).get(j).getOwner();
                allPaths[i][j].setMouseTransparent(false);
                if (owner != null) {
                    if (fogStatus) {
                        int statusNumber = toShowFog[i][j];
                        if (statusNumber == 1 || statusNumber == 2) {
                            String toClass = "country_player_" + owner.getPlayerNumber();
                            allPaths[i][j].getStyleClass().clear();
                            allPaths[i][j].getStyleClass().add(toClass);
                        } else {
                            String toClass = "country_fog";
                            allPaths[i][j].getStyleClass().clear();
                            allPaths[i][j].getStyleClass().add(toClass);
                            allPaths[i][j].setMouseTransparent(true);
                        }
                    } else {

                        String toClass = "country_player_" + owner.getPlayerNumber();
                        allPaths[i][j].getStyleClass().clear();
                        allPaths[i][j].getStyleClass().add(toClass);
                    }
                } else {

                    allPaths[i][j].getStyleClass().clear();
                    allPaths[i][j].getStyleClass().add("country_no_player");
                }
                if (countries.get(i).get(j).getBlizzard()) {
                    allPaths[i][j].getStyleClass().clear();
                    allPaths[i][j].getStyleClass().add("blizzard");
                    allPaths[i][j].setMouseTransparent(true);
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

    public void resetInput() {
        inputNumber.setText("");
    }

    public void setColorTurn() {
        controller.checkWinner();
        if (controller.getGameIsPlaying()) {
            for (Map.Entry<Integer, Circle> entry : playersCircles.entrySet()) {
                entry.getValue().getStyleClass().clear();
                entry.getValue().getStyleClass().add("none_active");
            }

            int turnIndex = controller.getCurrentPlayerIndex();
            playersCircles.get(turnIndex).getStyleClass().clear();
            playersCircles.get(turnIndex).getStyleClass().add("status_on");
            for (Gamer player : controller.getCurrentPlayer().getGameFriends()) {
                int playerNumber = player.getPlayerNumber();
                playersCircles.get(playerNumber - 1).getStyleClass().clear();
                playersCircles.get(playerNumber - 1).getStyleClass().add("friend");
            }
        }
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

    public void notification(String title, String inputText) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Notifications notify = Notifications.create().title(title)
                        .text(inputText)
                        .hideAfter(javafx.util.Duration.seconds(2))
                        .position(Pos.TOP_RIGHT);
                notify.darkStyle();
                notify.showInformation();
            }
        });


    }

    public void updateRequestsBox() {
        requestsHBox.getChildren().clear();
        EventHandler<MouseEvent> accept = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int playerNumber = Integer.parseInt(event.getPickResult().getIntersectedNode().getId().split("\\_")[1]);
                Gamer player = controller.getPlayerByPlayerNumber(playerNumber);
                if (player != null) {
                    notification("Accepted!", "You accepted this player as friend");
                    controller.getCurrentPlayer().getRequests().remove(player);
                    controller.getCurrentPlayer().getGameFriends().add(player);
                    player.getGameFriends().add(controller.getCurrentPlayer());
                    updateRequestsBox();
                }
                event.consume();
            }
        };
        EventHandler<MouseEvent> decline = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int playerNumber = Integer.parseInt(event.getPickResult().getIntersectedNode().getId().split("\\_")[1]);
                Gamer player = controller.getPlayerByPlayerNumber(playerNumber);
                if (player != null) {
                    notification("Declined!", "You declined this friend");
                    controller.getCurrentPlayer().getRequests().remove(player);
                    updateRequestsBox();
                }
                event.consume();
            }
        };
        for (Gamer player : controller.getCurrentPlayer().getRequests()) {
            String characterAddress = "/images/player_" + player.getPlayerNumber() + ".png";
            Circle requestedCharacter = new Circle(60);
            try {
                insertImage(requestedCharacter, characterAddress);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Button acceptButton = new Button("Accept");
            acceptButton.setPrefSize(100, 40);
            acceptButton.getStyleClass().add("accept_button");
            acceptButton.setId("accept_" + player.getPlayerNumber());
            acceptButton.setOnMouseClicked(accept);

            Button declineButton = new Button("Decline");
            declineButton.setPrefSize(100, 40);
            declineButton.getStyleClass().add("decline_button");
            declineButton.setId("decline_" + player.getPlayerNumber());
            declineButton.setOnMouseClicked(decline);

            VBox playerVBox = new VBox(requestedCharacter, acceptButton, declineButton);
            playerVBox.setAlignment(Pos.CENTER);

            playerVBox.setSpacing(10);
            requestsHBox.getChildren().add(playerVBox);
        }
    }

    public void setMyCardsLabels() {
        int[] cardsNumbers = controller.getCurrentPlayer().getCardsNumber();
        int number1 = cardsNumbers[0];
        int number2 = cardsNumbers[1];
        int number3 = cardsNumbers[2];
        card1Label.setText(String.valueOf(number1));
        card2Label.setText(String.valueOf(number2));
        card3Label.setText(String.valueOf(number3));
    }

    public void sendNotif() {
        boolean checkHasRequest = controller.getCheckRequests();
        if (checkHasRequest && !controller.getNotifSent()) {
            notification("Requests", "You have request(s)");
            controller.notifSent();
        }
    }

    public void makeRightHBox() throws URISyntaxException {
        EventHandler<MouseEvent> addFriendHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int playerNumber = Integer.parseInt(event.getPickResult().getIntersectedNode().getId().split("\\_")[1]);
                Gamer player = controller.getPlayerByPlayerNumber(playerNumber);
                if (player != null) {
                    if (!player.equals(controller.getCurrentPlayer())) {
                        changeNotifText(controller.addRequest(player));
                    } else {
                        changeNotifText("You can`t send friendship to yourself");
                    }
                }
                event.consume();
            }
        };


        //String playerImageAddress = "/images/player_";
        int bigCircleSize;
        if (controller.getRiskPlayers().size() <= 3) {
            bigCircleSize = 100;
        } else {
            bigCircleSize = 60;
        }
        rightVBox.getChildren().clear();
        playerLabels.clear();
        playersCircles.clear();

        for (Gamer player : controller.getRiskPlayers()) {
            Image friendImage = new Image(String.valueOf(getClass().getResource("/images/friend.png")));
            Circle friendCircle = new Circle(20);
            friendCircle.setFill(new ImagePattern(friendImage));
            friendCircle.setOnMouseClicked(addFriendHandler);
            friendCircle.setId("friend_" + player.getPlayerNumber());



            Image image = new Image(player.getImageURL());
            Circle littleCircle = new Circle(10);

            littleCircle.getStyleClass().add("none_active");
            playersCircles.put(player.getPlayerNumber(), littleCircle);


            Label playerLabel = new Label(String.valueOf(player.getDraftSoldiers()));
            playerLabel.getStyleClass().add("player_label");
            playerLabels.add(playerLabel);


            Circle playerColorCircle = new Circle(10);
            playerColorCircle.getStyleClass().add("country_player_color_" + player.getPlayerNumber());
            VBox verticalBox = new VBox(littleCircle, playerColorCircle, playerLabel);
            verticalBox.setAlignment(Pos.CENTER);
            verticalBox.setSpacing(10);


            Circle bigCircle = new Circle(bigCircleSize);
            bigCircle.setFill(new ImagePattern(new Image(player.getImageURL())));
            bigCircle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            HBox tempHBox = new HBox(friendCircle, verticalBox, bigCircle);
            tempHBox.setAlignment(Pos.CENTER);
            tempHBox.setSpacing(10);
            rightVBox.getChildren().add(tempHBox);


        }
        setColorTurn();
    }

    public void sendProgressBar() {
        controller.getProgressBar(progressBar);
    }

    public void updatePlayerLabels() {
        int i = 0;
        for (Label label : playerLabels) {
            label.setText(String.valueOf(controller.getRiskPlayers().get(i).getDraftSoldiers()));
            i++;
        }
    }

    public void insertImage(Shape shape, String imageAddress) throws URISyntaxException {
        Image image = new Image(String.valueOf(getClass().getResource(imageAddress).toURI()));
        shape.setFill(new ImagePattern(image));
        shape.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }


    public void progress() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double progressed = Double.valueOf((double) System.currentTimeMillis() / 1000 - currentTimeStamp) / Double.valueOf(duration);
                progressBar.setProgress(progressed);
                if (progressed >= 1) {
                    if (controller.getCurrentPlayer().getUsername().equals(Client.getClientInfo().getLoggedInUsername())) {
//                        System.out.println("Before : " + controller.getCurrentPlayer().getUsername());
                        controller.mainChangeTurn();
                        changeNotifText("Your time has been finished");
//                        System.out.println("After : " + controller.getCurrentPlayer().getUsername());
                        System.out.println(controller.getCurrentPlayer().getUsername());
                    }

                    currentTimeStamp = System.currentTimeMillis() / 1000L;
                    progressBar.setProgress(0);
                    controller.updateCurrentTime();
                    controller.updateRiskGameModel();
                }
            }
        };
        timer.start();
    }

    public void addColorToSelected() {
        Integer i = controller.getI();
        Integer j = controller.getJ();
        if (i != null && j != null) {
            allPaths[i - 1][j - 1].getStyleClass().clear();
            allPaths[i - 1][j - 1].getStyleClass().add("country_selected_source");
        }
    }

    public void updateMap() {
        colorizeCountry();
        setColorMode();
        putCountryName();
        updatePlayerLabels();
        updateCurrentTimestamp();
        setColorTurn();
    }

    public void updateCurrentTimestamp() {
        this.currentTimeStamp = controller.getCurrentTimestamp();
    }
}
