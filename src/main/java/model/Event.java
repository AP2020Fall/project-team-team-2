package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private static ArrayList<Event> events = new ArrayList<>();
    private String gameName;
    private LocalDate start;
    private LocalDate end;
    private int score;
    private String eventId;

    public Event(String gameName, LocalDate start, LocalDate end, int score, String eventId) {
        this.gameName = gameName;
        this.start = start;
        this.end = end;
        this.score = score;
        this.eventId = eventId;
    }

    public String getGameName() {
        return gameName;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getScore() {
        return score;
    }

    public String getEventId() {
        return eventId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static void addEvent(Event event) {
        events.add(event);
    }

    public static Event getEventById(String eventId) {
        for (Event event : events)
            if (event.getEventId().equals(eventId))
                return event;
        return null;
    }

    @Override
    public String toString() {
        return "Event{" +
                "gameName='" + gameName + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", score=" + score +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
