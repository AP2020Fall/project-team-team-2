package view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.ClientMasterController.ClientMasterController;
import controller.admin.AdminEventMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Client;
import model.Event;
import view.AlertMaker;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEventPopup implements Initializable {

    private final boolean edit;
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
    private String avatarAddress;
    private final ClientMasterController controller;

    public AddEventPopup(boolean edit, String event) {
        this.edit = edit;
        Client.getClientInfo().setEventId(event);
        controller = Client.getConnector().getController();
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
        File file = new File("src\\main\\resources\\images\\tournament.png");
        avatarAddress = file.toURI().toString();
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
        } else if (!controller.checkRelativeDate(startDate.getValue(), endDate.getValue())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Start date can not be after end.");
        } else if (controller.checkNumber(score.getText()) && Integer.parseInt(score.getText()) <= 0) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Score should be positive number.");
        } else if (!controller.doesGameExist(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid Information", "Game does not exist.");
        } else {
            controller.addEvent(gameName.getText(), startDate.getValue(), endDate.getValue(),
                    Integer.parseInt(score.getText()), detail.getText(), avatarAddress);
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
            controller.editEvent(gameName.getText(), Integer.parseInt(score.getText()), avatarAddress, detail.getText());
            popupWindow.close();
        }
    }

    @FXML
    void cancel() {
        popupWindow.close();
    }

    @FXML
    void addAvatar() {
        avatarAddress = AlertMaker.getImageFromUser();
        if(avatarAddress != null) {
            Image givenImage = new Image(avatarAddress);
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
        score.setText(controller.getAdminEventScore());
        startDate.setValue(controller.getAdminEventStartDate());
        endDate.setValue(controller.getAdminEventEndDate());
        startDate.setDisable(true);
        endDate.setDisable(true);
        gameName.setText(controller.getAdminEventGameName());
        avatar.setImage(controller.getAdminEventImage());
        detail.setText(controller.getAdminEventComment());
    }

}

