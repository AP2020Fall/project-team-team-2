package model;

import java.util.ArrayList;

public class Admin extends Account {
    public Admin(String firstName, String lastName, String accountName, int accountId, String password, String email, String phoneNumber) {
        super(firstName, lastName, accountName, accountId, password, email, phoneNumber);
    }

    private static ArrayList<Admin> allAdmins;

    public static ArrayList<Admin> getAllAdmins() {
        return allAdmins;
    }

    public static Admin getAdminById(int id) {
        return null;
    }
}
