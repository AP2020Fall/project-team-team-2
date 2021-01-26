package view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.admin.AdminEventMenuController;
import controller.admin.AdminGamesMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import main.Client;
import model.Event;
import view.AlertMaker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEventPopup implements Initializable {

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
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton editButton;
    private Stage popupWindow;
    private final boolean edit;
    private AdminEventMenuController controller;

    public AddEventPopup(boolean edit, Event event) {
        this.edit = edit;
        controller = new AdminEventMenuController(Client.getClientInfo());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (edit) {
            addButton.setVisible(false);
            editButton.setVisible(true);
            initializeEventInfo();
        } else {
            addButton.setVisible(true);
            editButton.setVisible(false);
        }
    }

    @FXML
    void add() {
        if (!controller.checkStartDate(startDate.getValue()) || !controller.checkEndDate(endDate.getValue())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Invalid dates.");
         } else if (!controller.checkRelativeDate(startDate.getValue(),endDate.getValue()))
        {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Start date can not be after end.");
        }else if (controller.checkNumber(score.getText()) &&  Integer.parseInt(score.getText()) <= 0) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Score should be positive number.");
        } else if (!controller.doesGameExist(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Game does not exist.");
        } else {
            controller.addEvent(gameName.getText(), startDate.getValue(), endDate.getValue(),
                    Integer.parseInt(score.getText()),detail.getText(), avatar.getImage());
            popupWindow.close();
        }
    }

    @FXML
    void edit() {
        if (Integer.parseInt(score.getText()) <= 0) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "score should be positive.");
        } else if (!controller.doesGameExist(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Game does not exist.");
        } else {
            controller.edit(gameName.getText(), Integer.parseInt(score.getText()), avatar.getImage(),detail.getText());
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
        if (givenImage != null) {
            avatar.setImage(givenImage);
        }
    }

    private void setInfoColourRed() {
        gameName.setFocusColor(Paint.valueOf("#ff0000"));
        gameName.setUnFocusColor(Paint.valueOf("#ff0000"));
        score.setFocusColor(Paint.valueOf("#ff0000"));
        score.setUnFocusColor(Paint.valueOf("#ff0000"));
        detail.setFocusColor(Paint.valueOf("#ff0000"));
        detail.setUnFocusColor(Paint.valueOf("#ff0000"));
    }

    private void initializeEventInfo() {
        score.setText(controller.getScore());
        startDate.setValue(controller.getStartDate());
        endDate.setValue(controller.getEndDate());
        startDate.setDisable(true);
        endDate.setDisable(true);
        gameName.setText(controller.getGameName());
        avatar.setImage(controller.getImage());
        detail.setText(controller.getComment());
    }

}

