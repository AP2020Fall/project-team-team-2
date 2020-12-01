package model;

import java.util.ArrayList;

public class Admin extends Account {
    public Admin(String firstName, String lastName, String username, String accountId, String password, String email, String phoneNumber) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
    }

    public static boolean isAdminExist(){
        for (Account account : Account.getAllAccounts()) {
            if (account instanceof Admin){
                return true;
            }
        }
        return false;
    }
}
