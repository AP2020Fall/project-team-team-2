package model.Entry;

import javafx.scene.image.ImageView;
import model.Player;


public class PlayerEntry {
    private final String name;
    private final ImageView avatar;
    public PlayerEntry(Player player) {
        name = player.getUsername();
        avatar = new ImageView(player.getImage());
        avatar.setFitWidth(48);
        avatar.setFitHeight(48);
    }

    public String getName() {
        return name;
    }


    public ImageView getAvatar() {
        return avatar;
    }
}
