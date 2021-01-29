package view;

import model.Account;

import java.util.Scanner;


public abstract class Menu {
    protected static Scanner scanner = new Scanner(System.in);
    protected Account account;

    public Menu(Account account) {
        this.account = account;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    protected void viewAccountMenu(Account account) {
        //new AccountMenu(account);
    }

}
