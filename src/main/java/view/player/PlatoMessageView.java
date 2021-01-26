package view.player;

import controller.player.PlatoMessageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Client;
import model.Entry.GameLogEntry;
import model.Entry.PlatoMessageEntry;
import view.View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlatoMessageView implements View, Initializable {
    private static Stage popupWindow;
    private final PlatoMessageController controller;
    @FXML
    private TreeTableView<PlatoMessageEntry> messageTable;
    public PlatoMessageView() {
        controller = new PlatoMessageController(Client.getClientInfo());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMessageTreeTable();
    }

    private void initializeMessageTreeTable() {
        TreeTableColumn<PlatoMessageEntry, ImageView> avatarColumn = new TreeTableColumn<>();
        avatarColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("avatar"));
        TreeTableColumn<PlatoMessageEntry, String> messageColumn = new TreeTableColumn<>();
        messageColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("text"));
        TreeTableColumn<PlatoMessageEntry, LocalTime> timeColumn = new TreeTableColumn<>();
        timeColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("time"));
        TreeTableColumn<PlatoMessageEntry, LocalDate> dayColumn = new TreeTableColumn<>();
        dayColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("day"));


        TreeItem<PlatoMessageEntry> messageRoot = new TreeItem<>(controller.getMessageRoot());
        if (controller.hasMessage()) {
            for (ArrayList<PlatoMessageEntry> messageEntries : controller.platoBotsMessages()) {
                TreeItem<PlatoMessageEntry> dayRoot = new TreeItem<>();
                for(PlatoMessageEntry messageEntry: messageEntries)
                    dayRoot.getChildren().add(new TreeItem<>(messageEntry));
                dayRoot.setExpanded(true);
                messageRoot.getChildren().add(dayRoot);
            }
            messageRoot.setExpanded(true);
            messageTable.setRoot(messageRoot);
            messageTable.setShowRoot(true);

        } else {
            messageTable.setPlaceholder(new Label("No messages."));
        }
        messageTable.getColumns().addAll(avatarColumn,dayColumn, messageColumn, timeColumn);
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/player/platoMessageView.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    public void openWindow() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/player/platoMessageView.fxml"));
        root.setController(this);
        if (popupWindow == null )
            popupWindow = new Stage(StageStyle.DECORATED);
        popupWindow.setTitle("PlatoBot Message");
        popupWindow.setScene(new Scene(root.load()));
        popupWindow.setResizable(false);
        popupWindow.show();
    }

    @FXML
    private void back() {
        popupWindow.close();
    }
}
