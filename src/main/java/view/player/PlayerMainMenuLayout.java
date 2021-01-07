package view.player;

import controller.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.TabHandler;
import view.View;
import view.ViewHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerMainMenuLayout implements View, Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField searchUsername;

    @FXML
    private Label moneyMenuBar = new Label();
    @FXML
    private Label scoreMenuBar = new Label();
    PlayerMainMenuController controller = new PlayerMainMenuController();

    public PlayerMainMenuLayout() {
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/playerMainMenuLayout.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMenuBar();
        TabHandler.getTabHandler().setBorderPane(borderPane);
        viewMainMenu();
    }

    @FXML
    private void search() {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            TabHandler.getTabHandler().push(
                    new PlayerProfileView(controller.searchPlayer(searchUsername.getText())));
        }
    }

    @FXML
    private void platoMessage(ActionEvent actionEvent) throws IOException {
        ViewHandler.getViewHandler().push(new PlatoMessageView());
    }

    @FXML
    private void back() {
        TabHandler.getTabHandler().back();
    }

    @FXML
    private void ahead() {
        TabHandler.getTabHandler().ahead();
    }

    @FXML
    private void viewMainMenu() {
        TabHandler.getTabHandler().push(new PlayerMainMenu());
    }

    @FXML
    private void viewFriendsMenu() {
        TabHandler.getTabHandler().push(new PlayerFriendsMenu());
    }

    @FXML
    private void viewGamesMenu() {
        TabHandler.getTabHandler().push(new PlayerGamesMenu());
    }

    @FXML
    private void viewAccountMenu() {
        TabHandler.getTabHandler().push(new PlayerAccountMenu());
    }

    public void initializeMenuBar() {
        scoreMenuBar.setText(controller.showPoints());
        moneyMenuBar.setText(controller.getMoney());
    }
}
