package view.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.ClientMasterController.ClientMasterController;
import controller.admin.AdminGameMenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Client;
import model.Game;
import view.AlertMaker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGamePopup implements Initializable {
    private final boolean edit;
    @FXML
    private JFXTextArea gameDetail;
    @FXML
    private JFXTextField gameName;
    @FXML
    private StackPane stackRoot;
    private Stage popupWindow;
    @FXML
    private ImageView avatar;
    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton editButton;
    private ClientMasterController controller;
    private String givenImage;
    public AddGamePopup(boolean edit,String gameName) {
        this.edit = edit;
        Client.getClientInfo().setGameName(gameName);
        controller = Client.getConnector().getController();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (edit) {
            addButton.setVisible(false);
            editButton.setVisible(true);
            initializeGameInfo();
        } else {
            addButton.setVisible(true);
            editButton.setVisible(false);
        }
    }

    private void initializeGameInfo() {
        gameName.setText(controller.getAdminGameName());
        gameDetail.setText(controller.getAdminGameDetails());
        avatar.setImage(controller.getAdminGameImage());
    }


    @FXML
    void add() {
        if (controller.doesGameExist(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid information", "The game already exists.");
        } else {
            controller.addGame(gameName.getText(), gameDetail.getText(), givenImage);
            popupWindow.close();
        }
    }

    @FXML
    void edit() {
        if (controller.doesGameExist(gameName.getText()) && !controller.getAdminGameName().equals(gameName.getText())) {
            setInfoColourRed();
            AlertMaker.showMaterialDialog(stackRoot, stackRoot.getChildren().get(0), "Okay",
                    "Invalid information", "The game already exists.");
        } else {
            controller.editGame(gameName.getText(), gameDetail.getText(), givenImage);
            popupWindow.close();
        }
    }

    @FXML
    void addAvatar(MouseEvent event) {
        givenImage = AlertMaker.getImageFromUser();
        if (givenImage != null) {
            avatar.setImage(new Image(givenImage));
        }
    }

    @FXML
    void cancel() {
        popupWindow.close();
    }

    private void setInfoColourRed() {
        gameDetail.setFocusColor(Paint.valueOf("#ff0000"));
        gameDetail.setUnFocusColor(Paint.valueOf("#ff0000"));
        gameName.setFocusColor(Paint.valueOf("#ff0000"));
        gameName.setUnFocusColor(Paint.valueOf("#ff0000"));
    }
}
