package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.ServerMasterController.SQLConnector;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Server;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Account extends Application {
    protected static ArrayList<Account> allAccounts = new ArrayList<>();
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
    private boolean isRobot = false;
    private String avatar;

    public static Admin getAdmin() {
        for(Account account: allAccounts)
            if(account instanceof Admin)
                return (Admin)account;
            return null;
    }


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
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("shit happened here");
    }

    public static void main(String[] args) {
        Account test = new Player("Emad","Zing","Emad","12345","bye","Emad.z","434311",
                4.0);
        SQLConnector.insertInDatabase(test.SQLAccountToMap(),"players");
        System.out.println(test.getImageURL());
    }

    private Map<String,Object> SQLAccountToMap()
    {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("first_name",this.firstName);
        resultMap.put("last_name",this.lastName);
        resultMap.put("username",this.username);
        resultMap.put("hash_password",this.password);
        resultMap.put("email_address",this.email);
        resultMap.put("phone_number",this.phoneNumber);
        resultMap.put("register_date",this.registerDay);
        resultMap.put("avatar_address",this.avatar);
        resultMap.put("player_id",this.accountId);
        resultMap.put("bio",this.bio);
        resultMap.put("is_admin",this.isRobot);
        return resultMap;
    }


    private Map<String, List<Object>> SQLGetCurrentPlayer()
    {
        java.util.Map<String, Object> newMap = new HashMap<>();
        newMap.put("player_id", this.accountId);
        Map<String, List<Object>> thisAccount =
                SQLConnector.selectFromDatabase(newMap, "players");
        System.out.println(thisAccount);
        if(thisAccount == null || thisAccount.isEmpty())
        {
            System.err.println("Not a valid entry");
            return null;
        }
        return thisAccount;
    }

    public Image getImage() {
        return new Image(getImageURL());
        //File file = new File("database\\accounts\\images\\" + accountId + ".jpg");
        //return new Image(file.toURI().toString());
    }
    public  String getImageURL()
    {
        Map<String,List<Object>> accountData = this.SQLGetCurrentPlayer();
        if(accountData != null) {
            System.out.println(accountData);
            return (String) accountData.get("avatar_address").get(0);
        }
        return "";
        //return avatar;
    }


    public void setImage(String url) {
        this.avatar = saveImageToFile(new Image(url),this.accountId);
    }

    public Account(String botName, String username) {
        this.firstName = botName;
        this.username = username;
    }

    public int getDayOfRegister() {
        return (int) ChronoUnit.DAYS.between(registerDay, LocalDate.now());
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Account(String firstName, String lastName, String accountName, String accountId, String password, String email, String phoneNumber) {
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

    public static void openFileToImage(Account account) {
        File file = new File("database\\accounts\\images\\"+account.getAccountId()+".jpg");
        account.setImage(file.toURI().toString());
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
