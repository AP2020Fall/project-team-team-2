package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Gamer {

    private String username;
    private String avatar;
    private  ArrayList<Card> cards;
    private int newSoldiers;
    private int playerNumber;
    private int draftSoldiers = 0;
    private  ArrayList<Gamer> requests;
    private  ArrayList<Gamer> gameFriends;

    public Gamer (String username,String avatar)
    {
        this.username = username;
        this.avatar =avatar;
        this.cards = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.gameFriends = new ArrayList<>();
    }

    public void setRequestAndFriendsList() {
        this.requests = new ArrayList<>();
        this.gameFriends = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getImageURL() {
        return avatar;
    }

    public void resetRequestAndFriends() {
        this.requests.clear();
        this.gameFriends.clear();
    }

    public int getDraftSoldiers() {
        return draftSoldiers;
    }

    public void addDraftSoldier(int draftSoldiers) {
        this.draftSoldiers += draftSoldiers;
    }

    public int getNewSoldiers() {
        return newSoldiers;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        Iterator iterate = this.cards.iterator();
        while (iterate.hasNext()) {
            if (iterate.next().equals(card)) {
                iterate.remove();
                break;
            }
        }
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public ArrayList<Gamer> getGameFriends() {
        return gameFriends;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int[] getCardsNumber() {
        int[] numbers = new int[3];
        int number1 = 0;
        int number2 = 0;
        int number3 = 0;
        for (Card card : cards) {
            if (card.equals(Card.CARD_1)) {
                number1++;
            }
            if (card.equals(Card.CARD_2)) {
                number2++;
            }
            if (card.equals(Card.CARD_3)) {
                number3++;
            }
        }
        numbers[0] = number1;
        numbers[1] = number2;
        numbers[2] = number3;
        return numbers;
    }

    public void addGameRequest(Gamer player) {
        requests.add(player);
    }

    public void addGameFriend(Gamer player) {
        gameFriends.add(player);
    }

    public void rejectRequest(Gamer player) {
        requests.remove(player);
    }

    public ArrayList<Gamer> getRequests() {
        return requests;
    }

    public boolean checkPlayerHasRequest() {
        return requests.size() > 0;
    }

    public void setCard() {
        this.cards = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Gamer{" +
                "username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
