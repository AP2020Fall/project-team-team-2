package model;


import java.util.ArrayList;
import java.util.Objects;

public class Scoreboard {
    public class Entry {
        private String playerName;
        //private int score;
        private int wins;
        private int numPlayed;

        public Entry(String playerName, int wins, int numPlayed) {
            this.playerName = playerName;
            //this.score = score;
            this.wins = wins;
            this.numPlayed = numPlayed;
        }
        public String getPlayerName() {
            return playerName;
        }

       /* public int getScore() {
            return score;
        }*/

        public int getWins() {
            return wins;
        }

        public int getNumPlayed() {
            return numPlayed;
        }

       /* public void setScore(int score) {
            this.score = score;
        }*/

        public void setWins(int wins) {
            this.wins = wins;
        }

        public void setNumPlayed(int numPlayed) {
            this.numPlayed = numPlayed;
        }

        @Override
        public String toString() {
            return   playerName + '\t' + wins + '\t'+ numPlayed;
        }
    }
    private final ArrayList<Entry> entries = new ArrayList<>();

    public Scoreboard()
    {

    }

    public void updateScoreboard(PlayLog playLog) {
        //create an entry for every player and increment the number of times they play.
        for(String player: playLog.getPlayers())
        {
            Entry entry = findPlayerEntry(player);
            if(entry == null)
            {
                entries.add(new Entry(player,0,1));
            }
            else
            {
                entry.setNumPlayed(entry.getNumPlayed()+1);
            }
        }
        //increment the wins of the winner
        if(playLog.getWinner() != null) {
            Entry entry = Objects.requireNonNull(findPlayerEntry(playLog.getWinner()),
                    "Invalid PlayLog has been passed to Scoreboard.");
            entry.setWins(entry.getWins() + 1);
        }
        entries.sort((a,b) -> (a.getWins() != b.getWins() ? Integer.compare(b.getWins(),a.getWins())
                : (a.getNumPlayed() != b.getNumPlayed() ? Integer.compare(a.getNumPlayed(),b.getNumPlayed())
                : a.getPlayerName().compareTo(b.getPlayerName()))));

    }

    private Entry findPlayerEntry(String playerName) {
        for (Entry entry : entries)
            if (entry.getPlayerName().equals(playerName))
                return entry;
        return null;
    }

    public ArrayList<Entry> getScoreboard() {
        return entries;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Username\tWins\tGames Played");
        for (Entry entry: entries)
            result.append(entry);
        return result.toString();
    }
}
