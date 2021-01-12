package model.Entry;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import model.Player;
import view.TabHandler;
import view.player.PlayerProfileView;
import view.ViewHandler;

public class FriendEntry {
    private String name;
    private Hyperlink remove = new Hyperlink("Remove");
    private Hyperlink view = new Hyperlink("View");
    private ImageView imageView;
    public FriendEntry(Player friend,Player player) {
        name = friend.getUsername();
        imageView = new ImageView(friend.getImage());
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
        remove.setOnAction(event -> {
            //System.out.println("Removing must be implemented");
            player.removeFriend(friend);
            friend.removeFriend(player);
            TabHandler.getTabHandler().refresh();
        });
        view.setOnAction(event -> {
            //System.out.println("Viewing must be implemented");
            TabHandler.getTabHandler().push(new PlayerProfileView(friend));
        });
    }

    public String getName() {
        return name;
    }

    public Hyperlink getRemove() {
        return remove;
    }

    public Hyperlink getView() {
        return view;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
