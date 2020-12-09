package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Account {
    protected static ArrayList<Account> allAccounts = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String username;
    private String accountId;
    private String password;
    private String email;
    private String phoneNumber;
    private boolean isRobot = false;

    public Account(String botName, String username) {
        this.firstName = botName;
        this.username = username;
    }

    public Account(String firstName, String lastName, String accountName, String accountId, String password, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = accountName;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        //allAccounts.add(this);
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equals(username)) {
                return account;
            }
        }
        return null;
    }

    public static Account getAccountById(String id) {
        for (Account account : allAccounts) {
            if (account.accountId.equals(id)) {
                return account;
            }
        }
        return null;
    }

    public static ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Account account : allAccounts)
            if (account instanceof Player)
                players.add((Player) account);
        return players;
    }

    public static void save() throws IOException {
        for (Account account : allAccounts) {
            save(account);
        }
    }

    private static void save(Account account) throws IOException {
        if (account instanceof Admin) {
            saveAdmin(account);
        } else if (account instanceof Player) {
            savePlayer(account);
        }
    }

    private static void savePlayer(Account account) throws IOException {
        Player player = (Player) account;
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(account);
        FileWriter file = new FileWriter("database" + "\\" + "accounts" + "\\" + "players" + "\\" + account.getUsername() + ".json");
        file.write(jsonAccount);
        file.close();
    }

    private static void saveAdmin(Account account) throws IOException {
        Admin admin = (Admin) account;
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(account);
        FileWriter file = new FileWriter("database" + "\\" + "accounts" + "\\" + "admins" + "\\" + account.getUsername() + ".json");
        file.write(jsonAccount);
        file.close();

    }

    public static void open() throws FileNotFoundException {
        openAdmin();
        openPlayers();
    }

    private static void openAdmin() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "accounts" + "\\" + "admin");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openAdmin(file));
            }
        }
    }

    private static void openPlayers() throws FileNotFoundException {
        File folder = new File("database" + "\\" + "accounts" + "\\" + "players");
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openPlayer(file));
            }
        }
    }

    private static Player openPlayer(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Player.class);
    }

    private static Admin openAdmin(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        return new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Admin.class);
    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return json;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return
                "firstName: " + firstName +
                        "\nlastName: " + lastName +
                        "\nusername: " + username +
                        "\nemail: " + email +
                        "\nphoneNumber: " + phoneNumber + "\n"
                ;
    }
}
