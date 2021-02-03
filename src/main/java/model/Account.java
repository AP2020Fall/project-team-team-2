package model;

import controller.ServerMasterController.SQLConnector;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                   String email, String phoneNumber,boolean isAdmin) {
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

    public Account(Map<String,Object> map)
    {
        this.firstName = (String) map.get("first_name");
        this.lastName = (String) map.get("last_name");
        this.username = (String)map.get("last_name");
        this.accountId = (String) map.get("player_id");
        this.password = (String) map.get("hash_password");
        this.email = (String) map.get("email_address");
        this.phoneNumber = (String) map.get("phone_number");
        setImage((String) map.get("avatar_address"));
        this.registerDay = LocalDate.parse((String) map.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME);
        this.bio = (String) map.get("bio");
        this.isAdmin = String.valueOf(map.get("is_admin")).equals("1");
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
        //File file = new File("database\\accounts\\images\\" + accountId + ".jpg");
        //return new Image(file.toURI().toString());
    }

    public String getImageURL() {
       /* Map<String, List<Object>> accountData = this.SQLGetCurrentPlayer();
        if (accountData != null) {
            System.out.println(accountData);
            return (String) accountData.get("avatar_address").get(0);
        }
        return "";*/
        return avatar;
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



    private static Player createPlayer(Map<String, Object> player) {
        return new Player((String) player.get("first_name"), (String) player.get("last_name"),
                (String) player.get("username"), (String) player.get("player_id"),
                (String) player.get("hash_password"), (String) player.get("email_address"),
                (String) player.get("phone_number"), (Double) player.get("money"));
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
        System.out.println("fsewfewefeq");
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
        System.out.println(thisAccount);
        if (thisAccount == null || thisAccount.isEmpty()) {
            return null;
        }
        if (String.valueOf(thisAccount.get("is_admin")).equals("1")) {
            return new Admin(thisAccount);
        } else {
            return createPlayer(thisAccount);
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
            return createPlayer(thisAccount);
        }
    }

    public static ArrayList<Player> getAllPlayers() {
        ArrayList<Player> result = new ArrayList<>();
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put("is_admin", 0);
        List<Map<String, Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "players");
        for (Map<String, Object> account : thisAccount)
            result.add(createPlayer(account));
        return result;
    }

    /*public static ArrayList<Player> getAllPlayers() {
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
        System.out.println("saving ended " + player.getUsername());
        saveImageToFile(player.getImage(), player.getAccountId());
        System.out.println("saving image ended " + player.getUsername());
    }

    private static void saveAdmin(Account account) throws IOException {
        Admin admin = (Admin) account;
        String jsonAccount = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().toJson(account);
        FileWriter file = new FileWriter("database" + "\\" + "accounts" + "\\" + "admin" + "\\" + account.getUsername() + ".json");
        file.write(jsonAccount);
        file.close();
        System.out.println("saving ended admin");
        saveImageToFile(admin.getImage(), admin.getAccountId());
        System.out.println("saving image ended admin");

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
        Player player = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Player.class);
        openFileToImage((Account) player);
        System.out.println("pl open ended");
        return player;
    }

    private static Admin openAdmin(File file) throws FileNotFoundException {
        StringBuilder json = fileToString(file);
        Admin admin = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create().fromJson(json.toString(), Admin.class);
        openFileToImage((Account) admin);
        System.out.println("adm open ended");
        return admin;
    }

    private static StringBuilder fileToString(File file) throws FileNotFoundException {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) json.append(reader.nextLine());
        reader.close();
        return json;
    }*/

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
