package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Admin extends Account {
    private ArrayList<Message> sentMessages;
    public Admin(String firstName, String lastName, String username, String accountId, String password, String email, String phoneNumber) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
        sentMessages = new ArrayList<>();
    }

    public static boolean isAdminExist() {
        for (Account account : Account.getAllAccounts()) {
            if (account instanceof Admin) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Message> getSentMessages() {
        Collections.sort(sentMessages);
        return sentMessages;
    }
}
