package model.Entry;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import model.Player;
import view.TabHandler;
import view.admin.AdminProfileView;


public class PlayerEntry {
    private String name;
    private Hyperlink view;
    private ImageView avatar;
    public PlayerEntry(Player player) {
        name = player.getUsername();
        avatar = new ImageView(player.getImage());
        avatar.setFitWidth(48);
        avatar.setFitHeight(48);
        view = new Hyperlink("View");
        view.setOnAction(event -> {
            TabHandler.getTabHandler().push(new AdminProfileView(player));
        });
    }

    public String getName() {
        return name;
    }

    public Hyperlink getView() {
        return view;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
