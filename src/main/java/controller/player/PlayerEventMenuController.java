package controller.player;

import javafx.scene.image.Image;
import model.Event;
import model.Game;

import java.util.Objects;

public class PlayerEventMenuController extends PlayerMainMenuLayoutController {
    private final Event event;
    public PlayerEventMenuController(Event event)
    {
        this.event = Objects.requireNonNull(event,"Event passed to PlayerEventMenu is null");
    }

    public String getGameName() {
        return event.getGameName();
    }

    public String getStartDate() {
        return  event.getStart().toString();
    }

    public String getEndDate() {
        return event.getEnd().toString();
    }

    public String getScore() {
        return String.valueOf( event.getScore());
    }

    public Image getEventImage() {
        return event.getImage();
    }

    public String getComment() {
        return event.getComment();
    }

    public Game getGame() {
        return Game.getGameByGameName(event.getGameName());
    }
    public Event getEvent()
    {
        return event;
    }
}
