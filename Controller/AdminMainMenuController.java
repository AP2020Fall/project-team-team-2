package controller;

import model.Admin;

import java.time.LocalDateTime;

public class AdminMainMenuController {
    private Admin admin;

    public AdminMainMenuController(Admin admin) {
        this.admin = admin;
    }

    public void addEvent(String gameName, LocalDateTime start, LocalDateTime end, int score) {
    }

    public void showEvents() {
    }

    public void editEvent(int eventId, String field, String value) {
    }

    public void removeEvent(int eventId) {
    }

    public void addSuggestion(String username, String gameName) {
    }

    public void showSuggestions() {
    }

    public void removeSuggestion(int suggestionId) {
    }

    public void showUsers() {
    }

    public void showUserProfile(String username) {
    }
}
