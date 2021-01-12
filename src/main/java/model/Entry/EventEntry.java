package model.Entry;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import model.Event;
import view.TabHandler;
import view.player.PlayerEventMenu;

import java.time.LocalDate;

public class EventEntry {
    private String name;
    private Hyperlink link;
    private Hyperlink edit;
    private LocalDate start;
    private LocalDate end;
    private String eventId;
    private String score;
    private ImageView imageView;
    public EventEntry(Event event)
    {
        name= event.getGameName();
        start = event.getStart();
        end  = event.getEnd();
        score = String.valueOf( event.getScore());
        eventId = event.getEventId();
       // imageView = new ImageView(event.getImage());
        imageView.setFitHeight(48);
        imageView.setFitWidth(48);
        link = new Hyperlink("open");
        link.setOnAction(events ->
        {
            TabHandler.getTabHandler().push(new PlayerEventMenu(event));
        });
        edit = new Hyperlink("edit");
        edit.setOnAction(events ->
        {
            //todo implement
            //TabHandler.getTabHandler().push(new PlayerEventMenu(event));
        });
    }

    public EventEntry(String title)
    {
        name = title;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hyperlink getLink() {
        return link;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getScore() {
        return score;
    }

    public Hyperlink getEdit() {
        return edit;
    }

    public String getEventId() {
        return eventId;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
