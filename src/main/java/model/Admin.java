package model;


import java.util.ArrayList;
import java.util.Collections;

public class Admin extends Account {
    private final ArrayList<Message> sentMessages;
    public Admin(String firstName, String lastName, String username, String accountId, String password, String email, String phoneNumber) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber);
        sentMessages = new ArrayList<>();
    }

    public static boolean isAdminExist() {
        return Account.getAdmin() != null;
    }

    public static void add(Admin admin) {
    }

    public ArrayList<Message> getSentMessages() {
        Collections.sort(sentMessages);
        return sentMessages;
    }
}
