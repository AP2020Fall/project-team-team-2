package controller;

import model.Event;

import java.util.Objects;

public class PlayerEventMenuController extends Controller{
    private Event event;
    public PlayerEventMenuController(Event event)
    {
        this.event = Objects.requireNonNull(event,"Event passed to PlayerEventMenu is null");
    }
}
