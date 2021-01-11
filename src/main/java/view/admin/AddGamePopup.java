package view.admin;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.admin.AdminGamesMenuController;
import controller.admin.AdminMainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.AlertMaker;

import java.io.IOException;

public class AddGamePopup {
    @FXML
    private JFXTextArea gameDetail;
    @FXML
    private JFXTextField gameName;
    @FXML
    private StackPane stackRoot;
    private Stage popupWindow;
    @FXML
    private ImageView avatar;

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
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
            "Invalid information","The game already exists.");
        }
        else{
            controller.addGame(gameName.getText(), gameDetail.getText(),avatar.getImage());
            popupWindow.close();
        }
    }

    @FXML
    void addAvatar(MouseEvent event) {
        Image givenImage = AlertMaker.getImageFromUser();
        if(givenImage != null)
        {
            avatar.setImage(givenImage);
        }
    }

    @FXML
    void cancel() {
        popupWindow.close();
    }
    private void setInfoColourRed()
    {
        gameDetail.setFocusColor(Paint.valueOf("#ff0000"));
        gameDetail.setUnFocusColor(Paint.valueOf("#ff0000"));
        gameName.setFocusColor(Paint.valueOf("#ff0000"));
        gameName.setUnFocusColor(Paint.valueOf("#ff0000"));
    }
}
