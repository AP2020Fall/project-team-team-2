package view;

import controller.FriendsMenuController;
import model.Account;
import model.FriendRequest;
import model.Player;

import java.util.regex.Matcher;

public class FriendsMenu extends Menu {
    FriendsMenuController controller;

    public FriendsMenu(Account account) {
        super(account);
        controller = new FriendsMenuController((Player) account);
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
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (controller.friendExists(username)) {
            System.out.println("this user has already been your friend");
        } else {
            controller.declineFriendRequest(username);
        }
    }

    private void accept(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (!controller.hasSentFriendRequest(username)) {
            System.out.println("this user hasn not send a friend request");
        } else {
            controller.acceptFriendRequest(username);
        }
    }

    private void showFriendsRequests() {
        if (controller.showFriendRequests().isEmpty())
            System.out.println("No friend requests.");
        else
        for (String friend : controller.showFriendRequests()) {
            System.out.println(friend);
        }
    }

    private void add(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else if (controller.friendExists(username)) {
            System.out.println("this user has already been your friend");
        }else if(account.getUsername().equals(username))
            System.out.println("can't add yourself.");
        else {
            controller.addFriend(username);
        }
    }

    private void viewUserProfile(String username) {
        if (!controller.isUsernameExist(username)) {
            System.out.println("username does not exist!");
        } else {
            controller.showUserProfile(username);
        }
    }

    private void remove(String username) {
        if (!controller.friendExists(username)) {
            System.out.println(username + " is not your friend!");
        } else {
            controller.removeFriend(username);
            System.out.println("friend removed successfully!");
        }
    }

    private void showFriends() {
        if(controller.showFriends().isEmpty())
            System.out.println("No friends.");
        else
        for (String friend : controller.showFriends()) {
            System.out.println(friend);
        }
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
