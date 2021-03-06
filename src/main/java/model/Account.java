package model;

import controller.ServerMasterController.SQLConnector;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Account {
    private static String rememberMeUsername;
    private static String rememberMePassword;
    private String bio;
    private String firstName;
    private String lastName;
    private String username;
    private String accountId;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDate registerDay;
    private final boolean isAdmin;
    private String status;
    private final boolean isRobot = false;
    private String avatar;

    public static String getRememberMePassword() {
        return rememberMePassword;
    }

    public static String getRememberMeUsername() {
        return rememberMeUsername;
    }

    public static void setRememberMePassword(String rememberMePassword) {
        Account.rememberMePassword = rememberMePassword;
    }

    public static void setRememberMeUsername(String rememberMeUsername) {
        Account.rememberMeUsername = rememberMeUsername;
    }

    public Account(String botName, String username) {
        this.firstName = botName;
        this.username = username;
        this.isAdmin = false;
    }

    public Account(String firstName, String lastName, String accountName, String accountId, String password,
                   String email, String phoneNumber, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = accountName;
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        setImage("/images/blankProfile.jpg");
        registerDay = LocalDate.now();
        bio = "No bio is given.";
        this.isAdmin = isAdmin;
    }

    public Account(Map<String, Object> map) {
        this.firstName = (String) map.get("first_name");
        this.lastName = (String) map.get("last_name");
        this.username = (String) map.get("username");
        this.accountId = (String) map.get("player_id");
        this.password = (String) map.get("hash_password");
        this.email = (String) map.get("email_address");
        this.phoneNumber = (String) map.get("phone_number");
        setImage((String) map.get("avatar_address"));
        this.registerDay = LocalDate.parse((String) map.get("register_date"));
        this.bio = (String) map.get("bio");
        this.isAdmin = String.valueOf(map.get("is_admin")).equals("1");
        this.status = (String) map.get("status");
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

    public String getBio() {
        return bio;
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

    public int getDayOfRegister() {
        return (int) ChronoUnit.DAYS.between(registerDay, LocalDate.now());
    }

    public LocalDate getRegisterDay() {
        return registerDay;
    }

    public Image getImage() {
        return new Image(getImageURL());
    }

    public String getImageURL() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setBio(String bio) {
        this.bio = bio;
        editField("bio", bio);
    }

    public void setPassword(String password) {
        this.password = password;
        editField("hash_password", password);
    }

    public void setEmail(String email) {
        this.email = email;
        editField("email_address", email);

    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        editField("phone_number", phoneNumber);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        editField("first_name", firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        editField("last_name", lastName);
    }

    public void setUsername(String username) {
        this.username = username;
        editField("username", username);
    }

    public void setImage(String url) {
        this.avatar = saveImageToFile(new Image(url), this.accountId);
        editField("avatar_address", this.avatar);
    }

    public void setStatus(String status) {
        this.status = status;
        editField("status", this.status);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void editField(String field, String value) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("player_id", this.accountId);
        Map<String, Object> newValueMap = new HashMap<>();
        newValueMap.put(field, value);
        SQLConnector.updateTable(conditionMap, newValueMap, "players");
    }


    public static Admin getAdmin() {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put("is_admin", 1);
        List<Map<String, Object>> allAccounts =
                SQLConnector.selectFromDatabase(newMap, "players");
        if (allAccounts == null || allAccounts.isEmpty()) {
            System.out.println("[MODEL]: Admin entry couldn't be found");
            return null;
        }
        return new Admin(allAccounts.get(0));
    }

    private static Map<String, Object> SQLAccountSearch(String column, String value) {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put(column, value);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "players");
        if (thisAccount == null || thisAccount.isEmpty()) {
            System.out.println("[MODEL]: Account with " + column + " = " + value + " couldn't be found");
            return null;
        }
        return thisAccount.get(0);
    }

    public static Account getAccountByUsername(String username) {
        Map<String, Object> thisAccount = SQLAccountSearch("username", username);
        if (thisAccount == null || thisAccount.isEmpty()) {
            return null;
        }
        if (String.valueOf(thisAccount.get("is_admin")).equals("1")) {
            return new Admin(thisAccount);
        } else {
            return new Player(thisAccount);
        }
    }


    public static Account getAccountById(String id) {
        Map<String, Object> thisAccount = SQLAccountSearch("player_id", id);
        if (thisAccount == null || thisAccount.isEmpty()) {
            return null;
        }
        if (thisAccount.get("is_admin").equals(1)) {
            return new Admin(thisAccount);
        } else {
            return new Player(thisAccount);
        }
    }


    public static ArrayList<Player> getAllPlayers() {
        ArrayList<Player> result = new ArrayList<>();
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put("is_admin", 0);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "players");
        for (Map<String, Object> account : thisAccount)
            result.add(new Player(account));
        return result;
    }

    public static String saveImageToFile(Image image, String accountId) {

        File folder = new File("database\\accounts\\images");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File outputFile = new File("database\\accounts\\images\\" + accountId + ".jpg");

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
            return outputFile.toURI().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/*
    public static void openFileToImage(Account account) {
        File file = new File("database\\accounts\\images\\" + account.getAccountId() + ".jpg");
        account.setImage(file.toURI().toString());
    }*/

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
