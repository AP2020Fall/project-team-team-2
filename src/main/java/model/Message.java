package model;

import java.time.LocalDateTime;

public class Message implements Comparable {
    private final String message;
    private final String messageId;
    private final LocalDateTime time;

    public Message(String message, String messageId, LocalDateTime time)  {
        this.message = message;
        this.messageId = messageId;
        this.time = time;
    }

    public void sendMessage() {
        for(Player player: Player.getAllPlayers())
            player.getMessages().add(this);
        Account.getAdmin().getSentMessages().add(this);
    }

    @Override
    public String toString() {
        return "Message:" + message;
        //+ time + '\n';
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Message)
        return ((Message)o).time.compareTo(this.time);
        return 0;
    }
}
