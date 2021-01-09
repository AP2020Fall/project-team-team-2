package view.admin;

import controller.admin.AdminGamesMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddEventPopup {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField gameName;
    @FXML
    private TextField score;
    @FXML
    private Label errorMsg;
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
        //if (!controller.checkStartDate(startDate.getValue()) || !controller.checkEndDate(endDate.getValue())) {
        //    System.out.println("invalid date!");
       // } else
        if (Integer.parseInt(score.getText()) <= 0) {
            errorMsg.setText("score should be positive!");
        } else if (!controller.doesGameExist(gameName.getText())) {
            errorMsg.setText("invalid game!");
        } else {
            controller.addEvent(gameName.getText(), startDate.getValue(), endDate.getValue(),
                    Integer.parseInt(score.getText()));
            popupWindow.close();
        }
    }

    @FXML
    void cancel() {
        popupWindow.close();
    }
}
