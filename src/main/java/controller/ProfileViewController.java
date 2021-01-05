package controller;

import model.Player;

public class ProfileViewController extends Controller {
    private Player player;
    public ProfileViewController(Player player)
    {
        this.player = player;
    }

    public String getUsername() {
        return player.getUsername();
    }
    public String getFirstName() {
        return player.getFirstName();
    }
    public String getLastName() {
        return player.getLastName();
    }
    public String getEmail() {
        return player.getEmail();
    }
    public String getPhoneNumber() {
        return player.getPhoneNumber();
    }
    public String getDaysPassed() {
        return String.valueOf(player.getDayOfRegister());
    }

    public boolean areFriends() {
        return ((Player)loggedIn).getFriends().contains(player);
    }
}
