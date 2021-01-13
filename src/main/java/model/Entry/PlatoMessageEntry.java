package model.Entry;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Account;
import model.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PlatoMessageEntry {
    private String text;
    private LocalTime time;
    private LocalDate day;
    private  ImageView avatar;

    public PlatoMessageEntry(Message message)
    {
        text = message.getMessage();
        time = message.getTime().toLocalTime();
    }
    public PlatoMessageEntry(LocalDate time)
    {
        this.day = time;
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

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDay() {
        return day;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
