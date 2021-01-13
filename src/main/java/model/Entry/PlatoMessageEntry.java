package model.Entry;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Account;
import model.Message;

import java.time.LocalDate;

public class PlatoMessageEntry {
    private String text;
    private LocalDate time;
    private  ImageView avatar;

    public PlatoMessageEntry(Message message)
    {
        text = message.getMessage();
    }
    public PlatoMessageEntry(LocalDate time)
    {
        this.time = time;
    }
    public PlatoMessageEntry()
    {
       this.avatar = new ImageView(Account.getAdmin().getImage());
        avatar.setFitHeight(48);
        avatar.setFitWidth(48);
    }
    public String getText() {
        return text;
    }

    public LocalDate getTime() {
        return time;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
