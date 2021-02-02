package model;

import com.google.gson.Gson;
import controller.ServerMasterController.SQLConnector;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message implements Comparable {
    private final String message;
    private final String messageId;
    private final LocalDateTime time;

    public Message(String message, String messageId, LocalDateTime time) {
        this.message = message;
        this.messageId = messageId;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessageId(){return messageId;}

    public void sendMessage() {
        addMessage();
        for (Player player : Player.getAllPlayers())
            player.addMessage(this.messageId);
        Account.getAdmin().addMessage(this.messageId);
    }

    private void addMessage() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message_id", this.getMessageId());
        resultMap.put("message_text", this.getMessage());
        resultMap.put("timestamp", this.time.format(DateTimeFormatter.ISO_DATE_TIME));
        SQLConnector.insertInDatabase(resultMap, "messages");

    }

    private static java.util.Map<String, Object> SQLMessageSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<java.util.Map<String, Object>> allMessages =
                SQLConnector.selectFromDatabase(newMap, "messages");
        if (allMessages == null || allMessages.isEmpty()) {
            System.err.println("[MODEL]: Message with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return allMessages.get(0);
    }


    private static Message createMessage(Map<String, Object> message) {
        return new Message((String) message.get("message_text"), (String) message.get("message_id"),
                LocalDateTime.parse((String) message.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME));
    }


    public static Message getMessageById(String messageId) {
        Map<String, Object> message = SQLMessageSearch("message_id", messageId);
        if (message == null || message.isEmpty()) {
            return null;
        }
        return createMessage(message);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Message)
            return ((Message) o).time.compareTo(this.time);
        return 0;
    }

    @Override
    public String toString() {
        return "Message:" + message;
        //+ time + '\n';
    }
}
