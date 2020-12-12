import model.Account;
import view.WelcomeMenu;

public class Main {
    public static void main(String[] args) {
        openFiles();
        new WelcomeMenu(null);
        saveFiles();
    }

    private static void openFiles() {
        try {
            Account.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void saveFiles() {
        try {
            Account.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}