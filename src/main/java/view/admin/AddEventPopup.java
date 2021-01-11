package view.admin;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.admin.AdminGamesMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

public class AddEventPopup {

    @FXML
    private StackPane stackRoot;
    @FXML
    private JFXTextField gameName;
    @FXML
    private JFXTextField score;
    @FXML
    private JFXTextArea detail;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private ImageView avatar;
    private Stage popupWindow;

    private AdminGamesMenuController controller;

    public AddEventPopup() {
        controller = new AdminGamesMenuController();
    }


    public void openWindow() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/addEventPopup.fxml"));
        root.setController(this);
        popupWindow = new Stage();
        popupWindow.setTitle("Add Event");
        popupWindow.setScene(new Scene(root.load()));
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setResizable(false);
        popupWindow.showAndWait();
    }
    @FXML
    void add() {
        //todo check date
        //if (!controller.checkStartDate(startDate.getValue()) || !controller.checkEndDate(endDate.getValue())) {
        //    System.out.println("invalid date!");
       // } else
        if (Integer.parseInt(score.getText()) <= 0) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
                    "Invalid Information", "score should be positive.");
        } else if (!controller.doesGameExist(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot,stackRoot.getChildren().get(0),"Okay",
                    "Invalid Information", "Game does not exist.");
        } else {
            controller.addEvent(gameName.getText(), startDate.getValue(), endDate.getValue(),
                    Integer.parseInt(score.getText()),avatar.getImage());
            popupWindow.close();
        }
    }

    @FXML
    void cancel() {
        popupWindow.close();
    }

    @FXML
    void addAvatar() {
        Image givenImage = AlertMaker.getImageFromUser();
        if(givenImage != null)
        {
            avatar.setImage(givenImage);
        }
    }

    private void setInfoColourRed()
    {
        gameName.setFocusColor(Paint.valueOf("#ff0000"));
        gameName.setUnFocusColor(Paint.valueOf("#ff0000"));
        startDate.setDefaultColor(Paint.valueOf("#ff0000"));
        endDate.setDefaultColor(Paint.valueOf("#ff0000"));
        score.setFocusColor(Paint.valueOf("#ff0000"));
        score.setUnFocusColor(Paint.valueOf("#ff0000"));
        detail.setFocusColor(Paint.valueOf("#ff0000"));
        detail.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

}

