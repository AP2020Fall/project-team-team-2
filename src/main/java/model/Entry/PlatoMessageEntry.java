package model.Entry;

import model.Message;

public class PlatoMessageEntry {
    private String text;

    public PlatoMessageEntry(Message message)
    {
        text = message.getMessage();
    }
    public String getText() {
        return text;
    }
}
