package view.player;

import controller.player.PlatoMessageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Entry.PlatoMessageEntry;
import view.View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlatoMessageView implements View, Initializable {
    private static Stage popupWindow;
    private final PlatoMessageController controller;
    @FXML
    private TableView<PlatoMessageEntry> messageTable;

    public PlatoMessageView() {
        controller = new PlatoMessageController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<PlatoMessageEntry, String> messageColumn = new TableColumn<>();
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        messageTable.getColumns().addAll(messageColumn);
        messageTable.getItems().addAll(controller.platoBotsMessages());
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
