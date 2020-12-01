package view;

import controller.RegisterController;
import model.Account;
import model.Admin;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RegisterMenu extends Menu {

    private RegisterController controller;

    public RegisterMenu(Account account) {
        super(account);
        System.out.println("Register Menu");
        controller = new RegisterController();
        registerMenu();
    }

    private void registerMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if (getMatcher(input, "^help$").find()) {
                help();
            } else if ((matcher = getMatcher(input, "^register (\\S+), (\\S+)$")).find()) {
                register(matcher.group(1), matcher.group(2));
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                System.out.println("invalid command!");
            }
        }
    }

    private void help() {
        System.out.println("register <username, password>\n" +
                "back");
    }

    private ArrayList<String> getAdditionalInfo() {
        ArrayList<String> inputs = new ArrayList<>();
        while (true) {
            String input;
            System.out.println("first name: ");
            inputs.add(scanner.nextLine());
            System.out.println("last name: ");
            inputs.add(scanner.nextLine());
            System.out.println("Email: ");
            input = scanner.nextLine();
            if (!input.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
                System.out.println("invalid email");
                continue;
            }
            inputs.add(input);
            System.out.println("phone number:(09121234567)");
            input = scanner.nextLine();
            if (!input.matches("09\\d{9}")) {
                System.out.println("invalid phone number");
                continue;
            }
            inputs.add(input);
            if (Admin.isAdminExist()) {
                System.out.println("money:");
                inputs.add(scanner.nextLine());
            }
            break;
        }
        return inputs;
    }

    private void register(String username, String password) {
        if (!controller.isUsernameExist(username)) {
            controller.createAccount(username, password, getAdditionalInfo());
            System.out.println(username + " registered successfully");
        } else {
            System.out.println("username exists!");
        }
    }
}
