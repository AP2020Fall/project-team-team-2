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



    protected void viewAccountMenu(Account account) {
        //new AccountMenu(account);
    }

}
