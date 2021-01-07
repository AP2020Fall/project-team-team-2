package model;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

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


    private static Event openEvent(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Event.class);
    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) json.append(reader.nextLine());
        reader.close();
        return json;
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

    public void delete()
    {
        events.remove(this);
        File file = new File("database" + "\\" + "events" + "\\" + eventId + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }
    }

    public static void save() throws IOException {
        for (Event event : events) {
            save(event);
        }
    }

    private static void save(Event event) throws IOException {
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(event);
        FileWriter file = new FileWriter("database" + "\\" + "events" + "\\" + event.getEventId() + ".json");
        file.write(jsonAccount);
        file.close();
    }

    public static void open() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "events");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                events.add(openEvent(file));
            }
        }
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
