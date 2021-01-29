package view.admin;

import com.jfoenix.controls.JFXTextArea;
import controller.Controller;
import controller.admin.AdminMessageViewController;
import controller.player.PlatoMessageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Client;
import model.Entry.PlatoMessageEntry;
import view.View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminMessageView implements View, Initializable {
    private static Stage popupWindow;
    private final AdminMessageViewController controller;
    @FXML
    private TreeTableView<PlatoMessageEntry> messageTable;
    @FXML
    private JFXTextArea message;

    public AdminMessageView() {
        controller = new AdminMessageViewController(Client.getClientInfo());
    }

    @Override
    public void show(Stage window) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/adminMessageView.fxml"));
        root.setController(this);
        window.setTitle("Plato");
        window.setScene(new Scene(root.load()));
        window.setResizable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMessageTreeTable();
    }


    @FXML
    void Send() {
        controller.sendMessage(message.getText());
        message.clear();
        messageTable.getColumns().clear();
        initializeMessageTreeTable();
    }

    public void openWindow() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/plato/admin/adminMessageView.fxml"));
        root.setController(this);
        if (popupWindow == null)
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
            ObservableList<TreeItem<PlatoMessageEntry>> messages = FXCollections.observableArrayList();
            for (ArrayList<PlatoMessageEntry> messageEntries : controller.platoBotsMessages()) {
                TreeItem<PlatoMessageEntry> dayRoot = new TreeItem<>();
                for (PlatoMessageEntry messageEntry : messageEntries)
                    dayRoot.getChildren().add(new TreeItem<>(messageEntry));
                dayRoot.setExpanded(true);
                messages.addAll(dayRoot);
            }
            messageRoot.getChildren().addAll(messages);
            messageRoot.setExpanded(true);
            messageTable.setRoot(messageRoot);
            messageTable.setShowRoot(true);

        } else {
            messageTable.setPlaceholder(new Label("No messages."));
        }
        messageTable.getColumns().addAll(avatarColumn, dayColumn, messageColumn, timeColumn);
    }
}