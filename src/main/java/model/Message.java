package model;

import java.time.LocalDateTime;

public class Message {
    private final String message;
    private final String messageId;
    private final LocalDateTime time;

    public Message(String message, String messageId, LocalDateTime time) {
        this.message = message;
        this.messageId = messageId;
        this.time = time;
    }

    public void sendMessage() {
        for(Player player: Player.getAllPlayers())
            player.getMessages().add(this);
    }

    @Override
    public String toString() {
        return "Message:" + message + '\n';
        //+ time + '\n';
    }
}
