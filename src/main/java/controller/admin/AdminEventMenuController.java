package controller.admin;

import com.google.gson.Gson;
import controller.Controller;
import main.ClientInfo;
import model.Event;

import java.time.LocalDate;

public class AdminEventMenuController extends Controller {
    private final Event event;

    public AdminEventMenuController(ClientInfo clientInfo) {
        super(clientInfo);
        this.event = Event.getEventById(clientInfo.getEventId());
        if (event == null)
            System.err.println("Event passed to AdminEventMenuController is null");
    }

    public String getGameName() {
        return event.getGameName();
    }//done

    public String getScore() {
        return String.valueOf(event.getScore());
    }//done

    public LocalDate getStartDate() {
        return event.getStart();
    }//done

    public LocalDate getEndDate() {
        return event.getEnd();
    }//done

    public String getImage() {
        return event.getImageURL();
    }//done

    public String getComment() {
        return event.getComment();
    }//done

    public void edit(String gameName, String score, String image, String comment) {
        event.setGameName(gameName);
        event.setScore(new Gson().fromJson(score,Integer.class));
        event.setImage(image);
        event.setComment(comment);
    }
    public void addEvent(String gameName, String start, String end, String score,String comment,String image) {
        //creates a new event and adds it to the list of events.
        //LocalDate startDate = createLocalDate(start);
        // LocalDate endDate = createLocalDate(end);
        Event event = new Event(gameName, new Gson().fromJson(start,LocalDate.class),
                new Gson().fromJson(end,LocalDate.class), new Gson().fromJson(score,Integer.class), generateId(),comment,image);
        Event.addEvent(event);
    }
}
