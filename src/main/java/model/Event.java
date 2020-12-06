package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private static final ArrayList<Event> events = new ArrayList<>();
    private String gameName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int score;
    private String eventId;

    public Event(String gameName, LocalDateTime start, LocalDateTime end, int score, String eventId) {
        this.gameName = gameName;
        this.start = start;
        this.end = end;
        this.score = score;
        this.eventId = eventId;
    }

    public String getGameName() {
        return gameName;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getScore() {
        return score;
    }

    public String getEventId() {
        return eventId;
    }

    public static ArrayList<Event> getEvents()
    {
       return events;
    }
    public static void addEvent(Event event)
    {
        events.add(event);
    }
}
