package model;

import java.util.ArrayList;

public abstract class Account {
    protected static ArrayList<Account> allAccounts = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String accountName;
    private String accountId;
    private String password;
    private String email;
    private String phoneNumber;

    public Account(String firstName, String lastName, String accountName, String accountId, String password, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountName = accountName;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        allAccounts.add(this);
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
