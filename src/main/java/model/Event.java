package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ServerMasterController.SQLConnector;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map;

public class Event {
    private String gameName;
    private LocalDate start;
    private LocalDate end;
    private int score;
    private String eventId;
    private String comment;
    private String avatar;

    public Event(String gameName, LocalDate start, LocalDate end, int score, String eventId, String comment, String image) {
        this.gameName = gameName;
        this.start = start;
        this.end = end;
        this.score = score;
        this.eventId = eventId;
        setImage(image);
        this.comment = comment;
    }
    public Event(Map<String,Object> map)
    {
        this.gameName = (String) map.get("game_name");
        this.eventId = (String) map.get("event_id");
        this.start = LocalDate.parse((String) map.get("start_date"));
        this.end = LocalDate.parse((String) map.get("end_date"));
        setImage((String) map.get("avatar_address"));
        this.score = Integer.parseInt((String) map.get("score"));
        this.comment = (String)map.get("event_comment");
    }
    /*
    private static Event openEvent(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        Event event = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Event.class);
        openFileToImage(event);
        return event;
    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) json.append(reader.nextLine());
        reader.close();
        return json;
    }

*/
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

    public Image getImage() {
        return new Image(avatar);
    }

    public String getComment() {
        return comment;
    }

    public String getImageURL() {
        return avatar;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
        editField("game_name",this.gameName);
    }


    public void setScore(int score) {
        this.score = score;
        editField("score",score);
    }

    public void setComment(String comment) {
        this.comment = comment;
        editField("event_comment", this.comment);
    }

    public void setImage(String url) {
        this.avatar = saveImageToFile(new Image(url),this.eventId);
        editField("avatar_address", this.avatar);
    }

    /*public void setStart(LocalDate start) {
        this.start = start;
    }
    public void setEnd(LocalDate end) {
        this.end = end;
    }*/


    public void editField(String field, Object value) {
        java.util.Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("event_id", this.eventId);
        Map<String, Object> newValueMap = new HashMap<>();
        newValueMap.put(field, value);
        SQLConnector.updateTable(conditionMap, newValueMap, "events");
    }

    public static void addEvent(Event event) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("game_name", event.getGameName());
        resultMap.put("start_date", event.getStart().toString());
        resultMap.put("end_date", event.getEnd().toString());
        resultMap.put("score", event.getScore());
        resultMap.put("avatar_address", event.getImageURL());
        System.out.println(event.getImageURL());
        resultMap.put("event_id", event.getEventId());
        resultMap.put("event_comment", event.getComment());
        SQLConnector.insertInDatabase(resultMap,"events");
    }

    public static List<Map<String, Object>> SQLEventSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "events");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: Event with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount;
    }

    public static ArrayList<Event> getEvents() {
        ArrayList<Event> result = new ArrayList<>();
        List<Map<String,Object>> eventList = SQLConnector.getWholeTable("events");
        if(eventList == null || eventList.isEmpty())
        {
            System.out.println("[MODEL]: no Event could be found");
            return result;
        }
        for (Map<String,Object> event:eventList)
        {
            if(!event.get("event_comment").equals("Casual"))
            result.add(new Event(event));
        }
        return result;
    }

    public static Event getEventById(String eventId) {
        List<Map<String,Object>> event = SQLEventSearch("event_id",eventId);
        if(event == null || event.isEmpty())
        {
            return null;
        }
        return new Event(event.get(0));
    }

    public void delete() {
        //events.remove(this);
        /*File file = new File("database" + "\\" + "events" + "\\event\\" + eventId + ".json");
        try {
            if (file.exists())
                file.delete();
        } catch (Exception ignored) {
        }*/
        Map<String, Object> event = new HashMap<>();
        event.put("event_id", eventId);
        if (SQLConnector.deleteFromTable(event, "events")) {
            File imageFile = new File("database\\events\\images\\" + eventId + ".jpg");
            try {
                if (imageFile.exists())
                    imageFile.delete();
            } catch (Exception ignored) {
                System.out.println("event image not found!");
            }
        }
        else{
            System.out.println("[MODEL]: Event with event ID = " + eventId + " couldn't be deleted");
        }
    }

    public static void delete(Object event_id) {
        Map<String,Object> event = new HashMap<>();
        event.put("event_id",event_id);
        if(SQLConnector.deleteFromTable(event,"events")) {
            File imageFile = new File("database\\events\\images\\" + event_id + ".jpg");
            try {
                if (imageFile.exists())
                    imageFile.delete();
            } catch (Exception ignored) {
                System.out.println("event image not found!");
            }
        }
        else{
            System.out.println("[MODEL]: Event with event ID = " + event_id + " couldn't be deleted");
        }
    }

    /*public static void save() throws IOException {
        for (Event event : events) {
            save(event);
        }
    }

    private static void save(Event event) throws IOException {
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(event);
        FileWriter file = new FileWriter("database" + "\\" + "events" + "\\event\\" + event.getEventId() + ".json");
        file.write(jsonAccount);
        file.close();
        System.out.println("saving ended " + event.getEventId());
        saveImageToFile(event.getImage(), event.getEventId());
        System.out.println("saving image ended " + event.getEventId());
    }*/

    /*public static void open() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "events\\event");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                events.add(openEvent(file));
            }
        }
    }*/

    public static String saveImageToFile(Image image, String eventId) {

        File folder = new File("database\\events\\images");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File outputFile = new File("database\\events\\images\\" + eventId + ".jpg");

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
            return outputFile.toURI().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* public static void openFileToImage(Event event) {
        File file = new File("database\\events\\images\\" + event.getEventId() + ".jpg");
        event.setImage(file.toURI().toString());
    }*/

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
