package view;
import model.Account;

import java.util.Scanner;


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
