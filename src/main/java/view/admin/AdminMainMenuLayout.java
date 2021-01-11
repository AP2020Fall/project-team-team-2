package view.admin;

import com.jfoenix.controls.JFXTextField;
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
    @FXML
    private JFXTextField searchUsername;
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
        System.out.println("1");
        TabHandler.getTabHandler().setBorderPane(borderPane);
        System.out.println("2");
        viewMainMenu();
        System.out.println("3");

    }
    @FXML private  void search(ActionEvent actionEvent) {
        //todo add similar name instead
        if (!controller.isUsernameExist(searchUsername.getText())) {
            System.out.println("username does not exist!");
        } else {
            TabHandler.getTabHandler().push(
                    new AdminProfileView(controller.searchPlayer(searchUsername.getText())));
        }
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
