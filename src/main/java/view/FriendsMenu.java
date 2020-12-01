package view;

import model.Account;

import java.util.regex.Matcher;

public class FriendsMenu extends Menu {

    public FriendsMenu(Account account) {
        super(account);
        friendsMenu();
    }

    private void friendsMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "help").find()) {
                help();
            } else if (getMatcher(input, "View account menu").find()) {
                viewAccountMenu(account);
            } else if (getMatcher(input, "Show friends").find()) {
                showFriends();
            } else if ((matcher = getMatcher(input, "Remove (\\S+)")).find()) {
                remove(matcher.group(1));
            } else if ((matcher = getMatcher(input, "View user profile (\\S+)")).find()) {
                viewUserProfile(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Add (\\S+)")).find()) {
                add(matcher.group(1));
            } else if (getMatcher(input, "Show friend requests").find()) {
                showFriendsRequests();
            } else if ((matcher = getMatcher(input, "Accept (\\S+)")).find()) {
                accept(matcher.group(1));
            } else if ((matcher = getMatcher(input, "Decline (\\S+)")).find()) {
                decline(matcher.group(1));
            } else if (getMatcher(input, "back").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void decline(String username) {

    }

    private void accept(String username) {

    }

    private void showFriendsRequests() {
    }

    private void add(String username) {

    }

    private void viewUserProfile(String username) {

    }

    private void remove(String username) {

    }

    private void showFriends() {

    }

    private void help() {
        System.out.println("View account menu\n" +
                "Show friends\n" +
                "Remove <username>\n" +
                "View user profile <username>\n" +
                "Add <username>\n" +
                "Show friend requests\n" +
                "Accept <username>\n" +
                "Decline <username>\n" +
                "help\n" +
                "back");
    }

}
