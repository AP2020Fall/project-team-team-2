package view;

import controller.AccountController;

import java.util.regex.Matcher;

public class RegisterMenu extends Menu {
    public RegisterMenu(AccountController account) {
        super(account);
        registerMenu();
    }

    private void registerMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else if ((matcher = getMatcher(input, "^register (\\S+), (\\S+)$")).find()) {
                register(matcher.group(1), matcher.group(2));
            }
        }
    }

    private void help() {
        System.out.println("register <username, password>\n" +
                "back");
    }

    private void register(String username, String password) {

    }
}
