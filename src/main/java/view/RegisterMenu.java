package view;

import controller.RegisterController;
import model.Account;
import model.Admin;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RegisterMenu extends Menu {

    private final RegisterController controller;

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
        String input;
        System.out.println("firstName: ");
        inputs.add(scanner.nextLine());
        System.out.println("lastName: ");
        inputs.add(scanner.nextLine());
        do {
            System.out.println("email: ");
            input = scanner.nextLine();
            if (!checkEmail(input)) {
                System.out.println("invalid email");
            }
        } while (!checkEmail(input));
        inputs.add(input);
        do {
            System.out.println("phoneNumber:(09121234567)");
            input = scanner.nextLine();
            if (!checkPhoneNumber(input)) {
                System.out.println("invalid phone number");
            }
        } while (!checkPhoneNumber(input));
        inputs.add(input);
        if (Admin.isAdminExist()) {
            System.out.println("money:");
            inputs.add(scanner.nextLine());
        }
        return inputs;
    }

    private void register(String username, String password) {
        if (!controller.isUsernameExist(username)) {
            ArrayList<String> additionalInfo = getAdditionalInfo();
            System.out.println(username + " registered successfully");
            controller.createAccount(username, password, additionalInfo);
        } else {
            System.out.println("username exists!");
        }
    }
}
