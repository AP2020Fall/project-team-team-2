package view;

import controller.AccountController;

public class MainMenu extends Menu{
    public MainMenu(AccountController account) {
        super(account);
        mainMenu();

    }

    private void mainMenu() {
        while (true){
            String input = scanner.nextLine();

            if (getMatcher(input , "^register$").find()){
                openRegisterMenu();
            }else if (getMatcher(input,"^login$").find()){
                openLoginMenu();
            }else if (getMatcher(input,"^help$").find()){
                help();
            }else if (getMatcher(input , "^exit$").find()){
                return;
            }
        }
    }

    private void help() {
        System.out.println("register\n" +
                "login\n" +
                "help\n" +
                "exit");
    }

    private void openLoginMenu() {
    }

    private void openRegisterMenu() {
    }

}
