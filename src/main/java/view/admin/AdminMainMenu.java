package view.admin;

import controller.ClientMasterController.ClientMasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Client;
import model.Entry.SuggestionEntry;
import view.Tab;
import view.TabHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainMenu implements Tab, Initializable {
    private final ClientMasterController controller;
    @FXML
    private TableView<SuggestionEntry> suggestionList;

    public AdminMainMenu() {
        controller = Client.getConnector().getController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializedTableSuggestionList();
    }

    @Override
    public Parent show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plato/admin/adminMainMenu.fxml"));
        loader.setController(this);
        return loader.load();
    }

    public void add() throws IOException {
        new AddSuggestionPopup().openWindow();
        TabHandler.getTabHandler().refresh();
    }

    public void delete() {
        for (SuggestionEntry suggestionEntry : suggestionList.getSelectionModel().getSelectedItems()) {
            controller.deleteSuggestion(suggestionEntry);
        }
        TabHandler.getTabHandler().refresh();
    }

    private void initializedTableSuggestionList() {
        TableColumn<SuggestionEntry, String> suggestionIdColumn = new TableColumn<>("ID");
        suggestionIdColumn.setCellValueFactory(new PropertyValueFactory<>("suggestionId"));
        TableColumn<SuggestionEntry, String> suggestionGameNameColumn = new TableColumn<>("Game");
        suggestionGameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        TableColumn<SuggestionEntry, String> suggestionPlayerNameColumn = new TableColumn<>("Player");
        suggestionPlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        suggestionList.setPlaceholder(new Label("No suggestion has been made."));
        suggestionList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        suggestionList.getColumns().addAll(suggestionIdColumn, suggestionGameNameColumn, suggestionPlayerNameColumn);
        suggestionList.getItems().addAll(controller.getSuggestions());
    }
}
