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
        viewMainMenu();
    }

    @FXML
    private void search() throws IOException {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            //ViewHandler.getViewHandler().push(new
            //  ProfileView(controller.searchPlayer(searchUsername.getText())));
            //FXMLLoader parent = new FXMLLoader(getClass().getResource("/plato/profileView.fxml"));
            //FXMLLoader parent =new FXMLLoader(getClass().getResource("/plato/profileView.fxml"));
           // parent.setController(new ProfileView(controller.searchPlayer(searchUsername.getText())));
            //invisibleTab.setContent(parent.load());
            //invisibleTab.setStyle("-fx-tooltip-visible false");
            //tabPane.getSelectionModel().select(invisibleTab);;
            //parent.setController();
        }
    }

    @FXML
    private void platoMessage(ActionEvent actionEvent) throws IOException {
        ViewHandler.getViewHandler().push(new PlatoMessageView());
    }
    @FXML
    private void back()
    {
        borderPane.setCenter(TabHandler.getTabHandler().back());
    }
    @FXML
    private void ahead()
    {
        borderPane.setCenter(TabHandler.getTabHandler().ahead());
    }

    @FXML
    private void viewMainMenu() {
        System.out.println("main menu");
        borderPane.setCenter(TabHandler.getTabHandler().push(new PlayerMainMenu()));
    }

    @FXML
    private void viewFriendsMenu() {
        System.out.println("friends menu");
        borderPane.setCenter(TabHandler.getTabHandler().push(new PlayerFriendsMenu()));
    }

    @FXML
    private void viewGamesMenu() {
        System.out.println("games menu");
        borderPane.setCenter( TabHandler.getTabHandler().push(new PlayerGamesMenu()));
    }

    @FXML
    private void viewAccountMenu() {
        System.out.println("account menu");
        borderPane.setCenter(TabHandler.getTabHandler().push(new PlayerAccountMenu()));
    }

    public void initializeMenuBar() {
        scoreMenuBar.setText(controller.showPoints());
        moneyMenuBar.setText(controller.getMoney());
    }
}
