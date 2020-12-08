package model;

import java.time.LocalDateTime;

public class Message {
    private final String message;
    private final String messageId;
    private final Player player;
    private final LocalDateTime time;
    private boolean read;

    public Message(String message, String messageId, Player player, LocalDateTime time) {
        this.message = message;
        this.messageId = messageId;
        this.player = player;
        this.time = time;
        this.read = false;
    }

    public void sendMessage() {
        player.getMessages().add(this);
    }

    @Override
    public String toString() {
        return "Message:" + message + '\n';
        //+ time + '\n';
    }
}
