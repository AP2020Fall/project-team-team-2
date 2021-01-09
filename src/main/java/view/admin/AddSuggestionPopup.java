package view.admin;

import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddSuggestionPopup {
    @FXML private TextField playerName;
    @FXML private  TextField gameName;
    @FXML private  Label errorMsg;
    private Stage popupWindow;
    private final AdminMainMenuController controller;

    public AddSuggestionPopup()
    {
        controller = new AdminMainMenuController();
    }

    public void openWindow() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/addSuggestionPopup.fxml"));
        root.setController(this);
        popupWindow = new Stage();
        popupWindow.setTitle("Add suggestion");
        popupWindow.setScene(new Scene(root.load()));
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setResizable(false);
        popupWindow.showAndWait();
    }
    @FXML private void add() {
        if (!controller.isUsernameExist(playerName.getText())) {
            errorMsg.setText("username does not exist!");
        } else if (!controller.doesGameExist(gameName.getText())) {
            errorMsg.setText("game does not exist!");
        } else if (controller.playerBeenSuggested(playerName.getText(), gameName.getText())) {
            errorMsg.setText("game has already been suggested!");
        } else {
            controller.addSuggestion(playerName.getText(), gameName.getText());
            popupWindow.close();
        }
    }

}
