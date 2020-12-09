import model.Account;
import model.Event;
import model.Game;
import model.Suggestion;
import view.WelcomeMenu;

public class Main {
    public static void main(String[] args) {
        openFiles();
        new WelcomeMenu(null);
    }

    private static void openFiles() {
        try {
            Account.open();
            Event.open();
            Suggestion.open();
            Game.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}