package model.Entry;

import javafx.scene.image.ImageView;
import model.Player;


public class PlayerEntry {
    private final String name;
    private final ImageView avatar;
    private final String status;
    public PlayerEntry(Player player) {
        name = player.getUsername();
        avatar = new ImageView(player.getImage());
        avatar.setFitWidth(48);
        avatar.setFitHeight(48);
        status = player.getStatus();
    }

    public String getName() {
        return name;
    }


    public ImageView getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }
}
