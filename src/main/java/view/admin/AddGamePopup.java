package view.admin;

import controller.admin.AdminGamesMenuController;
import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddGamePopup {
    @FXML
    private TextArea gameDetail;
    @FXML
    private TextField gameName;
    @FXML
    private Label errorMsg;
    private Stage popupWindow;

    private AdminGamesMenuController controller;

    public AddGamePopup() {
        controller = new AdminGamesMenuController();
    }


    public void openWindow() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/addGamePopup.fxml"));
        root.setController(this);
        popupWindow = new Stage();
        popupWindow.setTitle("Add game");
        popupWindow.setScene(new Scene(root.load()));
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setResizable(false);
        popupWindow.showAndWait();
    }


    @FXML
    void add() {
        if (controller.doesGameExist(gameName.getText())) {
            errorMsg.setText("game exists!");
        }
        else{
            controller.addGame(gameName.getText(), gameDetail.getText());
            popupWindow.close();
        }
    }

    @FXML
    void cancel() {
        popupWindow.close();
    }
}
