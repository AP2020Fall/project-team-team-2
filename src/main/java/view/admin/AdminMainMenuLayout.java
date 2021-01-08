package view.admin;

import controller.admin.AdminMainMenuController;
import controller.player.PlayerMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.TabHandler;
import view.View;
import view.player.PlayerProfileView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainMenuLayout implements View, Initializable {
    @FXML private BorderPane borderPane;
    @FXML private  TextField searchUsername;
    private AdminMainMenuController controller;

    public AdminMainMenuLayout() {
        controller = new AdminMainMenuController();
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/adminMainMenuLayout.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TabHandler.getTabHandler().setBorderPane(borderPane);
        viewMainMenu();

    }
    @FXML private  void search(ActionEvent actionEvent) {
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            TabHandler.getTabHandler().push(
                    new PlayerProfileView(controller.searchPlayer(searchUsername.getText())));
        }
    }

    @FXML private  void sendMessage(ActionEvent actionEvent) {
    }

    @FXML
    private void back() {
        TabHandler.getTabHandler().back();
    }

    @FXML
    private void ahead() {
        TabHandler.getTabHandler().ahead();
    }

    @FXML private  void viewAccountMenu(ActionEvent actionEvent) {
    }

    @FXML private  void viewGamesAndEventMenu(ActionEvent actionEvent) {
    }

    @FXML private  void viewPlayerList(ActionEvent actionEvent) {
    }

    @FXML private  void viewMainMenu() {
        if(TabHandler.getTabHandler().current() instanceof AdminMainMenu)
            TabHandler.getTabHandler().refresh();
        else
            TabHandler.getTabHandler().push(new AdminMainMenu());
    }


}
