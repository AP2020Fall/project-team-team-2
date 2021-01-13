package controller.admin;

import javafx.scene.image.Image;
import model.Event;

import java.time.LocalDate;

public class AdminEventMenuController extends AdminGamesMenuController{
    private final Event event;
    public AdminEventMenuController(Event event)
    {
        this.event = event;
    }

    public String getScore() {
        return String.valueOf(event.getScore());
    }

    public LocalDate getStartDate() {
        return event.getStart();
    }
    public LocalDate getEndDate()
    {
        return event.getEnd();
    }
    public String getGameName()
    {
        return event.getGameName();
    }

    public Image getImage() {
        return event.getImage();
    }
    public String getComment()
    {
        return event.getComment();
    }
    public void edit(String gameName, int score, Image image,String comment) {
        event.setGameName(gameName);
        event.setScore(score);
        event.setImage(image);
        event.setComment(comment);
    }
}
