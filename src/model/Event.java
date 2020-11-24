package model;

import java.time.LocalDateTime;

public class Event {
    private String gameName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int score;
    private int eventId;

    public Event(String gameName, LocalDateTime start, LocalDateTime end, int score, int eventId) {
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

    public int getEventId() {
        return eventId;
    }


}
