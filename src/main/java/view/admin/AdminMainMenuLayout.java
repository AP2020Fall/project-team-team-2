package view.admin;

import com.jfoenix.controls.JFXTextField;
import controller.admin.AdminMainMenuController;
import controller.player.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Main;
import model.Player;
import view.AlertMaker;
import view.TabHandler;
import view.View;
import view.player.PlayerProfileView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminMainMenuLayout implements View, Initializable {
    @FXML private BorderPane borderPane;
    @FXML
    private JFXTextField searchUsername;
    @FXML
    private ContextMenu searchContextMenu;
    private AdminMainMenuController controller;
    @FXML
    private StackPane stackRoot;


    public AdminMainMenuLayout() {
        controller = new AdminMainMenuController();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/adminMainMenuLayout.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        Scene scene = new Scene(root.load());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TabHandler.getTabHandler().setBorderPane(borderPane);
        TabHandler.getTabHandler().setStackPane(stackRoot);
        viewMainMenu();
    }
    @FXML private  void search(ActionEvent actionEvent) {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
                    "Invalid username","Username does not exist!");
        } else {
            TabHandler.getTabHandler().push(
                    new AdminProfileView(controller.searchPlayer(searchUsername.getText())));
        }
    }
    @FXML
    void updateContextMenu() {
        String searchQuery = searchUsername.getText();
        searchContextMenu.getItems().clear();
        searchContextMenu.getItems().addAll(controller.getSearchQuery(searchQuery));
        searchContextMenu.show(searchUsername, Side.BOTTOM,0,0);

    }
    @FXML private  void platoMessage(ActionEvent actionEvent) {
    }

    @FXML
    private void back() {
        TabHandler.getTabHandler().back();
    }

    @FXML
    private void ahead() {
        TabHandler.getTabHandler().ahead();
    }

    @FXML private  void viewAccountMenu() {
        if(TabHandler.getTabHandler().current() instanceof AdminAccountMenu)
            TabHandler.getTabHandler().refresh();
        else {
            TabHandler.getTabHandler().push(new AdminAccountMenu());
        }
    }

    @FXML private  void viewGamesAndEventMenu() {
        if(TabHandler.getTabHandler().current() instanceof AdminGamesMenu)
            TabHandler.getTabHandler().refresh();
        else {
            TabHandler.getTabHandler().push(new AdminGamesMenu());
        }
    }

    @FXML private  void viewPlayerList() {
        if(TabHandler.getTabHandler().current() instanceof AdminPlayerListMenu)
            TabHandler.getTabHandler().refresh();
        else {
            TabHandler.getTabHandler().push(new AdminPlayerListMenu());
        }
    }

    @FXML private  void viewMainMenu() {
        if(TabHandler.getTabHandler().current() instanceof AdminMainMenu)
            TabHandler.getTabHandler().refresh();
        else {
            TabHandler.getTabHandler().push(new AdminMainMenu());
        }
    }


}
