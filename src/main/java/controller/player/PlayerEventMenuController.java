package controller.player;

import javafx.scene.image.Image;
import main.ClientInfo;
import model.Event;
import model.Game;

import java.util.Objects;

public class PlayerEventMenuController {
    private final Event event;

    public PlayerEventMenuController(ClientInfo clientInfo) {
        this.event = Event.getEventById(clientInfo.getEventId());
        if (event == null)
            System.err.println("Event passed to PlayerEventMenuController is null");

    }

    public String getGameName() {
        return event.getGameName();
    }

    public String getStartDate() {
        return event.getStart().toString();
    }

    public String getEndDate() {
        return event.getEnd().toString();
    }

    public String getScore() {
        return String.valueOf(event.getScore());
    }

    public String getComment() {
        return event.getComment();
    }

    public String getImage() {
        return event.getImageURL();
    }

    public Game getGame() {
        return Game.getGameByGameName(event.getGameName());
    }

    public Event getEvent() {
        return event;
    }
}
