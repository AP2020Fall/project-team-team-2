package view.admin;

import com.jfoenix.controls.JFXTextField;
import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Client;
import view.AlertMaker;

import java.io.IOException;

public class AddSuggestionPopup {
    @FXML
    private JFXTextField playerName;
    @FXML
    private JFXTextField gameName;
    @FXML
    private StackPane stackRoot;

    private Stage popupWindow;
    private final AdminMainMenuController controller;

    public AddSuggestionPopup()
    {
        controller = new AdminMainMenuController(Client.getClientInfo());
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
        if (!controller.usernameExist(playerName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
                    "Invalid information","username does not exist.");
        } else if (!controller.doesGameExist(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
                    "Invalid information","game does not exist.");
        } else if (controller.playerBeenSuggested(playerName.getText(), gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
                    "Invalid information","game has already been suggested.");
        } else {
            controller.addSuggestion(playerName.getText(), gameName.getText());
            popupWindow.close();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        popupWindow.close();
    }
    private void setInfoColourRed()
    {
        playerName.setFocusColor(Paint.valueOf("#ff0000"));
        playerName.setUnFocusColor(Paint.valueOf("#ff0000"));
        gameName.setFocusColor(Paint.valueOf("#ff0000"));
        gameName.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

}
