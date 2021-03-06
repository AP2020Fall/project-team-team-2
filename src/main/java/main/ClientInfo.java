package main;

public class ClientInfo {
    private String loggedInUsername;
    private String gameName;
    private String eventId;
    private String playerUsername;
    private String availableGameId;

    public ClientInfo() {
        loggedInUsername = "";
        gameName = "";
        eventId = "";
        playerUsername = "";
        availableGameId =  "";
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getAvailableGameId() {
        return availableGameId;
    }

    public void setAvailableGameId(String availableGameId) {
        this.availableGameId = availableGameId;
    }

    public void unsetLoggedInUsername() {
        loggedInUsername = "";
        gameName = "";
        eventId = "";
        playerUsername = "";
        availableGameId = "";
    }
}
