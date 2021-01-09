package model.Entry;

import javafx.scene.control.Hyperlink;
import model.Player;
import view.TabHandler;
import view.admin.AdminProfileView;
import view.player.PlayerProfileView;

public class PlayerEntry {
    private String name;
    private Hyperlink view;
    public PlayerEntry(Player player) {
        name = player.getUsername();
        view = new Hyperlink("View");
        view.setOnAction(event -> {
            System.out.println("Viewing must be implemented");
           // TabHandler.getTabHandler().push(new AdminProfileView(player));
        });
    }

    public String getName() {
        return name;
    }

    public Hyperlink getView() {
        return view;
    }
}
