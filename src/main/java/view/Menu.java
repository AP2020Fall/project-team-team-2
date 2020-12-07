package view;


import model.Account;

import java.time.LocalDate;
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
        if (email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
            return true;
        }
        return false;
    }

    protected boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("09\\d{9}")) {
            return true;
        }
        return false;
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    protected void viewAccountMenu(Account account) {
        new AccountMenu(account);
    }

    protected LocalDate createLocalDate(String input) {
        String[] splitInput = input.split("\\/");
        int year = Integer.parseInt(splitInput[0]);
        int month = Integer.parseInt(splitInput[1]);
        int day = Integer.parseInt(splitInput[2]);
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate;
    }
}
