package view;
import model.Account;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    protected Account account;
    protected static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public Menu(Account account) {
        this.account = account;
    }

    protected boolean checkEmail(String email) {
        return email.matches("^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$");
    }

    protected boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("09\\d{9}");
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    protected void viewAccountMenu(Account account) {
        new AccountMenu(account);
    }

}
