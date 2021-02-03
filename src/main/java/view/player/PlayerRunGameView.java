package view.player;

import com.jfoenix.controls.JFXTextField;
import controller.ClientMasterController.ClientMasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Client;
import model.Entry.AvailableGameEntry;
import model.Entry.GameEntry;
import view.AlertMaker;
import view.Tab;
import view.TabHandler;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayerRunGameView implements Tab, Initializable {
    private final ContextMenu searchContextMenu;
    private final ArrayList<String> usernames;
    private final ClientMasterController controller;
    @FXML
    private JFXTextField player2Name;
    @FXML
    private JFXTextField player3Name;
    @FXML
    private JFXTextField player4Name;
    @FXML
    private JFXTextField player5Name;
    @FXML
    private ImageView gameAvatar;
    @FXML
    private ImageView player2Avatar;
    @FXML
    private ImageView player3Avatar;
    @FXML
    private ImageView player4Avatar;
    @FXML
    private ImageView player5Avatar;
    @FXML
    private Label eventMode;

    @FXML
    private ToggleButton friendToggle;

    @FXML
    private ToggleButton manualPlacementToggle;

    @FXML
    private ToggleButton blizzardToggle;

    @FXML
    private Button startButton;

    @FXML
    private ToggleButton fogWarToggle;

    @FXML
    private TextField playerNum;

    @FXML
    private TextField mapNum;

    @FXML
    private TextField limitTimeNum;

    @FXML
    private TableView<AvailableGameEntry> currentGames;

    public PlayerRunGameView(String gameName, String eventId) {
        Client.getClientInfo().setGameName(gameName);
        Client.getClientInfo().setEventId(eventId);
        controller = Client.getConnector().getController();
        usernames = new ArrayList<>();
        //usernames.add(Client.getClientInfo().getLoggedIn().getUsername());
        searchContextMenu = new ContextMenu();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/player/playerRunGameView.fxml"));
        loader.setController(this);
        return loader.load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeInfo();
        initializeCurrentGamesTable();
    }

    @FXML
    void playerSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) return;
        JFXTextField textField = (JFXTextField) event.getSource();
        String searchQuery = textField.getText();
        searchContextMenu.getItems().clear();
        searchContextMenu.getItems().addAll(controller.getSearchQuery(textField, searchQuery, this));
        searchContextMenu.show(textField, Side.RIGHT, 0, 0);
    }

    @FXML
    void start() {
        controller.runGame(usernames);
    }

    @FXML
    void cancel() {
        TabHandler.getTabHandler().back();
    }

    private void initializeInfo() {
        gameAvatar.setImage(controller.getGameImage());
        eventMode.setText(controller.getEventMode());
    }

    @FXML
    void update(ActionEvent event) {
        JFXTextField textField = (JFXTextField) event.getSource();
        update(textField);
    }

    public void update(JFXTextField textField) {
        if (!controller.usernameExist(textField.getText())) {
            AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                    TabHandler.getTabHandler().getStackRoot().getChildren().get(0), "Okay",
                    "Invalid username", "Username does not exist!");
        } else if (usernames.contains(textField.getText())) {
            AlertMaker.showMaterialDialog(TabHandler.getTabHandler().getStackRoot(),
                    TabHandler.getTabHandler().getStackRoot().getChildren().get(0), "Okay",
                    "Invalid username", "Username is already added!");
        } else {
            updateImageMenuContext(textField);
            textField.setDisable(true);
        }
    }

    private void updateImage(ImageView imageView, String username) {
        imageView.setImage(controller.getUsernameImage(username));
        usernames.add(username);
    }

    private void updateImageMenuContext(JFXTextField textField) {
        if (textField.equals(player2Name)) {
            updateImage(player2Avatar, textField.getText());
        } else if (textField.equals(player3Name)) {
            updateImage(player3Avatar, textField.getText());
        } else if (textField.equals(player4Name)) {
            updateImage(player4Avatar, textField.getText());
        } else if (textField.equals(player5Name)) {
            updateImage(player5Avatar, textField.getText());

        }
    }

    public static boolean checkToggle(boolean inputBoolean) {
        if (inputBoolean) {
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void friendAction() {
        allianceType(checkToggle(!friendToggle.isSelected()));
    }

    @FXML
    public void blizzardAction() {
        blizzardsType(checkToggle(!blizzardToggle.isSelected()));
    }

    @FXML
    public void manualPlacement() {
        placementType(checkToggle(!manualPlacementToggle.isSelected()));
    }

    @FXML
    public void fogWar() {
        fogsType(checkToggle(!fogWarToggle.isSelected()));
    }

    @FXML
    public void playersNum() {
        String playersNum = playerNum.getText();
        changePlayersNumber(playersNum);
    }

    @FXML
    public void startButtonClick() {
        chooseMapNumber(mapNum.getText());
        changeDurationTime(limitTimeNum.getText());
        changePlayersNumber(playerNum.getText());
        int mapNum = (int) controller.getPrimitiveSettings().get("Map Number");
        if (mapNum <= 10 && mapNum >= 1) {
            ViewHandler.getViewHandler().push(this.controller.startGame());
        }
    }

    public boolean checkIntInput(Object input) {
        if (input instanceof Integer) {
            return true;
        } else {
            return false;
        }
    }

    public void changePlayersNumber(String strNumber) {
        int playerNumber = Integer.parseInt(strNumber);
        String callback = controller.setPlayerNumber(playerNumber);
        System.out.println(callback);

    }

    public void chooseMapNumber(String strNumber) {
        int mapNumber = Integer.parseInt(strNumber);
        String callback = controller.setMapNumber(mapNumber);
        System.out.println(callback);
    }

    public void changeDurationTime(String strNumber) {
        int number = Integer.parseInt(strNumber);
        String callback = controller.setDurationTime(number);
        System.out.println(callback);
    }

    public void placementType(boolean type) {
        controller.setPlacementType(type);
    }

    public void allianceType(boolean selected) {
        controller.setAllianceType(selected);
    }

    public void blizzardsType(boolean type) {
        controller.setBlizzardsType(type);
    }

    public void fogsType(boolean type) {
        controller.setFogType(type);
    }

    private void initializeCurrentGamesTable()
    {
        TableColumn<AvailableGameEntry, ImageView> gameNameColumn = new TableColumn<>("Game Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));


        currentGames.setPlaceholder(new Label("No game has been created."));
        currentGames.getColumns().addAll(gameNameColumn);
        currentGames.getItems().addAll(controller.getAvailableGames());
    }
    @FXML
    void gameTableSelect(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (currentGames.getSelectionModel().getSelectedItems().size() != 0) {
                    AvailableGameEntry gameEntry = currentGames.getSelectionModel().getSelectedItems().get(0);
                    TabHandler.getTabHandler().push(new PlayerAvailableGameView(gameEntry.getAvailableGameId()));
                }
            }

        }
    }
}
