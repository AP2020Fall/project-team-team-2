package model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.ServerMasterController.SQLConnector;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Admin extends Account {
    private final ArrayList<String> sentMessages;
    public Admin(String firstName, String lastName, String username, String accountId, String password, String email, String phoneNumber) {
        super(firstName, lastName, username, accountId, password, email, phoneNumber,true);
        sentMessages = new ArrayList<>();
    }

    public Admin(Map<String,Object> map)
    {
        super(map);
        System.out.println("i have been here");
        sentMessages = new Gson().fromJson((String) map.get("admin_messages"), new TypeToken<ArrayList<String>>() {}.getType());
        System.out.println("i have been here as well");
    }

    public static boolean isAdminExist() {
        return Account.getAdmin() != null;
    }

    public static void add(Admin admin) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("first_name", admin.getFirstName());
        resultMap.put("last_name", admin.getLastName());
        resultMap.put("username", admin.getUsername());
        resultMap.put("hash_password", admin.getPassword());
        resultMap.put("email_address", admin.getEmail());
        resultMap.put("phone_number", admin.getPhoneNumber());
        resultMap.put("register_date", admin.getRegisterDay().format(DateTimeFormatter.ISO_DATE_TIME));
        resultMap.put("avatar_address", admin.getImageURL());
        resultMap.put("player_id", admin.getAccountId());
        resultMap.put("bio", admin.getBio());
        resultMap.put("is_admin", admin.isAdmin() ? 1 : 0);
        resultMap.put("admin_messages",new Gson().toJson(admin.getSentMessages()));
        SQLConnector.insertInDatabase(resultMap,"players");
    }

    public ArrayList<Message> getSentMessages() {
        ArrayList<Message> result = new ArrayList<>();
        for(String messageId:sentMessages)
        {
            result.add(Message.getMessageById(messageId));
        }
        Collections.sort(result);
        return result;
    }

    public void addMessage(String messageId) {
        sentMessages.add(messageId);
        editField("admin_messages",new Gson().toJson(sentMessages));
    }
}
