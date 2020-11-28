package view;

import java.util.Scanner;
public class View {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return View.scanner;
    }
}

